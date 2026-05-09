/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.core.domain.model.RegisterBody
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.exception.user.CaptchaException
 *  com.robotmonitor.common.exception.user.CaptchaExpireException
 *  com.robotmonitor.common.utils.MessageUtils
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.system.service.ISysConfigService
 *  com.robotmonitor.system.service.ISysUserService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.framework.web.service;

import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.core.domain.model.RegisterBody;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.exception.user.CaptchaException;
import com.robotmonitor.common.exception.user.CaptchaExpireException;
import com.robotmonitor.common.utils.MessageUtils;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.framework.manager.AsyncManager;
import com.robotmonitor.framework.manager.factory.AsyncFactory;
import com.robotmonitor.system.service.ISysConfigService;
import com.robotmonitor.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SysRegisterService {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private RedisCache redisCache;

    public String register(RegisterBody registerBody) {
        Object msg = "";
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();
        boolean captchaOnOff = this.configService.selectCaptchaOnOff();
        if (captchaOnOff) {
            this.validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }
        if (StringUtils.isEmpty((String)username)) {
            msg = "\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a";
        } else if (StringUtils.isEmpty((String)password)) {
            msg = "\u7528\u6237\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a";
        } else if (username.length() < 2 || username.length() > 20) {
            msg = "\u8d26\u6237\u957f\u5ea6\u5fc5\u987b\u57282\u523020\u4e2a\u5b57\u7b26\u4e4b\u95f4";
        } else if (password.length() < 5 || password.length() > 20) {
            msg = "\u5bc6\u7801\u957f\u5ea6\u5fc5\u987b\u57285\u523020\u4e2a\u5b57\u7b26\u4e4b\u95f4";
        } else if ("1".equals(this.userService.checkUserNameUnique(username))) {
            msg = "\u4fdd\u5b58\u7528\u6237'" + username + "'\u5931\u8d25\uff0c\u6ce8\u518c\u8d26\u53f7\u5df2\u5b58\u5728";
        } else {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(username);
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword((String)registerBody.getPassword()));
            boolean regFlag = this.userService.registerUser(sysUser);
            if (!regFlag) {
                msg = "\u6ce8\u518c\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u4eba\u5458";
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Register", MessageUtils.message((String)"user.register.success", (Object[])new Object[0]), new Object[0]));
            }
        }
        return msg;
    }

    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = "captcha_codes:" + (String)StringUtils.nvl((Object)uuid, (Object)"");
        String captcha = (String)this.redisCache.getCacheObject(verifyKey);
        this.redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }
}
