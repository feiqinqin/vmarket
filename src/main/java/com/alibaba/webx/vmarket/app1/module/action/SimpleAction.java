package com.alibaba.webx.vmarket.app1.module.action;

import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;

import com.alibaba.webx.vmarket.app1.SimpleObject;

public class SimpleAction {
    public void doGreeting(@FormGroup("simple") SimpleObject simple, Navigator nav) {
        String name = simple.getName();
        nav.redirectTo("app1Link").withTarget("hello").withParameter("name", name);
    }
}
