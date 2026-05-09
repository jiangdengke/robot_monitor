/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.exception.DemoModeException
 *  com.robotmonitor.common.exception.ServiceException
 *  com.robotmonitor.common.exception.user.CaptchaException
 *  com.robotmonitor.common.exception.user.CaptchaExpireException
 *  com.robotmonitor.common.exception.user.UserPasswordNotMatchException
 *  jakarta.servlet.http.HttpServletRequest
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.validation.BindException
 *  org.springframework.validation.ObjectError
 *  org.springframework.web.HttpRequestMethodNotSupportedException
 *  org.springframework.web.bind.MethodArgumentNotValidException
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.RestControllerAdvice
 */
package com.robotmonitor.framework.web.exception;

import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.exception.DemoModeException;
import com.robotmonitor.common.exception.ServiceException;
import com.robotmonitor.common.exception.user.CaptchaException;
import com.robotmonitor.common.exception.user.CaptchaExpireException;
import com.robotmonitor.common.exception.user.UserPasswordNotMatchException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR_MESSAGE = "\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5";
    private static final String METHOD_ERROR_MESSAGE = "\u4e0d\u652f\u6301\u8bf7\u6c42\u7684\u65b9\u6cd5";
    private static final String LOGIN_ERROR_MESSAGE = "\u7528\u6237\u4e0d\u5b58\u5728/\u5bc6\u7801\u9519\u8bef";
    private static final String CAPTCHA_ERROR_MESSAGE = "\u9a8c\u8bc1\u7801\u9519\u8bef";

    @ExceptionHandler(value={AccessDeniedException.class})
    public AjaxResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("\u8bf7\u6c42\u5730\u5740'{}',\u6743\u9650\u6821\u9a8c\u5931\u8d25'{}'", (Object)requestURI, (Object)e.getMessage());
        return AjaxResult.error((int)403, (String)"\u6ca1\u6709\u6743\u9650\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u6388\u6743");
    }

    @ExceptionHandler(value={HttpRequestMethodNotSupportedException.class})
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("\u8bf7\u6c42\u5730\u5740'{}',\u4e0d\u652f\u6301'{}'\u8bf7\u6c42", (Object)requestURI, (Object)e.getMethod());
        return AjaxResult.error((String)METHOD_ERROR_MESSAGE);
    }

    @ExceptionHandler(value={ServiceException.class})
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage(), (Throwable)e);
        return AjaxResult.error((String)ERROR_MESSAGE);
    }

    @ExceptionHandler(value={RuntimeException.class})
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("\u8bf7\u6c42\u5730\u5740'{}',\u53d1\u751f\u672a\u77e5\u5f02\u5e38.", (Object)requestURI, (Object)e);
        if (e instanceof UserPasswordNotMatchException) {
            return AjaxResult.error((String)LOGIN_ERROR_MESSAGE);
        }
        if (e instanceof CaptchaException || e instanceof CaptchaExpireException) {
            return AjaxResult.error((String)CAPTCHA_ERROR_MESSAGE);
        }
        return AjaxResult.error((String)ERROR_MESSAGE);
    }

    @ExceptionHandler(value={Exception.class})
    public AjaxResult handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("\u8bf7\u6c42\u5730\u5740'{}',\u53d1\u751f\u7cfb\u7edf\u5f02\u5e38.", (Object)requestURI, (Object)e);
        return AjaxResult.error((String)ERROR_MESSAGE);
    }

    @ExceptionHandler(value={BindException.class})
    public AjaxResult handleBindException(BindException e) {
        log.error(e.getMessage(), (Throwable)e);
        String message = ((ObjectError)e.getAllErrors().get(0)).getDefaultMessage();
        return AjaxResult.error((String)message);
    }

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), (Throwable)e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error((String)message);
    }

    @ExceptionHandler(value={DemoModeException.class})
    public AjaxResult handleDemoModeException(DemoModeException e) {
        return AjaxResult.error((String)"\u6f14\u793a\u6a21\u5f0f\uff0c\u4e0d\u5141\u8bb8\u64cd\u4f5c");
    }
}
