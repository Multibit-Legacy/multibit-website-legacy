package org.multibit.site.utils;

import org.multibit.site.core.atom.AtomFeed;
import org.multibit.site.core.atom.AtomFeedBuilder;
import org.multibit.site.core.resizer.ScreenShotResizer;
import org.multibit.site.core.sitemap.SiteMap;
import org.multibit.site.core.sitemap.SiteMapBuilder;

import java.nio.file.Paths;

/**
 * <p>Utility to provide the following to developers:</p>
 * <ul>
 * <li>Generate sitemap.xml, atom.xml</li>
 * <li>Generate atom.xml</li>
 * <li>Resize screenshots for MultiBit HD internal help</li>
 * </ul>
 */
public class WebSupportUtils {

  private static final String HOST = "https://multibit.org";

  public static void main(String[] args) throws Exception {

    SiteMap siteMap = SiteMapBuilder.build(HOST);
    SiteMapBuilder.writeToFile(siteMap);

    AtomFeed atomFeed = AtomFeedBuilder.build(HOST);
    AtomFeedBuilder.writeToFile(atomFeed);

    // At present this results in slight fuzziness
    // so has been switched off
    ScreenShotResizer.resizeAll(Paths.get("src/main/resources/assets/images/en/screenshots/mbhd-0.1"), "*.png");

  }

}
