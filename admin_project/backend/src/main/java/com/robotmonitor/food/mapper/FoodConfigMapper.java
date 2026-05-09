/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.enums.OperationType
 */
package com.robotmonitor.food.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.enums.OperationType;
import com.robotmonitor.food.domain.FoodConfig;
import com.robotmonitor.food.dto.FoodConfigDto;
import java.util.List;

public interface FoodConfigMapper {
    public FoodConfig selectFoodConfigByFoodId(Long var1);

    public List<FoodConfig> selectFoodConfigList(FoodConfig var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertFoodConfig(FoodConfig var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateFoodConfig(FoodConfig var1);

    public int deleteFoodConfigByFoodId(Long var1);

    public int deleteFoodConfigByFoodIds(Long[] var1);

    public List<FoodConfigDto> queryTypeList();
}
