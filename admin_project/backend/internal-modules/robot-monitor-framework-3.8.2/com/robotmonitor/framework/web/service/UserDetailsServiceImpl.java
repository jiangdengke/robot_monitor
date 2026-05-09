/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.core.domain.model.LoginUser
 *  com.robotmonitor.common.enums.UserStatus
 *  com.robotmonitor.common.exception.ServiceException
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.system.service.ISysDeptService
 *  com.robotmonitor.system.service.ISysUserService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.core.userdetails.UserDetailsService
 *  org.springframework.security.core.userdetails.UsernameNotFoundException
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.framework.web.service;

import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.core.domain.model.LoginUser;
import com.robotmonitor.common.enums.UserStatus;
import com.robotmonitor.common.exception.ServiceException;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.framework.web.service.SysPermissionService;
import com.robotmonitor.system.service.ISysDeptService;
import com.robotmonitor.system.service.ISysUserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl
implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private ISysDeptService deptService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = this.userService.selectUserByUserName(username);
        if (StringUtils.isNull((Object)user)) {
            log.info("\u767b\u5f55\u7528\u6237\uff1a{} \u4e0d\u5b58\u5728.", (Object)username);
            throw new ServiceException("\u767b\u5f55\u7528\u6237\uff1a" + username + " \u4e0d\u5b58\u5728");
        }
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("\u767b\u5f55\u7528\u6237\uff1a{} \u5df2\u88ab\u5220\u9664.", (Object)username);
            throw new ServiceException("\u5bf9\u4e0d\u8d77\uff0c\u60a8\u7684\u8d26\u53f7\uff1a" + username + " \u5df2\u88ab\u5220\u9664");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("\u767b\u5f55\u7528\u6237\uff1a{} \u5df2\u88ab\u505c\u7528.", (Object)username);
            throw new ServiceException("\u5bf9\u4e0d\u8d77\uff0c\u60a8\u7684\u8d26\u53f7\uff1a" + username + " \u5df2\u505c\u7528");
        }
        return this.createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        List roomList = this.deptService.allRoomCode(String.valueOf(user.getDeptId()));
        return new LoginUser(user.getUserId(), user.getDeptId(), user, this.permissionService.getMenuPermission(user), roomList);
    }
}
