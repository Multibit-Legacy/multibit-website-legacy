package org.multibit.site.core.sitemap;

import com.google.common.base.Optional;
import org.junit.Test;
import org.multibit.site.caches.InMemoryArtifactCache;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;

public class SiteMapBuilderTest {

  @Test
  public void verifyBuildSite() throws JAXBException, IOException {

    SiteMapBuilder testObject = new SiteMapBuilder();

    testObject.build();

    Optional<String> siteMapOptional = InMemoryArtifactCache.INSTANCE.getByResourcePath(InMemoryArtifactCache.SITE_MAP_KEY);

    assertThat(siteMapOptional.isPresent()).isTrue();

    String siteMap = siteMapOptional.get();

    // Simple check for correct content
    assertThat(siteMap).contains("http://localhost:8080/en/index.html");
    assertThat(siteMap).contains("http://localhost:8080/fr/index.html");
  }

}
