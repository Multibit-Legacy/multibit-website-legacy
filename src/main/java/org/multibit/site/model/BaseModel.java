package org.multibit.site.model;

import com.google.common.base.Optional;
import org.multibit.site.InMemoryAssetCache;
import org.multibit.site.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>Base class to provide the following to views:</p>
 * <ul>
 * <li>Access to Markdown content</li>
 *
 * @since 0.0.1
 *         
 */
public class BaseModel {

  private static final Logger log = LoggerFactory.getLogger(BaseModel.class);

  public String content;

  public BaseModel(String resourcePath) {

    log.debug("Locating resource under '/views/html{}'",resourcePath);

    // Check for a fully-formed view
    if (resourcePath == null) {
      return;
    }

    // Check the asset cache
    Optional<String> contentOptional = InMemoryAssetCache.INSTANCE.getByResourcePath(resourcePath);
    if (contentOptional.isPresent()) {
      content = contentOptional.get();
      return;
    }

    // Markdown processing is slow so result must be cached

    // Only asset type supported is HTML
    if (resourcePath.endsWith(".html")) {
      // Attempt a load
      // TODO Add i18n support
      InputStream is = BaseModel.class.getResourceAsStream("/views/html" + resourcePath);
      if (is == null) {
        throw new WebApplicationException(Response.Status.NOT_FOUND);
      }

      try {
        // Read the HTML fragment and cache it for later
        content = StreamUtils.toString(is);
        InMemoryAssetCache.INSTANCE.put(resourcePath, content);
        return;
      } catch (IOException e) {
        throw new WebApplicationException(e, Response.Status.NOT_FOUND);
      }

    }

    // Must have failed to be here
    throw new WebApplicationException(Response.Status.NOT_FOUND);

  }

  public String getContent() throws IOException {
    return content;
  }

}
