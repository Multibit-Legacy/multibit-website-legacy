package org.multibit.site.core.sitemap;

import com.google.common.collect.Sets;

import javax.xml.bind.annotation.*;
import java.util.Set;

/**
 * <p>Value object to provide the following to resources:</p>
 * <ul>
 * <li>Provision of sitemap markup</li>
 * </ul>
 *
 * @since 3.0.0
 *         
 */
@XmlRootElement(name="urlset")
@XmlSeeAlso(SiteUrl.class)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"urlset"})
public class SiteMap {

  @XmlElement(name="url")
  private Set<SiteUrl> urlset = Sets.newLinkedHashSet();

  public SiteMap() {
  }

  public Set<SiteUrl> getUrlset() {
    return urlset;
  }

  public void setUrlset(Set<SiteUrl> urlset) {
    this.urlset = urlset;
  }
}
