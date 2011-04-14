package web.tools;

import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

import java.lang.reflect.Array;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 有关字符串处理的工具类。
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 *
 * @author Michael Zhou
 */
@DefaultKey("stringUtil")
@ValidScope(Scope.APPLICATION)
public class StringUtil {
    private static final char[] DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int DIGITS_LENGTH = DIGITS.length;

    // 空字符串。
    public static final String EMPTY_STRING = "";

    /**
     * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
     * <p/>
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty(&quot;&quot;)        = true
     * StringUtil.isEmpty(&quot; &quot;)       = false
     * StringUtil.isEmpty(&quot;bob&quot;)     = false
     * StringUtil.isEmpty(&quot;  bob  &quot;) = false
     * </pre>
     *
     * @param str 要检查的字符串
     * @return 如果为空, 则返回<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 除去字符串头尾部的空白，如果字符串是<code>null</code>，依然返回<code>null</code>。
     * <p/>
     * <pre>
     * StringUtil.trim(null)          = null
     * StringUtil.trim(&quot;&quot;)            = &quot;&quot;
     * StringUtil.trim(&quot;     &quot;)       = &quot;&quot;
     * StringUtil.trim(&quot;abc&quot;)         = &quot;abc&quot;
     * StringUtil.trim(&quot;    abc    &quot;) = &quot;abc&quot;
     * </pre>
     *
     * @param str 要处理的字符串
     * @return 除去空白的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 除去字符串头尾部的空白，如果结果字符串是空字符串<code>""</code>，则返回<code>null</code>。
     * <p/>
     * <pre>
     * StringUtil.trimToNull(null)          = null
     * StringUtil.trimToNull(&quot;&quot;)            = null
     * StringUtil.trimToNull(&quot;     &quot;)       = null
     * StringUtil.trimToNull(&quot;abc&quot;)         = &quot;abc&quot;
     * StringUtil.trimToNull(&quot;    abc    &quot;) = &quot;abc&quot;
     * </pre>
     *
     * @param str 要处理的字符串
     * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
     *         <code>null</code>
     */
    public static String trimToNull(String str) {
        if (str == null) {
            return null;
        }

        String result = str.trim();

        if (result == null || result.length() == 0) {
            return null;
        }

        return result;
    }

    /**
     * 除去字符串头尾部的空白，如果字符串是<code>null</code>，则返回空字符串<code>""</code>。
     * <p/>
     * <pre>
     * StringUtil.trimToEmpty(null)          = &quot;&quot;
     * StringUtil.trimToEmpty(&quot;&quot;)            = &quot;&quot;
     * StringUtil.trimToEmpty(&quot;     &quot;)       = &quot;&quot;
     * StringUtil.trimToEmpty(&quot;abc&quot;)         = &quot;abc&quot;
     * StringUtil.trimToEmpty(&quot;    abc    &quot;) = &quot;abc&quot;
     * </pre>
     *
     * @param str 要处理的字符串
     * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
     *         <code>null</code>
     */
    public static String trimToEmpty(String str) {
        if (str == null) {
            return EMPTY_STRING;
        }

        String result = str.trim();

        if (result == null) {
            return EMPTY_STRING;
        }

        return result;
    }

    /**
     * 如果字符串是<code>null</code>或空字符串<code>""</code>，则返回指定默认字符串，否则返回字符串本身。
     * <p/>
     * <pre>
     * StringUtil.defaultIfEmpty(null, &quot;default&quot;)  = &quot;default&quot;
     * StringUtil.defaultIfEmpty(&quot;&quot;, &quot;default&quot;)    = &quot;default&quot;
     * StringUtil.defaultIfEmpty(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
     * StringUtil.defaultIfEmpty(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
     * </pre>
     *
     * @param str        要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String defaultIfEmpty(String str, String defaultStr) {
        return str == null || str.length() == 0 ? defaultStr : str;
    }

    /**
     * 将一个长整形转换成62进制的字符串。
     */
    public static String longToString(long longValue) {
        if (longValue == 0) {
            return String.valueOf(DIGITS[0]);
        }

        if (longValue < 0) {
            longValue = -longValue;
        }

        StringBuilder strValue = new StringBuilder();

        while (longValue != 0) {
            int digit = (int) (longValue % DIGITS_LENGTH);
            longValue = longValue / DIGITS_LENGTH;

            strValue.append(DIGITS[digit]);
        }

        return strValue.toString();
    }

    /**
     * 将一个byte数组转换成62进制的字符串。
     */
    public static String bytesToString(byte[] bytes) {
        if (isEmptyArray(bytes)) {
            return String.valueOf(DIGITS[0]);
        }

        StringBuilder strValue = new StringBuilder();
        int value = 0;
        int limit = Integer.MAX_VALUE >>> 8;
        int i = 0;

        do {
            while (i < bytes.length && value < limit) {
                value = (value << 8) + (0xFF & bytes[i++]);
            }

            while (value >= DIGITS_LENGTH) {
                strValue.append(DIGITS[value % DIGITS_LENGTH]);
                value = value / DIGITS_LENGTH;
            }
        } while (i < bytes.length);

        if (value != 0 || strValue.length() == 0) {
            strValue.append(DIGITS[value]);
        }

        return strValue.toString();
    }

    /**
     * 按Java的规则对字符串进行转义。
     * <p>
     * 将双引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成<code>He didn't say, \"Stop!\"</code>
     * </p>
     *
     * @param str 要转义的字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeJava(String str) {
        return escapeJavaStyleString(str, false, false);
    }

    /**
     * 按Java的规则对字符串进行转义。
     * <p>
     * 将双引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成<code>He didn't say, \"Stop!\"</code>
     * </p>
     *
     * @param str    要转义的字符串
     * @param strict 是否以严格的方式编码字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeJava(String str, boolean strict) {
        return escapeJavaStyleString(str, false, strict);
    }

    /**
     * 按Java或JavaScript的规则对字符串进行转义。
     *
     * @param str                要转义的字符串
     * @param escapeSingleQuotes 是否对单引号进行转义
     * @param strict             是否以严格的方式编码字符串
     * @return 转义后的字符串
     */
    private static String escapeJavaStyleString(String str, boolean escapeSingleQuotes, boolean strict) {
        if (str == null) {
            return null;
        }

        StringBuilder buffer = new StringBuilder();
        boolean modified = false;
        int length = str.length();

        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);

            if (ch < 32) {
                switch (ch) {
                    case '\b':
                        buffer.append('\\');
                        buffer.append('b');
                        break;

                    case '\n':
                        buffer.append('\\');
                        buffer.append('n');
                        break;

                    case '\t':
                        buffer.append('\\');
                        buffer.append('t');
                        break;

                    case '\f':
                        buffer.append('\\');
                        buffer.append('f');
                        break;

                    case '\r':
                        buffer.append('\\');
                        buffer.append('r');
                        break;

                    default:

                        if (ch > 0xf) {
                            buffer.append("\\u00" + Integer.toHexString(ch).toUpperCase());
                        } else {
                            buffer.append("\\u000" + Integer.toHexString(ch).toUpperCase());
                        }

                        break;
                }

                // 设置改变标志
                modified = true;
            } else if (strict && ch > 0xff) {
                if (ch > 0xfff) {
                    buffer.append("\\u" + Integer.toHexString(ch).toUpperCase());
                } else {
                    buffer.append("\\u0" + Integer.toHexString(ch).toUpperCase());
                }

                // 设置改变标志
                modified = true;
            } else {
                switch (ch) {
                    case '\'':

                        if (escapeSingleQuotes) {
                            buffer.append('\\');

                            // 设置改变标志
                            modified = true;
                        }

                        buffer.append('\'');

                        break;

                    case '"':
                        buffer.append('\\');
                        buffer.append('"');

                        // 设置改变标志
                        modified = true;
                        break;

                    case '\\':
                        buffer.append('\\');
                        buffer.append('\\');

                        // 设置改变标志
                        modified = true;
                        break;

                    default:
                        buffer.append(ch);
                        break;
                }
            }
        }

        return modified ? buffer.toString() : str;
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     *
     * @param str 不包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeJava(String str) {
        return unescapeJavaStyleString(str);
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     *
     * @param str 包含转义字符的字符串
     * @return 不包含转义字符的字符串
     */
    private static String unescapeJavaStyleString(String str) {
        if (str == null) {
            return null;
        }

        StringBuilder buffer = new StringBuilder();
        StringBuilder unicode = new StringBuilder(4);
        boolean modified = false;
        int length = str.length();
        boolean hadSlash = false;
        boolean inUnicode = false;

        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);

            if (inUnicode) {
                unicode.append(ch);

                if (unicode.length() == 4) {
                    String unicodeStr = unicode.toString();

                    try {
                        int value = Integer.parseInt(unicodeStr, 16);

                        buffer.append((char) value);

                        // 设置改变标志
                        modified = true;
                    } catch (NumberFormatException e) {
                        buffer.append("\\u" + unicodeStr);
                    }

                    unicode.setLength(0);
                    inUnicode = false;
                    hadSlash = false;
                }

                continue;
            }

            if (hadSlash) {
                hadSlash = false;

                switch (ch) {
                    case '\\':
                        buffer.append('\\');

                        // 设置改变标志
                        modified = true;
                        break;

                    case '\'':
                        buffer.append('\'');

                        // 设置改变标志
                        modified = true;
                        break;

                    case '\"':
                        buffer.append('"');

                        // 设置改变标志
                        modified = true;
                        break;

                    case 'r':
                        buffer.append('\r');

                        // 设置改变标志
                        modified = true;
                        break;

                    case 'f':
                        buffer.append('\f');

                        // 设置改变标志
                        modified = true;
                        break;

                    case 't':
                        buffer.append('\t');

                        // 设置改变标志
                        modified = true;
                        break;

                    case 'n':
                        buffer.append('\n');

                        // 设置改变标志
                        modified = true;
                        break;

                    case 'b':
                        buffer.append('\b');

                        // 设置改变标志
                        modified = true;
                        break;

                    case 'u': {
                        inUnicode = true;
                        break;
                    }

                    default:
                        buffer.append(ch);
                        break;
                }

                continue;
            } else if (ch == '\\') {
                hadSlash = true;
                continue;
            }

            buffer.append(ch);
        }

        if (hadSlash) {
            buffer.append('\\');
        }

        return modified ? buffer.toString() : str;
    }

    private final static Pattern CRLF_PATTERN = Pattern.compile("\\r|\\n|\\r\\n");

    /**
     * 从第二行开始，对每一行缩进指定空白。
     */
    public static String indent(String str, String indent) {
        if (isEmpty(str)) {
            return str;
        }

        StringBuilder buf = new StringBuilder();

        indent(buf, str, indent);

        return buf.toString();
    }

    /**
     * 从第二行开始，对每一行缩进指定空白。
     */
    public static void indent(Formatter buf, String str, String indent) {
        if (buf.out() instanceof StringBuilder) {
            indent((StringBuilder) buf.out(), str, indent);
        } else {
            buf.format("%s", indent(str, indent));
        }
    }

    /**
     * 从第二行开始，对每一行缩进指定空白。
     */
    public static void indent(StringBuilder buf, String str, String indent) {
        if (isEmpty(str)) {
            return;
        }

        indent = defaultIfNull(indent, EMPTY_STRING);

        if (isEmpty(indent)) {
            buf.append(str);
            return;
        }

        Matcher matcher = CRLF_PATTERN.matcher(str);
        int index = 0;

        while (matcher.find()) {
            if (index < matcher.start() && index > 0) {
                buf.append(indent);
            }

            buf.append(str, index, matcher.end());
            index = matcher.end();
        }

        if (index < str.length()) {
            if (index > 0) {
                buf.append(indent);
            }

            buf.append(str, index, str.length());
        }
    }

    /**
     * 将字符串的首字符转成大写（<code>Character.toTitleCase</code>），其它字符不变。
     * <p>
     * 如果字符串是<code>null</code>则返回<code>null</code>。
     * <p/>
     * <pre>
     * StringUtil.capitalize(null)  = null
     * StringUtil.capitalize(&quot;&quot;)    = &quot;&quot;
     * StringUtil.capitalize(&quot;cat&quot;) = &quot;Cat&quot;
     * StringUtil.capitalize(&quot;cAt&quot;) = &quot;CAt&quot;
     * </pre>
     * <p/>
     * </p>
     *
     * @param str 要转换的字符串
     * @return 首字符为大写的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String capitalize(String str) {
        int strLen;

        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        return new StringBuilder(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1))
                .toString();
    }

    /**
     * 将字符串的首字符转成小写，其它字符不变。
     * <p>
     * 如果字符串是<code>null</code>则返回<code>null</code>。
     * <p/>
     * <pre>
     * StringUtil.uncapitalize(null)  = null
     * StringUtil.uncapitalize(&quot;&quot;)    = &quot;&quot;
     * StringUtil.uncapitalize(&quot;Cat&quot;) = &quot;cat&quot;
     * StringUtil.uncapitalize(&quot;CAT&quot;) = &quot;CAT&quot;
     * </pre>
     * <p/>
     * </p>
     *
     * @param str 要转换的字符串
     * @return 首字符为小写的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String uncapitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        if (str.length() > 1 && Character.isUpperCase(str.charAt(1)) && Character.isUpperCase(str.charAt(0))) {
            return str;
        }

        char chars[] = str.toCharArray();

        chars[0] = Character.toLowerCase(chars[0]);

        return new String(chars);
    }

    /**
     * 当对象为<code>null</code>时，返回默认值。
     */
    public static <T, S extends T> T defaultIfNull(T object, S defaultValue) {
        return object == null ? defaultValue : object;
    }

    /**
     * 检查数组是否为<code>null</code>或空数组<code>[]</code>。
     * <p/>
     * <pre>
     * ArrayUtil.isEmptyArray(null)              = true
     * ArrayUtil.isEmptyArray(new int[0])        = true
     * ArrayUtil.isEmptyArray(new int[10])       = false
     * </pre>
     *
     * @param array 要检查的数组
     * @return 如果为空, 则返回<code>true</code>
     */
    public static boolean isEmptyArray(Object array) {
        if (array == null) {
            return true;
        }

        if (!array.getClass().isArray()) {
            return false;
        }

        return Array.getLength(array) == 0;
    }
}
