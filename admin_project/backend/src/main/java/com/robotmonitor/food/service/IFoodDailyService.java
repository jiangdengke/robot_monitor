/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.food.service;

import com.robotmonitor.food.domain.FoodDaily;
import com.robotmonitor.food.dto.FoodDailyDto;
import java.util.List;

public interface IFoodDailyService {
    public FoodDaily selectFoodDailyById(Long var1);

    public List<FoodDailyDto> selectFoodDailyList(String var1, String var2);

    public int insertFoodDaily(FoodDaily var1);

    public int updateFoodDaily(FoodDaily var1);

    public int deleteFoodDailyByIds(Long[] var1);

    public int deleteFoodDailyById(Long var1);
}
