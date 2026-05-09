/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.service;

import com.robotmonitor.config.domain.ConfigDeviceRegion;
import java.util.List;

public interface IConfigDeviceRegionService {
    public List<ConfigDeviceRegion> selectConfigDeviceRegionByDeviceId(Long var1);

    public List<ConfigDeviceRegion> selectConfigDeviceRegionList(ConfigDeviceRegion var1);

    public ConfigDeviceRegion selectConfigDeviceRegionByDeviceIdRegionId(Long var1, Long var2);

    public int insertConfigDeviceRegion(ConfigDeviceRegion var1);

    public int updateConfigDeviceRegion(ConfigDeviceRegion var1);

    public int deleteConfigDeviceRegionByDeviceIds(Long[] var1);

    public int deleteConfigDeviceRegion(ConfigDeviceRegion var1);
}
