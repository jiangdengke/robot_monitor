/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.config.domain.ConfigTable
 *  com.robotmonitor.config.service.IConfigTableService
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.robotmonitor.food.service.impl;

import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.domain.ConfigTable;
import com.robotmonitor.config.service.IConfigTableService;
import com.robotmonitor.food.domain.FoodConfig;
import com.robotmonitor.food.domain.FoodOrder;
import com.robotmonitor.food.domain.FoodOrderDetail;
import com.robotmonitor.food.dto.FoodOrderDto;
import com.robotmonitor.food.mapper.FoodOrderMapper;
import com.robotmonitor.food.service.IFoodConfigService;
import com.robotmonitor.food.service.IFoodOrderDetailService;
import com.robotmonitor.food.service.IFoodOrderService;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodOrderServiceImpl
implements IFoodOrderService {
    @Autowired
    private FoodOrderMapper foodOrderMapper;
    @Autowired
    private IFoodOrderDetailService foodOrderDetailService;
    @Autowired
    private IFoodConfigService foodConfigService;
    @Autowired
    private IConfigTableService tableService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String createOrder(FoodOrderDto paramter) throws Exception {
        Long tableId = paramter.getTableId();
        if (ObjectUtils.isEmpty((Object)tableId)) {
            throw new RuntimeException("\u8ba2\u9910\u684c\u53f7\u4e0d\u80fd\u4e3a\u7a7a");
        }
        List<FoodOrderDetail> list = paramter.getOrderDetailList();
        if (list == null || list.size() == 0) {
            throw new RuntimeException("\u8ba2\u9910\u6570\u636e\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ConfigTable table = this.tableService.selectConfigTableById(tableId);
        FoodOrder order = new FoodOrder();
        String orderCode = this.createOrderCode();
        order.setOrderCode(orderCode);
        order.setStatus("1");
        order.setDeskNo(table.getTableNo());
        order.setTableId(tableId);
        order.setRoomCode(table.getRoomCode());
        order.setCardNo(paramter.getCardNo());
        order.setRemark(paramter.getRemark());
        order.setCreateTime(new Date());
        this.foodOrderMapper.insertFoodOrder(order);
        Long foodId = null;
        FoodConfig foodConfig = null;
        Long foodOrderId = order.getId();
        for (FoodOrderDetail p : list) {
            foodId = p.getFoodId();
            foodConfig = this.foodConfigService.selectFoodConfigByFoodId(foodId);
            p.setOrderId(foodOrderId);
            p.setFoodName(foodConfig.getName());
            p.setPrice(foodConfig.getPrice());
            this.foodOrderDetailService.insertFoodOrderDetail(p);
        }
        return orderCode;
    }

    @Override
    public FoodOrder selectFoodOrderByFoodOrderId(Long foodOrderId) {
        return this.foodOrderMapper.selectFoodOrderById(foodOrderId);
    }

    @Override
    public List<FoodOrder> selectFoodOrderList(FoodOrder foodOrder) {
        return this.foodOrderMapper.selectFoodOrderList(foodOrder);
    }

    @Override
    public int insertFoodOrder(FoodOrder foodOrder) {
        foodOrder.setCreateTime(DateUtils.getNowDate());
        return this.foodOrderMapper.insertFoodOrder(foodOrder);
    }

    @Override
    public int updateFoodOrder(FoodOrder foodOrder) {
        foodOrder.setUpdateTime(new Date());
        return this.foodOrderMapper.updateFoodOrder(foodOrder);
    }

    @Override
    public int deleteFoodOrderByIds(Long[] ids) {
        return this.foodOrderMapper.deleteFoodOrderByIds(ids);
    }

    @Override
    public int deleteFoodOrderById(Long id) {
        return this.foodOrderMapper.deleteFoodOrderById(id);
    }

    @Override
    public List<FoodOrderDto> queryFoodOrderList(FoodOrder foodOrder) {
        List<FoodOrderDto> orderList = this.foodOrderMapper.queryFoodOrderList(foodOrder);
        for (FoodOrderDto order : orderList) {
            order.setOrderDetailList(this.foodOrderDetailService.selectFoodOrderDetailListByOrderId(order.getId()));
        }
        return orderList;
    }

    private synchronized String createOrderCode() {
        return DateUtils.dateTimeNow();
    }
}
