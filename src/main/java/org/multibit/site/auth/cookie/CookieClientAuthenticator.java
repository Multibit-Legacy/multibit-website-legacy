package org.multibit.site.auth.cookie;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import org.multibit.site.auth.UserDto;
import org.multibit.site.caches.InMemorySessionTokenCache;

import java.util.UUID;

/**
 * <p>Authenticator to provide the following to application:</p>
 * <ul>
 * <li>Verifies the provided credentials are valid</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class CookieClientAuthenticator implements Authenticator<CookieClientCredentials, UserDto> {

  @Override
  public Optional<UserDto> authenticate(CookieClientCredentials credentials) throws AuthenticationException {

    // Determine if the user is known by their session key
    Optional<UserDto> user =
      InMemorySessionTokenCache
        .INSTANCE
        .getBySessionToken(credentials.getSessionToken());

    if (!user.isPresent()) {

      // We create an anonymous user for this session
      UserDto anonymousUser = new UserDto(UUID.randomUUID());

      // Keep a copy in the session cache
      InMemorySessionTokenCache
        .INSTANCE
        .put(anonymousUser.getSessionToken(), anonymousUser);

      return Optional.of(anonymousUser);

    }

    // Must be OK to be here
    return user;

  }

}
