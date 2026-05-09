/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.enums.OperationType
 */
package com.robotmonitor.config.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.enums.OperationType;
import com.robotmonitor.config.domain.ConfigDevice;
import java.util.List;

public interface ConfigDeviceMapper {
    public ConfigDevice selectConfigDeviceById(Long var1);

    public List<ConfigDevice> selectConfigDeviceList(ConfigDevice var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigDevice(ConfigDevice var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateConfigDevice(ConfigDevice var1);

    public int deleteConfigDeviceById(Long var1);

    public int deleteConfigDeviceByIds(Long[] var1);

    public ConfigDevice selectConfigDeviceByDeepGlintDeviceId(String var1);

    public List<ConfigDevice> selectExitDevices();
}
