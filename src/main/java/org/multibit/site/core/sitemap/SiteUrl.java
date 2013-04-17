package org.multibit.site.core.sitemap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents a single URL within the site
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "loc", "lastmod" })
public class SiteUrl {

  @XmlElement
  private String loc;

  @XmlElement
  private String lastmod;

  public SiteUrl() {}

  public SiteUrl(String loc, String lastmod) {
    this.loc = loc;
    this.lastmod = lastmod;
  }

  public String getLoc() {
    return loc;
  }

  public void setLoc(String loc) {
    this.loc = loc;
  }

  public String getLastmod() {
    return lastmod;
  }

  public void setLastmod(String lastmod) {
    this.lastmod = lastmod;
  }
}