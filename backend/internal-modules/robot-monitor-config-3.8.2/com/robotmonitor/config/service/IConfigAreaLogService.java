/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.service;

import com.robotmonitor.config.domain.ConfigAreaLog;
import java.util.List;

public interface IConfigAreaLogService {
    public ConfigAreaLog selectConfigAreaLogById(Long var1);

    public List<ConfigAreaLog> selectConfigAreaLogList(ConfigAreaLog var1);

    public int insertConfigAreaLog(ConfigAreaLog var1);

    public int updateConfigAreaLog(ConfigAreaLog var1);

    public int deleteConfigAreaLogByIds(Long[] var1);

    public int deleteConfigAreaLogById(Long var1);
}
