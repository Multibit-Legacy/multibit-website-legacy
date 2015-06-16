package org.multibit.site.core.atom;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.common.reflect.ClassPath;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.multibit.site.SiteService;
import org.multibit.site.caches.InMemoryArtifactCache;
import org.multibit.site.resources.PublicPageResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXB;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * <p>Atom feed builder</p>
 * <p>Scans the classpath and adds any blog .html files as entries. The result is available
 * under <a href="http://localhost:8080/atom.xml">/atom.xml</a></p>
 */
public class AtomFeedBuilder {

  private static final Logger log = LoggerFactory.getLogger(SiteService.class);

  private static final DateTimeFormatter blogFormat = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.UTC);

  public AtomFeedBuilder() {
  }

  /**
   * Build a simple Atom XML
   *
   * @param host The host name (e.g. "https://multibit.org")
   *
   * @throws java.io.IOException If something goes wrong
   */
  public static AtomFeed build(String host) throws IOException, URISyntaxException {

    log.info("Building atom.xml...");

    // Keep track of the current time for updates
    DateTime now = new DateTime(DateTimeZone.UTC);

    final AtomFeed atomFeed;

    // Read the existing feed (if present)
    InputStream is = AtomFeedBuilder.class.getResourceAsStream("/views/atom.xml");
    if (is != null) {
      // Existing feed so build from that
      Source source = new StreamSource(is);
      atomFeed = JAXB.unmarshal(source, AtomFeed.class);
    } else {
      // New feed so create it
      UUID feedId = UUID.randomUUID();
      atomFeed = new AtomFeed("urn:uuid:" + feedId.toString(), "MultiBit Blog", now);
      atomFeed.setAuthor("MultiBit Team");
    }

    // Update the overall feed timestamp since we are rebuilding it
    atomFeed.setUpdated(now);

    Collections.sort(atomFeed.getAtomEntries(), Collections.reverseOrder(new AtomEntryComparator()));

    // Create a map of existing entries based on their URLs (not ids)
    Map<String, AtomEntry> existingEntries = Maps.newHashMap();
    for (AtomEntry atomEntry : atomFeed.getAtomEntries()) {
      existingEntries.put(atomEntry.getLink().getHref(), atomEntry);
    }

    // Examine the classpath for new/updated entries
    ClassPath classpath = ClassPath.from(PublicPageResource.class.getClassLoader());
    for (ClassPath.ResourceInfo resourceInfo : classpath.getResources()) {

      String resourceName = resourceInfo.getResourceName();
      if (resourceName.contains("/blog/") && !resourceName.contains("png")) {

        // Load the resource as a String
        URL resourceUrl = Resources.getResource(resourceName);
        File entryFile = new File(resourceUrl.toURI());

        // Get the content
        String entryHtml = Resources.toString(resourceUrl, Charsets.UTF_8);

        // Extract a suitable URL converting the dates from yyyy-mm-dd to yyyy/mm/dd to match existing paths
        String entryHref = resourceInfo.getResourceName().replace("views/html/", host + "/");
        entryHref = entryHref.replaceFirst("-", "/").replaceFirst("-", "/").replaceFirst("-", "/");

        // Extract the title
        int hStartPos = entryHtml.indexOf("<h");
        int hEndPos = entryHtml.indexOf("</h");
        String title = "";
        if (hStartPos >= 0 && hEndPos > hStartPos) {
          title = entryHtml.substring(hStartPos, hEndPos + 5);
        }
        // Extract the summary
        int pStartPos = entryHtml.indexOf("<p");
        int pEndPos = entryHtml.indexOf("</p");
        String summary = "";
        if (pStartPos >= 0 && pEndPos > pStartPos) {
          summary = entryHtml.substring(pStartPos, pEndPos + 3)
          + "<br/>"
          + "<p>Read the rest on the <a target='_blank' href='"
          + entryHref
          + "'>MultiBit blog</a>.</p>";
        }

        // Check if the URL has been used before
        final AtomEntry atomEntry;
        boolean changed = false;
        if (existingEntries.containsKey(entryHref)) {

          log.debug("Existing: '{}'", entryHref);

          // Existing entry
          atomEntry = existingEntries.get(entryHref);

          // Check for changes in title only to avoid formatting issues between machines etc
          if (!atomEntry.getTitle().equals(title)) {
            log.info("Updating: '{}'", entryHref);
            changed = true;
          } else {
            atomEntry.setTitle(title);

          }
        } else {

          log.debug("New: '{}'", entryHref);

          String entryId = "urn:uuid:" + UUID.randomUUID();
          AtomLink link = new AtomLink("self", entryHref);

          // Infer the updated time based on the article name
          DateTime updated;
          try {
            updated = blogFormat.parseDateTime(entryFile.getName().substring(0, 10));
            updated = updated.plusHours(12);
          } catch (IllegalArgumentException e) {
            updated = now;
          }
          atomEntry = new AtomEntry(entryId, title, link, updated, summary);

          atomFeed.getAtomEntries().add(atomEntry);

        }

        if (changed) {
          atomEntry.setTitle(title);
          atomEntry.setSummary(summary);
          atomEntry.setUpdated(now);
        }

      }
    }

    return atomFeed;

  }

  /**
   * @param atomFeed The Atom feed
   */
  public static void cache(AtomFeed atomFeed) {

    // Marshal
    Result result = new StreamResult(new StringWriter()) {

      /** Returns the written XML as a string. */
      public String toString() {
        return getWriter().toString();
      }

    };
    JAXB.marshal(atomFeed, result);

    // Store this artifact for the long term
    InMemoryArtifactCache.INSTANCE.put(InMemoryArtifactCache.ATOM_FEED_KEY, result.toString());

  }

  /**
   * @param atomFeed The atom feed to write the the local file system under version control
   */
  public static void writeToFile(AtomFeed atomFeed) throws IOException {

    // Marshal
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream("src/main/resources/views/atom.xml");
      Result result = new StreamResult(fos);
      JAXB.marshal(atomFeed, result);
    } finally {
      if (fos != null) {
        fos.close();
      }
    }

  }

}