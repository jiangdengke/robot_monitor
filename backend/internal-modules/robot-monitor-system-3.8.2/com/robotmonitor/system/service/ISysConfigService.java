/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.system.service;

import com.robotmonitor.system.domain.SysConfig;
import java.util.List;

public interface ISysConfigService {
    public SysConfig selectConfigById(Long var1);

    public String selectConfigByKey(String var1);

    public boolean selectCaptchaOnOff();

    public List<SysConfig> selectConfigList(SysConfig var1);

    public int insertConfig(SysConfig var1);

    public int updateConfig(SysConfig var1);

    public void deleteConfigByIds(Long[] var1);

    public void loadingConfigCache();

    public void clearConfigCache();

    public void resetConfigCache();

    public String checkConfigKeyUnique(SysConfig var1);
}
