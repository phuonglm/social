package org.exoplatform.social.core;

import com.ibm.icu.text.Transliterator;

/**
 * @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a>
 */
public class StringUtils {
  
  public static String cleanString(String str) {
    if (str == null) {
      throw new IllegalArgumentException("String argument must not be null.");
    }

    str = Transliterator.getInstance("Latin; NFD; [:Nonspacing Mark:] Remove; NFC;").transliterate(str);

    // the character ? seems to not be changed to d by the transliterate
    // function

    StringBuilder cleanedStr = new StringBuilder(str.trim());
    // delete special character
    for (int i = 0; i < cleanedStr.length(); i++) {
      char c = cleanedStr.charAt(i);
      if (c == ' ') {
        if (i > 0 && cleanedStr.charAt(i - 1) == '_') {
          cleanedStr.deleteCharAt(i--);
        } else {
          c = '_';
          cleanedStr.setCharAt(i, c);
        }
        continue;
      }

      if (!(Character.isLetterOrDigit(c) || c == '_')) {
        cleanedStr.deleteCharAt(i--);
        continue;
      }

      if (i > 0 && c == '_' && cleanedStr.charAt(i - 1) == '_') {
        cleanedStr.deleteCharAt(i--);
      }
    }
    return cleanedStr.toString().toLowerCase();
  }

  public static String removeSpecialCharacterInSpaceFilter(String input){
    String result = input.replaceAll("[^\\pL\\pM\\p{Nd}\\p{Nl}\\p{Pc}[\\p{InEnclosedAlphanumerics}&&\\p{So}]\\?\\*%0-9]", " ");
    result = result.replaceAll("\\s+", " ");
    return result.trim();
  }
  
  /**
   * Escapes jcr special characters.
   *
   * @param string
   * @return
   */
  public static String escapeJCRSpecialCharacters(String string) {
    if (string == null) {
      return null;
    }
    return string.replace("[", "%5B").replace("]", "%5D").replace(":", "%3A");
  }

}
