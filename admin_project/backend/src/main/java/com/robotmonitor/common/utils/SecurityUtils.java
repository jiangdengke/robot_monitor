/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 */
package com.robotmonitor.common.utils;

import com.robotmonitor.common.core.domain.model.LoginUser;
import com.robotmonitor.common.exception.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtils {
    public static Long getUserId() {
        try {
            return SecurityUtils.getLoginUser().getUserId();
        }
        catch (Exception e) {
            throw new ServiceException("\u83b7\u53d6\u7528\u6237ID\u5f02\u5e38", 401);
        }
    }

    public static Long getDeptId() {
        try {
            return SecurityUtils.getLoginUser().getDeptId();
        }
        catch (Exception e) {
            throw new ServiceException("\u83b7\u53d6\u90e8\u95e8ID\u5f02\u5e38", 401);
        }
    }

    public static String getUsername() {
        try {
            return SecurityUtils.getLoginUser().getUsername();
        }
        catch (Exception e) {
            throw new ServiceException("\u83b7\u53d6\u7528\u6237\u8d26\u6237\u5f02\u5e38", 401);
        }
    }

    public static LoginUser getLoginUser() {
        try {
            return (LoginUser)SecurityUtils.getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            throw new ServiceException("\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u5f02\u5e38", 401);
        }
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode((CharSequence)password);
    }

    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches((CharSequence)rawPassword, encodedPassword);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
