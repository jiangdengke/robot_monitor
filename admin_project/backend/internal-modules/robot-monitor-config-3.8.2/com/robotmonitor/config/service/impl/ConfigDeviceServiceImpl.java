/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.mapper.ConfigDeviceMapper;
import com.robotmonitor.config.service.IConfigDeviceService;
import com.robotmonitor.config.service.IDeepGlintService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigDeviceServiceImpl
implements IConfigDeviceService {
    private static final Logger log = LoggerFactory.getLogger(ConfigDeviceServiceImpl.class);
    @Autowired
    private ConfigDeviceMapper configDeviceMapper;
    @Autowired
    private IDeepGlintService deepGlintService;

    @Override
    public ConfigDevice selectConfigDeviceById(Long id) {
        return this.configDeviceMapper.selectConfigDeviceById(id);
    }

    @Override
    public List<ConfigDevice> selectConfigDeviceList(ConfigDevice configDevice) {
        return this.configDeviceMapper.selectConfigDeviceList(configDevice);
    }

    @Override
    public int insertConfigDevice(ConfigDevice configDevice) {
        configDevice.setCreateTime(DateUtils.getNowDate());
        return this.configDeviceMapper.insertConfigDevice(configDevice);
    }

    @Override
    public int updateConfigDevice(ConfigDevice configDevice) {
        configDevice.setUpdateTime(DateUtils.getNowDate());
        return this.configDeviceMapper.updateConfigDevice(configDevice);
    }

    @Override
    public int deleteConfigDeviceByIds(Long[] ids) {
        return this.configDeviceMapper.deleteConfigDeviceByIds(ids);
    }

    @Override
    public int deleteConfigDeviceById(Long id) {
        return this.configDeviceMapper.deleteConfigDeviceById(id);
    }
}
