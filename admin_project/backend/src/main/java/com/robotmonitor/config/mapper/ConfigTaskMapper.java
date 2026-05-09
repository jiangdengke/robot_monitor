/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.core.domain.config.ConfigTask
 *  com.robotmonitor.common.enums.OperationType
 */
package com.robotmonitor.config.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.enums.OperationType;
import java.util.List;

public interface ConfigTaskMapper {
    public ConfigTask selectConfigTaskById(Long var1);

    public List<ConfigTask> selectConfigTaskList(ConfigTask var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigTask(ConfigTask var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateConfigTask(ConfigTask var1);

    @AutoFill(value=OperationType.UPDATE)
    public int deleteConfigTaskById(Long var1);

    public int deleteConfigTaskByIds(Long[] var1);
}
