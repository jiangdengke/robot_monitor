/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.food.service;

import com.robotmonitor.food.domain.FoodOrder;
import com.robotmonitor.food.dto.FoodOrderDto;
import java.util.List;

public interface IFoodOrderService {
    public FoodOrder selectFoodOrderByFoodOrderId(Long var1);

    public List<FoodOrder> selectFoodOrderList(FoodOrder var1);

    public int insertFoodOrder(FoodOrder var1);

    public int updateFoodOrder(FoodOrder var1);

    public int deleteFoodOrderByIds(Long[] var1);

    public int deleteFoodOrderById(Long var1);

    public String createOrder(FoodOrderDto var1) throws Exception;

    public List<FoodOrderDto> queryFoodOrderList(FoodOrder var1);
}
