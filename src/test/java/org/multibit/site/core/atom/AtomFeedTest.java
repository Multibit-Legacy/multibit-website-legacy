package org.multibit.site.core.atom;

import org.junit.Test;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;

import static org.fest.assertions.api.Assertions.assertThat;

public class AtomFeedTest {

  @Test
  public void verifyXmlUnmarshal() throws JAXBException, IOException {

    InputStream is = AtomFeedTest.class.getResourceAsStream("/fixtures/atom/test-atom.xml");
    Source source = new StreamSource(is);
    AtomFeed atomFeed = JAXB.unmarshal(source,AtomFeed.class);

    assertThat(atomFeed.getTitle()).isEqualTo("Example Feed");

  }

}
