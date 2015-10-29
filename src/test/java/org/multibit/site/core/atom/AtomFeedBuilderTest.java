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
  public void verifyBuildAndCache() throws JAXBException, IOException, URISyntaxException {

    AtomFeedBuilder.cache(AtomFeedBuilder.build("http://localhost:8080"));

    Optional<String> atomFeedOptional = InMemoryArtifactCache.INSTANCE.getByResourcePath(InMemoryArtifactCache.ATOM_FEED_KEY);

    assertThat(atomFeedOptional.isPresent()).isTrue();

    String atomFeed = atomFeedOptional.get();

    // Simple check for correct content
    assertThat(atomFeed).contains("http://localhost:8080/en/blog/2013/04/23/java-is-secure.html");
    assertThat(atomFeed).contains("2013-04-23T12:00:00Z");

    // Check that deleted articles do not appear in the Atom feed
    assertThat(atomFeed).doesNotContain("/en/blog/2015/10/22/multibit-hd-release-0.1.4.html");
    assertThat(atomFeed).doesNotContain("/en/blog/2015/10/21/multibit-hd-release-0.1.4.html");

  }

}
