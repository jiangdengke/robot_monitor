/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.food.service.impl;

import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.food.domain.FoodDaily;
import com.robotmonitor.food.dto.FoodDailyDto;
import com.robotmonitor.food.mapper.FoodDailyMapper;
import com.robotmonitor.food.service.IFoodDailyService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodDailyServiceImpl
implements IFoodDailyService {
    @Autowired
    private FoodDailyMapper foodDailyMapper;

    @Override
    public FoodDaily selectFoodDailyById(Long id) {
        return this.foodDailyMapper.selectFoodDailyById(id);
    }

    @Override
    public List<FoodDailyDto> selectFoodDailyList(String foodDate, String roomCode) {
        List<FoodDailyDto> list = this.foodDailyMapper.selectFoodDailyList(foodDate, roomCode);
        for (FoodDailyDto dto : list) {
            if (!StringUtils.isNotEmpty((String)dto.getImgIds())) continue;
            Long[] imageIds = (Long[])Arrays.stream(dto.getImgIds().split(",")).map(Long::parseLong).toArray(Long[]::new);
            ArrayList<String> imgUrlList = new ArrayList<String>();
            dto.setImgUrlList(imgUrlList);
            for (Long imageId : imageIds) {
                imgUrlList.add("/api/rest/image/config/" + imageId);
            }
        }
        return list;
    }

    @Override
    public int insertFoodDaily(FoodDaily foodDaily) {
        foodDaily.setStatus("1");
        return this.foodDailyMapper.insertFoodDaily(foodDaily);
    }

    @Override
    public int updateFoodDaily(FoodDaily foodDaily) {
        foodDaily.setUpdateTime(DateUtils.getNowDate());
        return this.foodDailyMapper.updateFoodDaily(foodDaily);
    }

    @Override
    public int deleteFoodDailyByIds(Long[] ids) {
        return this.foodDailyMapper.deleteFoodDailyByIds(ids);
    }

    @Override
    public int deleteFoodDailyById(Long id) {
        return this.foodDailyMapper.deleteFoodDailyById(id);
    }
}
