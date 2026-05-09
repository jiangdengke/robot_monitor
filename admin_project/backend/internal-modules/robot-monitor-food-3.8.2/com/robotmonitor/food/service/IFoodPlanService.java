/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.food.service;

import com.robotmonitor.food.domain.FoodPlan;
import java.util.List;

public interface IFoodPlanService {
    public FoodPlan selectFoodPlanById(Long var1);

    public List<FoodPlan> selectFoodPlanList(FoodPlan var1);

    public int insertFoodPlan(FoodPlan var1);

    public int updateFoodPlan(FoodPlan var1);

    public int deleteFoodPlanByIds(Long[] var1);

    public int deleteFoodPlanById(Long var1);
}
