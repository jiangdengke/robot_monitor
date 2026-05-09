/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSON
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.model.LoginUser
 *  com.robotmonitor.common.utils.ServletUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.web.authentication.logout.LogoutSuccessHandler
 */
package com.robotmonitor.framework.security.handle;

import com.alibaba.fastjson2.JSON;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.model.LoginUser;
import com.robotmonitor.common.utils.ServletUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.framework.manager.AsyncManager;
import com.robotmonitor.framework.manager.factory.AsyncFactory;
import com.robotmonitor.framework.web.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class LogoutSuccessHandlerImpl
implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = this.tokenService.getLoginUser(request);
        if (StringUtils.isNotNull((Object)loginUser)) {
            String userName = loginUser.getUsername();
            this.tokenService.delLoginUser(loginUser.getToken());
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, "Logout", "\u9000\u51fa\u6210\u529f", new Object[0]));
        }
        ServletUtils.renderString((HttpServletResponse)response, (String)JSON.toJSONString((Object)AjaxResult.error((int)200, (String)"\u9000\u51fa\u6210\u529f")));
    }
}
