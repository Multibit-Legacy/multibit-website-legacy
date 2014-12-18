package org.multibit.site.core.languages;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>Utility to provide the following to Views:</p>
 * <ul>
 * <li>Access to internationalised text strings</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public class Languages {

  public static final String BASE_NAME = "languages.language";

  /**
   * Utilities have private constructors
   */
  private Languages() {
  }

  /**
   * @param value         The encoding of the locale (e.g. "ll", "ll_rr", "ll_rr_vv")
   * @param defaultLocale The default locale if the value cannot be interpreted
   *
   * @return A new resource bundle based on the locale
   */
  public static Locale newLocaleFromCode(String value, Locale defaultLocale) {

    if (value==null) {
      return defaultLocale;
    }

    // Cover hyphenation or unusual separation
    String[] parameters = value.replace("-", "_").split("_");

    if (parameters.length == 0) {
      return defaultLocale;
    }

    final Locale newLocale;

    switch (parameters.length) {
      case 1:
        newLocale = new Locale(parameters[0]);
        break;
      case 2:
        newLocale = new Locale(parameters[0], parameters[1]);
        break;
      case 3:
        newLocale = new Locale(parameters[0], parameters[1], parameters[2]);
        break;
      default:
        return defaultLocale;
    }

    // Trap bare HTTP requests
    if (newLocale.getLanguage().equals("null")) {
      return defaultLocale;
    }

    return newLocale;

  }

  /**
   * @param key    The key (must be present in the bundle)
   * @param values An optional collection of value substitutions for {@link java.text.MessageFormat}
   *
   * @return The localised text with any substitutions made
   */
  public static String safeText(String key, Locale locale, Object... values) {

    ResourceBundle rb = ResourceBundle.getBundle(BASE_NAME, locale);

    final String message;

    if (!rb.containsKey(key)) {
      // If no key is present then use it direct
      message = "Key '" + key + "' is not localised for '" + locale.getLanguage() + "'";
    } else {
      // Must have the key to be here
      message = rb.getString(key);
    }

    return MessageFormat.format(message, values);
  }

}
