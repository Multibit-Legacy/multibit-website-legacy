package org.multibit.site.core.resources;

import com.sun.jersey.api.client.ClientResponse;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.multibit.site.SiteService;
import org.multibit.site.core.testing.BaseResourceTest;
import org.multibit.site.resources.BaseResource;
import org.multibit.site.resources.PublicPageResource;

import javax.xml.bind.JAXBException;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * <p>Resource to provide the following to application:</p>
 * <ul>
 * <li>Provision of bare help from v0.1.x of the MultiBit HD client</li>
 * </ul>
 *
 * @since 4.0.0
 *         
 */
public class PublicPageResourceTest extends BaseResourceTest {

  private final PublicPageResource testObject = new PublicPageResource();

  @BeforeClass
  public static void setUp() {

    new SiteService().configureFeeds();

  }

  @Override
  protected void setUpResources() throws JAXBException {

    addProvider(ViewMessageBodyWriter.class);

    // Configure response

    // Configure resources
    addResource(testObject);

  }

  @Test
  public void viewFavicon() throws Exception {

    ClientResponse actualResponse = configureAsClient("/favicon.ico")
      .get(ClientResponse.class);

    assertThat(actualResponse.getEntity(byte[].class)).isNotNull();

    // Assert headers
    assertThat(actualResponse.getHeaders().get("Strict-Transport-Security").get(0)).isEqualTo(BaseResource.HSTS_HEADER_VALUE);
  }

  @Test
  public void viewRobots() throws Exception {

    ClientResponse actualResponse = configureAsClient("/robots.txt")
      .get(ClientResponse.class);

    assertThat(actualResponse.getEntity(String.class)).contains("Sitemap");

    // Assert headers
    assertThat(actualResponse.getHeaders().get("Strict-Transport-Security").get(0)).isEqualTo(BaseResource.HSTS_HEADER_VALUE);
  }

  @Test
  public void viewSiteMap() throws Exception {

    ClientResponse actualResponse = configureAsClient("/sitemap.xml")
      .get(ClientResponse.class);

    assertThat(actualResponse.getEntity(String.class)).contains("urlset");

    // Assert headers
    assertThat(actualResponse.getHeaders().get("Strict-Transport-Security").get(0)).isEqualTo(BaseResource.HSTS_HEADER_VALUE);
  }

  @Test
  public void viewAtomFeed() throws Exception {

    ClientResponse actualResponse = configureAsClient("/atom.xml")
      .get(ClientResponse.class);

    assertThat(actualResponse.getEntity(String.class)).contains("MultiBit Blog");

    // Assert headers
    assertThat(actualResponse.getHeaders().get("Strict-Transport-Security").get(0)).isEqualTo(BaseResource.HSTS_HEADER_VALUE);

  }

  @Test
  public void viewLocalisedIndexPage() throws Exception {

    ClientResponse actualResponse = configureAsClient("/")
      .get(ClientResponse.class);

    assertThat(actualResponse.getStatus()).isEqualTo(200);
    assertThat(actualResponse.getEntity(String.class)).contains("Global");

    // Assert headers
    assertThat(actualResponse.getHeaders().get("Strict-Transport-Security").get(0)).isEqualTo(BaseResource.HSTS_HEADER_VALUE);
  }

  @Test
  public void viewIndexPage() throws Exception {

    ClientResponse actualResponse = configureAsClient("/index.html")
      .get(ClientResponse.class);

    assertThat(actualResponse.getStatus()).isEqualTo(200);
    assertThat(actualResponse.getEntity(String.class)).contains("Global");

    // Assert headers
    assertThat(actualResponse.getHeaders().get("Strict-Transport-Security").get(0)).isEqualTo(BaseResource.HSTS_HEADER_VALUE);
  }

  @Test
  public void viewLocalisedDownloadPage() throws Exception {

    ClientResponse actualResponse = configureAsClient("/download.html")
      .get(ClientResponse.class);

    assertThat(actualResponse.getStatus()).isEqualTo(200);
    assertThat(actualResponse.getEntity(String.class)).contains("0.0.1");

    // Assert headers
    assertThat(actualResponse.getHeaders().get("Strict-Transport-Security").get(0)).isEqualTo(BaseResource.HSTS_HEADER_VALUE);
  }

}
