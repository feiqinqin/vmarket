package web.tools;

import common.StringEscapeUtil;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

@Deprecated
@DefaultKey("securityUtil")
@ValidScope(Scope.APPLICATION)
public class SecurityUtil extends StringEscapeUtil {
    public static String jsEncode(String str) {
        return StringEscapeUtil.escapeJavaScript(str);
    }

    public static String jsEncode(Object value) {
        if (value == null) {
            return null;
        }

        return StringEscapeUtil.escapeJavaScript(value.toString());
    }

    public static String ignoretext(Object value) {
        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
