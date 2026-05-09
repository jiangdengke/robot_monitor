/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigAudio
 */
package com.robotmonitor.config.service;

import com.robotmonitor.common.core.domain.config.ConfigAudio;
import java.util.List;

public interface IConfigAudioService {
    public ConfigAudio selectConfigAudioById(Long var1);

    public List<ConfigAudio> selectConfigAudioList(ConfigAudio var1);

    public int insertConfigAudio(ConfigAudio var1);

    public int updateConfigAudio(ConfigAudio var1);

    public int deleteConfigAudioByIds(Long[] var1);

    public int deleteConfigAudioById(Long var1);

    public ConfigAudio getConfigAudioByKeyAndLanguageAndRoomCode(String var1, String var2, String var3);
}
