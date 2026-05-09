/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.config.ConfigRobotAudio
 *  com.robotmonitor.common.utils.CreateVoiceUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.config.ConfigRobotAudio;
import com.robotmonitor.common.utils.CreateVoiceUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.config.mapper.ConfigRobotAudioMapper;
import com.robotmonitor.config.service.IConfigRobotAudioService;
import com.robotmonitor.config.service.IConfigRobotService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConfigRobotAudioServiceImpl
implements IConfigRobotAudioService {
    @Autowired
    private ConfigRobotAudioMapper configRobotAudioMapper;
    @Autowired
    private IConfigRobotService robotService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ConfigRobotAudio selectConfigRobotAudioById(Long id) {
        return this.configRobotAudioMapper.selectConfigRobotAudioById(id);
    }

    @Override
    public List<ConfigRobotAudio> selectConfigRobotAudioList(ConfigRobotAudio configRobotAudio) {
        return this.configRobotAudioMapper.selectConfigRobotAudioList(configRobotAudio);
    }

    @Override
    public int insertConfigRobotAudio(ConfigRobotAudio configRobotAudio) {
        if (this.checkKey(configRobotAudio)) {
            return 0;
        }
        String s = CreateVoiceUtils.createVoice((StringRedisTemplate)this.stringRedisTemplate, (String)configRobotAudio.getTextInfo(), (String)configRobotAudio.getLanguageType());
        configRobotAudio.setAudioValue(s);
        return this.configRobotAudioMapper.insertConfigRobotAudio(configRobotAudio);
    }

    private boolean checkKey(ConfigRobotAudio configRobotAudio) {
        Long cnt = this.configRobotAudioMapper.getCntByKey(configRobotAudio);
        return cnt > 0L;
    }

    @Override
    public int updateConfigRobotAudio(ConfigRobotAudio configRobotAudio) {
        if (this.checkKey(configRobotAudio)) {
            return 0;
        }
        String s = CreateVoiceUtils.createVoice((StringRedisTemplate)this.stringRedisTemplate, (String)configRobotAudio.getTextInfo(), (String)configRobotAudio.getLanguageType());
        configRobotAudio.setAudioValue(s);
        return this.configRobotAudioMapper.updateConfigRobotAudio(configRobotAudio);
    }

    @Override
    public int deleteConfigRobotAudioByIds(Long[] ids) {
        return this.configRobotAudioMapper.deleteConfigRobotAudioByIds(ids);
    }

    @Override
    public int deleteConfigRobotAudioById(Long id) {
        return this.configRobotAudioMapper.deleteConfigRobotAudioById(id);
    }

    @Override
    public List<ConfigRobotAudio> getNewRobotAudio(String robotId, String lastUpdateTime) throws ParseException {
        ConfigRobotAudio info = new ConfigRobotAudio();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        if (StringUtils.isEmpty((String)lastUpdateTime)) {
            lastUpdateTime = "190001010000";
        }
        Date dUpdate = sdf.parse(lastUpdateTime);
        ConfigRobot robot = this.robotService.selectConfigRobotByRobotId(robotId);
        info.setRoomCode(robot.getRoomCode());
        info.setUpdateTime(dUpdate);
        return this.configRobotAudioMapper.getNewRobotAudio(info);
    }
}
