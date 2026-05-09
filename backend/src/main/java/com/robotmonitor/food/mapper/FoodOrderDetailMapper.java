/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.food.mapper;

import com.robotmonitor.food.domain.FoodOrderDetail;
import java.util.List;

public interface FoodOrderDetailMapper {
    public FoodOrderDetail selectFoodOrderDetailByFoodOrderDetailId(Long var1);

    public List<FoodOrderDetail> selectFoodOrderDetailList(FoodOrderDetail var1);

    public int insertFoodOrderDetail(FoodOrderDetail var1);

    public int updateFoodOrderDetail(FoodOrderDetail var1);

    public int deleteFoodOrderDetailByFoodOrderDetailId(Long var1);

    public int deleteFoodOrderDetailByFoodOrderDetailIds(Long[] var1);
}
