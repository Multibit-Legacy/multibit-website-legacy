package org.multibit.site.model;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.multibit.site.caches.InMemoryAssetCache;
import org.multibit.site.core.languages.Languages;
import org.multibit.site.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <p>Base class to provide the following to views:</p>
 * <ul>
 * <li>Access to Markdown content</li>
 *
 * @since 0.0.1
 *  
 */
public class BaseModel {

  private static final Logger log = LoggerFactory.getLogger(BaseModel.class);

  /**
   * This is a read only collection of string templates
   */
  private static final Map<String, String> navbars = Maps.newConcurrentMap();

  static {
    initialiseNavBar();
  }

  // Request scope variables

  private final Locale locale;
  private final boolean acceptedTandC;

  /**
   * The HTML content of the page
   */
  private String content;

  /**
   * The navbar key
   */
  private String navbar = "default";

  /**
   * Default is to not show download buttons
   */
  private boolean showDownload = false;

  /**
   * Default is to use /index.html as T&C acceptance endpoint
   */
  private String acceptAction = "/index.html";

  private List<String> errors = Lists.newArrayList();
  private List<String> messages = Lists.newArrayList();

  /**
   * @param resourcePath  The resource path for the HTML content
   * @param acceptedTandC True if the terms and conditions have been accepted
   * @param locale        The request locale
   */
  public BaseModel(String resourcePath, boolean acceptedTandC, Locale locale) {

    this.locale = locale;
    this.acceptedTandC = acceptedTandC;

    // Check for a fully-formed view
    if (resourcePath == null) {
      return;
    }

    // Determine the navbar status
    if (resourcePath.contains("download")) {
      navbar = "download";
    }
    if (resourcePath.contains("faq")) {
      navbar = "faq";
    }
    if (resourcePath.contains("community")) {
      navbar = "community";
    }
    if (resourcePath.contains("blog")) {
      navbar = "blog";
    }
    if (resourcePath.contains("help")) {
      navbar = "help";
    }

    // Check the asset cache
    Optional<String> contentOptional = InMemoryAssetCache.INSTANCE.getByResourcePath(resourcePath);
    if (contentOptional.isPresent()) {
      content = contentOptional.get();
      return;
    }

    // Must be dealing with a fresh view to be here

    // Only asset type supported is HTML
    if (resourcePath.endsWith(".html")) {
      // Attempt a load
      InputStream is = BaseModel.class.getResourceAsStream("/views/html" + resourcePath);
      if (is == null) {

        // Check for English locale
        if (!resourcePath.startsWith("/en/")) {
          // Could be a missing translation so fall back to English
          is = BaseModel.class.getResourceAsStream("/views/html/en/" + resourcePath.substring(4));
        }

        if (is == null) {
          // Really failed now
          throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

      }

      try {
        // Read the HTML fragment and cache it for later
        content = StreamUtils.toString(is, Charsets.UTF_8);
        InMemoryAssetCache.INSTANCE.put(resourcePath, content);

        return;
      } catch (IOException e) {
        throw new WebApplicationException(e, Response.Status.NOT_FOUND);
      }

    }

    // Must have failed to be here
    throw new WebApplicationException(Response.Status.NOT_FOUND);

  }


  /**
   * @return Localised text for the request locale
   */
  public String msg(String key) {
    return Languages.safeText(key, locale);
  }

  /**
   * @return The HTML content (as read during initialization)
   */
  public String getContent() {
    return content;
  }

  /**
   * @return A list of errors that should be displayed prominently
   */
  public List<String> getErrors() {
    return errors;
  }

  /**
   * @return A list of errors that should be displayed prominently
   */
  public List<String> getMessages() {
    return messages;
  }

  /**
   * @return The navbar for this request
   */
  public String getNavBar() {

    log.debug("NavBar= '{}', return = '{}'", navbar, navbars.get(navbar));

    return navbars.get(navbar == null ? "default" : navbar);

  }

  /**
   * @return True if the terms and conditions have been accepted
   */
  public boolean isAcceptedTandC() {
    return acceptedTandC;
  }

  /**
   * @return True if the download buttons should be shown
   */
  public boolean isShowDownload() {
    return showDownload;
  }

  public void setShowDownload(boolean showDownload) {
    this.showDownload = showDownload;
  }

  /**
   * @return The relative path of the accept terms and conditions action
   */
  public String getAcceptAction() {
    return acceptAction;
  }

  public void setAcceptAction(String acceptAction) {
    this.acceptAction = acceptAction;
  }

  /**
   * @return The initialised navbar map (one-off initialisation)
   */
  private static Map<String, String> initialiseNavBar() {

    String template =
      "<li {active1}><a href=\"/download.html\" title=\"Download latest and previous versions\">Download</a></li>\n"
        + "<li {active2}><a href=\"/faq.html\" title=\"Frequently asked questions\">FAQ</a></li>\n"
        + "<li {active3}><a href=\"/community.html\" title=\"The Bitcoin community at a glance\">Community</a></li>\n"
        + "<li {active4}><a href=\"/blog.html\"  title=\"Blog posts\">Blog</a></li>\n"
        + "<li {active5}><a href=\"/help.html\" title=\"Help with this site and our software\">Help</a></li>\n";

    navbars.put("download", template
      .replace("{active1}", "class=\"active\"")
      .replace("{active2}", "")
      .replace("{active3}", "")
      .replace("{active4}", "")
      .replace("{active5}", "")
    );

    navbars.put("faq", template
      .replace("{active1}", "")
      .replace("{active2}", "class=\"active\"")
      .replace("{active3}", "")
      .replace("{active4}", "")
      .replace("{active5}", "")
    );

    navbars.put("community", template
      .replace("{active1}", "")
      .replace("{active2}", "")
      .replace("{active3}", "class=\"active\"")
      .replace("{active4}", "")
      .replace("{active5}", "")
    );

    navbars.put("blog", template
      .replace("{active1}", "")
      .replace("{active2}", "")
      .replace("{active3}", "")
      .replace("{active4}", "class=\"active\"")
      .replace("{active5}", "")
    );

    navbars.put("help", template
      .replace("{active1}", "")
      .replace("{active2}", "")
      .replace("{active3}", "")
      .replace("{active4}", "")
      .replace("{active5}", "class=\"active\"")
    );

    navbars.put("default", template
      .replace("{active1}", "")
      .replace("{active2}", "")
      .replace("{active3}", "")
      .replace("{active4", "")
      .replace("{active5}", "")
    );

    return navbars;
  }

}
