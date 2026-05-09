/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.system.service.ISysMenuService
 *  com.robotmonitor.system.service.ISysRoleService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.framework.web.service;

import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.system.service.ISysMenuService;
import com.robotmonitor.system.service.ISysRoleService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SysPermissionService {
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysMenuService menuService;

    public Set<String> getRolePermission(SysUser user) {
        HashSet<String> roles = new HashSet<String>();
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(this.roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    public Set<String> getMenuPermission(SysUser user) {
        HashSet<String> perms = new HashSet<String>();
        if (user.isAdmin()) {
            perms.add("*:*:*");
        } else {
            perms.addAll(this.menuService.selectMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }
}
