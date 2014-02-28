package org.multibit.site.caches;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * <p>Cache to provide the following to resources:</p>
 * <ul>
 * <li>In-memory thread-safe cache for long-lived artifacts generated periodically</li>
 * </ul>
 *
 * @since 3.1.0
 *
 */
public enum InMemoryArtifactCache {

  // Provide a global singleton for the application
  INSTANCE;

  public static final String SITE_MAP_KEY = "/views/sitemap.xml";
  public static final String ATOM_FEED_KEY = "/views/atom.xml";

  // A lot of threads will hit this cache
  private volatile Cache<String, String> pageCache;

  InMemoryArtifactCache() {
    reset();
  }

  /**
   * Resets the cache and allows the expiry time to be set (perhaps for testing)
   */
  public InMemoryArtifactCache reset() {

    // Build the cache
    if (pageCache != null) {
      pageCache.invalidateAll();
    }

    // Store a few items permanently
    pageCache = CacheBuilder
      .newBuilder()
      .maximumSize(100)
      .build();

    return INSTANCE;
  }

  /**
   * @param resourcePath The resource path to locate the Freemarker template
   *
   * @return The matching ClientUser or absent
   */
  public Optional<String> getByResourcePath(String resourcePath) {

    // Turn off page caching for development here
    /*
     if (true) {
       return Optional.absent();
     }
     */

    // Check the cache
    Optional<String> viewOptional = Optional.fromNullable(pageCache.getIfPresent(resourcePath));

    if (viewOptional.isPresent()) {
      // Ensure we refresh the cache on a check to maintain the session timeout
      pageCache.put(resourcePath, viewOptional.get());
    }

    return viewOptional;

  }

  /**
   * @param resourcePath The resource path acting as the
   * @param content      The rendered content to cache
   */
  public void put(String resourcePath, String content) {
    Preconditions.checkNotNull(content);
    Preconditions.checkNotNull(resourcePath);
    pageCache.put(resourcePath, content);
  }

}
