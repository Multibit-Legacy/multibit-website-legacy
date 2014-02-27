package org.multibit.site.core.atom;

import com.google.common.base.Optional;
import org.junit.Test;
import org.multibit.site.caches.InMemoryArtifactCache;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.fest.assertions.api.Assertions.assertThat;

public class AtomFeedBuilderTest {

  @Test
  public void verifyBuildSite() throws JAXBException, IOException, URISyntaxException {

    AtomFeedBuilder testObject = new AtomFeedBuilder();

    testObject.build();

    Optional<String> atomFeedOptional = InMemoryArtifactCache.INSTANCE.getByResourcePath(InMemoryArtifactCache.ATOM_FEED_KEY);

    assertThat(atomFeedOptional.isPresent()).isTrue();

    String atomFeed = atomFeedOptional.get();

    // Simple check for correct content
    assertThat(atomFeed).contains("http://localhost:8080/en/blog/2013-04-23-java-is-secure.html");

  }

}
