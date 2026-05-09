/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.robotmonitor.food.service.impl;

import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.food.domain.FoodDaily;
import com.robotmonitor.food.domain.FoodPlan;
import com.robotmonitor.food.mapper.FoodDailyMapper;
import com.robotmonitor.food.mapper.FoodPlanMapper;
import com.robotmonitor.food.service.IFoodPlanService;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodPlanServiceImpl
implements IFoodPlanService {
    @Autowired
    private FoodPlanMapper foodPlanMapper;
    @Autowired
    private FoodDailyMapper foodDailyMapper;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int insertFoodPlan(FoodPlan foodPlan) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        foodPlan.setCreateTime(DateUtils.getNowDate());
        if (this.foodPlanMapper.insertFoodPlan(foodPlan) > 0) {
            LocalDate start = LocalDate.parse(foodPlan.getStartDay());
            LocalDate end = LocalDate.parse(foodPlan.getEndDay());
            this.foodDailyMapper.deleteFoodDailyByDate(foodPlan.getStartDay(), foodPlan.getEndDay(), foodPlan.getRoomCode());
            long days = ChronoUnit.DAYS.between(start, end);
            for (long i = 0L; i <= days; ++i) {
                LocalDate foodDate = start.plusDays(i);
                for (String id : foodPlan.getFoodIds().split(",")) {
                    FoodDaily daily = new FoodDaily();
                    daily.setFoodDate(foodDate);
                    daily.setFoodId(Long.parseLong(id));
                    daily.setStatus("1");
                    daily.setRoomCode(foodPlan.getRoomCode());
                    this.foodDailyMapper.insertFoodDaily(daily);
                }
            }
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int updateFoodPlan(FoodPlan foodPlan) {
        foodPlan.setRoomCode(foodPlan.getRoomCode());
        if (this.foodPlanMapper.updateFoodPlan(foodPlan) > 0) {
            LocalDate start = LocalDate.parse(foodPlan.getStartDay());
            LocalDate end = LocalDate.parse(foodPlan.getEndDay());
            this.foodDailyMapper.deleteFoodDailyByDate(foodPlan.getStartDay(), foodPlan.getEndDay(), foodPlan.getRoomCode());
            long days = ChronoUnit.DAYS.between(start, end);
            for (long i = 0L; i <= days; ++i) {
                LocalDate foodDate = start.plusDays(i);
                for (String id : foodPlan.getFoodIds().split(",")) {
                    FoodDaily daily = new FoodDaily();
                    daily.setFoodDate(foodDate);
                    daily.setFoodId(Long.parseLong(id));
                    daily.setStatus("1");
                    daily.setRoomCode(foodPlan.getRoomCode());
                    this.foodDailyMapper.insertFoodDaily(daily);
                }
            }
        }
        return 1;
    }

    @Override
    public FoodPlan selectFoodPlanById(Long id) {
        return this.foodPlanMapper.selectFoodPlanById(id);
    }

    @Override
    public List<FoodPlan> selectFoodPlanList(FoodPlan foodPlan) {
        return this.foodPlanMapper.selectFoodPlanList(foodPlan);
    }

    @Override
    public int deleteFoodPlanByIds(Long[] ids) {
        return this.foodPlanMapper.deleteFoodPlanByIds(ids);
    }

    @Override
    public int deleteFoodPlanById(Long id) {
        return this.foodPlanMapper.deleteFoodPlanById(id);
    }
}
