package org.multibit.site.core.sitemap;

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
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

/**
 * <p>Site map builder</p>
 * <p>Scans the classpath and adds any .html files to the list. The result is available
 * under <a href="http://localhost:8080/sitemap.xml">/sitemap.xml</a></p>
 */
public class SiteMapBuilder {

  private static final Logger log = LoggerFactory.getLogger(SiteService.class);

  /**
   * Build a simple site map XML
   *
   * @param host The host (e.g. "https://multibit.org")
   *
   * @throws java.io.IOException If something goes wrong
   */
  public static SiteMap build(String host) throws IOException {

    log.info("Building sitemap.xml...");

    // Assume that all resources have been created fresh at start up (no other way to tell)
    DateTimeFormatter fmt = ISODateTimeFormat.date();
    String now = fmt.print(new DateTime().withZone(DateTimeZone.UTC));

    // Scan the classpath looking for HTML content
    SiteMap siteMap = new SiteMap();
    ClassPath classpath = ClassPath.from(PublicPageResource.class.getClassLoader());
    for (ClassPath.ResourceInfo resourceInfo : classpath.getResources()) {
      if (resourceInfo.getResourceName().endsWith(".html")) {

        String loc = resourceInfo.getResourceName().replace("views/html/", host + "/");
        if (loc.startsWith("http")) {
          SiteUrl url = new SiteUrl(loc, now);
          siteMap.getUrlset().add(url);
        }

      }
    }

    return siteMap;

  }

  /**
   * @param siteMap The site map to cache
   */
  public static void cache(SiteMap siteMap) {

    // Marshal
    Result result = new StreamResult(new StringWriter()) {

      /** Returns the written XML as a string. */
      public String toString() {
        return getWriter().toString();
      }

    };
    JAXB.marshal(siteMap, result);

    // Store this artifact for the long term
    InMemoryArtifactCache.INSTANCE.put(InMemoryArtifactCache.SITE_MAP_KEY, result.toString());

  }

  /**
   * @param siteMap The site map to write the the local file system under version control
   */
  public static void writeToFile(SiteMap siteMap) throws IOException {

    // Marshal
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream("src/main/resources/views/sitemap.xml");
      Result result = new StreamResult(fos);
      JAXB.marshal(siteMap, result);
    } finally {
      if (fos != null) {
        fos.close();
      }
    }

  }

}