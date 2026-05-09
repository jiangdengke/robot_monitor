/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.config.domain.ConfigDeviceRegion;
import com.robotmonitor.config.mapper.ConfigDeviceRegionMapper;
import com.robotmonitor.config.service.IConfigDeviceRegionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigDeviceRegionServiceImpl
implements IConfigDeviceRegionService {
    @Autowired
    private ConfigDeviceRegionMapper configDeviceRegionMapper;

    @Override
    public List<ConfigDeviceRegion> selectConfigDeviceRegionByDeviceId(Long deviceId) {
        return this.configDeviceRegionMapper.selectConfigDeviceRegionByDeviceId(deviceId);
    }

    @Override
    public ConfigDeviceRegion selectConfigDeviceRegionByDeviceIdRegionId(Long deviceId, Long regionId) {
        return this.configDeviceRegionMapper.selectConfigDeviceRegionByDeviceIdRegionId(deviceId, regionId);
    }

    @Override
    public List<ConfigDeviceRegion> selectConfigDeviceRegionList(ConfigDeviceRegion configDeviceRegion) {
        return this.configDeviceRegionMapper.selectConfigDeviceRegionList(configDeviceRegion);
    }

    @Override
    public int insertConfigDeviceRegion(ConfigDeviceRegion configDeviceRegion) {
        return this.configDeviceRegionMapper.insertConfigDeviceRegion(configDeviceRegion);
    }

    @Override
    public int updateConfigDeviceRegion(ConfigDeviceRegion configDeviceRegion) {
        return this.configDeviceRegionMapper.updateConfigDeviceRegion(configDeviceRegion);
    }

    @Override
    public int deleteConfigDeviceRegionByDeviceIds(Long[] deviceIds) {
        return this.configDeviceRegionMapper.deleteConfigDeviceRegionByDeviceIds(deviceIds);
    }

    @Override
    public int deleteConfigDeviceRegion(ConfigDeviceRegion configDeviceRegion) {
        return this.configDeviceRegionMapper.deleteConfigDeviceRegion(configDeviceRegion);
    }
}
