/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 */
package com.robotmonitor.config.service;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import java.util.List;

public interface IConfigRegionService {
    public ConfigRegion selectConfigRegionById(Long var1);

    public List<ConfigRegion> selectConfigRegionList(ConfigRegion var1);

    public int insertConfigRegion(ConfigRegion var1);

    public int updateConfigRegion(ConfigRegion var1);

    public int deleteConfigRegionByIds(Long[] var1);

    public int deleteConfigRegionById(Long var1);

    public List<ConfigRegion> selectIsGuideRegion(String var1);
}
