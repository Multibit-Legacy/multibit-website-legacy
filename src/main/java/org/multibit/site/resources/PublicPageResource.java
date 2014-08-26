package org.multibit.site.resources;

import com.google.common.base.Optional;
import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import org.multibit.site.caches.InMemoryArtifactCache;
import org.multibit.site.core.cleaner.AdvertLoader;
import org.multibit.site.model.BaseModel;
import org.multibit.site.views.PublicFreemarkerView;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;
import java.util.concurrent.*;

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
 * GET     /ad (org.multibit.site.resources.PublicPageResource)
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
  private static final String FAILSAFE = "http://localhost:8080/ka/failsafe.html";

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

    return Response
      .ok(is)
      .type("image/png")
      .build();
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

    return Response.ok(is).build();
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

    return Response
      .ok(siteMap.get())
      .type(MediaType.TEXT_XML)
      .build();
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

    return Response
      .ok(atomFeed.get())
      .type(MediaType.APPLICATION_ATOM_XML)
      .build();
  }

  /**
   * @return The advert HTML suitable for inclusion in an iframe
   */
  @GET
  @Path("/ad")
  @Timed
  @CacheControl(noCache = true)
  public Response viewAdvert() {

    ExecutorService service = Executors.newSingleThreadExecutor();

    Future<Response> response = service.submit(new Callable<Response>() {

      @Override
      public Response call() throws Exception {
        try {
          byte[] advert = new AdvertLoader().loadAndClean();

          // Seems OK so serve it
          return Response
            .ok(advert)
            .type(MediaType.TEXT_HTML)
            .build();

        } catch (Throwable e) {
          // Any problem gets the fail safe response
          return failSafeResponse();
        }
      }
    });

    try {
      return response.get(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      return failSafeResponse();
    } catch (ExecutionException e) {
      return failSafeResponse();
    } catch (TimeoutException e) {
      return failSafeResponse();
    }

  }

  /**
   * No cache to ensure cookie status is correctly updated
   *
   * @return The default index page for the main site with no cookie
   */
  @GET
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(noCache = true)
  public PublicFreemarkerView<BaseModel> viewDefaultIndexPage() {

    BaseModel model = new BaseModel("/" + DEFAULT_LANGUAGE + "/index.html", acceptedTandC());
    model.setShowDownload(true);
    model.setAcceptAction("/index.html");
    return new PublicFreemarkerView<BaseModel>("content/main.ftl", model);

  }

  /**
   * @return The default index page for the main site with acceptance of terms and conditions
   */
  @POST
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewDefaultIndexPageWithCookie() {

    return getResponseWithCookie("/index.html");

  }

  /**
   * No cache to ensure cookie status is correctly updated
   *
   * @return The index page for the main site (requires a specific entry point)
   */
  @GET
  @Path("index.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(noCache = true)
  public PublicFreemarkerView<BaseModel> viewIndexPage() {

    return viewDefaultIndexPage();

  }

  /**
   * @return The index page offers terms and conditions
   */
  @POST
  @Path("index.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewIndexPageWithCookie() {

    return viewDefaultIndexPageWithCookie();

  }

  /**
   * No cache to ensure cookie status is correctly updated
   *
   * @return The download page for the main site (requires a specific entry point)
   */
  @GET
  @Path("download.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(noCache = true)
  public PublicFreemarkerView<BaseModel> viewDownloadPage() {

    BaseModel model = new BaseModel("/" + DEFAULT_LANGUAGE + "/download.html", acceptedTandC());
    model.setShowDownload(true);
    model.setAcceptAction("/download.html");
    return new PublicFreemarkerView<BaseModel>("content/main.ftl", model);

  }

  /**
   * @return The download page offers terms and conditions
   */
  @POST
  @Path("download.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public Response viewDownloadPageWithCookie() {

    return getResponseWithCookie("/download.html");

  }

  /**
   * @param page The page name (or slug)
   *
   * @return The default language page for the main site
   */
  @GET
  @Path("{page}.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public PublicFreemarkerView<BaseModel> viewDefaultPage(
    @PathParam("page") String page
  ) {

    BaseModel model = new BaseModel("/" + DEFAULT_LANGUAGE + "/" + page + ".html", false);
    return new PublicFreemarkerView<BaseModel>("content/main.ftl", model);

  }

  /**
   * @param lang The two letter language code (ISO 639-1)
   *
   * @return The language specific index page for the main site
   */
  @GET
  @Path("{lang}")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public PublicFreemarkerView<BaseModel> viewLanguageSpecificDefaultHomePage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang
  ) {

    BaseModel model = new BaseModel("/" + lang + "/index.html", acceptedTandC());
    return new PublicFreemarkerView<BaseModel>("content/main.ftl", model);

  }

  /**
   * @param lang The two letter language code (ISO 639-1)
   * @param page The page name (or slug)
   *
   * @return The language specific page for the main site
   */
  @GET
  @Path("{lang}/{page}.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public PublicFreemarkerView<BaseModel> viewLanguageSpecificPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("page") String page
  ) {

    BaseModel model = new BaseModel("/" + lang + "/" + page + ".html", acceptedTandC());
    return new PublicFreemarkerView<BaseModel>("content/main.ftl", model);

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
  public PublicFreemarkerView<BaseModel> viewDefaultBlogPage(
    @Digits(integer = 2, fraction = 0) @PathParam("year") String year,
    @Digits(integer = 2, fraction = 0) @PathParam("month") String month,
    @Digits(integer = 2, fraction = 0) @PathParam("day") String day,
    @PathParam("page") String page
  ) {

    return viewLanguageSpecificBlogPage(DEFAULT_LANGUAGE, year, month, day, page);

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
  public PublicFreemarkerView<BaseModel> viewLanguageSpecificBlogPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @Digits(integer = 2, fraction = 0) @PathParam("year") String year,
    @Digits(integer = 2, fraction = 0) @PathParam("month") String month,
    @Digits(integer = 2, fraction = 0) @PathParam("day") String day,
    @PathParam("page") String page
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/blog/" + year + "-" + month + "-" + day + "-" + page + ".html";

    BaseModel model = new BaseModel(resourcePath, acceptedTandC());
    return new PublicFreemarkerView<BaseModel>("content/blog.ftl", model);

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
  public PublicFreemarkerView<BaseModel> viewDefaultHelpPage() {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + DEFAULT_LANGUAGE + "/help.html";

    // Use the main template since this is a starting point for a user
    BaseModel model = new BaseModel(resourcePath, acceptedTandC());
    return new PublicFreemarkerView<BaseModel>("content/main.ftl", model);

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
  public PublicFreemarkerView<BaseModel> viewDefaultLanguageSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/help.html";

    BaseModel model = new BaseModel(resourcePath, acceptedTandC());
    return new PublicFreemarkerView<BaseModel>("content/help.ftl", model);

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
  public PublicFreemarkerView<BaseModel> viewDefaultLanguageVersionSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("version") String version
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/help/" + version + "/help_contents.html";

    BaseModel model = new BaseModel(resourcePath, acceptedTandC());
    return new PublicFreemarkerView<BaseModel>("content/help.ftl", model);

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
  public PublicFreemarkerView<BaseModel> viewLanguageVersionSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("version") String version,
    @PathParam("pathParam") String pathParam
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/help/" + version + "/" + pathParam;

    BaseModel model = new BaseModel(resourcePath, acceptedTandC());
    return new PublicFreemarkerView<BaseModel>("content/help.ftl", model);

  }

  /**
   * <p>Provide the fail safe response</p>
   *
   * @return A 307 to be recorded in the server logs triggering an action by administrators
   */
  private Response failSafeResponse() {
    return Response
      .temporaryRedirect(URI.create(FAILSAFE))
      .type(MediaType.TEXT_HTML)
      .build();
  }

  /**
   * @param resourcePath The resource path (e.g. "/index.html")
   *
   * @return A response containing a session cookie
   */
  private Response getResponseWithCookie(String resourcePath) {

    NewCookie cookie = new NewCookie(COOKIE_NAME, UUID.randomUUID().toString(), "/", null, null, 30 * 60, true);

    // Accepted by virtue of the POST
    BaseModel model = new BaseModel("/" + DEFAULT_LANGUAGE + resourcePath, true);
    model.setShowDownload(true);
    model.setAcceptAction(resourcePath);
    PublicFreemarkerView<BaseModel> entity = new PublicFreemarkerView<BaseModel>("content/main.ftl", model);

    return Response.ok().entity(entity).cookie(cookie).build();
  }

}
