/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.enums.OperationType
 *  org.apache.ibatis.annotations.Param
 */
package com.robotmonitor.food.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.enums.OperationType;
import com.robotmonitor.food.domain.FoodDaily;
import com.robotmonitor.food.dto.FoodDailyDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FoodDailyMapper {
    public FoodDaily selectFoodDailyById(Long var1);

    public List<FoodDailyDto> selectFoodDailyList(String var1, String var2);

    @AutoFill(value=OperationType.INSERT)
    public int insertFoodDaily(FoodDaily var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateFoodDaily(FoodDaily var1);

    public int deleteFoodDailyById(Long var1);

    public int deleteFoodDailyByIds(Long[] var1);

    public int deleteFoodDailyByDate(@Param(value="dStart") String var1, @Param(value="dEnd") String var2, @Param(value="roomCode") String var3);
}
