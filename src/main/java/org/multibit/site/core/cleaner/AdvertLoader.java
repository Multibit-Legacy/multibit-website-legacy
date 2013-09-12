package org.multibit.site.core.cleaner;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * <p>[Pattern] to provide the following to {@link Object}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class AdvertLoader {

  /**
   * The advert server (e.g. KarmaAds)
   */
  private static final String ADVERT_SERVER_HOST = "https://karma-ads.com";

  /**
   * The private key to the advert server (provided as an environment variable on the server)
   */
  private static final String ADVERT_SERVER_KEY = System.getenv("ADVERT_SERVER_KEY");

  /**
   * The advert server
   */
  private static final URI advertServer = URI.create(ADVERT_SERVER_HOST + "/service/ad/"+ ADVERT_SERVER_KEY);

  private final CleanerProperties cleanerProperties;

  public AdvertLoader() {

    // Configure a strict set of cleaner properties
    cleanerProperties = new CleanerProperties();
    cleanerProperties.setTranslateSpecialEntities(true);
    cleanerProperties.setTransResCharsToNCR(true);
    cleanerProperties.setOmitComments(true);
    cleanerProperties.setOmitXmlDeclaration(true);
    cleanerProperties.setOmitDeprecatedTags(true);
    cleanerProperties.setOmitUnknownTags(true);
    cleanerProperties.setAllowHtmlInsideAttributes(false);
  }

  /**
   * @return A byte[] consisting of the serialized output of the cleaned HTML
   */
  public byte[] loadAndClean() {

    try {

      // Clean a fresh advert from the advertising network
      TagNode node = new HtmlCleaner(cleanerProperties).clean(advertServer.toURL());

      // Make some adjustments
      node.traverse(new AdvertTagVisitor(ADVERT_SERVER_HOST));

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      new PrettyXmlSerializer(cleanerProperties).writeToStream(node, out);
      return out.toByteArray();

    } catch (IOException e) {
      // Rethrow
      throw new WebApplicationException(e,Response.Status.BAD_REQUEST);
    }

  }
}
