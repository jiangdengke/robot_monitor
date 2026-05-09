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
import com.robotmonitor.config.domain.ConfigAreaDetail;
import java.util.List;

public interface ConfigAreaDetailMapper {
    public ConfigAreaDetail selectConfigAreaDetailById(Long var1);

    public List<ConfigAreaDetail> selectConfigAreaDetailList(ConfigAreaDetail var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigAreaDetail(ConfigAreaDetail var1);

    public int updateConfigAreaDetail(ConfigAreaDetail var1);

    public int deleteConfigAreaDetailById(Long var1);

    public int deleteConfigAreaDetailByIds(Long[] var1);

    public int deleteByAreaId(Long var1);

    public List<ConfigAreaDetail> selectDetailListByAreaId(Long var1);

    public List<ConfigAreaDetail> selectDetailListWithoutAudioByAreaId(Long var1);

    public String findAreaNameByRoomCodeAndLanguageType(String var1, String var2);

    public List<ConfigAreaDetail> selectAreaByRegionId(Long var1);
}
