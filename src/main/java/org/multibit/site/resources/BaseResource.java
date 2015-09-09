package org.multibit.site.resources;

import com.sun.jersey.api.core.HttpContext;
import org.multibit.site.core.languages.Languages;
import org.multibit.site.model.BaseModel;
import org.multibit.site.views.PublicFreemarkerView;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

/**
 * <p>Abstract base class to provide the following to subclasses:</p>
 * <ul>
 * <li>Provision of common methods</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public abstract class BaseResource {

  public static final String HSTS_HEADER_VALUE = "max-age=31536000; includeSubDomains";
  /**
   * The fallback language that should be fully available
   */
  protected static final String ENGLISH = "en";

  public static final String COOKIE_NAME = "MBHD-Session";

  private static final SecureRandom random = new SecureRandom();

  /**
   * Jersey creates a fresh resource every request so this is safe
   */
  @Context
  protected UriInfo uriInfo;

  /**
   * Jersey creates a fresh resource every request so this is safe
   */
  @Context
  protected HttpHeaders httpHeaders;

  /**
   * Jersey creates a fresh resource every request so this is safe
   */
  @Context
  protected HttpContext httpContext;

  /**
   * <p>Decorate the response with all standard headers</p>
   *
   * @param builder The response builder
   *
   * @return The decorated response builder with all standard headers in place
   */
  public static Response.ResponseBuilder applyHeaders(Response.ResponseBuilder builder) {

    return builder
      // CORS - Allow access to content from any domain
      .header(com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
      // HSTS - Supporting browsers should use HTTPS for all requests to this domain
      .header("Strict-Transport-Security", HSTS_HEADER_VALUE);

  }

  /**
   * @return The most appropriate locale for the upstream request (never null)
   */
  public Locale getLocale() {

    Locale defaultLocale = Locale.UK;

    if (httpHeaders == null) {
      return defaultLocale;
    } else {
      List<Locale> locales = httpHeaders.getAcceptableLanguages();
      if (locales == null || locales.isEmpty()) {
        return defaultLocale;
      }
      return Languages.newLocaleFromCode(
        locales.get(0).toString().replace("*", "en"),
        defaultLocale
      );
    }
  }

  public WebApplicationException badRequest() {
    return new WebApplicationException(Response.Status.BAD_REQUEST);
  }

  public WebApplicationException notFound() {
    return new WebApplicationException(Response.Status.NOT_FOUND);
  }

  /**
   * @return True if the session cookie exists indicating acceptance of the terms and conditions
   */
  protected boolean acceptedTandC() {

    return httpHeaders != null && httpHeaders.getCookies().containsKey(COOKIE_NAME);
  }

  protected Response pageResponse(BaseModel model, String templatePath) {
    return applyHeaders(Response
      .ok()
      .entity(new PublicFreemarkerView<BaseModel>(templatePath, model))
    ).build();
  }

  /**
   *
   * @return A random value according to the desired weighting to be used
   */
  protected int getBannerPrefix() {

    // Random between 0 and 1
    return random.nextInt(2);

  }
}
