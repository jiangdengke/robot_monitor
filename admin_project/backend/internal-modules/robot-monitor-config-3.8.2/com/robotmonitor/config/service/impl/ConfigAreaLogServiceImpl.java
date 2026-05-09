/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.domain.ConfigAreaLog;
import com.robotmonitor.config.mapper.ConfigAreaLogMapper;
import com.robotmonitor.config.service.IConfigAreaLogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigAreaLogServiceImpl
implements IConfigAreaLogService {
    @Autowired
    private ConfigAreaLogMapper configAreaLogMapper;

    @Override
    public ConfigAreaLog selectConfigAreaLogById(Long id) {
        return this.configAreaLogMapper.selectConfigAreaLogById(id);
    }

    @Override
    public List<ConfigAreaLog> selectConfigAreaLogList(ConfigAreaLog configAreaLog) {
        return this.configAreaLogMapper.selectConfigAreaLogList(configAreaLog);
    }

    @Override
    public int insertConfigAreaLog(ConfigAreaLog configAreaLog) {
        configAreaLog.setCreateTime(DateUtils.getNowDate());
        return this.configAreaLogMapper.insertConfigAreaLog(configAreaLog);
    }

    @Override
    public int updateConfigAreaLog(ConfigAreaLog configAreaLog) {
        return this.configAreaLogMapper.updateConfigAreaLog(configAreaLog);
    }

    @Override
    public int deleteConfigAreaLogByIds(Long[] ids) {
        return this.configAreaLogMapper.deleteConfigAreaLogByIds(ids);
    }

    @Override
    public int deleteConfigAreaLogById(Long id) {
        return this.configAreaLogMapper.deleteConfigAreaLogById(id);
    }
}
