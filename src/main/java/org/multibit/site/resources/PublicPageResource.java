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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.*;

/**
 * <p>Resource to provide the following to application:</p>
 * <ul>
 * <li>Provision of all static HTML pages</li>
 * </ul>
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
   * @return The default index page for the main site
   */
  @GET
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  public PublicFreemarkerView<BaseModel> getDefaultHomePage() {

    BaseModel model = new BaseModel("/" + DEFAULT_LANGUAGE + "/index.html");
    return new PublicFreemarkerView<BaseModel>("content/home.ftl", model);

  }

  /**
   * @return The index page for the main site (requires a specific entry point)
   */
  @GET
  @Path("index.html")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
  public PublicFreemarkerView<BaseModel> getHomePage() {

    return getDefaultHomePage();

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
  public PublicFreemarkerView<BaseModel> getDefaultPage(
    @PathParam("page") String page
  ) {

    BaseModel model = new BaseModel("/" + DEFAULT_LANGUAGE + "/" + page + ".html");
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
  public PublicFreemarkerView<BaseModel> getLanguageSpecificDefaultHomePage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang
  ) {

    BaseModel model = new BaseModel("/" + lang + "/index.html");
    return new PublicFreemarkerView<BaseModel>("content/home.ftl", model);

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
  public PublicFreemarkerView<BaseModel> getLanguageSpecificPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("page") String page
  ) {

    BaseModel model = new BaseModel("/" + lang + "/" + page + ".html");
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
  public PublicFreemarkerView<BaseModel> getDefaultBlogPage(
    @Digits(integer = 2, fraction = 0) @PathParam("year") String year,
    @Digits(integer = 2, fraction = 0) @PathParam("month") String month,
    @Digits(integer = 2, fraction = 0) @PathParam("day") String day,
    @PathParam("page") String page
  ) {

    return getLanguageSpecificBlogPage(DEFAULT_LANGUAGE, year, month, day, page);

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
  public PublicFreemarkerView<BaseModel> getLanguageSpecificBlogPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @Digits(integer = 2, fraction = 0) @PathParam("year") String year,
    @Digits(integer = 2, fraction = 0) @PathParam("month") String month,
    @Digits(integer = 2, fraction = 0) @PathParam("day") String day,
    @PathParam("page") String page
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/blog/" + year + "-" + month + "-" + day + "-" + page + ".html";

    BaseModel model = new BaseModel(resourcePath);
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
  public PublicFreemarkerView<BaseModel> getDefaultHelpPage() {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + DEFAULT_LANGUAGE + "/help.html";

    // Use the main template since this is a starting point for a user
    BaseModel model = new BaseModel(resourcePath);
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
  public PublicFreemarkerView<BaseModel> getDefaultLanguageSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/help.html";

    BaseModel model = new BaseModel(resourcePath);
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
  public PublicFreemarkerView<BaseModel> getDefaultLanguageVersionSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("version") String version
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/help/" + version + "/help_contents.html";

    BaseModel model = new BaseModel(resourcePath);
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
  public PublicFreemarkerView<BaseModel> getLanguageVersionSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("version") String version,
    @PathParam("pathParam") String pathParam
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/help/" + version + "/" + pathParam;

    BaseModel model = new BaseModel(resourcePath);
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

}
