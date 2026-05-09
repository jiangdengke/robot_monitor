/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.core.domain.config.ConfigRobotAudio
 *  com.robotmonitor.common.enums.OperationType
 */
package com.robotmonitor.config.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.core.domain.config.ConfigRobotAudio;
import com.robotmonitor.common.enums.OperationType;
import java.util.List;

public interface ConfigRobotAudioMapper {
    public ConfigRobotAudio selectConfigRobotAudioById(Long var1);

    public List<ConfigRobotAudio> selectConfigRobotAudioList(ConfigRobotAudio var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigRobotAudio(ConfigRobotAudio var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateConfigRobotAudio(ConfigRobotAudio var1);

    public int deleteConfigRobotAudioById(Long var1);

    public int deleteConfigRobotAudioByIds(Long[] var1);

    public Long getCntByKey(ConfigRobotAudio var1);

    public List<ConfigRobotAudio> getNewRobotAudio(ConfigRobotAudio var1);
}
