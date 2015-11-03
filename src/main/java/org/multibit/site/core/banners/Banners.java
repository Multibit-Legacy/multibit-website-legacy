package org.multibit.site.core.banners;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;

import java.net.URI;
import java.security.SecureRandom;
import java.util.Map;

/**
 * <p>Enum singleton to provide the following to application:</p>
 * <ul>
 * <li>Provision of all banner images and links</li>
 * </ul>
 */
public enum Banners {

  INSTANCE;

  private static final SecureRandom random = new SecureRandom();
  private static final Range<Integer> trezorRange = Range.closed(0, 199);
  private static final Range<Integer> keepKeyRange = Range.closed(200, 999);

  /**
   * This is a read only collection of banner strings
   */
  private static final Map<String, String> banners = Maps.newConcurrentMap();

  static {

    // TREZOR
    banners.put("0.id", "0");
    banners.put("0.slug", "trezor");
    banners.put("0.large-title", "TREZOR for $99 promo");
    banners.put("0.medium-title", "TREZOR for $99 promo");
    banners.put("0.small-title", "TREZOR&nbsp;$99&nbsp;Code: multibit.org");
    banners.put("0.large-url", "https://buytrezor.com/?a=multibit.org");
    banners.put("0.medium-url", "https://buytrezor.com/?a=multibit.org");
    banners.put("0.small-url", "TREZOR for $99 promo");
    banners.put("0.large-image-path", "/images/banner/trezor-99-banner-512x72.png");
    banners.put("0.medium-image-path", "/images/banner/trezor-99-banner-280x72.png");

    // KeepKey
    banners.put("1.id", "1");
    banners.put("1.slug", "keep-key");
    banners.put("1.large-title", "KeepKey: Your Private Bitcoin Vault");
    banners.put("1.medium-title", "KeepKey: Your Private Bitcoin Vault");
    banners.put("1.small-title", "KeepKey:&nbsp;Your&nbsp;Private&nbsp;Bitcoin&nbsp;Vault");
    banners.put("1.large-url", "https://padlock.link/ulpj");
    banners.put("1.medium-url", "https://padlock.link/u0f7");
    banners.put("1.small-url", "https://padlock.link/wgrt");
    banners.put("1.large-image-path", "/images/banner/keepkey-invite-banner-512x72.png");
    banners.put("1.medium-image-path", "/images/banner/keepkey-invite-banner-280x72.png");

  }

  /**
   * @return A random value according to the desired weighting to be used
   */
  public int getBannerId() {

    // Select between 0 and 999 for granularity to 0.1%
    Integer value = random.nextInt(1000);

    // Use ranges to enforce display ratio (see Range definitions to adjust)
    if (trezorRange.contains(value)) {
      return 0;
    }
    if (keepKeyRange.contains(value)) {
      return 1;
    }

    // Incorrect configuration so use default
    return 0;
  }

  /**
   * @param rawBannerId The banner Id
   * @param rawSource   The source of the click (identifies the appropriate redirect URI)
   *
   * @return A suitable redirect URI
   */
  public static URI createRedirectUri(int rawBannerId, String rawSource) {

    // Validate the parameters
    int bannerId = rawBannerId >= 0 && rawBannerId <= 1 ? rawBannerId : 1;

    if (!"small|medium|large|".contains(rawSource + "|")) {
      // Unknown source so redirect back to MultiBit.org
      return URI.create("https://multibit.org");
    }

    // Build a suitable cache key
    String cacheKey = bannerId + "." + rawSource + "-url";

    // Do not expect a failure here due to the validation
    return URI.create(get(cacheKey));
  }

  public static String get(String cacheKey) {
    return banners.get(cacheKey);
  }
}
