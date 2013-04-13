package org.multibit.site;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>DropWizard Configuration to provide the following to application:</p>
 * <ul>
 * <li>Initialisation code</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class SiteConfiguration extends Configuration {

  @NotEmpty
  @JsonProperty
  private String assetCachePolicy="maximumSize=10000, expireAfterAccess=5s";

  public String getAssetCachePolicy() {
    return assetCachePolicy;
  }

}
