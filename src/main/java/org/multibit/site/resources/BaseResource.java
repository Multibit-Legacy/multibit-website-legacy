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

  protected static final String DEFAULT_LANGUAGE = "en";

  public static final String COOKIE_NAME = "MBHD-Session";

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
      return Languages.newLocaleFromCode(locales.get(0).toString());
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
    return Response
      .ok()
      .entity(new PublicFreemarkerView<BaseModel>(templatePath, model))
      .header(com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
      .build();
  }
}
