/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSON
 *  com.robotmonitor.common.annotation.RepeatSubmit
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.utils.ServletUtils
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.stereotype.Component
 *  org.springframework.web.method.HandlerMethod
 *  org.springframework.web.servlet.HandlerInterceptor
 */
package com.robotmonitor.framework.interceptor;

import com.alibaba.fastjson2.JSON;
import com.robotmonitor.common.annotation.RepeatSubmit;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public abstract class RepeatSubmitInterceptor
implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null && this.isRepeatSubmit(request, annotation)) {
                AjaxResult ajaxResult = AjaxResult.error((String)annotation.message());
                ServletUtils.renderString((HttpServletResponse)response, (String)JSON.toJSONString((Object)ajaxResult));
                return false;
            }
            return true;
        }
        return true;
    }

    public abstract boolean isRepeatSubmit(HttpServletRequest var1, RepeatSubmit var2);
}
