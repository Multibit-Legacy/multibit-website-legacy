package org.multibit.site.auth.cookie;

import com.google.common.base.Optional;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Value object to provide the following to cookie authenticator:</p>
 * <ul>
 * <li>Storage of the necessary credentials for cookie authentication</li>
 * </ul>
 * <p>A set of user-provided cookie authentication credentials, consisting of a session token and "remember me" token.
 * </p>
 *
 * @since 0.0.4
 */
public class CookieClientCredentials {

  /**
   * The sessionToken is a UUID that is only valid for the length of the session
   * and is obtained after a successful authentication
   * It may be absent during an authentication for the public role
   */
  private final Optional<UUID> sessionToken;

  private final boolean isPublic;

  /**
   * @param sessionToken        The session token (expires when browser tab is closed)
   * @param isPublic            True if the authentication can be made purely at the client side
   */
  public CookieClientCredentials(
    Optional<UUID> sessionToken,
    boolean isPublic
  ) {
    this.sessionToken = checkNotNull(sessionToken);
    this.isPublic = isPublic;
  }

  /**
   * @return The temporary session token that authenticates this user
   */
  public Optional<UUID> getSessionToken() {
    return sessionToken;
  }

  /**
   * @return True if the authentication can be made purely at the client side (anonymous for session duration)
   */
  public boolean isPublic() {
    return isPublic;
  }

  @Override
  public String toString() {
    return "CookieClientCredentials{" +
      "sessionToken=" + sessionToken +
      ", isPublic=" + isPublic +
      '}';
  }
}
