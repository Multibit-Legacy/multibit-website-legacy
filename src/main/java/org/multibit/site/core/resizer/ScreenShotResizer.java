package org.multibit.site.core.resizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * <p>Utility to provide the following to build process:</p>
 * <ul>
 * <li>Resizing of screen shot images to standard size for MultiBit HD internal help</li>
 * </ul>
 *
 * @since 0.0.4
 *  
 */
public class ScreenShotResizer extends SimpleFileVisitor<Path> {

  private static final Logger log = LoggerFactory.getLogger(ScreenShotResizer.class);

  private final PathMatcher matcher;
  private int resizeCount = 0;
  private int skipCount = 0;

  /**
   * @param startingDir The starting directory
   * @param pattern     The glob pattern (e.g. "*.png")
   *
   * @throws IOException If something goes wrong
   */
  public static void resizeAll(Path startingDir, String pattern) throws IOException {

    ScreenShotResizer finder = new ScreenShotResizer(pattern);
    Files.walkFileTree(startingDir, finder);
    finder.done();
  }

  /**
   * @param pattern The glob pattern (e.g. "*.png")
   */
  public ScreenShotResizer(String pattern) {
    matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
  }

  /**
   * <p>Match the file against the glob pattern</p>
   *
   * @param file The current file in the tree walk
   *
   * @throws java.io.IOException If something goes wrong
   */
  public void find(Path file) throws IOException {
    Path name = file.getFileName();
    if (name != null && matcher.matches(name)) {

      // Perform the resize ...
      final BufferedImage originalImage = ImageIO.read(file.toFile());
      if (originalImage.getWidth() > 670) {
        final BufferedImage resizedImage = resizeSharp(originalImage, 670);
        ImageIO.write(resizedImage, "PNG", file.toFile());
        log.info("Resized: '{}'", file);
        resizeCount++;
      } else {
        log.info("Skipped: '{}'", file);
        skipCount++;
      }

    }
  }

  public void done() {
    log.info("Resized total: {}", resizeCount);
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
    try {
      find(file);
    } catch (IOException e) {
      log.warn("Problem with '{}': {}", file, e.getMessage());
    }
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    try {
      find(dir);
    } catch (IOException e) {
      log.warn("Problem with '{}': {}", dir, e.getMessage());
    }

    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException e) {
    log.error("Problem with '{}': {}", file, e.getMessage());
    return FileVisitResult.CONTINUE;
  }

  /**
   * @param image    The source image
   * @param maxWidth The maximum width (assumes a landscape image)
   *
   * @return The re-sized image with no blurring and preserved transparency
   */
  private BufferedImage resizeSharp(BufferedImage image, int maxWidth) {

    // Assume a screen shot and calculate the appropriate ratio
    // for minimum UI width
    double ratio = (double) image.getWidth(null) / maxWidth;
    int height = (int) (image.getHeight(null) / ratio);

    // Preserve transparency
    BufferedImage thumbnail = new BufferedImage(maxWidth, height, BufferedImage.TYPE_INT_ARGB);

    // Perform a bi-cubic interpolation with anti-aliasing for sharp result
    Graphics2D g = thumbnail.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.drawImage(image, 0, 0, maxWidth, height, null);
    g.dispose();

    return thumbnail;

  }

}
