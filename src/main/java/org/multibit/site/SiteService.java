package org.multibit.site;

import com.google.common.reflect.ClassPath;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;
import org.eclipse.jetty.server.session.SessionHandler;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.multibit.site.core.sitemap.SiteMap;
import org.multibit.site.core.sitemap.SiteUrl;
import org.multibit.site.health.SiteHealthCheck;
import org.multibit.site.resources.PublicPageResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXB;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * <p>Service to provide the following to application:</p>
 * <ul>
 * <li>Provision of access to resources</li>
 * </ul>
 * <p>Use <code>java -jar site-develop-SNAPSHOT.jar server site-config.yml</code> to start the demo</p>
 *
 * @since 0.0.1
 *         
 */
public class SiteService extends Service<SiteConfiguration> {

  private static final Logger log = LoggerFactory.getLogger(SiteService.class);

  /**
   * Main entry point to the application
   *
   * @param args CLI arguments
   *
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    new SiteService().run(args);
  }

  private SiteService() {

  }

  @Override
  public void initialize(Bootstrap<SiteConfiguration> bootstrap) {

    log.info("Initializing bundles...");

    // Bundles
    bootstrap.addBundle(new AssetsBundle("/assets/css", "/css"));
    bootstrap.addBundle(new AssetsBundle("/assets/images", "/images"));
    bootstrap.addBundle(new AssetsBundle("/assets/jquery", "/jquery"));
    bootstrap.addBundle(new ViewBundle());
  }

  @Override
  public void run(SiteConfiguration openIDDemoConfiguration, Environment environment) throws Exception {

    log.info("Scanning environment...");

    // Configure environment
    environment.scanPackagesForResourcesAndProviders(PublicPageResource.class);

    // Health checks
    environment.addHealthCheck(new SiteHealthCheck());

    // Providers
    environment.addProvider(new ViewMessageBodyWriter());

    // Session handler
    environment.setSessionHandler(new SessionHandler());

    log.info("Building sitemap.xml...");

    // Assume that all resources have been created fresh at start up (no other way to tell)
    DateTimeFormatter fmt = ISODateTimeFormat.date();
    String now = fmt.print(new DateTime().withZone(DateTimeZone.UTC));

    // Scan the classpath looking for HTML content
    SiteMap siteMap = new SiteMap();
    ClassPath classpath = ClassPath.from(PublicPageResource.class.getClassLoader());
    for (ClassPath.ResourceInfo resourceInfo : classpath.getResources()) {
      if (resourceInfo.getResourceName().endsWith(".html")) {

        String loc = resourceInfo.getResourceName().replace("views/html/", "http://localhost:8080/");
        if (loc.startsWith("http")) {
          SiteUrl url = new SiteUrl(loc, now);
          siteMap.getUrlset().add(url);
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
    JAXB.marshal(siteMap, result);

    InMemoryAssetCache.INSTANCE.put("/views/sitemap.xml", result.toString());

    log.info("Done");

  }
}
