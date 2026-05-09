/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.core.domain.model.LoginUser
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.framework.web.service.TokenService
 *  com.robotmonitor.system.service.ISysUserService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.system;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.core.domain.model.LoginUser;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.framework.web.service.TokenService;
import com.robotmonitor.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/system/user/profile"})
public class SysProfileController
extends BaseController {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private TokenService tokenService;

    @GetMapping
    public AjaxResult profile() {
        LoginUser loginUser = this.getLoginUser();
        SysUser user = loginUser.getUser();
        AjaxResult ajax = AjaxResult.success((Object)user);
        ajax.put("roleGroup", (Object)this.userService.selectUserRoleGroup(loginUser.getUsername()));
        ajax.put("postGroup", (Object)this.userService.selectUserPostGroup(loginUser.getUsername()));
        return ajax;
    }

    @Log(title="\u4e2a\u4eba\u4fe1\u606f", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        LoginUser loginUser = this.getLoginUser();
        SysUser sysUser = loginUser.getUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty((String)user.getPhonenumber()) && "1".equals(this.userService.checkPhoneUnique(user))) {
            return AjaxResult.error((String)("\u4fee\u6539\u7528\u6237'" + user.getUserName() + "'\u5931\u8d25\uff0c\u624b\u673a\u53f7\u7801\u5df2\u5b58\u5728"));
        }
        if (StringUtils.isNotEmpty((String)user.getEmail()) && "1".equals(this.userService.checkEmailUnique(user))) {
            return AjaxResult.error((String)("\u4fee\u6539\u7528\u6237'" + user.getUserName() + "'\u5931\u8d25\uff0c\u90ae\u7bb1\u8d26\u53f7\u5df2\u5b58\u5728"));
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        if (this.userService.updateUserProfile(user) > 0) {
            sysUser.setNickName(user.getNickName());
            sysUser.setPhonenumber(user.getPhonenumber());
            sysUser.setEmail(user.getEmail());
            sysUser.setSex(user.getSex());
            this.tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error((String)"\u4fee\u6539\u4e2a\u4eba\u4fe1\u606f\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
    }

    @Log(title="\u4e2a\u4eba\u4fe1\u606f", businessType=BusinessType.UPDATE)
    @PutMapping(value={"/updatePwd"})
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = this.getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword((String)oldPassword, (String)password)) {
            return AjaxResult.error((String)"\u4fee\u6539\u5bc6\u7801\u5931\u8d25\uff0c\u65e7\u5bc6\u7801\u9519\u8bef");
        }
        if (SecurityUtils.matchesPassword((String)newPassword, (String)password)) {
            return AjaxResult.error((String)"\u65b0\u5bc6\u7801\u4e0d\u80fd\u4e0e\u65e7\u5bc6\u7801\u76f8\u540c");
        }
        if (this.userService.resetUserPwd(userName, SecurityUtils.encryptPassword((String)newPassword)) > 0) {
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword((String)newPassword));
            this.tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error((String)"\u4fee\u6539\u5bc6\u7801\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
    }
}
