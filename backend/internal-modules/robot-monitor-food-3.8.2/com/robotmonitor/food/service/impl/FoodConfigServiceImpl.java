/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.food.service.impl;

import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.food.domain.FoodConfig;
import com.robotmonitor.food.dto.FoodConfigDetailDto;
import com.robotmonitor.food.dto.FoodConfigDto;
import com.robotmonitor.food.mapper.FoodConfigMapper;
import com.robotmonitor.food.service.IFoodConfigService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodConfigServiceImpl
implements IFoodConfigService {
    @Autowired
    private FoodConfigMapper foodConfigMapper;

    @Override
    public FoodConfig selectFoodConfigByFoodId(Long foodId) {
        return this.foodConfigMapper.selectFoodConfigByFoodId(foodId);
    }

    @Override
    public List<FoodConfig> selectFoodConfigList(FoodConfig foodConfig) {
        List<FoodConfig> foodConfigList = this.foodConfigMapper.selectFoodConfigList(foodConfig);
        for (FoodConfig food : foodConfigList) {
            if (!StringUtils.isNotEmpty((String)food.getImgIds())) continue;
            Long[] imageIds = (Long[])Arrays.stream(food.getImgIds().split(",")).map(Long::parseLong).toArray(Long[]::new);
            ArrayList<String> imgUrlList = new ArrayList<String>();
            food.setImgUrlList(imgUrlList);
            for (Long imageId : imageIds) {
                imgUrlList.add("/api/rest/image/config/" + imageId);
            }
        }
        return foodConfigList;
    }

    @Override
    public int insertFoodConfig(FoodConfig foodConfig) {
        foodConfig.setCreateTime(DateUtils.getNowDate());
        return this.foodConfigMapper.insertFoodConfig(foodConfig);
    }

    @Override
    public int updateFoodConfig(FoodConfig foodConfig) {
        foodConfig.setUpdateTime(DateUtils.getNowDate());
        return this.foodConfigMapper.updateFoodConfig(foodConfig);
    }

    @Override
    public int deleteFoodConfigByFoodIds(Long[] foodIds) {
        return this.foodConfigMapper.deleteFoodConfigByFoodIds(foodIds);
    }

    @Override
    public int deleteFoodConfigByFoodId(Long foodId) {
        return this.foodConfigMapper.deleteFoodConfigByFoodId(foodId);
    }

    @Override
    public List<FoodConfigDto> queryFoodConfigList() {
        List<FoodConfigDto> list = this.foodConfigMapper.queryTypeList();
        List<FoodConfig> foodConfigList = this.selectFoodConfigList(null);
        Map<String, List<FoodConfigDetailDto>> mapConfig = foodConfigList.stream().map(v -> {
            FoodConfigDetailDto d = new FoodConfigDetailDto();
            d.setFoodId(v.getFoodId());
            d.setDicTypeCode(v.getDicTypeCode());
            d.setName(v.getName());
            d.setImgIds(v.getImgIds());
            d.setCalorie(v.getCalorie());
            if (ObjectUtils.isNotEmpty((Object)v.getPrice())) {
                d.setPrice(String.format("%.2f", (double)v.getPrice()));
            }
            d.setRemark(v.getRemark());
            d.setImgUrlList(v.getImgUrlList());
            return d;
        }).collect(Collectors.groupingBy(FoodConfigDetailDto::getDicTypeCode));
        List<FoodConfigDetailDto> detailList = null;
        for (FoodConfigDto f : list) {
            detailList = mapConfig.get(f.getTypeName());
            if (detailList == null) {
                detailList = new ArrayList<FoodConfigDetailDto>();
            }
            f.setList(detailList);
        }
        return list;
    }
}
