package org.multibit.site.core.testing;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Type;

/**
 * <p>Jersey injectable to provide the following to functional tests:</p>
 * <ul>
 * <li>Provision of a mock HttpServletResponse to avoid errors with Context annotation</li>
 * </ul>
 *
 * @since 0.0.4
 */
@Provider
public class MockHttpContextResponseInjectable
  extends AbstractHttpContextInjectable<HttpServletResponse>
  implements InjectableProvider<Context, Type>
{

  @Override
  public Injectable<HttpServletResponse> getInjectable(
    ComponentContext ic,
    Context a,
    Type c
  ) {

    if (c.equals(HttpServletResponse.class)) {
      return this;
    }

    return null;
  }

  @Override
  public ComponentScope getScope() {
    return ComponentScope.PerRequest;
  }

  @Override
  public HttpServletResponse getValue(HttpContext c) {
    return null;
  }
}

