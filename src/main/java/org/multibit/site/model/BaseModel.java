package org.multibit.site.model;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.multibit.site.core.languages.Languages;
import org.multibit.site.utils.StreamUtils;

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

  /**
   * This is a read only collection of localised string templates keyed first on supported locales then on active element
   */
  private static final Map<String, Map<String, String>> localisedNavbars = Maps.newConcurrentMap();

  static {
    localisedNavbars.put(Locale.ENGLISH.getLanguage(), localiseNavBar(Locale.ENGLISH));
    localisedNavbars.put(Locale.JAPANESE.getLanguage(), localiseNavBar(Locale.JAPANESE));
    localisedNavbars.put(Locale.FRENCH.getLanguage(), localiseNavBar(Locale.FRENCH));
    localisedNavbars.put(Locale.SIMPLIFIED_CHINESE.getLanguage(), localiseNavBar(Locale.SIMPLIFIED_CHINESE));
    localisedNavbars.put(new Locale("es").getLanguage(), localiseNavBar(new Locale("es")));
    localisedNavbars.put(new Locale("ru").getLanguage(), localiseNavBar(new Locale("ru")));
  }

  /**
   * This is a read only collection of banner strings
   */
  private static final Map<String, String> banners = Maps.newConcurrentMap();

  static {
    // TREZOR
    banners.put("0.large-title", "TREZOR for $99 promo");
    banners.put("0.medium-title", "TREZOR for $99 promo");
    banners.put("0.text-title", "TREZOR&nbsp;$99&nbsp;Code: multibit.org");
    banners.put("0.large-url", "https://buytrezor.com/?a=multibit.org");
    banners.put("0.medium-url", "https://buytrezor.com/?a=multibit.org");
    banners.put("0.text-url", "TREZOR for $99 promo");
    banners.put("0.large-image-path", "/images/banner/trezor-99-banner-512x72.png");
    banners.put("0.medium-image-path", "/images/banner/trezor-99-banner-280x72.png");

    // KeepKey
    banners.put("1.large-title", "KeepKey: Your Private Bitcoin Vault");
    banners.put("1.medium-title", "KeepKey: Your Private Bitcoin Vault");
    banners.put("1.text-title", "KeepKey:&nbsp;Your&nbsp;Private&nbsp;Bitcoin&nbsp;Vault");
    banners.put("1.large-url", "https://padlock.link/ulpj");
    banners.put("1.medium-url", "https://padlock.link/u0f7");
    banners.put("1.text-url", "https://padlock.link/wgrt");
    banners.put("1.large-image-path", "/images/banner/keepkey-invite-banner-512x72.png");
    banners.put("1.medium-image-path", "/images/banner/keepkey-invite-banner-280x72.png");
  }

  // Request scope variables

  private final Locale locale;
  private final boolean acceptedTandC;
  private final int bannerPrefix;

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

  private List<String> messages = Lists.newArrayList();

  /**
   * @param resourcePath  The resource path for the HTML content
   * @param acceptedTandC True if the terms and conditions have been accepted
   * @param locale        The request locale
   * @param bannerPrefix  The prefix of the banner to show for this page impression (0, 1, etc)
   */
  public BaseModel(String resourcePath, boolean acceptedTandC, Locale locale, int bannerPrefix) {

    this.locale = locale;
    this.acceptedTandC = acceptedTandC;
    this.bannerPrefix = bannerPrefix;

    // Check for a fully-formed view
    if (resourcePath == null) {
      return;
    }

    Preconditions.checkArgument(resourcePath.contains("/" + locale.getLanguage() + "/"));

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
        // Read the HTML fragment
        content = StreamUtils.toString(is, Charsets.UTF_8);

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
   * @param key The advert key (e.g. "large-title")
   *
   * @return The string (can be HTML) associated with the given
   */
  public String advert(String key) {
    return banners.get(bannerPrefix + "." + key);
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
  public List<String> getMessages() {
    return messages;
  }

  /**
   * @return The localised navbar for this request
   */
  public String getNavBar() {

    final Map<String, String> localisedNavbar;
    if (localisedNavbars.containsKey(locale.getLanguage())) {
      localisedNavbar = localisedNavbars.get(locale.getLanguage());
    } else {
      localisedNavbar = localisedNavbars.get(Locale.ENGLISH.getLanguage());
    }

    return localisedNavbar.get(navbar == null ? "default" : navbar);

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
  private static Map<String, String> localiseNavBar(Locale locale) {

    String template = Languages.safeText("navbar.download", locale) + "\n"
      + Languages.safeText("navbar.faq", locale) + "\n"
      + Languages.safeText("navbar.community", locale) + "\n"
      + Languages.safeText("navbar.blog", locale) + "\n"
      + Languages.safeText("navbar.help", locale) + "\n";

    Map<String, String> localisedNavbar = Maps.newConcurrentMap();

    localisedNavbar.put("download", template
        .replace("[active1]", "class=\"active\"")
        .replace("[active2]", "")
        .replace("[active3]", "")
        .replace("[active4]", "")
        .replace("[active5]", "")
    );

    localisedNavbar.put("faq", template
        .replace("[active1]", "")
        .replace("[active2]", "class=\"active\"")
        .replace("[active3]", "")
        .replace("[active4]", "")
        .replace("[active5]", "")
    );

    localisedNavbar.put("community", template
        .replace("[active1]", "")
        .replace("[active2]", "")
        .replace("[active3]", "class=\"active\"")
        .replace("[active4]", "")
        .replace("[active5]", "")
    );

    localisedNavbar.put("blog", template
        .replace("[active1]", "")
        .replace("[active2]", "")
        .replace("[active3]", "")
        .replace("[active4]", "class=\"active\"")
        .replace("[active5]", "")
    );

    localisedNavbar.put("help", template
        .replace("[active1]", "")
        .replace("[active2]", "")
        .replace("[active3]", "")
        .replace("[active4]", "")
        .replace("[active5]", "class=\"active\"")
    );

    localisedNavbar.put("default", template
        .replace("[active1]", "")
        .replace("[active2]", "")
        .replace("[active3]", "")
        .replace("[active4", "")
        .replace("[active5]", "")
    );

    return localisedNavbar;
  }

}
