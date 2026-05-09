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
import com.robotmonitor.system.service.ISysConfigService;
import com.robotmonitor.system.service.ISysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SysLoginService {
    private static final Logger log = LoggerFactory.getLogger(SysLoginService.class);

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
        Authentication authentication;
        try {
            authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            log.error("Login authentication failed for user {}", username, e);
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", e.getMessage()));
            throw new ServiceException(e.getMessage());
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Success", MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        this.recordLoginInfo(loginUser.getUserId());
        return this.tokenService.createToken(loginUser);
    }

    public String robotLogin(String username, String password) {
        Authentication authentication;
        try {
            authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            log.error("Robot login authentication failed for user {}", username, e);
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", e.getMessage()));
            throw new ServiceException(e.getMessage());
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Success", MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        this.recordLoginInfo(loginUser.getUserId());
        return this.tokenService.createToken(loginUser);
    }

    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = "captcha_codes:" + StringUtils.nvl(uuid, "");
        String captcha = this.redisCache.getCacheObject(verifyKey);
        this.redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, "Error", MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr((HttpServletRequest) ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        this.userService.updateUserProfile(sysUser);
    }
}
