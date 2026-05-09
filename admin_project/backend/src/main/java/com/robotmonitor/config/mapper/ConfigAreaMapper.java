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
import com.robotmonitor.config.domain.ConfigArea;
import java.util.List;

public interface ConfigAreaMapper {
    public ConfigArea selectConfigAreaById(Long var1);

    public List<ConfigArea> selectConfigAreaList(ConfigArea var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigArea(ConfigArea var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateConfigArea(ConfigArea var1);

    public int deleteConfigAreaById(Long var1);

    public int deleteConfigAreaByIds(Long[] var1);

    public List<ConfigArea> selectAreaByRoomCodeAndNameAndLanguage(String var1, String var2, String var3);
}
