package org.multibit.site;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;
import org.eclipse.jetty.server.session.SessionHandler;
import org.multibit.site.health.SiteHealthCheck;
import org.multibit.site.resources.PublicPageResource;

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

  /**
   * Main entry point to the application
   *
   * @param args CLI arguments
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    new SiteService().run(args);
  }

  private SiteService() {

  }

  @Override
  public void initialize(Bootstrap<SiteConfiguration> openIDDemoConfigurationBootstrap) {

    // Bundles
    openIDDemoConfigurationBootstrap.addBundle(new AssetsBundle("/assets/css", "/css"));
    openIDDemoConfigurationBootstrap.addBundle(new AssetsBundle("/assets/images", "/images"));
    openIDDemoConfigurationBootstrap.addBundle(new AssetsBundle("/assets/jquery", "/jquery"));
    openIDDemoConfigurationBootstrap.addBundle(new AssetsBundle("/views/html/en", "/en"));
    openIDDemoConfigurationBootstrap.addBundle(new ViewBundle());
  }

  @Override
  public void run(SiteConfiguration openIDDemoConfiguration, Environment environment) throws Exception {

    // Configure environment
    environment.scanPackagesForResourcesAndProviders(PublicPageResource.class);

    // Health checks
    environment.addHealthCheck(new SiteHealthCheck());

    // Providers
    environment.addProvider(new ViewMessageBodyWriter());

    // Session handler
    environment.setSessionHandler(new SessionHandler());

  }
}
