package org.multibit.site.core.testing;

import com.sun.jersey.api.client.WebResource;
import com.yammer.dropwizard.testing.ResourceTest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;

/**
 * <p>Base class to provide the following to tests:</p>
 * <ul>
 * <li>common methods for testing resources against an in-memory container</li>
 * </ul>
 *
 * @since 0.0.4
 *         
 */
public abstract class BaseResourceTest extends ResourceTest {

  static {
    // Fix all DateTime values to midnight on January 01 2000 UTC
    DateTimeUtils.setCurrentMillisFixed(new DateTime(2000, 1, 1, 0, 0, 0, 0).withZone(DateTimeZone.UTC).getMillis());
  }

  /**
   * <p>Subclasses must use this to configure mocks of any objects that the
   * test object depends on.</p>
   *
   * @throws Exception If something goes wrong
   */
  protected abstract void setUpResources() throws Exception;

  /**
   * Configure request as a client to access the resource on behalf of a user
   *
   * @param path The relative path to the resource
   *
   * @return A web resource suitable for method chaining
   *
   */
  protected WebResource configureAsClient(String path) {
    return client().resource(path);
  }

}