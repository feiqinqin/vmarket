package web.tools;

import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

/**
 * Created by IntelliJ IDEA.
 * User: czy-thinkpad
 * Date: 11-4-12
 * Time: ÏÂÎç10:59
 * To change this template use File | Settings | File Templates.
 */
@DefaultKey("uiModule")
@ValidScope(Scope.APPLICATION)
public class UiModuleTool {

    private String staticUrl = "assets.daily.taobao.net";
    
    private String assetsUrl = "assets.daily.taobao.net";

    public void setAssetsUrl(String assetsUrl) {
        this.assetsUrl = assetsUrl;
        this.staticUrl = assetsUrl;
    }

    public String render() {
        try {
            return "http://" + this.assetsUrl;
        } catch (Exception e) {
            
        } finally {
            this.assetsUrl = staticUrl;
        }
        return "";
    }

    public UiModuleTool setTarget(String url) {
        this.assetsUrl += url;
        return this;
    }

    public UiModuleTool addQueryData(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.assetsUrl);
        if(this.assetsUrl.indexOf("?") != -1) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        sb.append(key).append("=").append(value);
        this.assetsUrl = sb.toString();
        return this;
    }

    @Override
    public String toString() {
        return render();
    }
}

