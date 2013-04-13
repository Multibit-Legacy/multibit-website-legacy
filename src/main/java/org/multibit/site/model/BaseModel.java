package org.multibit.site.model;

import org.pegdown.PegDownProcessor;

import java.io.IOException;

/**
 * <p>Base class to provide the following to views:</p>
 * <ul>
 * <li>Access to common data (user, adverts etc)</li>
 *
 * @since 0.0.1
 *         
 */
public class BaseModel {

  public String content;


  public String getContent() throws IOException {

    // Hard coded but could come from anywhere
    String markdown = "## Example Markdown\n" +
      "\n" +
      "This is a paragraph\n" +
      "\n" +
      "This is another\n" +
      "\n" +
      "[This is a link](http://example.org)";

    // New processor each time due to pegdown not being thread-safe internally
    PegDownProcessor processor = new PegDownProcessor();

    // Return the rendered HTML
    return processor.markdownToHtml(markdown);

  }

}
