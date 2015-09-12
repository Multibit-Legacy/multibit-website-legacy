package org.multibit.site.core.banners;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>Value object to provide the following to banner API:</p>
 * <ul>
 * <li>External configuration of banner ratios and parameters</li>
 * </ul>
 *
 * @since 0.0.4
 *  
 */
public class Banner {

  @NotEmpty
  @JsonProperty
  private String id;

  // Ratio and pricing

  @NotEmpty
  @JsonProperty
  private String range;

  @NotEmpty
  @JsonProperty
  private String name;

  @JsonProperty
  private String cpm;

  @NotEmpty
  @JsonProperty
  private String cpmCurrency = "USD";

  // Presentation and links

  @NotEmpty
  @JsonProperty
  private String largeTitle;

  @NotEmpty
  @JsonProperty
  private String mediumTitle;

  @NotEmpty
  @JsonProperty
  private String textTitle;

  @NotEmpty
  @JsonProperty
  private String largeUrl;

  @NotEmpty
  @JsonProperty
  private String mediumUrl;

  @NotEmpty
  @JsonProperty
  private String textUrl;

  @NotEmpty
  @JsonProperty
  private String largeImagePath;

  @NotEmpty
  @JsonProperty
  private String mediumImagePath;

  /**
   * @return The bounding values representing a closed range (e.g. "0,499")
   */
  public String getRange() {
    return range;
  }

  /**
   * @return The banner name (e.g. "Trezor $99 promo")
   */
  public String getName() {
    return name;
  }

  /**
   * @return The banner name (e.g. "Trezor $99 promo")
   */
  public String getLargeTitle() {
    return largeTitle;
  }

  /**
   * @return The banner name (e.g. "Trezor $99 promo")
   */
  public String getMediumTitle() {
    return mediumTitle;
  }

  /**
   * @return The banner name (e.g. "Trezor $99 promo")
   */
  public String getTextTitle() {
    return textTitle;
  }

  /**
   * @return The banner name (e.g. "Trezor $99 promo")
   */
  public String getLargeUrl() {
    return largeUrl;
  }

  /**
   * @return The banner name (e.g. "Trezor $99 promo")
   */
  public String getMediumUrl() {
    return mediumUrl;
  }

  /**
   * @return The banner name (e.g. "Trezor $99 promo")
   */
  public String getTextUrl() {
    return textUrl;
  }

  /**
   * @return The banner name (e.g. "Trezor $99 promo")
   */
  public String getLargeImagePath() {
    return largeImagePath;
  }

  /**
   * @return The banner name (e.g. "Trezor $99 promo")
   */
  public String getMediumImagePath() {
    return mediumImagePath;
  }
}

