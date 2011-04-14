package web.tools;

import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

import java.lang.reflect.Array;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * �й��ַ�������Ĺ����ࡣ
 * <p>
 * ������е�ÿ�����������ԡ���ȫ���ش���<code>null</code>���������׳�<code>NullPointerException</code>��
 * </p>
 *
 * @author Michael Zhou
 */
@DefaultKey("stringUtil")
@ValidScope(Scope.APPLICATION)
public class StringUtil {
    private static final char[] DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int DIGITS_LENGTH = DIGITS.length;

    // ���ַ�����
    public static final String EMPTY_STRING = "";

    /**
     * ����ַ����Ƿ�Ϊ<code>null</code>����ַ���<code>""</code>��
     * <p/>
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty(&quot;&quot;)        = true
     * StringUtil.isEmpty(&quot; &quot;)       = false
     * StringUtil.isEmpty(&quot;bob&quot;)     = false
     * StringUtil.isEmpty(&quot;  bob  &quot;) = false
     * </pre>
     *
     * @param str Ҫ�����ַ���
     * @return ���Ϊ��, �򷵻�<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * ��ȥ�ַ���ͷβ���Ŀհף�����ַ�����<code>null</code>����Ȼ����<code>null</code>��
     * <p/>
     * <pre>
     * StringUtil.trim(null)          = null
     * StringUtil.trim(&quot;&quot;)            = &quot;&quot;
     * StringUtil.trim(&quot;     &quot;)       = &quot;&quot;
     * StringUtil.trim(&quot;abc&quot;)         = &quot;abc&quot;
     * StringUtil.trim(&quot;    abc    &quot;) = &quot;abc&quot;
     * </pre>
     *
     * @param str Ҫ������ַ���
     * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * ��ȥ�ַ���ͷβ���Ŀհף��������ַ����ǿ��ַ���<code>""</code>���򷵻�<code>null</code>��
     * <p/>
     * <pre>
     * StringUtil.trimToNull(null)          = null
     * StringUtil.trimToNull(&quot;&quot;)            = null
     * StringUtil.trimToNull(&quot;     &quot;)       = null
     * StringUtil.trimToNull(&quot;abc&quot;)         = &quot;abc&quot;
     * StringUtil.trimToNull(&quot;    abc    &quot;) = &quot;abc&quot;
     * </pre>
     *
     * @param str Ҫ������ַ���
     * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
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
     * ��ȥ�ַ���ͷβ���Ŀհף�����ַ�����<code>null</code>���򷵻ؿ��ַ���<code>""</code>��
     * <p/>
     * <pre>
     * StringUtil.trimToEmpty(null)          = &quot;&quot;
     * StringUtil.trimToEmpty(&quot;&quot;)            = &quot;&quot;
     * StringUtil.trimToEmpty(&quot;     &quot;)       = &quot;&quot;
     * StringUtil.trimToEmpty(&quot;abc&quot;)         = &quot;abc&quot;
     * StringUtil.trimToEmpty(&quot;    abc    &quot;) = &quot;abc&quot;
     * </pre>
     *
     * @param str Ҫ������ַ���
     * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
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
     * ����ַ�����<code>null</code>����ַ���<code>""</code>���򷵻�ָ��Ĭ���ַ��������򷵻��ַ�������
     * <p/>
     * <pre>
     * StringUtil.defaultIfEmpty(null, &quot;default&quot;)  = &quot;default&quot;
     * StringUtil.defaultIfEmpty(&quot;&quot;, &quot;default&quot;)    = &quot;default&quot;
     * StringUtil.defaultIfEmpty(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
     * StringUtil.defaultIfEmpty(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
     * </pre>
     *
     * @param str        Ҫת�����ַ���
     * @param defaultStr Ĭ���ַ���
     * @return �ַ��������ָ����Ĭ���ַ���
     */
    public static String defaultIfEmpty(String str, String defaultStr) {
        return str == null || str.length() == 0 ? defaultStr : str;
    }

    /**
     * ��һ��������ת����62���Ƶ��ַ�����
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
     * ��һ��byte����ת����62���Ƶ��ַ�����
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
     * ��Java�Ĺ�����ַ�������ת�塣
     * <p>
     * ��˫���źͿ����ַ�ת����<code>'\\'</code>��ͷ����ʽ������tab�Ʊ������ת����<code>\t</code>��
     * </p>
     * <p>
     * Java��JavaScript�ַ�����Ψһ����ǣ�JavaScript����Ե����Ž���ת�壬��Java����Ҫ��
     * </p>
     * <p>
     * ���磺�ַ�����<code>He didn't say, "Stop!"</code>��ת����<code>He didn't say, \"Stop!\"</code>
     * </p>
     *
     * @param str Ҫת����ַ���
     * @return ת�����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String escapeJava(String str) {
        return escapeJavaStyleString(str, false, false);
    }

    /**
     * ��Java�Ĺ�����ַ�������ת�塣
     * <p>
     * ��˫���źͿ����ַ�ת����<code>'\\'</code>��ͷ����ʽ������tab�Ʊ������ת����<code>\t</code>��
     * </p>
     * <p>
     * Java��JavaScript�ַ�����Ψһ����ǣ�JavaScript����Ե����Ž���ת�壬��Java����Ҫ��
     * </p>
     * <p>
     * ���磺�ַ�����<code>He didn't say, "Stop!"</code>��ת����<code>He didn't say, \"Stop!\"</code>
     * </p>
     *
     * @param str    Ҫת����ַ���
     * @param strict �Ƿ����ϸ�ķ�ʽ�����ַ���
     * @return ת�����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String escapeJava(String str, boolean strict) {
        return escapeJavaStyleString(str, false, strict);
    }

    /**
     * ��Java��JavaScript�Ĺ�����ַ�������ת�塣
     *
     * @param str                Ҫת����ַ���
     * @param escapeSingleQuotes �Ƿ�Ե����Ž���ת��
     * @param strict             �Ƿ����ϸ�ķ�ʽ�����ַ���
     * @return ת�����ַ���
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

                // ���øı��־
                modified = true;
            } else if (strict && ch > 0xff) {
                if (ch > 0xfff) {
                    buffer.append("\\u" + Integer.toHexString(ch).toUpperCase());
                } else {
                    buffer.append("\\u0" + Integer.toHexString(ch).toUpperCase());
                }

                // ���øı��־
                modified = true;
            } else {
                switch (ch) {
                    case '\'':

                        if (escapeSingleQuotes) {
                            buffer.append('\\');

                            // ���øı��־
                            modified = true;
                        }

                        buffer.append('\'');

                        break;

                    case '"':
                        buffer.append('\\');
                        buffer.append('"');

                        // ���øı��־
                        modified = true;
                        break;

                    case '\\':
                        buffer.append('\\');
                        buffer.append('\\');

                        // ���øı��־
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
     * ��Java�Ĺ�����ַ������з���ת�塣
     * <p>
     * <code>'\\'</code>��ͷ����ʽת������Ӧ���ַ�������<code>\t</code>����ת����tab�Ʊ��
     * </p>
     * <p>
     * ���ת������ܱ�ʶ���������������䡣
     * </p>
     *
     * @param str ������ת���ַ����ַ���
     * @return �ָ���δת����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String unescapeJava(String str) {
        return unescapeJavaStyleString(str);
    }

    /**
     * ��Java�Ĺ�����ַ������з���ת�塣
     * <p>
     * <code>'\\'</code>��ͷ����ʽת������Ӧ���ַ�������<code>\t</code>����ת����tab�Ʊ��
     * </p>
     * <p>
     * ���ת������ܱ�ʶ���������������䡣
     * </p>
     *
     * @param str ����ת���ַ����ַ���
     * @return ������ת���ַ����ַ���
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

                        // ���øı��־
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

                        // ���øı��־
                        modified = true;
                        break;

                    case '\'':
                        buffer.append('\'');

                        // ���øı��־
                        modified = true;
                        break;

                    case '\"':
                        buffer.append('"');

                        // ���øı��־
                        modified = true;
                        break;

                    case 'r':
                        buffer.append('\r');

                        // ���øı��־
                        modified = true;
                        break;

                    case 'f':
                        buffer.append('\f');

                        // ���øı��־
                        modified = true;
                        break;

                    case 't':
                        buffer.append('\t');

                        // ���øı��־
                        modified = true;
                        break;

                    case 'n':
                        buffer.append('\n');

                        // ���øı��־
                        modified = true;
                        break;

                    case 'b':
                        buffer.append('\b');

                        // ���øı��־
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
     * �ӵڶ��п�ʼ����ÿһ������ָ���հס�
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
     * �ӵڶ��п�ʼ����ÿһ������ָ���հס�
     */
    public static void indent(Formatter buf, String str, String indent) {
        if (buf.out() instanceof StringBuilder) {
            indent((StringBuilder) buf.out(), str, indent);
        } else {
            buf.format("%s", indent(str, indent));
        }
    }

    /**
     * �ӵڶ��п�ʼ����ÿһ������ָ���հס�
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
     * ���ַ��������ַ�ת�ɴ�д��<code>Character.toTitleCase</code>���������ַ����䡣
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
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
     * @param str Ҫת�����ַ���
     * @return ���ַ�Ϊ��д���ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
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
     * ���ַ��������ַ�ת��Сд�������ַ����䡣
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
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
     * @param str Ҫת�����ַ���
     * @return ���ַ�ΪСд���ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
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
     * ������Ϊ<code>null</code>ʱ������Ĭ��ֵ��
     */
    public static <T, S extends T> T defaultIfNull(T object, S defaultValue) {
        return object == null ? defaultValue : object;
    }

    /**
     * ��������Ƿ�Ϊ<code>null</code>�������<code>[]</code>��
     * <p/>
     * <pre>
     * ArrayUtil.isEmptyArray(null)              = true
     * ArrayUtil.isEmptyArray(new int[0])        = true
     * ArrayUtil.isEmptyArray(new int[10])       = false
     * </pre>
     *
     * @param array Ҫ��������
     * @return ���Ϊ��, �򷵻�<code>true</code>
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
