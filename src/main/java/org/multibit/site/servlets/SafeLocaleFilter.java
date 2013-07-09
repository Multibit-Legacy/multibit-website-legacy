package org.multibit.site.servlets;

/**
 * <p>Servlet filter to provide the following to application:</p>
 * <ul>
 * <li>Wrapping of original request to allow modification</li>
 * </ul>
 */

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SafeLocaleFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // Do nothing
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    if (request instanceof HttpServletRequest) {

      // Wrap the original request
      SafeHeadersRequest safeRequest = new SafeHeadersRequest((HttpServletRequest) request);

      chain.doFilter(safeRequest, response);
    } else {
      // Use the originals
      chain.doFilter(request, response);
    }

  }

  @Override
  public void destroy() {
    // Do nothing
  }

}

