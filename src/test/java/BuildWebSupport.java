import org.multibit.site.core.atom.AtomFeed;
import org.multibit.site.core.atom.AtomFeedBuilder;
import org.multibit.site.core.sitemap.SiteMap;
import org.multibit.site.core.sitemap.SiteMapBuilder;

/**
 * <p>Utility to provide the following to developers:</p>
 * <ul>
 * <li>Build tool to generate the site and Atom feed XML</li>
 * </ul>
 */
public class BuildWebSupport {

  private static final String HOST = "https://multibit.org";

  public static void main(String[] args) throws Exception {

    SiteMap siteMap = SiteMapBuilder.build(HOST);
    SiteMapBuilder.writeToFile(siteMap);

    AtomFeed atomFeed = AtomFeedBuilder.build(HOST);
    AtomFeedBuilder.writeToFile(atomFeed);

  }

}
