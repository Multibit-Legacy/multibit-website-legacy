package org.multibit.site;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * <p>Cache to provide the following to resources:</p>
 * <ul>
 * <li>In-memory thread-safe cache for page view instances</li>
 * </ul>
 *
 * @since 0.0.1
 *
 *        TODO Make this work for the rendered FreemarkerView
 *         
 */
public enum InMemoryAssetCache {

  // Provide a global singleton for the application
  INSTANCE;

  // A lot of threads will hit this cache
  private volatile Cache<String, String> pageCache;

  InMemoryAssetCache() {
    reset(15, TimeUnit.MINUTES);
  }

  /**
   * Resets the cache and allows the expiry time to be set (perhaps for testing)
   *
   * @param duration The duration before a page must be manually rebuilt
   * @param unit     The {@link java.util.concurrent.TimeUnit} that duration is expressed in
   */
  public InMemoryAssetCache reset(int duration, TimeUnit unit) {

    // Build the cache
    if (pageCache != null) {
      pageCache.invalidateAll();
    }

    // If there is no activity against a key then we want
    // it to be expired from the cache, but each fresh write
    // will reset the expiry timer
    pageCache = CacheBuilder
      .newBuilder()
      .expireAfterWrite(duration, unit)
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

    // TODO Remove this when caching is required
    if (true) {
      return Optional.absent();
    }

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
