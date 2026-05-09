package com.robotmonitor.food.mapper.jooq;

import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.isBlank;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.stringValue;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toBigDecimal;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toDate;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toDouble;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toInteger;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toLocalDateTime;
import static com.robotmonitor.jooq.generated.Tables.FOOD_CONFIG;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;
import static com.robotmonitor.jooq.generated.Tables.SYS_DICT_DATA;

import com.robotmonitor.food.domain.FoodConfig;
import com.robotmonitor.food.dto.FoodConfigDto;
import com.robotmonitor.food.mapper.FoodConfigMapper;
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
public class JooqFoodConfigMapper implements FoodConfigMapper {
    private final DSLContext dsl;

    public JooqFoodConfigMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public FoodConfig selectFoodConfigByFoodId(Long foodId) {
        return select()
            .where(FOOD_CONFIG.FOOD_ID.eq(foodId))
            .fetchOne(this::mapFoodConfig);
    }

    @Override
    public List<FoodConfig> selectFoodConfigList(FoodConfig query) {
        return select()
            .where(conditions(query))
            .orderBy(FOOD_CONFIG.FOOD_ID.desc())
            .fetch(this::mapFoodConfig);
    }

    @Override
    public int insertFoodConfig(FoodConfig food) {
        Long id = dsl.insertInto(FOOD_CONFIG)
            .set(writeValues(food))
            .returningResult(FOOD_CONFIG.FOOD_ID)
            .fetchOne(FOOD_CONFIG.FOOD_ID);
        food.setFoodId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updateFoodConfig(FoodConfig food) {
        if (food.getFoodId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(food);
        values.remove(FOOD_CONFIG.FOOD_ID);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(FOOD_CONFIG)
            .set(values)
            .where(FOOD_CONFIG.FOOD_ID.eq(food.getFoodId()))
            .execute();
    }

    @Override
    public int deleteFoodConfigByFoodId(Long foodId) {
        return dsl.deleteFrom(FOOD_CONFIG).where(FOOD_CONFIG.FOOD_ID.eq(foodId)).execute();
    }

    @Override
    public int deleteFoodConfigByFoodIds(Long[] foodIds) {
        if (foodIds == null || foodIds.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(FOOD_CONFIG).where(FOOD_CONFIG.FOOD_ID.in(Arrays.asList(foodIds))).execute();
    }

    @Override
    public List<FoodConfigDto> queryTypeList() {
        return dsl.select(SYS_DICT_DATA.DICT_LABEL, SYS_DICT_DATA.DICT_VALUE)
            .from(SYS_DICT_DATA)
            .where(SYS_DICT_DATA.DICT_TYPE.eq("food_type"))
            .and(SYS_DICT_DATA.STATUS.eq("0"))
            .orderBy(SYS_DICT_DATA.DICT_SORT.asc())
            .fetch(record -> {
                FoodConfigDto dto = new FoodConfigDto();
                dto.setTypeName(record.get(SYS_DICT_DATA.DICT_LABEL));
                dto.setValue(record.get(SYS_DICT_DATA.DICT_VALUE));
                return dto;
            });
    }

    private org.jooq.SelectOnConditionStep<? extends Record> select() {
        return dsl.select(
                FOOD_CONFIG.FOOD_ID,
                FOOD_CONFIG.NAME,
                FOOD_CONFIG.IMG_IDS,
                FOOD_CONFIG.PRICE,
                FOOD_CONFIG.CALORIE,
                FOOD_CONFIG.DIC_TYPE_CODE,
                FOOD_CONFIG.REMARK,
                FOOD_CONFIG.CREATE_BY,
                FOOD_CONFIG.CREATE_TIME,
                FOOD_CONFIG.UPDATE_BY,
                FOOD_CONFIG.UPDATE_TIME,
                FOOD_CONFIG.ROOM_CODE,
                SYS_DEPT.DEPT_NAME
            )
            .from(FOOD_CONFIG)
            .leftJoin(SYS_DEPT).on(FOOD_CONFIG.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition conditions(FoodConfig food) {
        if (food == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            likeIfPresent(FOOD_CONFIG.NAME, food.getName()),
            eqIfPresent(FOOD_CONFIG.IMG_IDS, food.getImgIds()),
            eqIfPresent(FOOD_CONFIG.PRICE, toBigDecimal(food.getPrice())),
            eqIfPresent(FOOD_CONFIG.CALORIE, toInteger(food.getCalorie())),
            eqIfPresent(FOOD_CONFIG.DIC_TYPE_CODE, food.getDicTypeCode()),
            eqIfPresent(FOOD_CONFIG.ROOM_CODE, food.getRoomCode())
        );
    }

    private Map<Field<?>, Object> writeValues(FoodConfig food) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, FOOD_CONFIG.FOOD_ID, food.getFoodId());
        put(values, FOOD_CONFIG.NAME, food.getName());
        put(values, FOOD_CONFIG.IMG_IDS, food.getImgIds());
        put(values, FOOD_CONFIG.PRICE, toBigDecimal(food.getPrice()));
        put(values, FOOD_CONFIG.CALORIE, toInteger(food.getCalorie()));
        put(values, FOOD_CONFIG.DIC_TYPE_CODE, food.getDicTypeCode());
        put(values, FOOD_CONFIG.REMARK, food.getRemark());
        put(values, FOOD_CONFIG.CREATE_BY, food.getCreateBy());
        put(values, FOOD_CONFIG.CREATE_TIME, toLocalDateTime(food.getCreateTime()));
        put(values, FOOD_CONFIG.UPDATE_BY, food.getUpdateBy());
        put(values, FOOD_CONFIG.UPDATE_TIME, toLocalDateTime(food.getUpdateTime()));
        put(values, FOOD_CONFIG.ROOM_CODE, food.getRoomCode());
        return values;
    }

    private FoodConfig mapFoodConfig(Record record) {
        FoodConfig food = new FoodConfig();
        food.setFoodId(record.get(FOOD_CONFIG.FOOD_ID));
        food.setName(record.get(FOOD_CONFIG.NAME));
        food.setImgIds(record.get(FOOD_CONFIG.IMG_IDS));
        food.setPrice(toDouble(record.get(FOOD_CONFIG.PRICE)));
        food.setCalorie(stringValue(record.get(FOOD_CONFIG.CALORIE)));
        food.setDicTypeCode(record.get(FOOD_CONFIG.DIC_TYPE_CODE));
        food.setRemark(record.get(FOOD_CONFIG.REMARK));
        food.setCreateBy(record.get(FOOD_CONFIG.CREATE_BY));
        food.setCreateTime(toDate(record.get(FOOD_CONFIG.CREATE_TIME)));
        food.setUpdateBy(record.get(FOOD_CONFIG.UPDATE_BY));
        food.setUpdateTime(toDate(record.get(FOOD_CONFIG.UPDATE_TIME)));
        food.setRoomCode(record.get(FOOD_CONFIG.ROOM_CODE));
        food.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return food;
    }

    private Condition eqIfPresent(Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((Field<Object>) field).eq(value);
    }

    private Condition likeIfPresent(Field<String> field, String value) {
        if (isBlank(value)) {
            return DSL.noCondition();
        }
        return field.like("%" + value + "%");
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null) {
            values.put(field, value);
        }
    }
}
