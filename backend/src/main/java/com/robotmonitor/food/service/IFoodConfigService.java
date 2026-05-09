/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.food.service;

import com.robotmonitor.food.domain.FoodConfig;
import com.robotmonitor.food.dto.FoodConfigDto;
import java.util.List;

public interface IFoodConfigService {
    public FoodConfig selectFoodConfigByFoodId(Long var1);

    public List<FoodConfig> selectFoodConfigList(FoodConfig var1);

    public int insertFoodConfig(FoodConfig var1);

    public int updateFoodConfig(FoodConfig var1);

    public int deleteFoodConfigByFoodIds(Long[] var1);

    public int deleteFoodConfigByFoodId(Long var1);

    public List<FoodConfigDto> queryFoodConfigList();
}
