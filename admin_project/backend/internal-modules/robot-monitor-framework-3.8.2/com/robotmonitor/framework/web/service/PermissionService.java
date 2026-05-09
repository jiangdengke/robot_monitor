/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.entity.SysRole
 *  com.robotmonitor.common.core.domain.model.LoginUser
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  org.springframework.stereotype.Service
 *  org.springframework.util.CollectionUtils
 */
package com.robotmonitor.framework.web.service;

import com.robotmonitor.common.core.domain.entity.SysRole;
import com.robotmonitor.common.core.domain.model.LoginUser;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.common.utils.StringUtils;
import java.util.Collection;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service(value="ss")
public class PermissionService {
    private static final String ALL_PERMISSION = "*:*:*";
    private static final String SUPER_ADMIN = "admin";
    private static final String ROLE_DELIMETER = ",";
    private static final String PERMISSION_DELIMETER = ",";

    public boolean hasPermi(String permission) {
        if (StringUtils.isEmpty((String)permission)) {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull((Object)loginUser) || CollectionUtils.isEmpty((Collection)loginUser.getPermissions())) {
            return false;
        }
        return this.hasPermissions(loginUser.getPermissions(), permission);
    }

    public boolean lacksPermi(String permission) {
        return !this.hasPermi(permission);
    }

    public boolean hasAnyPermi(String permissions) {
        if (StringUtils.isEmpty((String)permissions)) {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull((Object)loginUser) || CollectionUtils.isEmpty((Collection)loginUser.getPermissions())) {
            return false;
        }
        Set authorities = loginUser.getPermissions();
        for (String permission : permissions.split(",")) {
            if (permission == null || !this.hasPermissions(authorities, permission)) continue;
            return true;
        }
        return false;
    }

    public boolean hasRole(String role) {
        if (StringUtils.isEmpty((String)role)) {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull((Object)loginUser) || CollectionUtils.isEmpty((Collection)loginUser.getUser().getRoles())) {
            return false;
        }
        for (SysRole sysRole : loginUser.getUser().getRoles()) {
            String roleKey = sysRole.getRoleKey();
            if (!SUPER_ADMIN.equals(roleKey) && !roleKey.equals(StringUtils.trim((String)role))) continue;
            return true;
        }
        return false;
    }

    public boolean lacksRole(String role) {
        return !this.hasRole(role);
    }

    public boolean hasAnyRoles(String roles) {
        if (StringUtils.isEmpty((String)roles)) {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull((Object)loginUser) || CollectionUtils.isEmpty((Collection)loginUser.getUser().getRoles())) {
            return false;
        }
        for (String role : roles.split(",")) {
            if (!this.hasRole(role)) continue;
            return true;
        }
        return false;
    }

    private boolean hasPermissions(Set<String> permissions, String permission) {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim((String)permission));
    }
}
