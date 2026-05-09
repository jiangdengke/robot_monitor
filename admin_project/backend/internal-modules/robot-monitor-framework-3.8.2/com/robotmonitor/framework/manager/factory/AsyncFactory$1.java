/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.LogUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.ip.AddressUtils
 *  com.robotmonitor.common.utils.spring.SpringUtils
 *  com.robotmonitor.system.domain.SysLogininfor
 *  com.robotmonitor.system.service.ISysLogininforService
 *  eu.bitwalker.useragentutils.UserAgent
 */
package com.robotmonitor.framework.manager.factory;

import com.robotmonitor.common.utils.LogUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.ip.AddressUtils;
import com.robotmonitor.common.utils.spring.SpringUtils;
import com.robotmonitor.system.domain.SysLogininfor;
import com.robotmonitor.system.service.ISysLogininforService;
import eu.bitwalker.useragentutils.UserAgent;
import java.util.TimerTask;

static class AsyncFactory.1
extends TimerTask {
    final /* synthetic */ String val$ip;
    final /* synthetic */ String val$username;
    final /* synthetic */ String val$status;
    final /* synthetic */ String val$message;
    final /* synthetic */ Object[] val$args;
    final /* synthetic */ UserAgent val$userAgent;

    AsyncFactory.1(String string, String string2, String string3, String string4, Object[] objectArray, UserAgent userAgent) {
        this.val$ip = string;
        this.val$username = string2;
        this.val$status = string3;
        this.val$message = string4;
        this.val$args = objectArray;
        this.val$userAgent = userAgent;
    }

    @Override
    public void run() {
        String address = AddressUtils.getRealAddressByIP((String)this.val$ip);
        StringBuilder s = new StringBuilder();
        s.append(LogUtils.getBlock((Object)this.val$ip));
        s.append(address);
        s.append(LogUtils.getBlock((Object)this.val$username));
        s.append(LogUtils.getBlock((Object)this.val$status));
        s.append(LogUtils.getBlock((Object)this.val$message));
        sys_user_logger.info(s.toString(), this.val$args);
        String os = this.val$userAgent.getOperatingSystem().getName();
        String browser = this.val$userAgent.getBrowser().getName();
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(this.val$username);
        logininfor.setIpaddr(this.val$ip);
        logininfor.setLoginLocation(address);
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(this.val$message);
        if (StringUtils.equalsAny((CharSequence)this.val$status, (CharSequence[])new CharSequence[]{"Success", "Logout", "Register"})) {
            logininfor.setStatus("0");
        } else if ("Error".equals(this.val$status)) {
            logininfor.setStatus("1");
        }
        ((ISysLogininforService)SpringUtils.getBean(ISysLogininforService.class)).insertLogininfor(logininfor);
    }
}
