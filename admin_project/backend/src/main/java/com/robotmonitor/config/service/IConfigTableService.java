/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.service;

import com.robotmonitor.config.domain.ConfigTable;
import java.util.List;

public interface IConfigTableService {
    public ConfigTable selectConfigTableById(Long var1);

    public List<ConfigTable> selectConfigTableList(ConfigTable var1);

    public int insertConfigTable(ConfigTable var1);

    public int updateConfigTable(ConfigTable var1);

    public int deleteConfigTableByIds(Long[] var1);

    public int deleteConfigTableById(Long var1);

    public int setTableStatus(ConfigTable var1);
}
