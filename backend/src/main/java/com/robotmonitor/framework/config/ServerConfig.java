/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.ServletUtils
 *  jakarta.servlet.http.HttpServletRequest
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.framework.config;

import com.robotmonitor.common.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ServerConfig {
    public String getUrl() {
        HttpServletRequest request = ServletUtils.getRequest();
        return ServerConfig.getDomain(request);
    }

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }
}
