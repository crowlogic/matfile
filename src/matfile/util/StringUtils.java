package matfile.util;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StringUtils
{
  public static final Date BEGINNING_OF_TIME = new Date(0);
  public static final String[] EMPTY_STRING_ARRAY = new String[0];
  public static final String ISO_8859_1 = "iso-8859-1";
  public static final int MAX_WORDS_IN_HYPHENATED_TERM = 2;
  public static final String SHA_DIGEST_ALGORITHM = "SHA1";
  public static final String UTF8_ENCODING_NAME = "UTF-8";
  public static final DecimalFormat penniesPriceFormatter = new DecimalFormat("0.###");
  public static final DecimalFormat millsPriceFormatter = new DecimalFormat("0.#####");
  public static final DecimalFormat zillsPriceFormatter = new DecimalFormat("0.######");

  public static String stripSpacesAndNewlines(String str)
  {
    return str.replaceAll("\\ ", "").replace("\n", " ");
  }

  /**
   * Returns ascii form of string
   * 
   * @param s
   * @return
   */
  public static String asciiNormalize(String s)
  {
    String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
    return temp.replaceAll("[^\\p{ASCII}]", "");
  }

  public static String convertToHex(byte[] data)
  {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < data.length; i++)
    {
      int halfbyte = (data[i] >>> 4) & 0x0F;
      int two_halfs = 0;
      do
      {
        if ((0 <= halfbyte) && (halfbyte <= 9))
        {
          buf.append((char) ('0' + halfbyte));
        }
        else
        {
          buf.append((char) ('a' + (halfbyte - 10)));
        }
        halfbyte = data[i] & 0x0F;
      }
      while (two_halfs++ < 1);
    }
    return buf.toString();
  }

  /**
   * [1,2,3] -> "1,2,3"
   * 
   * @param elements
   * @param delimeter
   * @return
   */
  public static <T> String join(Collection<T> elements, String delimeter)
  {
    StringBuffer sb = new StringBuffer();
    for (T i : elements)
    {
      sb.append(i + delimeter);
    }
    String str = sb.toString();
    return str.isEmpty() ? str : str.substring(0, str.length() - 1);
  }

  /**
   * [1,2,3] -> "1,2,3"
   * 
   * @param elements
   * @param seperator
   * @return
   */
  public static String join(int[] elements, String seperator)
  {
    StringBuffer sb = new StringBuffer();
    for (int i : elements)
    {
      sb.append(i + seperator);
    }
    String str = sb.toString();
    return str.isEmpty() ? str : str.substring(0, str.length() - 1);
  }

  /**
   * Joins the element of the collection and appends them to a StringBuffer
   * 
   * @param sb
   * @param fields
   * @param prefix if not null then this is prefixed to each field
   */
  public static void join(StringBuffer sb, Collection<String> fields, String prefix)
  {
    if (prefix == null)
    {
      prefix = "";
    }

    boolean first = true;
    for (String field : fields)
    {
      if (!first)
      {
        sb.append(",");
      }
      sb.append(prefix + field);
      first = false;
    }
  }

  /**
   * [1,2,3] -> "1,2,3"
   * 
   * @param elements
   * @param delimeter
   * @return
   */
  public static <T> String join(T[] elements, String delimeter)
  {
    StringBuffer sb = new StringBuffer();
    for (T i : elements)
    {
      sb.append(i + delimeter);
    }
    String str = sb.toString();
    return str.isEmpty() ? str : str.substring(0, str.length() - 1);
  }

  /**
   * Repeat rep string length times seperated by sep
   * 
   * @param rep
   * @param sep
   * @param length
   * @return
   */
  public static String repeatJoin(String rep, String sep, int length)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++)
    {
      if (i > 0)
      {
        sb.append(sep);
      }
      sb.append(rep);
    }
    return sb.toString();
  }

  /**
   * Takes "wild" text and makes it all nice and lowercase
   * 
   * @param text
   * @return
   */
  public static String tameString(String text)
  {
    text = text.replaceAll("\\&.*;", "");
    text = text.replaceAll("--.", "");
    text = text.replaceAll("\\s+", " ");
    text = text.replaceAll("\\+", " ");
    text = text.replaceAll("Description\\.[^=]+\\=[\\S]+", "");
    text = text.replaceAll("’s\\ ", "");
    text = text.replaceAll("<[^>]*>", " ");
    text = text.replaceAll("[\\(\\)\\:\\,\\.\\'\\`\\/\\$\\%\\?\\;\\!\\+\\\"\\=\\[\\]\\&\\\\\\@]", "");
    return trim(text.toLowerCase());
  }

  /**
   * Parses an expression representing some duration of time and returns the
   * results in seconds. The expression is space delimited and can have any number
   * of parts of time, for exaple: "3 DAYS 4 hours 7 SeCoNds" is a valid
   * expression
   * 
   * @param expression there must be an even number of tokens in the expression
   *                   and tokens must be seperated by a SINGLE space
   * @return time in milliseconds
   * @throws IllegalArgumentException if any of the unit tokens is not a valid
   *                                  TimeUnit
   */
  public static long timeStringToMillis(final String expression)
  {
    assert expression != null : "expression cannot be null";

    String tokens[] = expression.split(" ");

    long time = 0;
    for (int i = 0; i < tokens.length; i += 2)
    {
      time += TimeUnit.valueOf(tokens[i + 1].toUpperCase()).toMillis(Long.valueOf(tokens[i]));
    }

    return time;
  }

  /**
   * Converts floating point num in [0..1] to max 3 character length string
   * percentage
   * 
   * @param f
   * @return percent string
   */
  public static String toPercentString(float f)
  {
    return String.format("%3d", Math.round(Math.abs(f * 100)));
  }

  /**
   * Trims string and/or converts "" to null
   * 
   * @param str
   * @return trimmed string or null of the original or resulting trimmed string
   *         had 0 length
   */
  public static String trim(String symbol)
  {
    if (symbol != null)
    {
      symbol = symbol.trim();
    }
    return (symbol == null || symbol.isEmpty()) ? null : symbol;
  }

  /**
   * Trims string and/or converts "" or strings shorter than
   * nullifyStringsLessThan to null
   * 
   * @param str
   * @param nullifyStringsLessThan if str is less than nullifyStringsLessThan long
   *                               then it gets converted to null
   * @return trimmed string or null of the original or resulting trimmed string
   *         had 0 length
   */
  public static String trimAndNullifyIfShorterThan(String str, int nullifyStringsLessThan)
  {
    if (str != null)
    {
      str = str.trim();
    }
    return (str == null || str.isEmpty() || str.length() < nullifyStringsLessThan) ? null : str;
  }

  public final static String trimString(String str, int len)
  {
    if (str == null)
    {
      return null;
    }

    String substr = str.substring(0, Math.min(str.length(), len));

    return substr;
  }

  public static List<String> fastSplit(String line, char split)
  {
    StringBuilder sb = new StringBuilder();
    boolean quoted = false;

    List<String> list = new ArrayList<>();

    for (char c : line.toCharArray())
    {
      if (quoted)
      {
        if (c == '"')
          quoted = false;
        else
          sb.append(c);
      }
      else
      {
        if (c == '"')
        {
          quoted = true;
        }
        else if (c == split)
        {
          list.add(sb.toString());
          sb = new StringBuilder();
        }
        else
        {
          sb.append(c);
        }
      }
    }

    if (quoted)
      throw new IllegalArgumentException("csvString: Unterminated quotation mark.");

    list.add(sb.toString());
    return list;
  }
}
