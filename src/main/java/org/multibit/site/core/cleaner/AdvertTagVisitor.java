package org.multibit.site.core.cleaner;

import org.htmlcleaner.HtmlNode;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagNodeVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * <p>Visitor to provide the following to third party HTML:</p>
 * <ul>
 * <li>Converts relative URLs to known absolute</li>
 * <li>Detects relative URLs and converts to known absolute</li>
 * <li>Detects tags that should not be present</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class AdvertTagVisitor implements TagNodeVisitor {
  
  private static final Logger log = LoggerFactory.getLogger(AdvertTagVisitor.class);

  /**
   * Only these tags will be permitted
   */
  private static final String allowedTags = "html head body link div a b i span ";

  /**
   * The advert server host (provided by the AdvertLoader)
   */
  private final String advertServerHost;

  /**
   * @param advertServerHost The advert server host (e.g. "https://karma-ads.com")
   */
  public AdvertTagVisitor(String advertServerHost) {
    this.advertServerHost = advertServerHost;
  }

  public boolean visit(TagNode tagNode, HtmlNode htmlNode) {

    if (htmlNode instanceof TagNode) {
      TagNode tag = (TagNode) htmlNode;

      checkForAllowedTags(tag);

      makeHrefAbsolute(tag);

    }

    // Continue traversing the DOM tree
    return true;
  }

  /**
   * Detects if the node is on the allowed list
   *
   * @param tag The tag node
   */
  private void checkForAllowedTags(TagNode tag) {
    if (!allowedTags.contains(tag.getName()+" ")) {
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }
  }

  /**
   * Modifies the href attribute from relative to absolute
   *
   * @param tag The tag node
   */
  private void makeHrefAbsolute(TagNode tag) {

    // Make HREF attributes absolute from expected relative
    String href = tag.getAttributeByName("href");
    String name = tag.getName();

    if (href == null) {
      return;
    }

    if (!href.startsWith("/")) {
      // Absolute URL detected - trigger failsafe
      log.error("Advert server presented absolute link: '{}' in tag '{}'. Switched to failsafe.",href, name);
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    } else {
      if ("link".equalsIgnoreCase(name)) {
        href="/ka"+href;
      } else {
        // Change to advert server direct link
        href = advertServerHost + href;
      }
      tag.getAttributes().put("href", href);
    }

  }
}
