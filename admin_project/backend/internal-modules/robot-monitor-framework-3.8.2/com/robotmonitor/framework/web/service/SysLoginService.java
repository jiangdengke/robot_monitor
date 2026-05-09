/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.core.domain.model.LoginUser
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.exception.ServiceException
 *  com.robotmonitor.common.exception.user.CaptchaException
 *  com.robotmonitor.common.exception.user.CaptchaExpireException
 *  com.robotmonitor.common.exception.user.UserPasswordNotMatchException
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.MessageUtils
 *  com.robotmonitor.common.utils.ServletUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.ip.IpUtils
 *  com.robotmonitor.system.service.ISysConfigService
 *  com.robotmonitor.system.service.ISysUserService
 *  jakarta.annotation.Resource
 *  jakarta.servlet.http.HttpServletRequest
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.authentication.BadCredentialsException
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.framework.web.service;

import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.core.domain.model.LoginUser;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.exception.ServiceException;
import com.robotmonitor.common.exception.user.CaptchaException;
import com.robotmonitor.common.exception.user.CaptchaExpireException;
import com.robotmonitor.common.exception.user.UserPasswordNotMatchException;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.MessageUtils;
import com.robotmonitor.common.utils.ServletUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.ip.IpUtils;
import com.robotmonitor.framework.manager.AsyncManager;
import com.robotmonitor.framework.manager.factory.AsyncFactory;
import com.robotmonitor.framework.web.service.TokenService;
import com.robotmonitor.system.service.ISysConfigService;
import com.robotmonitor.system.service.ISysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysConfigService configService;

    public String login(String username, String password, String code, String uuid) {
        boolean captchaOnOff = this.configService.selectCaptchaOnOff();
        if (captchaOnOff) {
            this.validateCaptcha(username, code, uuid);
        }
        Authentication authentication = null;
        try {
            authentication = this.authenticationManager.authenticate((Authentication)new UsernamePasswordAuthenticationToken((Object)username, (Object)password));
        }
        catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", MessageUtils.message((String)"user.password.not.match", (Object[])new Object[0]), new Object[0]));
                throw new UserPasswordNotMatchException();
            }
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", e.getMessage(), new Object[0]));
            throw new ServiceException(e.getMessage());
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Success", MessageUtils.message((String)"user.login.success", (Object[])new Object[0]), new Object[0]));
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        this.recordLoginInfo(loginUser.getUserId());
        return this.tokenService.createToken(loginUser);
    }

    public String robotLogin(String username, String password) {
        Authentication authentication = null;
        try {
            authentication = this.authenticationManager.authenticate((Authentication)new UsernamePasswordAuthenticationToken((Object)username, (Object)password));
        }
        catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", MessageUtils.message((String)"user.password.not.match", (Object[])new Object[0]), new Object[0]));
                throw new UserPasswordNotMatchException();
            }
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", e.getMessage(), new Object[0]));
            throw new ServiceException(e.getMessage());
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Success", MessageUtils.message((String)"user.login.success", (Object[])new Object[0]), new Object[0]));
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        this.recordLoginInfo(loginUser.getUserId());
        return this.tokenService.createToken(loginUser);
    }

    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = "captcha_codes:" + (String)StringUtils.nvl((Object)uuid, (Object)"");
        String captcha = (String)this.redisCache.getCacheObject(verifyKey);
        this.redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", MessageUtils.message((String)"user.jcaptcha.expire", (Object[])new Object[0]), new Object[0]));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", MessageUtils.message((String)"user.jcaptcha.error", (Object[])new Object[0]), new Object[0]));
            throw new CaptchaException();
        }
    }

    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr((HttpServletRequest)ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        this.userService.updateUserProfile(sysUser);
    }
}
