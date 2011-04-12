package web.tools;

import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;
import org.apache.velocity.tools.view.ImportSupport;

/**
 * use for tms
 */
@DefaultKey("tmsTool")
@ValidScope(Scope.REQUEST)
public class TmsTool extends ImportSupport {
    public String importRgn(String obj, Integer area) {
        if (obj == null) {
            LOG.warn("TmsTool.read(): url is null!");
            return null;
        }
        String url = String.valueOf(obj).trim();
        if (url.length() == 0) {
            LOG.warn("TmsTool.read(): url is empty string!");
            return null;
        }
        // tms prefix
        url = "http://www.taobao.com/go/" + url;
        try {
            return acquireString(url);
        } catch (Exception ex) {
            LOG.error("TmsTool.read(): Exception while aquiring '" + url + "'", ex);
            return null;
        }
    }
}
