package org.multibit.site.core.resources;

import com.yammer.dropwizard.views.ViewMessageBodyWriter;
import org.junit.Test;
import org.multibit.site.core.testing.BaseResourceTest;
import org.multibit.site.resources.PublicHD0_1Resource;

import javax.ws.rs.core.MediaType;
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
public class PublicHD0_1ResourceTest extends BaseResourceTest {

  private final PublicHD0_1Resource testObject = new PublicHD0_1Resource();

  @Override
  protected void setUpResources() throws JAXBException {

    addProvider(ViewMessageBodyWriter.class);

    // Configure response

    // Configure resources
    addResource(testObject);

  }

  @Test
  public void getHelpImage() throws Exception {

    byte[] actualResponse = configureAsClient("/hd0.1/helpImages/dow-linux.png")
      .accept("image/png")
      .get(byte[].class);

    assertThat(actualResponse).isNotNull();

  }

  @Test
  public void getDefaultLanguageHelpContents() throws Exception {

    String actualResponse = configureAsClient("/hd0.1")
      .accept(MediaType.TEXT_HTML)
      .get(String.class);

    assertThat(actualResponse).contains("<head>");

  }

  @Test
  public void getDefaultLanguageHelpContents_GettingStarted() throws Exception {

    String actualResponse = configureAsClient("/hd0.1/getting-started.html")
      .accept(MediaType.TEXT_HTML)
      .get(String.class);

    assertThat(actualResponse).contains("Getting started");

  }

}
