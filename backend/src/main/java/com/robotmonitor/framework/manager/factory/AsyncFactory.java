/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.LogUtils
 *  com.robotmonitor.common.utils.ServletUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.ip.AddressUtils
 *  com.robotmonitor.common.utils.ip.IpUtils
 *  com.robotmonitor.common.utils.spring.SpringUtils
 *  com.robotmonitor.system.domain.SysLogininfor
 *  com.robotmonitor.system.domain.SysOperLog
 *  com.robotmonitor.system.service.ISysLogininforService
 *  com.robotmonitor.system.service.ISysOperLogService
 *  eu.bitwalker.useragentutils.UserAgent
 *  jakarta.servlet.http.HttpServletRequest
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.robotmonitor.framework.manager.factory;

import com.robotmonitor.common.utils.LogUtils;
import com.robotmonitor.common.utils.ServletUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.ip.AddressUtils;
import com.robotmonitor.common.utils.ip.IpUtils;
import com.robotmonitor.common.utils.spring.SpringUtils;
import com.robotmonitor.system.domain.SysLogininfor;
import com.robotmonitor.system.domain.SysOperLog;
import com.robotmonitor.system.service.ISysLogininforService;
import com.robotmonitor.system.service.ISysOperLogService;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger((String)"sys-user");

    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object ... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString((String)ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr((HttpServletRequest)ServletUtils.getRequest());
        return new TimerTask(){

            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP((String)ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock((Object)ip));
                s.append(address);
                s.append(LogUtils.getBlock((Object)username));
                s.append(LogUtils.getBlock((Object)status));
                s.append(LogUtils.getBlock((Object)message));
                sys_user_logger.info(s.toString(), args);
                String os = userAgent.getOperatingSystem().getName();
                String browser = userAgent.getBrowser().getName();
                SysLogininfor logininfor = new SysLogininfor();
                logininfor.setUserName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                if (StringUtils.equalsAny((CharSequence)status, (CharSequence[])new CharSequence[]{"Success", "Logout", "Register"})) {
                    logininfor.setStatus("0");
                } else if ("Error".equals(status)) {
                    logininfor.setStatus("1");
                }
                ((ISysLogininforService)SpringUtils.getBean(ISysLogininforService.class)).insertLogininfor(logininfor);
            }
        };
    }

    public static TimerTask recordOper(final SysOperLog operLog) {
        return new TimerTask(){

            @Override
            public void run() {
                operLog.setOperLocation(AddressUtils.getRealAddressByIP((String)operLog.getOperIp()));
                ((ISysOperLogService)SpringUtils.getBean(ISysOperLogService.class)).insertOperlog(operLog);
            }
        };
    }
}
