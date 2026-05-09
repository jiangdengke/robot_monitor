/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.food.service.impl;

import com.robotmonitor.food.domain.FoodOrderDetail;
import com.robotmonitor.food.mapper.FoodOrderDetailMapper;
import com.robotmonitor.food.service.IFoodOrderDetailService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderDetailServiceImpl
implements IFoodOrderDetailService {
    @Autowired
    private FoodOrderDetailMapper foodOrderDetailMapper;

    @Override
    public FoodOrderDetail selectFoodOrderDetailByFoodOrderDetailId(Long foodOrderDetailId) {
        return this.foodOrderDetailMapper.selectFoodOrderDetailByFoodOrderDetailId(foodOrderDetailId);
    }

    @Override
    public List<FoodOrderDetail> selectFoodOrderDetailList(FoodOrderDetail foodOrderDetail) {
        return this.foodOrderDetailMapper.selectFoodOrderDetailList(foodOrderDetail);
    }

    @Override
    public List<FoodOrderDetail> selectFoodOrderDetailListByOrderId(Long orderId) {
        FoodOrderDetail foodOrderDetail = new FoodOrderDetail();
        foodOrderDetail.setOrderId(orderId);
        return this.selectFoodOrderDetailList(foodOrderDetail);
    }

    @Override
    public int insertFoodOrderDetail(FoodOrderDetail foodOrderDetail) {
        foodOrderDetail.setCreateTime(new Date());
        return this.foodOrderDetailMapper.insertFoodOrderDetail(foodOrderDetail);
    }

    @Override
    public int updateFoodOrderDetail(FoodOrderDetail foodOrderDetail) {
        foodOrderDetail.setUpdateTime(new Date());
        return this.foodOrderDetailMapper.updateFoodOrderDetail(foodOrderDetail);
    }

    @Override
    public int deleteFoodOrderDetailByFoodOrderDetailIds(Long[] foodOrderDetailIds) {
        return this.foodOrderDetailMapper.deleteFoodOrderDetailByFoodOrderDetailIds(foodOrderDetailIds);
    }

    @Override
    public int deleteFoodOrderDetailByFoodOrderDetailId(Long foodOrderDetailId) {
        return this.foodOrderDetailMapper.deleteFoodOrderDetailByFoodOrderDetailId(foodOrderDetailId);
    }
}
