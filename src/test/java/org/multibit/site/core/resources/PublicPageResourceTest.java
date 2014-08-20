package org.multibit.site.core.resources;

import com.sun.jersey.api.client.ClientResponse;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;
import org.junit.Ignore;
import org.junit.Test;
import org.multibit.site.core.testing.BaseResourceTest;
import org.multibit.site.core.testing.MockHttpContextResponseInjectable;
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

  @Override
  protected void setUpResources() throws JAXBException {

    addProvider(ViewMessageBodyWriter.class);
    addProvider(MockHttpContextResponseInjectable.class);

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
  public void getDefaultHomepage() throws Exception {

    String actualResponse = configureAsClient("/")
      .get(String.class);

    assertThat(actualResponse).contains("MultiBit HD");

  }

  @Test
  public void getHomepage() throws Exception {

    String actualResponse = configureAsClient("/index.html")
      .get(String.class);

    assertThat(actualResponse).contains("MultiBit HD");

  }

  @Ignore
  public void getDefaultHomePageWithCookie() throws Exception {

    ClientResponse actualResponse = configureAsClient("/")
      .post(ClientResponse.class);

    assertThat(actualResponse.getCookies().size()).isEqualTo(1);
    assertThat(actualResponse.getCookies().get(0).toCookie().getName()).isEqualTo(BaseResource.COOKIE_NAME);

  }

}
