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
import com.robotmonitor.food.domain.FoodPlan;
import java.util.List;

public interface FoodPlanMapper {
    public FoodPlan selectFoodPlanById(Long var1);

    public List<FoodPlan> selectFoodPlanList(FoodPlan var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertFoodPlan(FoodPlan var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateFoodPlan(FoodPlan var1);

    public int deleteFoodPlanById(Long var1);

    public int deleteFoodPlanByIds(Long[] var1);
}
