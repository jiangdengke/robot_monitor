/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSON
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.domain.model.LoginUser
 *  com.robotmonitor.common.enums.BusinessStatus
 *  com.robotmonitor.common.enums.HttpMethod
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.common.utils.ServletUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.ip.IpUtils
 *  com.robotmonitor.system.domain.SysOperLog
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.AfterReturning
 *  org.aspectj.lang.annotation.AfterThrowing
 *  org.aspectj.lang.annotation.Aspect
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Component
 *  org.springframework.validation.BindingResult
 *  org.springframework.web.multipart.MultipartFile
 *  org.springframework.web.servlet.HandlerMapping
 */
package com.robotmonitor.framework.aspectj;

import com.alibaba.fastjson2.JSON;
import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.domain.model.LoginUser;
import com.robotmonitor.common.enums.BusinessStatus;
import com.robotmonitor.common.enums.HttpMethod;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.common.utils.ServletUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.ip.IpUtils;
import com.robotmonitor.framework.manager.AsyncManager;
import com.robotmonitor.framework.manager.factory.AsyncFactory;
import com.robotmonitor.system.domain.SysOperLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @AfterReturning(pointcut="@annotation(controllerLog)", returning="jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        this.handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    @AfterThrowing(value="@annotation(controllerLog)", throwing="e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        this.handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(JoinPoint joinPoint, Log controllerLog, Exception e, Object jsonResult) {
        try {
            LoginUser loginUser = SecurityUtils.getLoginUser();
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(Integer.valueOf(BusinessStatus.SUCCESS.ordinal()));
            String ip = IpUtils.getIpAddr((HttpServletRequest)ServletUtils.getRequest());
            operLog.setOperIp(ip);
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (loginUser != null) {
                operLog.setOperName(loginUser.getUsername());
            }
            if (e != null) {
                operLog.setStatus(Integer.valueOf(BusinessStatus.FAIL.ordinal()));
                operLog.setErrorMsg(StringUtils.substring((String)e.getMessage(), (int)0, (int)2000));
            }
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            this.getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        }
        catch (Exception exp) {
            log.error("==\u524d\u7f6e\u901a\u77e5\u5f02\u5e38==");
            log.error("\u5f02\u5e38\u4fe1\u606f:{}", (Object)exp.getMessage());
            exp.printStackTrace();
        }
    }

    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, Object jsonResult) throws Exception {
        operLog.setBusinessType(Integer.valueOf(log.businessType().ordinal()));
        operLog.setTitle(log.title());
        operLog.setOperatorType(Integer.valueOf(log.operatorType().ordinal()));
        if (log.isSaveRequestData()) {
            this.setRequestValue(joinPoint, operLog);
        }
        if (log.isSaveResponseData() && StringUtils.isNotNull((Object)jsonResult)) {
            operLog.setJsonResult(StringUtils.substring((String)JSON.toJSONString((Object)jsonResult), (int)0, (int)2000));
        }
    }

    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = this.argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring((String)params, (int)0, (int)2000));
        } else {
            Map paramsMap = (Map)ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring((String)paramsMap.toString(), (int)0, (int)2000));
        }
    }

    private String argsArrayToString(Object[] paramsArray) {
        Object params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!StringUtils.isNotNull((Object)o) || this.isFilterObject(o)) continue;
                try {
                    Object jsonObj = JSON.toJSON((Object)o);
                    params = (String)params + jsonObj.toString() + " ";
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        return ((String)params).trim();
    }

    public boolean isFilterObject(Object o) {
        Map map;
        Iterator iterator;
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection)o;
            Iterator iterator2 = collection.iterator();
            if (iterator2.hasNext()) {
                Object value = iterator2.next();
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz) && (iterator = (map = (Map)o).entrySet().iterator()).hasNext()) {
            Map.Entry value;
            Map.Entry entry = value = iterator.next();
            return entry.getValue() instanceof MultipartFile;
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof BindingResult;
    }
}
