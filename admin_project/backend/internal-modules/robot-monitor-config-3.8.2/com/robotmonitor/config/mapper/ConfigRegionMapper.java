/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.enums.OperationType
 */
package com.robotmonitor.config.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.enums.OperationType;
import java.util.List;

public interface ConfigRegionMapper {
    public ConfigRegion selectConfigRegionById(Long var1);

    public List<ConfigRegion> selectConfigRegionList(ConfigRegion var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigRegion(ConfigRegion var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateConfigRegion(ConfigRegion var1);

    public int deleteConfigRegionById(Long var1);

    public int deleteConfigRegionByIds(Long[] var1);

    public List<ConfigRegion> selectIsGuideRegion(String var1);
}
