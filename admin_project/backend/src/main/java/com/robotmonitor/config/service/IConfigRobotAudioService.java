/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRobotAudio
 */
package com.robotmonitor.config.service;

import com.robotmonitor.common.core.domain.config.ConfigRobotAudio;
import java.text.ParseException;
import java.util.List;

public interface IConfigRobotAudioService {
    public ConfigRobotAudio selectConfigRobotAudioById(Long var1);

    public List<ConfigRobotAudio> selectConfigRobotAudioList(ConfigRobotAudio var1);

    public int insertConfigRobotAudio(ConfigRobotAudio var1);

    public int updateConfigRobotAudio(ConfigRobotAudio var1);

    public int deleteConfigRobotAudioByIds(Long[] var1);

    public int deleteConfigRobotAudioById(Long var1);

    public List<ConfigRobotAudio> getNewRobotAudio(String var1, String var2) throws ParseException;
}
