/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.DataSource
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.core.text.Convert
 *  com.robotmonitor.common.enums.DataSourceType
 *  com.robotmonitor.common.exception.ServiceException
 *  com.robotmonitor.common.utils.StringUtils
 *  jakarta.annotation.PostConstruct
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.system.service.impl;

import com.robotmonitor.common.annotation.DataSource;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.core.text.Convert;
import com.robotmonitor.common.enums.DataSourceType;
import com.robotmonitor.common.exception.ServiceException;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.system.domain.SysConfig;
import com.robotmonitor.system.mapper.SysConfigMapper;
import com.robotmonitor.system.service.ISysConfigService;
import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl
implements ISysConfigService {
    @Autowired
    private SysConfigMapper configMapper;
    @Autowired
    private RedisCache redisCache;

    @PostConstruct
    public void init() {
        this.loadingConfigCache();
    }

    @Override
    @DataSource(value=DataSourceType.MASTER)
    public SysConfig selectConfigById(Long configId) {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return this.configMapper.selectConfig(config);
    }

    @Override
    public String selectConfigByKey(String configKey) {
        String configValue = Convert.toStr((Object)this.redisCache.getCacheObject(this.getCacheKey(configKey)));
        if (StringUtils.isNotEmpty((String)configValue)) {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = this.configMapper.selectConfig(config);
        if (StringUtils.isNotNull((Object)((Object)retConfig))) {
            this.redisCache.setCacheObject(this.getCacheKey(configKey), (Object)retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return "";
    }

    @Override
    public boolean selectCaptchaOnOff() {
        String captchaOnOff = this.selectConfigByKey("sys.account.captchaOnOff");
        if (StringUtils.isEmpty((String)captchaOnOff)) {
            return true;
        }
        return Convert.toBool((Object)captchaOnOff);
    }

    @Override
    public List<SysConfig> selectConfigList(SysConfig config) {
        return this.configMapper.selectConfigList(config);
    }

    @Override
    public int insertConfig(SysConfig config) {
        int row = this.configMapper.insertConfig(config);
        if (row > 0) {
            this.redisCache.setCacheObject(this.getCacheKey(config.getConfigKey()), (Object)config.getConfigValue());
        }
        return row;
    }

    @Override
    public int updateConfig(SysConfig config) {
        int row = this.configMapper.updateConfig(config);
        if (row > 0) {
            this.redisCache.setCacheObject(this.getCacheKey(config.getConfigKey()), (Object)config.getConfigValue());
        }
        return row;
    }

    @Override
    public void deleteConfigByIds(Long[] configIds) {
        for (Long configId : configIds) {
            SysConfig config = this.selectConfigById(configId);
            if (StringUtils.equals((CharSequence)"Y", (CharSequence)config.getConfigType())) {
                throw new ServiceException(String.format("\u5185\u7f6e\u53c2\u6570\u3010%1$s\u3011\u4e0d\u80fd\u5220\u9664 ", config.getConfigKey()));
            }
            this.configMapper.deleteConfigById(configId);
            this.redisCache.deleteObject(this.getCacheKey(config.getConfigKey()));
        }
    }

    @Override
    public void loadingConfigCache() {
        List<SysConfig> configsList = this.configMapper.selectConfigList(new SysConfig());
        for (SysConfig config : configsList) {
            this.redisCache.setCacheObject(this.getCacheKey(config.getConfigKey()), (Object)config.getConfigValue());
        }
    }

    @Override
    public void clearConfigCache() {
        Collection keys = this.redisCache.keys("sys_config:*");
        this.redisCache.deleteObject(keys);
    }

    @Override
    public void resetConfigCache() {
        this.clearConfigCache();
        this.loadingConfigCache();
    }

    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        Long configId = StringUtils.isNull((Object)config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = this.configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull((Object)((Object)info)) && info.getConfigId().longValue() != configId.longValue()) {
            return "1";
        }
        return "0";
    }

    private String getCacheKey(String configKey) {
        return "sys_config:" + configKey;
    }
}
