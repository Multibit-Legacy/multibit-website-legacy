package org.multibit.site.resources;

import com.google.common.base.Optional;
import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import org.multibit.site.caches.InMemoryArtifactCache;
import org.multibit.site.core.banners.Banners;
import org.multibit.site.model.BaseModel;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * <p>Resource to provide the following to application:</p>
 * <ul>
 * <li>Provision of all static HTML pages</li>
 * </ul>
 * <p>The main structure is as follows:</p>
 * <pre>
 *
 * GET     /v0.4 (org.multibit.site.resources.PublicClientVersion0_4Resource)
 * GET     /v0.4/helpImages/{image} (org.multibit.site.resources.PublicClientVersion0_4Resource)
 * GET     /v0.4/{lang}/{pathParam: (?).*} (org.multibit.site.resources.PublicClientVersion0_4Resource)
 * GET     /v0.4/{page}.html (org.multibit.site.resources.PublicClientVersion0_4Resource)
 * GET     /v0.5 (org.multibit.site.resources.PublicClientVersion0_5Resource)
 * GET     /v0.5/helpImages/{image} (org.multibit.site.resources.PublicClientVersion0_5Resource)
 * GET     /v0.5/{lang}/{pathParam: (?).*} (org.multibit.site.resources.PublicClientVersion0_5Resource)
 * GET     /v0.5/{page}.html (org.multibit.site.resources.PublicClientVersion0_5Resource)
 * GET     /error/401 (org.multibit.site.resources.PublicErrorResource)
 * GET     /error/404 (org.multibit.site.resources.PublicErrorResource)
 * GET     /error/500 (org.multibit.site.resources.PublicErrorResource)
 * GET     / (org.multibit.site.resources.PublicPageResource)
 * GET     /{lang}/blog/{year}/{month}/{day}/{page}.html (org.multibit.site.resources.PublicPageResource)
 * GET     /atom.xml (org.multibit.site.resources.PublicPageResource)
 * GET     /blog/{year}/{month}/{day}/{page}.html (org.multibit.site.resources.PublicPageResource)
 * GET     /favicon.ico (org.multibit.site.resources.PublicPageResource)
 * GET     /help (org.multibit.site.resources.PublicPageResource)
 * GET     /index.html (org.multibit.site.resources.PublicPageResource)
 * GET     /robots.txt (org.multibit.site.resources.PublicPageResource)
 * GET     /sitemap.xml (org.multibit.site.resources.PublicPageResource)
 * GET     /{lang} (org.multibit.site.resources.PublicPageResource)
 * GET     /{lang}/help (org.multibit.site.resources.PublicPageResource)
 * GET     /{lang}/help/{version} (org.multibit.site.resources.PublicPageResource)
 * GET     /{lang}/help/{version}/{pathParam: (?).*} (org.multibit.site.resources.PublicPageResource)
 * GET     /{lang}/{page}.html (org.multibit.site.resources.PublicPageResource)
 * GET     /{page}.html (org.multibit.site.resources.PublicPageResource)
 *
 * </pre>
 *
 * @since 3.0.0
 *  
 */
@Path("/")
public class PublicPageResource extends BaseResource {

  /**
   * The failsafe HTML to ensure continued correct presentation
   */
  private static final String FAILSAFE = "/ka/failsafe.html";

  /**
   * Provide the favicon
   *
   * @return A favicon image from the assets
   */
  @GET
  @Path("favicon.ico")
  @Timed
  @CacheControl(maxAge = 24, maxAgeUnit = TimeUnit.HOURS)
  public Response viewFavicon() {

    InputStream is = PublicPageResource.class.getResourceAsStream("/assets/images/favicon.ico");

    return applyHeaders(Response
        .ok(is)
        .type("image/png")
    ).build();
  }

  /**
   * @return The /robots.txt file
   */
  @GET
  @Path("robots.txt")
  @Timed
  @CacheControl(maxAge = 24, maxAgeUnit = TimeUnit.HOURS)
  @Produces(MediaType.TEXT_PLAIN)
  public Response viewRobots() {

    InputStream is = PublicPageResource.class.getResourceAsStream("/views/robots.txt");

    return applyHeaders(Response
        .ok(is)
    ).build();
  }

  /**
   * @return The /sitemap.xml file
   */
  @GET
  @Path("sitemap.xml")
  @Timed
  @CacheControl(maxAge = 24, maxAgeUnit = TimeUnit.HOURS)
  public Response viewSitemap() throws IOException {

    // Pull this from the long-lived artifact cache
    Optional<String> siteMap = InMemoryArtifactCache.INSTANCE.getByResourcePath(InMemoryArtifactCache.SITE_MAP_KEY);
    if (!siteMap.isPresent()) {
      throw notFound();
    }

    return applyHeaders(Response
        .ok(siteMap.get())
        .type(MediaType.TEXT_XML)
    ).build();
  }

  /**
   * @return The /atom.xml file
   */
  @GET
  @Path("atom.xml")
  @Timed
  @CacheControl(maxAge = 24, maxAgeUnit = TimeUnit.HOURS)
  public Response viewAtomFeed() throws IOException {

    // Pull this from the long-lived artifact cache
    Optional<String> atomFeed = InMemoryArtifactCache.INSTANCE.getByResourcePath(InMemoryArtifactCache.ATOM_FEED_KEY);
    if (!atomFeed.isPresent()) {
      throw notFound();
    }

    return applyHeaders(Response
        .ok(atomFeed.get())
        .type(MediaType.APPLICATION_ATOM_XML)
    ).build();
  }

  /**
   * <p>Provide the redirect for the given source and banner ID</p>
   *
   * @param rawSlug     The slug (e.g. "trezor", "keep-key")
   * @param rawBannerId The banner Id (e.g. "0", "1")
   * @param rawSource   The source (e.g. "small", "medium", "large")
   *
   * @return A redirect response based on
   */
  @GET
  @Path("/goto/{slug}/{bannerId}/{source}")
  @Timed
  @CacheControl(maxAge = 24, maxAgeUnit = TimeUnit.HOURS)
  public Response bannerRedirect(@PathParam("slug") String rawSlug,
                                 @PathParam("bannerId") int rawBannerId,
                                 @PathParam("source") String rawSource) {

    // All validation performed in Banners
    URI uri = Banners.createRedirectUri(rawBannerId, rawSource);

    return Response.temporaryRedirect(uri).build();

  }

  /**
   * @return The default index page for the main site
   */
  @GET
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(noCache = true)
  public Response viewLocalisedIndexPage() {

    BaseModel model = new BaseModel("/" + getLocale().getLanguage() + "/index.html", getLocale(), bannerId);
    model.setShowDownload(true);

    return pageResponse(model, "content/main.ftl");

  }

  /**
   * @return The index page for the main site (requires a specific entry point)
   */
  @GET
  @Path("index.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(noCache = true)
  public Response viewIndexPage() {

    return viewLocalisedIndexPage();

  }

  /**
   * @return The download page for the main site (requires a specific entry point)
   */
  @GET
  @Path("download.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(noCache = true)
  public Response viewLocalisedDownloadPage() {

    BaseModel model = new BaseModel("/" + getLocale().getLanguage() + "/download.html", getLocale(), bannerId);
    model.setShowDownload(true);

    return pageResponse(model, "content/main.ftl");

  }

  /**
   * @param page The page name (or slug)
   *
   * @return The localised page for the main site
   */
  @GET
  @Path("{page}.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewLocalisedPage(
    @PathParam("page") String page
  ) {

    BaseModel model = new BaseModel("/" + getLocale().getLanguage() + "/" + page + ".html", getLocale(), bannerId);

    return pageResponse(model, "content/main.ftl");

  }

  /**
   * @param lang The two letter language code (ISO 639-1)
   *
   * @return The language specific index page for the main site overriding the locale
   */
  @GET
  @Path("{lang}")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewLanguageSpecificDefaultHomePage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang
  ) {

    BaseModel model = new BaseModel("/" + lang + "/index.html", new Locale(lang), bannerId);
    model.setShowDownload(true);

    return pageResponse(model, "content/main.ftl");

  }

  /**
   * @param lang The two letter language code (ISO 639-1)
   * @param page The page name (or slug)
   *
   * @return The language specific page for the main site overriding the locale
   */
  @GET
  @Path("{lang}/{page}.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewLanguageSpecificPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("page") String page
  ) {

    BaseModel model = new BaseModel("/" + lang + "/" + page + ".html", new Locale(lang), bannerId);

    if ("index".equalsIgnoreCase(page)) {
      model.setShowDownload(true);
    }

    return pageResponse(model, "content/main.ftl");
  }

  /**
   * Provide the default language blog page
   *
   * @param year  A four digit year specifier
   * @param month A two digit month specifier (1-based)
   * @param day   A two digit day specifier (1-based)
   * @param page  The page name (or slug)
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Path("blog/{year}/{month}/{day}/{page}.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewDefaultBlogPage(
    @Digits(integer = 2, fraction = 0) @PathParam("year") String year,
    @Digits(integer = 2, fraction = 0) @PathParam("month") String month,
    @Digits(integer = 2, fraction = 0) @PathParam("day") String day,
    @PathParam("page") String page
  ) {

    return viewLanguageSpecificBlogPage(ENGLISH, year, month, day, page);

  }

  /**
   * Provide a language specific blog page
   *
   * @param lang  The two letter language code (ISO 639-1)
   * @param year  A four digit year specifier
   * @param month A two digit month specifier (1-based)
   * @param day   A two digit day specifier (1-based)
   * @param page  The page name (or slug)
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Path("/{lang}/blog/{year}/{month}/{day}/{page}.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewLanguageSpecificBlogPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @Digits(integer = 2, fraction = 0) @PathParam("year") String year,
    @Digits(integer = 2, fraction = 0) @PathParam("month") String month,
    @Digits(integer = 2, fraction = 0) @PathParam("day") String day,
    @PathParam("page") String page
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/blog/" + year + "-" + month + "-" + day + "-" + page + ".html";

    BaseModel model = new BaseModel(resourcePath, new Locale(lang), bannerId);

    return pageResponse(model, "content/blog.ftl");

  }

  /**
   * Provide the default language help page
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Path("help")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewDefaultHelpPage() {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + ENGLISH + "/help.html";

    // Use the main template since this is a starting point for a user
    BaseModel model = new BaseModel(resourcePath, Locale.ENGLISH, bannerId);

    return pageResponse(model, "content/main.ftl");

  }

  /**
   * Provide the language specific help page
   *
   * This is normally a list of all available versions in the required language
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Path("{lang}/help")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewDefaultLanguageSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/help.html";

    BaseModel model = new BaseModel(resourcePath, new Locale(lang), bannerId);

    return pageResponse(model, "content/main.ftl");


  }

  /**
   * Provide the default language and version specific help page
   *
   * This is normally a list of all help for the given version in the given language
   *
   * @param lang    The two letter language code (ISO 639-1)
   * @param version The version number (e.g. v0.4, v0.5.3 etc)
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Path("{lang}/help/{version}")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewDefaultLanguageVersionSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("version") String version
  ) {

    String resourcePath;
    if (version.contains("hd")) {
      resourcePath = "/" + lang + "/help/" + version + "/help_contents.html";
      BaseModel model = new BaseModel(resourcePath, new Locale(lang), bannerId);
      return pageResponse(model, "content/hd-help.ftl");
    }

    // Must be classic
    resourcePath = "/" + lang + "/help/" + version + "/contents.html";
    BaseModel model = new BaseModel(resourcePath, new Locale(lang), bannerId);

    return pageResponse(model, "content/mbc-help.ftl");

  }

  /**
   * Provide a language and version specific help page
   *
   * @param lang      The two letter language code (ISO 639-1)
   * @param version   The version number (e.g. v0.4, v0.5.3 etc)
   * @param pathParam The path parameters leading to the required resource
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Path("{lang}/help/{version}/{pathParam: (?).*}")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewLanguageVersionSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("version") String version,
    @PathParam("pathParam") String pathParam
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/help/" + version + "/" + pathParam;

    BaseModel model = new BaseModel(resourcePath, new Locale(lang), bannerId);

    return version.contains("hd") ? pageResponse(model, "content/hd-help.ftl") : pageResponse(model, "content/mbc-help.ftl");

  }

}
