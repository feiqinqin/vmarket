package web.tools;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;
import org.apache.velocity.tools.generic.SafeConfig;
import org.apache.velocity.tools.generic.ValueParser;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * control tools
 */
@DefaultKey("control")
@ValidScope(Scope.REQUEST)
public class ControlTool extends SafeConfig {
    /**
     * The maximum number of loops allowed when recursing.
     *
     * @since VelocityTools 1.2
     */
    public static final int DEFAULT_PARSE_DEPTH = 20;

    public static final String KEY_FORCE_THREAD_SAFE = "forceThreadSafe";

    private static final String LOG_TAG = "RenderTool.eval()";

    private VelocityEngine engine = null;
    private Context context;
    private int parseDepth = DEFAULT_PARSE_DEPTH;
    private boolean catchExceptions = true;
    private boolean forceThreadSafe = true;

    protected ServletContext application;

    private String template;

    private Map<String, Object> contextParameters = new HashMap<String, Object>();

    /**
     * Looks for deprecated parse depth and catch.exceptions properties,
     * as well as any 'forceThreadSafe' setting.
     */
    protected void configure(ValueParser parser) {
        // check if they want thread-safety manually turned off
        this.forceThreadSafe =
                parser.getBoolean(KEY_FORCE_THREAD_SAFE, forceThreadSafe);
        // if we're request-scoped, then there's no point in forcing the issue
        if (Scope.REQUEST.equals(parser.getString("scope"))) {
            this.forceThreadSafe = false;
        }
    }

    /**
     * Sets the {@link ServletContext}. This is required
     * for this tool to operate and will throw a NullPointerException
     * if this is not set or is set to {@code null}.
     */
    public void setServletContext(ServletContext application)
    {
        if (application == null)
        {
            throw new NullPointerException("servlet context should not be null");
        }
        this.application = application;
    }

    /**
     * Allow user to specify a VelocityEngine to be used
     * in place of the Velocity singleton.
     */
    public void setVelocityEngine(VelocityEngine ve) {
        this.engine = ve;
    }

    public void setVelocityContext(Context context) {
        if (!isConfigLocked()) {
            if (context == null) {
                throw new NullPointerException("context must not be null");
            }
            this.context = context;
        } else if (this.context != context) {
            debug("RenderTool: Attempt was made to set a new context while config was locked.");
        }
    }

    /**
     * Set the maximum number of loops allowed when recursing.
     *
     * @since VelocityTools 1.2
     */
    public void setParseDepth(int depth) {
        if (!isConfigLocked()) {
            this.parseDepth = depth;
        } else if (this.parseDepth != depth) {
            debug("RenderTool: Attempt was made to alter parse depth while config was locked.");
        }
    }

    /**
     * Get the maximum number of loops allowed when recursing.
     *
     * @since VelocityTools 1.2
     */
    public int getParseDepth() {
        return this.parseDepth;
    }

    /**
     * Sets whether or not the render() and eval() methods should catch
     * exceptions during their execution or not.
     *
     * @since VelocityTools 1.3
     */
    public void setCatchExceptions(boolean catchExceptions) {
        if (!isConfigLocked()) {
            this.catchExceptions = catchExceptions;
        } else if (this.catchExceptions != catchExceptions) {
            debug("RenderTool: Attempt was made to alter catchE while config was locked.");
        }
    }

    /**
     * Returns <code>true</code> if this render() and eval() methods will
     * catch exceptions thrown during rendering.
     *
     * @since VelocityTools 1.3
     */
    public boolean getCatchExceptions() {
        return this.catchExceptions;
    }

    // internal convenience methods
    private void debug(String message) {
        if (engine == null) {
            Velocity.getLog().debug(message);
        } else {
            engine.getLog().debug(message);
        }
    }

    private void debug(String message, Throwable t) {
        if (engine == null) {
            Velocity.getLog().debug(message, t);
        } else {
            engine.getLog().debug(message, t);
        }
    }

    /* Internal implementation of the eval() method function. */
    protected String internalEval(Context ctx, String vtl) throws Exception {
        if (vtl == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        boolean success;
        if (engine == null) {
            success = Velocity.evaluate(ctx, sw, LOG_TAG, vtl);
        } else {
            success = engine.evaluate(ctx, sw, LOG_TAG, vtl);
        }
        if (success) {
            return sw.toString();
        }
        /* or would it be preferable to return the original? */
        return null;
    }

    /**
     * <p>Recursively evaluates a String containing VTL using the
     * current context, and returns the result as a String. It
     * will continue to re-evaluate the output of the last
     * evaluation until an evaluation returns the same code
     * that was fed into it or the number of recursive loops
     * exceeds the set parse depth.</p>
     *
     * @param ctx the current Context
     * @param vtl the code to be evaluated
     * @return the evaluated code as a String
     */
    public String recurse(Context ctx, String vtl) throws Exception {
        return internalRecurse(ctx, vtl, 0);
    }

    protected String internalRecurse(Context ctx, String vtl, int count) throws Exception {
        String result = eval(ctx, vtl);
        if (result == null || result.equals(vtl)) {
            return result;
        } else {
            // if we haven't reached our parse depth...
            if (count < parseDepth) {
                // continue recursing
                return internalRecurse(ctx, result, count + 1);
            } else {
                // abort, log and return what we have so far
                debug("RenderTool.recurse() exceeded the maximum parse depth of "
                        + parseDepth + "on the following template: " + vtl);
                return result;
            }
        }
    }


    /**
     * <p>Evaluates a String containing VTL using the current context,
     * and returns the result as a String.  By default if this fails, then
     * <code>null</code> will be returned, though this tool can be configured
     * to let Exceptions pass through. This evaluation is not recursive.</p>
     *
     * @param ctx the current Context
     * @param vtl the code to be evaluated
     * @return the evaluated code as a String
     */
    public String eval(Context ctx, String vtl) throws Exception {
        if (this.catchExceptions) {
            try {
                return internalEval(ctx, vtl);
            } catch (Exception e) {
                debug(LOG_TAG + " failed due to " + e, e);
                return null;
            }
        } else {
            return internalEval(ctx, vtl);
        }
    }


    public ControlTool setTemplate(String template) {
        this.template = template;
        return this;
    }

    public ControlTool setModule(String module) {
        return setTemplate(module);
    }

    public ControlTool setParameter(String key, Object value) {
        contextParameters.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return render();
    }

    private String render() {
        if(this.template == null || this.template.isEmpty()) {
            return "$control.setTemplate(null)";
        }
        debug("render once :" + this.template);
        VelocityContext context = new VelocityContext(this.context);
        for (Map.Entry<String, Object> stringObjectEntry : contextParameters.entrySet()) {
            context.put(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        StringBuilder builder = new StringBuilder();
        try {
            if(this.template.indexOf(":") != -1) {
                // absolute path
                File file = new File(this.template);
                if(file.exists()) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    int i;
                    while ((i = bufferedReader.read()) != -1) {
                        builder.append((char) i);
                    }
                    bufferedReader.close();
                } else {
                    return "$control.setTemplate£¨" +this.template + ")";
                }
            } else {
                if(!this.template.startsWith("/")) {
                    this.template = "/" + this.template;
                }
                String realPath = this.application.getRealPath("/");
                File file = new File(realPath + this.template);
                if(file.exists()) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    int i;
                    while ((i = bufferedReader.read()) != -1) {
                        builder.append((char) i);
                    }
                    bufferedReader.close();
                } else {
                    return "$control.setTemplate£¨" +this.template + ")";
                }
            }

            return recurse(context, builder.toString());
        } catch (Exception e) {
            debug(e.toString());
        } finally {
            contextParameters.clear();
            this.template = null;
        }
        return "$control.setTemplate£¨" +this.template + ")";
    }

}
