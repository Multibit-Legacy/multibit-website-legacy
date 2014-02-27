package org.multibit.site.utils;

import org.multibit.site.core.atom.AtomFeed;
import org.multibit.site.core.atom.AtomFeedBuilder;
import org.multibit.site.core.sitemap.SiteMap;
import org.multibit.site.core.sitemap.SiteMapBuilder;

/**
 * <p>Utility to provide the following to developers:</p>
 * <ul>
 * <li>Build tool to generate web support files: sitemap.xml, atom.xml</li>
 * </ul>
 */
public class WebSupportUtils {

  private static final String HOST = "https://multibit.org";

  public static void main(String[] args) throws Exception {

    SiteMap siteMap = SiteMapBuilder.build(HOST);
    SiteMapBuilder.writeToFile(siteMap);

    AtomFeed atomFeed = AtomFeedBuilder.build(HOST);
    AtomFeedBuilder.writeToFile(atomFeed);

  }

}
