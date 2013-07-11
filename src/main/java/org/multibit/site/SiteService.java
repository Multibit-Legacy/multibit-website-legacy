package org.multibit.site;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;
import org.eclipse.jetty.server.session.SessionHandler;
import org.multibit.site.core.sitemap.SiteMapBuilder;
import org.multibit.site.health.SiteHealthCheck;
import org.multibit.site.resources.PublicPageResource;
import org.multibit.site.servlets.SafeLocaleFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    // Filters
    environment.addFilter(new SafeLocaleFilter(), "/*");

    // Session handler
    environment.setSessionHandler(new SessionHandler());

    // Build the site map
    new SiteMapBuilder().build();

  }

}
