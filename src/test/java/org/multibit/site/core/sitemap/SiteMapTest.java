package org.multibit.site.core.sitemap;

import org.junit.Test;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;

import static org.fest.assertions.api.Assertions.assertThat;

public class SiteMapTest {

  @Test
  public void verifyXmlUnmarshal() throws JAXBException, IOException {

    InputStream is = SiteMapTest.class.getResourceAsStream("/fixtures/sitemap/test-sitemap.xml");
    Source source = new StreamSource(is);
    SiteMap siteMap = JAXB.unmarshal(source,SiteMap.class);
    assertThat(siteMap.getUrlset().size()).isEqualTo(3);

  }

}
