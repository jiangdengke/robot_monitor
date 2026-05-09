/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.ip.AddressUtils
 *  com.robotmonitor.common.utils.spring.SpringUtils
 *  com.robotmonitor.system.domain.SysOperLog
 *  com.robotmonitor.system.service.ISysOperLogService
 */
package com.robotmonitor.framework.manager.factory;

import com.robotmonitor.common.utils.ip.AddressUtils;
import com.robotmonitor.common.utils.spring.SpringUtils;
import com.robotmonitor.system.domain.SysOperLog;
import com.robotmonitor.system.service.ISysOperLogService;
import java.util.TimerTask;

static class AsyncFactory.2
extends TimerTask {
    final /* synthetic */ SysOperLog val$operLog;

    AsyncFactory.2(SysOperLog sysOperLog) {
        this.val$operLog = sysOperLog;
    }

    @Override
    public void run() {
        this.val$operLog.setOperLocation(AddressUtils.getRealAddressByIP((String)this.val$operLog.getOperIp()));
        ((ISysOperLogService)SpringUtils.getBean(ISysOperLogService.class)).insertOperlog(this.val$operLog);
    }
}
