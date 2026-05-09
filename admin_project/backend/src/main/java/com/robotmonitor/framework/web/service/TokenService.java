/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.model.LoginUser
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.ServletUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.ip.AddressUtils
 *  com.robotmonitor.common.utils.ip.IpUtils
 *  com.robotmonitor.common.utils.uuid.IdUtils
 *  com.robotmonitor.system.service.ISysDeptService
 *  eu.bitwalker.useragentutils.UserAgent
 *  io.jsonwebtoken.Claims
 *  io.jsonwebtoken.Jwts
 *  io.jsonwebtoken.SignatureAlgorithm
 *  jakarta.servlet.http.HttpServletRequest
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.framework.web.service;

import com.robotmonitor.common.core.domain.model.LoginUser;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.ServletUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.ip.AddressUtils;
import com.robotmonitor.common.utils.ip.IpUtils;
import com.robotmonitor.common.utils.uuid.IdUtils;
import com.robotmonitor.system.service.ISysDeptService;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenService {
    @Value(value="${token.header}")
    private String header;
    @Value(value="${token.secret}")
    private String secret;
    @Value(value="${token.expireTime}")
    private int expireTime;
    protected static final long MILLIS_SECOND = 1000L;
    protected static final long MILLIS_MINUTE = 60000L;
    private static final Long MILLIS_MINUTE_TEN = 1200000L;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysDeptService deptService;

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = this.getToken(request);
        if (StringUtils.isNotEmpty((String)token)) {
            try {
                Claims claims = this.parseToken(token);
                String uuid = (String)claims.get((Object)"login_user_key");
                String userKey = this.getTokenKey(uuid);
                LoginUser user = (LoginUser)this.redisCache.getCacheObject(userKey);
                return user;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return null;
    }

    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull((Object)loginUser) && StringUtils.isNotEmpty((String)loginUser.getToken())) {
            long depId = loginUser.getDeptId();
            this.refreshToken(loginUser);
        }
    }

    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty((String)token)) {
            String userKey = this.getTokenKey(token);
            this.redisCache.deleteObject(userKey);
        }
    }

    public String createToken(LoginUser loginUser) {
        String token = IdUtils.randomUUID();
        loginUser.setToken(token);
        this.setUserAgent(loginUser);
        this.refreshToken(loginUser);
        HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("login_user_key", token);
        return this.createToken(claims);
    }

    public void verifyToken(LoginUser loginUser) {
        long currentTime;
        long expireTime = loginUser.getExpireTime();
        if (expireTime - (currentTime = System.currentTimeMillis()) <= MILLIS_MINUTE_TEN) {
            this.refreshToken(loginUser);
        }
    }

    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(Long.valueOf(System.currentTimeMillis()));
        loginUser.setExpireTime(Long.valueOf(loginUser.getLoginTime() + (long)this.expireTime * 60000L));
        String userKey = this.getTokenKey(loginUser.getToken());
        this.redisCache.setCacheObject(userKey, (Object)loginUser, Integer.valueOf(this.expireTime), TimeUnit.MINUTES);
    }

    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString((String)ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr((HttpServletRequest)ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP((String)ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, this.secret).compact();
        return token;
    }

    private Claims parseToken(String token) {
        return (Claims)Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = this.parseToken(token);
        return claims.getSubject();
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(this.header);
        if (StringUtils.isNotEmpty((String)token) && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return "login_tokens:" + uuid;
    }
}
