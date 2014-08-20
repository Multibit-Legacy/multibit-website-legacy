package org.multibit.site.auth.cookie;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * <p>Injectable to provide the following to {@link CookieClientRestrictedToProvider}:</p>
 * <ul>
 * <li>Performs decode from HTTP request cookie</li>
 * <li>Carries cookie authentication data</li>
 * </ul>
 *
 * @since 0.0.4
 */
class CookieClientRestrictedToInjectable<T> extends AbstractHttpContextInjectable<T> {

  private static final Logger log = LoggerFactory.getLogger(CookieClientRestrictedToInjectable.class);

  private final Authenticator<CookieClientCredentials, T> authenticator;
  private final String sessionTokenName;

  /**
   * @param authenticator    The Authenticator that will compare credentials
   * @param sessionTokenName The session token cookie name (short lived with full access)
   */
  CookieClientRestrictedToInjectable(
    Authenticator<CookieClientCredentials, T> authenticator,
    String sessionTokenName) {
    this.authenticator = authenticator;
    this.sessionTokenName = sessionTokenName;

  }

  public Authenticator<CookieClientCredentials, T> getAuthenticator() {
    return authenticator;
  }

  public String getSessionTokenName() {
    return sessionTokenName;
  }

  @Override
  public T getValue(HttpContext httpContext) {

    Map<String, Cookie> cookies = httpContext.getRequest().getCookies();

    try {

      Optional<UUID> sessionToken = Optional.absent();

      // Configure the UUIDs based on cookie values (must be valid UUIDs)
      Cookie sessionTokenCookie = cookies.get(sessionTokenName);
      if (sessionTokenCookie != null) {
        sessionToken = Optional.of(UUID.fromString(sessionTokenCookie.getValue()));
      }

      // Build the credentials (authenticator will handle absence)
      final CookieClientCredentials credentials = new CookieClientCredentials(
        sessionToken,
        true);

      // Authenticate
      final Optional<T> result = authenticator.authenticate(credentials);
      if (result.isPresent()) {
        return result.get();
      }

    } catch (IllegalArgumentException e) {
      log.warn("Error decoding credentials (not a UUID)", e);
    } catch (IllegalStateException e) {
      log.warn("Error decoding credentials (no session token)", e);
    } catch (AuthenticationException e) {
      log.warn("Error authenticating credentials", e);
      throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
    }

    // Must have failed to be here
    // Simply throw a 401 and leave it to the app to handle
    throw new WebApplicationException(Response.Status.UNAUTHORIZED);
  }

}

