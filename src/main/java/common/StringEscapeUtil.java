package common;

import java.io.IOException;
import java.util.BitSet;

/**
 * �ַ���ת�幤���࣬�ܽ��ַ���ת������Ӧ Java��Java Script��HTML��XML����SQL������ʽ��
 * 
 * @author Michael Zhou
 */
public class StringEscapeUtil {
    /* ================================================================== */
    /* Java��JavaScript�� */
    /* ================================================================== */

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
     * @param str Ҫת����ַ���
     * @param strict �Ƿ����ϸ�ķ�ʽ�����ַ���
     * @return ת�����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String escapeJava(String str, boolean strict) {
        return escapeJavaStyleString(str, false, strict);
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
     * @param out �����
     * @throws IllegalArgumentException ��������Ϊ<code>null</code>
     * @throws java.io.IOException ������ʧ��
     */
    public static void escapeJava(String str, Appendable out) throws IOException {
        escapeJavaStyleString(str, false, out, false);
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
     * @param out �����
     * @param strict �Ƿ����ϸ�ķ�ʽ�����ַ���
     * @throws IllegalArgumentException ��������Ϊ<code>null</code>
     * @throws java.io.IOException ������ʧ��
     */
    public static void escapeJava(String str, Appendable out, boolean strict) throws IOException {
        escapeJavaStyleString(str, false, out, strict);
    }

    /**
     * ��JavaScript�Ĺ�����ַ�������ת�塣
     * <p>
     * ��˫���š������źͿ����ַ�ת����<code>'\\'</code>��ͷ����ʽ������tab�Ʊ������ת����<code>\t</code>��
     * </p>
     * <p>
     * Java��JavaScript�ַ�����Ψһ����ǣ�JavaScript����Ե����Ž���ת�壬��Java����Ҫ��
     * </p>
     * <p>
     * ���磺�ַ�����<code>He didn't say, "Stop!"</code>��ת����<code>He didn\'t say, \"Stop!\"</code>
     * </p>
     *
     * @param str Ҫת����ַ���
     * @return ת�����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String escapeJavaScript(String str) {
        return escapeJavaStyleString(str, true, false);
    }

    /**
     * ��JavaScript�Ĺ�����ַ�������ת�塣
     * <p>
     * ��˫���š������źͿ����ַ�ת����<code>'\\'</code>��ͷ����ʽ������tab�Ʊ������ת����<code>\t</code>��
     * </p>
     * <p>
     * Java��JavaScript�ַ�����Ψһ����ǣ�JavaScript����Ե����Ž���ת�壬��Java����Ҫ��
     * </p>
     * <p>
     * ���磺�ַ�����<code>He didn't say, "Stop!"</code>��ת����<code>He didn\'t say, \"Stop!\"</code>
     * </p>
     *
     * @param str Ҫת����ַ���
     * @param strict �Ƿ����ϸ�ķ�ʽ�����ַ���
     * @return ת�����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String escapeJavaScript(String str, boolean strict) {
        return escapeJavaStyleString(str, true, strict);
    }

    /**
     * ��JavaScript�Ĺ�����ַ�������ת�塣
     * <p>
     * ��˫���š������źͿ����ַ�ת����<code>'\\'</code>��ͷ����ʽ������tab�Ʊ������ת����<code>\t</code>��
     * </p>
     * <p>
     * Java��JavaScript�ַ�����Ψһ����ǣ�JavaScript����Ե����Ž���ת�壬��Java����Ҫ��
     * </p>
     * <p>
     * ���磺�ַ�����<code>He didn't say, "Stop!"</code>��ת����<code>He didn\'t say, \"Stop!\"</code>
     * </p>
     *
     * @param str Ҫת����ַ���
     * @param out �����
     * @throws IllegalArgumentException ��������Ϊ<code>null</code>
     * @throws java.io.IOException ������ʧ��
     */
    public static void escapeJavaScript(String str, Appendable out) throws IOException {
        escapeJavaStyleString(str, true, out, false);
    }

    /**
     * ��JavaScript�Ĺ�����ַ�������ת�塣
     * <p>
     * ��˫���š������źͿ����ַ�ת����<code>'\\'</code>��ͷ����ʽ������tab�Ʊ������ת����<code>\t</code>��
     * </p>
     * <p>
     * Java��JavaScript�ַ�����Ψһ����ǣ�JavaScript����Ե����Ž���ת�壬��Java����Ҫ��
     * </p>
     * <p>
     * ���磺�ַ�����<code>He didn't say, "Stop!"</code>��ת����<code>He didn\'t say, \"Stop!\"</code>
     * </p>
     *
     * @param str Ҫת����ַ���
     * @param out �����
     * @param strict �Ƿ����ϸ�ķ�ʽ�����ַ���
     * @throws IllegalArgumentException ��������Ϊ<code>null</code>
     * @throws java.io.IOException ������ʧ��
     */
    public static void escapeJavaScript(String str, Appendable out, boolean strict) throws IOException {
        escapeJavaStyleString(str, true, out, strict);
    }

    /**
     * ��Java��JavaScript�Ĺ�����ַ�������ת�塣
     *
     * @param str Ҫת����ַ���
     * @param javascript �Ƿ�Ե����ź�slash����ת��
     * @param strict �Ƿ����ϸ�ķ�ʽ�����ַ���
     * @return ת�����ַ���
     */
    private static String escapeJavaStyleString(String str, boolean javascript, boolean strict) {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(str.length() * 2);

            if (escapeJavaStyleString(str, javascript, out, strict)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringBuilder�����ܷ�������쳣
        }
    }

    /**
     * ��Java��JavaScript�Ĺ�����ַ�������ת�塣
     *
     * @param str Ҫת����ַ���
     * @param javascript �Ƿ�Ե����ź�slash����ת��
     * @param out �����
     * @param strict �Ƿ����ϸ�ķ�ʽ�����ַ���
     * @return ����ַ���û�б仯���򷵻�<code>false</code>
     */
    private static boolean escapeJavaStyleString(String str, boolean javascript, Appendable out, boolean strict)
            throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        int length = str.length();

        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);

            if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                        break;

                    case '\n':
                        out.append('\\');
                        out.append('n');
                        break;

                    case '\t':
                        out.append('\\');
                        out.append('t');
                        break;

                    case '\f':
                        out.append('\\');
                        out.append('f');
                        break;

                    case '\r':
                        out.append('\\');
                        out.append('r');
                        break;

                    default:

                        if (ch > 0xf) {
                            out.append("\\u00" + Integer.toHexString(ch).toUpperCase());
                        } else {
                            out.append("\\u000" + Integer.toHexString(ch).toUpperCase());
                        }

                        break;
                }

                // ���øı��־
                needToChange = true;
            } else if (strict && ch > 0xff) {
                if (ch > 0xfff) {
                    out.append("\\u").append(Integer.toHexString(ch).toUpperCase());
                } else {
                    out.append("\\u0").append(Integer.toHexString(ch).toUpperCase());
                }

                // ���øı��־
                needToChange = true;
            } else {
                switch (ch) {
                    case '\'':
                    case '/': // ע�⣺����javascript����/����escape����Ҫ�İ�ȫ��ʩ��

                        if (javascript) {
                            out.append('\\');

                            // ���øı��־
                            needToChange = true;
                        }

                        out.append(ch);

                        break;

                    case '"':
                        out.append('\\');
                        out.append('"');

                        // ���øı��־
                        needToChange = true;
                        break;

                    case '\\':
                        out.append('\\');
                        out.append('\\');

                        // ���øı��־
                        needToChange = true;
                        break;

                    default:
                        out.append(ch);
                        break;
                }
            }
        }

        return needToChange;
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
     * @param out �����
     * @throws IllegalArgumentException ��������Ϊ<code>null</code>
     * @throws java.io.IOException ������ʧ��
     */
    public static void unescapeJava(String str, Appendable out) throws IOException {
        unescapeJavaStyleString(str, out);
    }

    /**
     * ��JavaScript�Ĺ�����ַ������з���ת�塣
     * <p>
     * <code>'\\'</code>��ͷ����ʽת������Ӧ���ַ�������<code>\t</code>����ת����tab�Ʊ��
     * </p>
     * <p>
     * ���ת������ܱ�ʶ���������������䡣
     * </p>
     *
     * @param str ����ת���ַ����ַ���
     * @return �ָ���δת����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String unescapeJavaScript(String str) {
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
     * @param out �����
     * @throws IllegalArgumentException ��������Ϊ<code>null</code>
     * @throws java.io.IOException ������ʧ��
     */
    public static void unescapeJavaScript(String str, Appendable out) throws IOException {
        unescapeJavaStyleString(str, out);
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

        try {
            StringBuilder out = new StringBuilder(str.length());

            if (unescapeJavaStyleString(str, out)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringBuilder�����ܷ�������쳣
        }
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
     * @param out �����
     * @return ����ַ���û�б仯���򷵻�<code>false</code>
     * @throws IllegalArgumentException ��������Ϊ<code>null</code>
     * @throws java.io.IOException ������ʧ��
     */
    private static boolean unescapeJavaStyleString(String str, Appendable out) throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        int length = str.length();
        StringBuffer unicode = new StringBuffer(4);
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

                        out.append((char) value);
                        unicode.setLength(0);
                        inUnicode = false;
                        hadSlash = false;

                        // ���øı��־
                        needToChange = true;
                    } catch (NumberFormatException e) {
                        out.append("\\u" + unicodeStr);
                    }
                }

                continue;
            }

            if (hadSlash) {
                hadSlash = false;

                switch (ch) {
                    case '\\':
                        out.append('\\');

                        // ���øı��־
                        needToChange = true;
                        break;

                    case '\'':
                        out.append('\'');

                        // ���øı��־
                        needToChange = true;
                        break;

                    case '\"':
                        out.append('"');

                        // ���øı��־
                        needToChange = true;
                        break;

                    case 'r':
                        out.append('\r');

                        // ���øı��־
                        needToChange = true;
                        break;

                    case 'f':
                        out.append('\f');

                        // ���øı��־
                        needToChange = true;
                        break;

                    case 't':
                        out.append('\t');

                        // ���øı��־
                        needToChange = true;
                        break;

                    case 'n':
                        out.append('\n');

                        // ���øı��־
                        needToChange = true;
                        break;

                    case 'b':
                        out.append('\b');

                        // ���øı��־
                        needToChange = true;
                        break;

                    case 'u': {
                        inUnicode = true;
                        break;
                    }

                    default:
                        out.append(ch);
                        break;
                }

                continue;
            } else if (ch == '\\') {
                hadSlash = true;
                continue;
            }

            out.append(ch);
        }

        if (hadSlash) {
            out.append('\\');
        }

        return needToChange;
    }

    /* ================================================================== */
    /* URL/URI encoding/decoding�� */
    /* ����RFC2396��http://www.ietf.org/rfc/rfc2396.txt */
    /* ================================================================== */

    /** "Alpha" characters from RFC 2396. */
    private static final BitSet ALPHA = new BitSet(256);

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            ALPHA.set(i);
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            ALPHA.set(i);
        }
    }

    /** "Alphanum" characters from RFC 2396. */
    private static final BitSet ALPHANUM = new BitSet(256);

    static {
        ALPHANUM.or(ALPHA);

        for (int i = '0'; i <= '9'; i++) {
            ALPHANUM.set(i);
        }
    }

    /** "Mark" characters from RFC 2396. */
    private static final BitSet MARK = new BitSet(256);

    static {
        MARK.set('-');
        MARK.set('_');
        MARK.set('.');
        MARK.set('!');
        MARK.set('~');
        MARK.set('*');
        MARK.set('\'');
        MARK.set('(');
        MARK.set(')');
    }

    /** "Reserved" characters from RFC 2396. */
    private static final BitSet RESERVED = new BitSet(256);

    static {
        RESERVED.set(';');
        RESERVED.set('/');
        RESERVED.set('?');
        RESERVED.set(':');
        RESERVED.set('@');
        RESERVED.set('&');
        RESERVED.set('=');
        RESERVED.set('+');
        RESERVED.set('$');
        RESERVED.set(',');
    }

    /** "Unreserved" characters from RFC 2396. */
    private static final BitSet UNRESERVED = new BitSet(256);

    static {
        UNRESERVED.or(ALPHANUM);
        UNRESERVED.or(MARK);
    }

    /** ��һ������ת����16���Ƶ�ת���� */
    private static char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };


    /**
     * �ж�ָ���ַ��Ƿ��ǡ���ȫ���ģ�����ַ�������ת����URL���롣
     *
     * @param ch Ҫ�жϵ��ַ�
     * @param strict �Ƿ����ϸ�ķ�ʽ����
     * @return ����ǡ���ȫ���ģ��򷵻�<code>true</code>
     */
    private static boolean isSafeCharacter(int ch, boolean strict) {
        if (strict) {
            return UNRESERVED.get(ch);
        } else {
            return ch > ' ' && !RESERVED.get(ch) && !Character.isWhitespace((char) ch);
        }
    }

}
