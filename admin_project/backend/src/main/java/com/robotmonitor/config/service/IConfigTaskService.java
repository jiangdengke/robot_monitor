/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigTask
 */
package com.robotmonitor.config.service;

import com.robotmonitor.common.core.domain.config.ConfigTask;
import java.util.List;

public interface IConfigTaskService {
    public ConfigTask selectConfigTaskById(Long var1);

    public List<ConfigTask> selectConfigTaskList(ConfigTask var1);

    public int insertConfigTask(ConfigTask var1);

    public int updateConfigTask(ConfigTask var1);

    public int deleteConfigTaskByIds(Long[] var1);

    public int deleteConfigTaskById(Long var1);

    public ConfigTask findDefaultTask(Long var1);
}
