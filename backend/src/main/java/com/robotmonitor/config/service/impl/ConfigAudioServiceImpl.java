/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigAudio
 *  com.robotmonitor.common.utils.CreateVoiceUtils
 *  io.jsonwebtoken.lang.Collections
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigAudio;
import com.robotmonitor.common.utils.CreateVoiceUtils;
import com.robotmonitor.config.mapper.ConfigAudioMapper;
import com.robotmonitor.config.service.IConfigAudioService;
import io.jsonwebtoken.lang.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConfigAudioServiceImpl
implements IConfigAudioService {
    @Autowired
    private ConfigAudioMapper configAudioMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ConfigAudio selectConfigAudioById(Long id) {
        return this.configAudioMapper.selectConfigAudioById(id);
    }

    @Override
    public List<ConfigAudio> selectConfigAudioList(ConfigAudio configAudio) {
        return this.configAudioMapper.selectConfigAudioList(configAudio);
    }

    @Override
    public int insertConfigAudio(ConfigAudio configAudio) {
        if (this.checkKey(configAudio)) {
            return 0;
        }
        String s = CreateVoiceUtils.createVoice((StringRedisTemplate)this.stringRedisTemplate, (String)configAudio.getTextInfo(), (String)configAudio.getLanguageType());
        configAudio.setAudioValue(s);
        return this.configAudioMapper.insertConfigAudio(configAudio);
    }

    private boolean checkKey(ConfigAudio configAudio) {
        Long cnt = this.configAudioMapper.getCntByKey(configAudio);
        return cnt > 0L;
    }

    @Override
    public int updateConfigAudio(ConfigAudio configAudio) {
        String s = CreateVoiceUtils.createVoice((StringRedisTemplate)this.stringRedisTemplate, (String)configAudio.getTextInfo(), (String)configAudio.getLanguageType());
        configAudio.setAudioValue(s);
        return this.configAudioMapper.updateConfigAudio(configAudio);
    }

    @Override
    public int deleteConfigAudioByIds(Long[] ids) {
        return this.configAudioMapper.deleteConfigAudioByIds(ids);
    }

    @Override
    public int deleteConfigAudioById(Long id) {
        return this.configAudioMapper.deleteConfigAudioById(id);
    }

    @Override
    public ConfigAudio getConfigAudioByKeyAndLanguageAndRoomCode(String key, String language, String roomCode) {
        ConfigAudio queryRequest = new ConfigAudio();
        queryRequest.setAudioKey(key);
        queryRequest.setLanguageType(language);
        queryRequest.setRoomCode(roomCode);
        List<ConfigAudio> configAudios = this.selectConfigAudioList(queryRequest);
        if (Collections.isEmpty(configAudios)) {
            return null;
        }
        return configAudios.get(0);
    }
}
