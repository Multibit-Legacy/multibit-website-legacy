package org.multibit.site.auth;

import java.util.UUID;

/**
 * <p>DTO to provide the following to Resources:</p>
 * <ul>
 * <li>Provision of state for a single User as viewed by the Customer</li>
 * </ul>
 * <p>This is used when the client needs to authenticate the user, and when proxying
 * requests upstream to MBM.</p>
 *
 * @since 0.0.1
 *         
 */
public class UserDto {

  /**
   * A shared secret between this client the user's browser that is revoked when the session ends
   */
  private final UUID sessionToken;

  public UserDto(UUID sessionToken) {
    this.sessionToken = sessionToken;
  }

  /**
   * @return The session key
   */
  public UUID getSessionToken() {
    return sessionToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserDto userDto = (UserDto) o;

    if (!sessionToken.equals(userDto.sessionToken)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return sessionToken.hashCode();
  }
}
