package org.multibit.site.core.atom;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.common.reflect.ClassPath;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

/**
 * <p>Atom feed builder</p>
 * <p>Scans the classpath and adds any blog .html files as entries. The result is available
 * under <a href="http://localhost:8080/atom.xml">/atom.xml</a></p>
 */
public class AtomFeedBuilder {

  private static final Logger log = LoggerFactory.getLogger(SiteService.class);

  public AtomFeedBuilder() {
  }

  /**
   * Build a simple Atom XML
   *
   * @throws java.io.IOException If something goes wrong
   */
  public void build() throws IOException, URISyntaxException {

    log.info("Building atom.xml...");

    // Attempt to infer the blog creation dates from their timestamps
    DateTimeFormatter fmt = ISODateTimeFormat.date();
    String now = fmt.print(new DateTime().withZone(DateTimeZone.UTC));

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
      atomFeed = new AtomFeed("urn:uuid:" + feedId.toString(), "MultiBit Blog", fmt.parseDateTime(now));
    }

    // Create a map of existing entries based on their URLs (not ids)
    Map<String, AtomEntry> existingEntries = Maps.newHashMap();
    for (AtomEntry atomEntry : atomFeed.getAtomEntries()) {
      existingEntries.put(atomEntry.getLink().getHref(), atomEntry);
    }

    // Examine the classpath for new/updated entries
    ClassPath classpath = ClassPath.from(PublicPageResource.class.getClassLoader());
    for (ClassPath.ResourceInfo resourceInfo : classpath.getResources()) {

      String resourceName = resourceInfo.getResourceName();
      if (resourceName.contains("/blog/")) {

        // Load the resource as a String
        URL resourceUrl = Resources.getResource(resourceName);

        // Get the file timestamp
        File entryFile = new File(resourceUrl.toURI());
        DateTime updated = new DateTime(entryFile.lastModified());

        // Get the content
        String entryHtml = Resources.toString(resourceUrl, Charsets.UTF_8);

        // Build the basis for the Atom entry
        String entryHref = resourceInfo.getResourceName().replace("views/html/", "http://localhost:8080/");
        int hStartPos = entryHtml.indexOf("<h");
        int hEndPos = entryHtml.indexOf("</h");
        String title = entryHtml.substring(hStartPos + 4, hEndPos);

        int pStartPos = entryHtml.indexOf("<p");
        int pEndPos = entryHtml.indexOf("</p");
        String summary = entryHtml.substring(pStartPos + 3, pEndPos);

        // Check if the URL has been used before
        final AtomEntry atomEntry;
        boolean changed = false;
        if (existingEntries.containsKey(entryHref)) {

          // Existing entry
          atomEntry = existingEntries.get(entryHref);

          // Check for changes in title, summary etc
          if (!atomEntry.getTitle().equals(title)) {
            changed = true;
          } else if (!atomEntry.getSummary().equals(summary)) {
            changed = true;
          } else if (!atomEntry.getUpdated().equals(updated)) {
            changed = true;
          }
        } else {

          // New entry so use the updated time
          String entryId = "urn:uuid:" + UUID.randomUUID();

          AtomLink link = new AtomLink("self", entryHref);
          atomEntry = new AtomEntry(entryId, title, link, updated, summary);

          atomFeed.getAtomEntries().add(atomEntry);

        }

        if (changed) {
          atomEntry.setTitle(title);
          atomEntry.setSummary(summary);
          atomEntry.setUpdated(updated);
        }

      }
    }

    // Marshal
    Result result = new StreamResult(new StringWriter()) {

      /** Returns the written XML as a string. */
      public String toString() {
        return getWriter().toString();
      }

    };
    JAXB.marshal(atomFeed, result);

    System.out.println(result.toString());

    // Store this artifact for the long term
    InMemoryArtifactCache.INSTANCE.put(InMemoryArtifactCache.ATOM_FEED_KEY, result.toString());

    log.info("Done");
  }
}