package org.multibit.site.core.testing;

import com.google.common.collect.Lists;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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
    return new HttpServletResponse() {

      List<Cookie> cookies = Lists.newArrayList();

      @Override
      public void addCookie(Cookie cookie) {
        cookies.add(cookie);
      }

      @Override
      public boolean containsHeader(String name) {
        return HttpHeaders.COOKIE.equalsIgnoreCase(name) && !cookies.isEmpty();
      }

      @Override
      public String encodeURL(String url) {
        return null;
      }

      @Override
      public String encodeRedirectURL(String url) {
        return null;
      }

      @Override
      public String encodeUrl(String url) {
        return null;
      }

      @Override
      public String encodeRedirectUrl(String url) {
        return null;
      }

      @Override
      public void sendError(int sc, String msg) throws IOException {

      }

      @Override
      public void sendError(int sc) throws IOException {

      }

      @Override
      public void sendRedirect(String location) throws IOException {

      }

      @Override
      public void setDateHeader(String name, long date) {

      }

      @Override
      public void addDateHeader(String name, long date) {

      }

      @Override
      public void setHeader(String name, String value) {

      }

      @Override
      public void addHeader(String name, String value) {

      }

      @Override
      public void setIntHeader(String name, int value) {

      }

      @Override
      public void addIntHeader(String name, int value) {

      }

      @Override
      public void setStatus(int sc) {

      }

      @Override
      public void setStatus(int sc, String sm) {

      }

      @Override
      public int getStatus() {
        return 0;
      }

      @Override
      public String getHeader(String name) {
        return null;
      }

      @Override
      public Collection<String> getHeaders(String name) {
        return null;
      }

      @Override
      public Collection<String> getHeaderNames() {
        return null;
      }

      @Override
      public String getCharacterEncoding() {
        return null;
      }

      @Override
      public String getContentType() {
        return null;
      }

      @Override
      public ServletOutputStream getOutputStream() throws IOException {
        return null;
      }

      @Override
      public PrintWriter getWriter() throws IOException {
        return null;
      }

      @Override
      public void setCharacterEncoding(String charset) {

      }

      @Override
      public void setContentLength(int len) {

      }

      @Override
      public void setContentType(String type) {

      }

      @Override
      public void setBufferSize(int size) {

      }

      @Override
      public int getBufferSize() {
        return 0;
      }

      @Override
      public void flushBuffer() throws IOException {

      }

      @Override
      public void resetBuffer() {

      }

      @Override
      public boolean isCommitted() {
        return false;
      }

      @Override
      public void reset() {

      }

      @Override
      public void setLocale(Locale loc) {

      }

      @Override
      public Locale getLocale() {
        return null;
      }
    };
  }
}

