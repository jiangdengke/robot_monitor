package com.robotmonitor.food.mapper.jooq;

import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toDate;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toLocalDate;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toLocalDateTime;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.stringValue;
import static com.robotmonitor.jooq.generated.Tables.FOOD_CONFIG;
import static com.robotmonitor.jooq.generated.Tables.FOOD_DAILY;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.food.domain.FoodDaily;
import com.robotmonitor.food.dto.FoodDailyDto;
import com.robotmonitor.food.mapper.FoodDailyMapper;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqFoodDailyMapper implements FoodDailyMapper {
    private final DSLContext dsl;

    public JooqFoodDailyMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public FoodDaily selectFoodDailyById(Long id) {
        return dsl.select(FOOD_DAILY.fields())
            .select(SYS_DEPT.DEPT_NAME)
            .from(FOOD_DAILY)
            .leftJoin(SYS_DEPT).on(FOOD_DAILY.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE))
            .where(FOOD_DAILY.ID.eq(id))
            .fetchOne(this::mapFoodDaily);
    }

    @Override
    public List<FoodDailyDto> selectFoodDailyList(String foodDate, String roomCode) {
        return dsl.select(
                FOOD_DAILY.ID,
                FOOD_DAILY.FOOD_DATE,
                FOOD_DAILY.FOOD_ID,
                FOOD_DAILY.STATUS,
                FOOD_DAILY.ROOM_CODE,
                FOOD_CONFIG.NAME,
                FOOD_CONFIG.DIC_TYPE_CODE,
                SYS_DEPT.DEPT_NAME,
                FOOD_CONFIG.CALORIE,
                FOOD_CONFIG.REMARK,
                FOOD_CONFIG.IMG_IDS
            )
            .from(FOOD_DAILY)
            .leftJoin(FOOD_CONFIG).on(FOOD_CONFIG.FOOD_ID.eq(FOOD_DAILY.FOOD_ID))
            .leftJoin(SYS_DEPT).on(FOOD_DAILY.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE))
            .where(FOOD_DAILY.FOOD_DATE.eq(toLocalDate(foodDate)))
            .and(FOOD_DAILY.ROOM_CODE.eq(roomCode))
            .orderBy(FOOD_DAILY.STATUS.desc())
            .fetch(this::mapFoodDailyDto);
    }

    @Override
    public int insertFoodDaily(FoodDaily foodDaily) {
        Long id = dsl.insertInto(FOOD_DAILY)
            .set(writeValues(foodDaily))
            .returningResult(FOOD_DAILY.ID)
            .fetchOne(FOOD_DAILY.ID);
        foodDaily.setId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updateFoodDaily(FoodDaily foodDaily) {
        if (foodDaily.getId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(foodDaily);
        values.remove(FOOD_DAILY.ID);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(FOOD_DAILY)
            .set(values)
            .where(FOOD_DAILY.ID.eq(foodDaily.getId()))
            .execute();
    }

    @Override
    public int deleteFoodDailyById(Long id) {
        return dsl.deleteFrom(FOOD_DAILY).where(FOOD_DAILY.ID.eq(id)).execute();
    }

    @Override
    public int deleteFoodDailyByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(FOOD_DAILY).where(FOOD_DAILY.ID.in(Arrays.asList(ids))).execute();
    }

    @Override
    public int deleteFoodDailyByDate(String dStart, String dEnd, String roomCode) {
        return dsl.deleteFrom(FOOD_DAILY)
            .where(FOOD_DAILY.FOOD_DATE.ge(toLocalDate(dStart)))
            .and(FOOD_DAILY.FOOD_DATE.le(toLocalDate(dEnd)))
            .and(FOOD_DAILY.ROOM_CODE.eq(roomCode))
            .execute();
    }

    private Map<Field<?>, Object> writeValues(FoodDaily foodDaily) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, FOOD_DAILY.ID, foodDaily.getId());
        put(values, FOOD_DAILY.FOOD_DATE, foodDaily.getFoodDate());
        put(values, FOOD_DAILY.FOOD_ID, foodDaily.getFoodId());
        put(values, FOOD_DAILY.STATUS, foodDaily.getStatus());
        put(values, FOOD_DAILY.CREATE_BY, foodDaily.getCreateBy());
        put(values, FOOD_DAILY.CREATE_TIME, toLocalDateTime(foodDaily.getCreateTime()));
        put(values, FOOD_DAILY.UPDATE_BY, foodDaily.getUpdateBy());
        put(values, FOOD_DAILY.UPDATE_TIME, toLocalDateTime(foodDaily.getUpdateTime()));
        put(values, FOOD_DAILY.ROOM_CODE, foodDaily.getRoomCode());
        return values;
    }

    private FoodDaily mapFoodDaily(Record record) {
        FoodDaily foodDaily = new FoodDaily();
        foodDaily.setId(record.get(FOOD_DAILY.ID));
        foodDaily.setFoodDate(record.get(FOOD_DAILY.FOOD_DATE));
        foodDaily.setFoodId(record.get(FOOD_DAILY.FOOD_ID));
        foodDaily.setStatus(record.get(FOOD_DAILY.STATUS));
        foodDaily.setCreateBy(record.get(FOOD_DAILY.CREATE_BY));
        foodDaily.setCreateTime(toDate(record.get(FOOD_DAILY.CREATE_TIME)));
        foodDaily.setUpdateBy(record.get(FOOD_DAILY.UPDATE_BY));
        foodDaily.setUpdateTime(toDate(record.get(FOOD_DAILY.UPDATE_TIME)));
        foodDaily.setRoomCode(record.get(FOOD_DAILY.ROOM_CODE));
        foodDaily.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return foodDaily;
    }

    private FoodDailyDto mapFoodDailyDto(Record record) {
        FoodDailyDto dto = new FoodDailyDto();
        dto.setId(record.get(FOOD_DAILY.ID));
        dto.setFoodDate(record.get(FOOD_DAILY.FOOD_DATE));
        dto.setFoodId(record.get(FOOD_DAILY.FOOD_ID));
        dto.setStatus(record.get(FOOD_DAILY.STATUS));
        dto.setRoomCode(record.get(FOOD_DAILY.ROOM_CODE));
        dto.setFoodName(record.get(FOOD_CONFIG.NAME));
        dto.setFoodType(record.get(FOOD_CONFIG.DIC_TYPE_CODE));
        dto.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        dto.setCalorie(stringValue(record.get(FOOD_CONFIG.CALORIE)));
        dto.setRemark(record.get(FOOD_CONFIG.REMARK));
        dto.setImgIds(record.get(FOOD_CONFIG.IMG_IDS));
        return dto;
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null) {
            values.put(field, value);
        }
    }
}
