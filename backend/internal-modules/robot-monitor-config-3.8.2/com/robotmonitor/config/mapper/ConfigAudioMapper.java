/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.core.domain.config.ConfigAudio
 *  com.robotmonitor.common.enums.OperationType
 */
package com.robotmonitor.config.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.core.domain.config.ConfigAudio;
import com.robotmonitor.common.enums.OperationType;
import java.util.List;

public interface ConfigAudioMapper {
    public ConfigAudio selectConfigAudioById(Long var1);

    public List<ConfigAudio> selectConfigAudioList(ConfigAudio var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigAudio(ConfigAudio var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateConfigAudio(ConfigAudio var1);

    public int deleteConfigAudioById(Long var1);

    public int deleteConfigAudioByIds(Long[] var1);

    public Long getCntByKey(ConfigAudio var1);
}
