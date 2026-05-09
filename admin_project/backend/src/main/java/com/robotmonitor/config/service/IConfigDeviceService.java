/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.service;

import com.robotmonitor.config.domain.ConfigDevice;
import java.util.List;

public interface IConfigDeviceService {
    public ConfigDevice selectConfigDeviceById(Long var1);

    public List<ConfigDevice> selectConfigDeviceList(ConfigDevice var1);

    public int insertConfigDevice(ConfigDevice var1);

    public int updateConfigDevice(ConfigDevice var1);

    public int deleteConfigDeviceByIds(Long[] var1);

    public int deleteConfigDeviceById(Long var1);
}
