/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.enums.OperationType
 */
package com.robotmonitor.config.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.enums.OperationType;
import com.robotmonitor.config.domain.ConfigTable;
import java.util.List;

public interface ConfigTableMapper {
    public ConfigTable selectConfigTableById(Long var1);

    public List<ConfigTable> selectConfigTableList(ConfigTable var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigTable(ConfigTable var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateConfigTable(ConfigTable var1);

    public int deleteConfigTableById(Long var1);

    public int deleteConfigTableByIds(Long[] var1);

    public int setTableStatus(ConfigTable var1);
}
