/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.mapper.ConfigRegionMapper;
import com.robotmonitor.config.service.IConfigAreaService;
import com.robotmonitor.config.service.IConfigRegionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigRegionServiceImpl
implements IConfigRegionService {
    @Autowired
    private ConfigRegionMapper configRegionMapper;
    @Autowired
    private IConfigAreaService configAreaService;

    @Override
    public ConfigRegion selectConfigRegionById(Long id) {
        return this.configRegionMapper.selectConfigRegionById(id);
    }

    @Override
    public List<ConfigRegion> selectConfigRegionList(ConfigRegion configRegion) {
        return this.configRegionMapper.selectConfigRegionList(configRegion);
    }

    @Override
    public int insertConfigRegion(ConfigRegion configRegion) {
        configRegion.setCreateTime(DateUtils.getNowDate());
        return this.configRegionMapper.insertConfigRegion(configRegion);
    }

    @Override
    public int updateConfigRegion(ConfigRegion configRegion) {
        configRegion.setUpdateTime(DateUtils.getNowDate());
        return this.configRegionMapper.updateConfigRegion(configRegion);
    }

    @Override
    public int deleteConfigRegionByIds(Long[] ids) {
        return this.configRegionMapper.deleteConfigRegionByIds(ids);
    }

    @Override
    public int deleteConfigRegionById(Long id) {
        return this.configRegionMapper.deleteConfigRegionById(id);
    }

    @Override
    public List<ConfigRegion> selectIsGuideRegion(String roomCode) {
        return this.configRegionMapper.selectIsGuideRegion(roomCode);
    }
}
