/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.StringUtils
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.quartz.task;

import com.robotmonitor.common.utils.StringUtils;
import org.springframework.stereotype.Component;

@Component(value="ryTask")
public class RyTask {
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format((String)"\u6267\u884c\u591a\u53c2\u65b9\u6cd5\uff1a \u5b57\u7b26\u4e32\u7c7b\u578b{}\uff0c\u5e03\u5c14\u7c7b\u578b{}\uff0c\u957f\u6574\u578b{}\uff0c\u6d6e\u70b9\u578b{}\uff0c\u6574\u5f62{}", (Object[])new Object[]{s, b, l, d, i}));
    }

    public void ryParams(String params) {
        System.out.println("\u6267\u884c\u6709\u53c2\u65b9\u6cd5\uff1a" + params);
    }

    public void ryNoParams() {
        System.out.println("\u6267\u884c\u65e0\u53c2\u65b9\u6cd5");
    }
}
