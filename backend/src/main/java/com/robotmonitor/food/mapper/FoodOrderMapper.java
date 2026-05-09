/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.food.mapper;

import com.robotmonitor.food.domain.FoodOrder;
import com.robotmonitor.food.dto.FoodOrderDto;
import java.util.List;

public interface FoodOrderMapper {
    public FoodOrder selectFoodOrderById(Long var1);

    public List<FoodOrder> selectFoodOrderList(FoodOrder var1);

    public int insertFoodOrder(FoodOrder var1);

    public int updateFoodOrder(FoodOrder var1);

    public int deleteFoodOrderById(Long var1);

    public int deleteFoodOrderByIds(Long[] var1);

    public List<FoodOrderDto> queryFoodOrderList(FoodOrder var1);

    public void updateWorkStatus(FoodOrder var1);
}
