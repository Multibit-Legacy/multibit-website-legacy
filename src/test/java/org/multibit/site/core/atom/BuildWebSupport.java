package org.multibit.site.core.atom;

import org.multibit.site.core.sitemap.SiteMapBuilder;

/**
 * <p>Utility to provide the following to developers:<br>
 * <ul>
 * <li>Quick tool to import the self-signed certificate from localhost:8443 into the local trust store</li>
 * </ul>
 * Example:
 * <pre>
 * javac InstallCert.java
 * sudo java InstallCert localhost:8443 myPassword
 * </pre>
 * </p>
 */

public class BuildWebSupport {

  public static void main(String[] args) throws Exception {

    SiteMapBuilder siteMapBuilder = new SiteMapBuilder();
    siteMapBuilder.build();

    AtomFeedBuilder atomFeedBuilder = new AtomFeedBuilder();
    atomFeedBuilder.build();

  }

}
