/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.enums.OperationType
 *  org.apache.ibatis.annotations.Param
 */
package com.robotmonitor.config.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.enums.OperationType;
import com.robotmonitor.config.domain.ConfigDeviceRegion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConfigDeviceRegionMapper {
    public List<ConfigDeviceRegion> selectConfigDeviceRegionByDeviceId(Long var1);

    public ConfigDeviceRegion selectConfigDeviceRegionByDeviceIdRegionId(Long var1, Long var2);

    public List<ConfigDeviceRegion> selectConfigDeviceRegionList(ConfigDeviceRegion var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigDeviceRegion(ConfigDeviceRegion var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateConfigDeviceRegion(ConfigDeviceRegion var1);

    public int deleteConfigDeviceRegionByDeviceId(Long var1);

    public int deleteConfigDeviceRegionByDeviceIds(Long[] var1);

    public List<ConfigDeviceRegion> selectByDeviceId(@Param(value="deviceId") Long var1);

    public int deleteConfigDeviceRegion(ConfigDeviceRegion var1);
}
