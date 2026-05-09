/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigTask
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.mapper.ConfigTaskMapper;
import com.robotmonitor.config.service.IConfigTaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigTaskServiceImpl
implements IConfigTaskService {
    @Autowired
    private ConfigTaskMapper configTaskMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ConfigTask selectConfigTaskById(Long id) {
        return this.configTaskMapper.selectConfigTaskById(id);
    }

    @Override
    public List<ConfigTask> selectConfigTaskList(ConfigTask configTask) {
        return this.configTaskMapper.selectConfigTaskList(configTask);
    }

    @Override
    public int insertConfigTask(ConfigTask configTask) {
        configTask.setIsDelete("0");
        return this.configTaskMapper.insertConfigTask(configTask);
    }

    @Override
    public int updateConfigTask(ConfigTask configTask) {
        configTask.setUpdateTime(DateUtils.getNowDate());
        return this.configTaskMapper.updateConfigTask(configTask);
    }

    @Override
    public int deleteConfigTaskByIds(Long[] ids) {
        return this.configTaskMapper.deleteConfigTaskByIds(ids);
    }

    @Override
    public int deleteConfigTaskById(Long id) {
        return this.configTaskMapper.deleteConfigTaskById(id);
    }

    @Override
    public ConfigTask findDefaultTask(Long id) {
        ConfigTask configTask = (ConfigTask)this.redisCache.getCacheObject("robot_default_task_id:" + id);
        if (null == configTask) {
            configTask = this.configTaskMapper.selectConfigTaskById(id);
            this.redisCache.setCacheObject("robot_default_task_id:" + id, (Object)configTask);
        }
        return configTask;
    }
}
