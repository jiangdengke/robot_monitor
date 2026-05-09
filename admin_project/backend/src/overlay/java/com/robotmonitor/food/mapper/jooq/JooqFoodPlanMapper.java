package com.robotmonitor.food.mapper.jooq;

import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.isBlank;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toDate;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toLocalDate;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toLocalDateTime;
import static com.robotmonitor.jooq.generated.Tables.FOOD_PLAN;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.food.domain.FoodPlan;
import com.robotmonitor.food.mapper.FoodPlanMapper;
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
public class JooqFoodPlanMapper implements FoodPlanMapper {
    private final DSLContext dsl;

    public JooqFoodPlanMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public FoodPlan selectFoodPlanById(Long id) {
        return select()
            .where(FOOD_PLAN.ID.eq(id))
            .fetchOne(this::mapPlan);
    }

    @Override
    public List<FoodPlan> selectFoodPlanList(FoodPlan query) {
        return select()
            .where(conditions(query))
            .orderBy(FOOD_PLAN.CREATE_TIME.desc())
            .fetch(this::mapPlan);
    }

    @Override
    public int insertFoodPlan(FoodPlan plan) {
        Long id = dsl.insertInto(FOOD_PLAN)
            .set(writeValues(plan))
            .returningResult(FOOD_PLAN.ID)
            .fetchOne(FOOD_PLAN.ID);
        plan.setId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updateFoodPlan(FoodPlan plan) {
        if (plan.getId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(plan);
        values.remove(FOOD_PLAN.ID);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(FOOD_PLAN)
            .set(values)
            .where(FOOD_PLAN.ID.eq(plan.getId()))
            .execute();
    }

    @Override
    public int deleteFoodPlanById(Long id) {
        return dsl.deleteFrom(FOOD_PLAN)
            .where(FOOD_PLAN.ID.eq(id))
            .execute();
    }

    @Override
    public int deleteFoodPlanByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(FOOD_PLAN)
            .where(FOOD_PLAN.ID.in(Arrays.asList(ids)))
            .execute();
    }

    private org.jooq.SelectOnConditionStep<? extends Record> select() {
        return dsl.select(
                FOOD_PLAN.ID,
                FOOD_PLAN.START_DAY,
                FOOD_PLAN.END_DAY,
                FOOD_PLAN.CREATE_BY,
                FOOD_PLAN.CREATE_TIME,
                FOOD_PLAN.UPDATE_BY,
                FOOD_PLAN.UPDATE_TIME,
                FOOD_PLAN.ROOM_CODE,
                FOOD_PLAN.FOOD_IDS,
                FOOD_PLAN.FOOD_NAMES,
                SYS_DEPT.DEPT_NAME
            )
            .from(FOOD_PLAN)
            .leftJoin(SYS_DEPT).on(FOOD_PLAN.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition conditions(FoodPlan plan) {
        if (plan == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(FOOD_PLAN.START_DAY, toLocalDate(plan.getStartDay())),
            eqIfPresent(FOOD_PLAN.END_DAY, toLocalDate(plan.getEndDay())),
            eqIfPresent(FOOD_PLAN.ROOM_CODE, plan.getRoomCode()),
            eqIfPresent(FOOD_PLAN.FOOD_IDS, plan.getFoodIds()),
            eqIfPresent(FOOD_PLAN.FOOD_NAMES, plan.getFoodNames())
        );
    }

    private Map<Field<?>, Object> writeValues(FoodPlan plan) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, FOOD_PLAN.ID, plan.getId());
        put(values, FOOD_PLAN.START_DAY, toLocalDate(plan.getStartDay()));
        put(values, FOOD_PLAN.END_DAY, toLocalDate(plan.getEndDay()));
        put(values, FOOD_PLAN.CREATE_BY, plan.getCreateBy());
        put(values, FOOD_PLAN.CREATE_TIME, toLocalDateTime(plan.getCreateTime()));
        put(values, FOOD_PLAN.UPDATE_BY, plan.getUpdateBy());
        put(values, FOOD_PLAN.UPDATE_TIME, toLocalDateTime(plan.getUpdateTime()));
        put(values, FOOD_PLAN.ROOM_CODE, plan.getRoomCode());
        put(values, FOOD_PLAN.FOOD_IDS, plan.getFoodIds());
        put(values, FOOD_PLAN.FOOD_NAMES, plan.getFoodNames());
        return values;
    }

    private FoodPlan mapPlan(Record record) {
        FoodPlan plan = new FoodPlan();
        plan.setId(record.get(FOOD_PLAN.ID));
        plan.setStartDay(record.get(FOOD_PLAN.START_DAY) == null ? null : record.get(FOOD_PLAN.START_DAY).toString());
        plan.setEndDay(record.get(FOOD_PLAN.END_DAY) == null ? null : record.get(FOOD_PLAN.END_DAY).toString());
        plan.setCreateBy(record.get(FOOD_PLAN.CREATE_BY));
        plan.setCreateTime(toDate(record.get(FOOD_PLAN.CREATE_TIME)));
        plan.setUpdateBy(record.get(FOOD_PLAN.UPDATE_BY));
        plan.setUpdateTime(toDate(record.get(FOOD_PLAN.UPDATE_TIME)));
        plan.setRoomCode(record.get(FOOD_PLAN.ROOM_CODE));
        plan.setFoodIds(record.get(FOOD_PLAN.FOOD_IDS));
        plan.setFoodNames(record.get(FOOD_PLAN.FOOD_NAMES));
        plan.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return plan;
    }

    private Condition eqIfPresent(Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((Field<Object>) field).eq(value);
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null && (!(value instanceof String text) || !text.isBlank())) {
            values.put(field, value);
        }
    }
}
