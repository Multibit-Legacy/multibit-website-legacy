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

    byte[] actualResponse = configureAsClient("/favicon.ico")
      .get(byte[].class);

    assertThat(actualResponse).isNotNull();

  }

  @Test
  public void viewRobots() throws Exception {

    String actualResponse = configureAsClient("/robots.txt")
      .get(String.class);

    assertThat(actualResponse).contains("Sitemap");

  }

  @Test
  public void viewSiteMap() throws Exception {

    String actualResponse = configureAsClient("/sitemap.xml")
      .get(String.class);

    assertThat(actualResponse).contains("urlset");

  }

  @Test
  public void viewAtomFeed() throws Exception {

    String actualResponse = configureAsClient("/atom.xml")
      .get(String.class);

    assertThat(actualResponse).contains("MultiBit Blog");

  }

  @Test
  public void viewDefaultIndexPage() throws Exception {

    String actualResponse = configureAsClient("/")
      .get(String.class);

    assertThat(actualResponse).contains("International");

  }

  @Test
  public void viewDefaultIndexPage_Accepted() throws Exception {

    ClientResponse actualResponse = configureAsClient("/")
      .post(ClientResponse.class);

    assertThat(actualResponse.getEntity(String.class)).contains("International");
    assertThat(actualResponse.getCookies().size()).isEqualTo(1);
    assertThat(actualResponse.getCookies().get(0).toCookie().getName()).isEqualTo(BaseResource.COOKIE_NAME);

  }

  @Test
  public void viewIndexPage() throws Exception {

    ClientResponse actualResponse = configureAsClient("/index.html")
      .get(ClientResponse.class);

    assertThat(actualResponse.getEntity(String.class)).contains("International");
    assertThat(actualResponse.getCookies().size()).isEqualTo(0);

  }

  @Test
  public void viewIndexPage_Accepted() throws Exception {

    ClientResponse actualResponse = configureAsClient("/index.html")
      .post(ClientResponse.class);

    assertThat(actualResponse.getEntity(String.class)).contains("International");
    assertThat(actualResponse.getCookies().size()).isEqualTo(1);
    assertThat(actualResponse.getCookies().get(0).toCookie().getName()).isEqualTo(BaseResource.COOKIE_NAME);

  }

  @Test
  public void viewDownloadPage() throws Exception {

    ClientResponse actualResponse = configureAsClient("/download.html")
      .get(ClientResponse.class);

    assertThat(actualResponse.getEntity(String.class)).contains("0.0.1");
    assertThat(actualResponse.getCookies().size()).isEqualTo(0);

  }

  @Test
  public void viewDownloadPage_Accepted() throws Exception {

    ClientResponse actualResponse = configureAsClient("/download.html")
      .post(ClientResponse.class);

    assertThat(actualResponse.getEntity(String.class)).contains("0.0.1");
    assertThat(actualResponse.getCookies().size()).isEqualTo(1);
    assertThat(actualResponse.getCookies().get(0).toCookie().getName()).isEqualTo(BaseResource.COOKIE_NAME);

  }

}
