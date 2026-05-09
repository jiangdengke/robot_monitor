/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.utils.DateUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.domain.ConfigTable;
import com.robotmonitor.config.mapper.ConfigTableMapper;
import com.robotmonitor.config.service.IConfigTableService;
import com.robotmonitor.config.service.impl.ConfigRegionServiceImpl;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigTableServiceImpl
implements IConfigTableService {
    @Autowired
    private ConfigTableMapper configTableMapper;
    @Autowired
    private ConfigRegionServiceImpl configRegionService;

    @Override
    public ConfigTable selectConfigTableById(Long id) {
        return this.configTableMapper.selectConfigTableById(id);
    }

    @Override
    public List<ConfigTable> selectConfigTableList(ConfigTable configTable) {
        List<ConfigTable> list = this.configTableMapper.selectConfigTableList(configTable);
        for (ConfigTable table : list) {
            ConfigRegion region = this.configRegionService.selectConfigRegionById(table.getRegionId());
            if (!ObjectUtils.isNotEmpty((Object)region)) continue;
            table.setRegionName(region.getRegionName());
        }
        return list;
    }

    @Override
    public int insertConfigTable(ConfigTable configTable) {
        configTable.setCreateTime(DateUtils.getNowDate());
        ConfigTable param = new ConfigTable();
        param.setTableNo(configTable.getTableNo());
        param.setRoomCode(configTable.getRoomCode());
        List<ConfigTable> list = this.configTableMapper.selectConfigTableList(param);
        if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
            return 99;
        }
        return this.configTableMapper.insertConfigTable(configTable);
    }

    @Override
    public int updateConfigTable(ConfigTable configTable) {
        configTable.setUpdateTime(DateUtils.getNowDate());
        return this.configTableMapper.updateConfigTable(configTable);
    }

    @Override
    public int deleteConfigTableByIds(Long[] ids) {
        return this.configTableMapper.deleteConfigTableByIds(ids);
    }

    @Override
    public int deleteConfigTableById(Long id) {
        return this.configTableMapper.deleteConfigTableById(id);
    }

    @Override
    public int setTableStatus(ConfigTable table) {
        return this.configTableMapper.setTableStatus(table);
    }
}
