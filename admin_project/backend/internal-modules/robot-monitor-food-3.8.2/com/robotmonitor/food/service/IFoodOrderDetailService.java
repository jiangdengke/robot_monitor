/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.food.service;

import com.robotmonitor.food.domain.FoodOrderDetail;
import java.util.List;

public interface IFoodOrderDetailService {
    public FoodOrderDetail selectFoodOrderDetailByFoodOrderDetailId(Long var1);

    public List<FoodOrderDetail> selectFoodOrderDetailList(FoodOrderDetail var1);

    public List<FoodOrderDetail> selectFoodOrderDetailListByOrderId(Long var1);

    public int insertFoodOrderDetail(FoodOrderDetail var1);

    public int updateFoodOrderDetail(FoodOrderDetail var1);

    public int deleteFoodOrderDetailByFoodOrderDetailIds(Long[] var1);

    public int deleteFoodOrderDetailByFoodOrderDetailId(Long var1);
}
