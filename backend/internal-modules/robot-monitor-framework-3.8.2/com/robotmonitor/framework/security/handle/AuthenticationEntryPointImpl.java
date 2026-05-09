/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSON
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.utils.ServletUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.web.AuthenticationEntryPoint
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.framework.security.handle;

import com.alibaba.fastjson2.JSON;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.utils.ServletUtils;
import com.robotmonitor.common.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointImpl
implements AuthenticationEntryPoint,
Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        int code = 401;
        String msg = StringUtils.format((String)"\u8bf7\u6c42\u8bbf\u95ee\uff1a{}\uff0c\u8ba4\u8bc1\u5931\u8d25\uff0c\u65e0\u6cd5\u8bbf\u95ee\u7cfb\u7edf\u8d44\u6e90", (Object[])new Object[]{request.getRequestURI()});
        ServletUtils.renderString((HttpServletResponse)response, (String)JSON.toJSONString((Object)AjaxResult.error((int)code, (String)msg)));
    }
}
