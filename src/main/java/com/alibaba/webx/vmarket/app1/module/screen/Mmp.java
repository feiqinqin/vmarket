package com.alibaba.webx.vmarket.app1.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: zhangting
 * Date: 11-1-27
 * Time: ÏÂÎç12:04
 * To change this template use File | Settings | File Templates.
 */
public class Mmp {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    public void execute() {
        request.setAttribute("useMmpFrameWork", "true");
        request.setAttribute("mmpSubuserNick", "true");
    }
}
