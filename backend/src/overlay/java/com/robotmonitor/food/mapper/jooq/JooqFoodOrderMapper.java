package com.robotmonitor.food.mapper.jooq;

import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.isBlank;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toDate;
import static com.robotmonitor.food.mapper.jooq.JooqFoodMapperSupport.toLocalDateTime;
import static com.robotmonitor.jooq.generated.Tables.FOOD_ORDER;
import static com.robotmonitor.jooq.generated.Tables.FOOD_ORDER_DETAIL;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.food.domain.FoodOrder;
import com.robotmonitor.food.dto.FoodOrderDto;
import com.robotmonitor.food.mapper.FoodOrderMapper;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
public class JooqFoodOrderMapper implements FoodOrderMapper {
    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("0.00");
    private final DSLContext dsl;

    public JooqFoodOrderMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public FoodOrder selectFoodOrderById(Long id) {
        return select()
            .where(FOOD_ORDER.ID.eq(id))
            .fetchOne(this::mapFoodOrder);
    }

    @Override
    public List<FoodOrder> selectFoodOrderList(FoodOrder query) {
        return select()
            .where(conditions(query))
            .orderBy(FOOD_ORDER.CREATE_TIME.desc())
            .fetch(this::mapFoodOrder);
    }

    @Override
    public int insertFoodOrder(FoodOrder order) {
        Long id = dsl.insertInto(FOOD_ORDER)
            .set(writeValues(order))
            .returningResult(FOOD_ORDER.ID)
            .fetchOne(FOOD_ORDER.ID);
        order.setId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updateFoodOrder(FoodOrder order) {
        if (order.getId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(order);
        values.remove(FOOD_ORDER.ID);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(FOOD_ORDER)
            .set(values)
            .where(FOOD_ORDER.ID.eq(order.getId()))
            .execute();
    }

    @Override
    public int deleteFoodOrderById(Long id) {
        return dsl.deleteFrom(FOOD_ORDER).where(FOOD_ORDER.ID.eq(id)).execute();
    }

    @Override
    public int deleteFoodOrderByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(FOOD_ORDER).where(FOOD_ORDER.ID.in(Arrays.asList(ids))).execute();
    }

    @Override
    public List<FoodOrderDto> queryFoodOrderList(FoodOrder query) {
        Field<BigDecimal> orderPrice = DSL.sum(FOOD_ORDER_DETAIL.PRICE.mul(FOOD_ORDER_DETAIL.NUM.cast(BigDecimal.class))).as("order_price");
        return dsl.select(
                FOOD_ORDER.ID,
                FOOD_ORDER.ORDER_CODE,
                FOOD_ORDER.DESK_NO,
                FOOD_ORDER.STATUS,
                FOOD_ORDER.ROOM_CODE,
                FOOD_ORDER.TABLE_ID,
                FOOD_ORDER.REMARK,
                FOOD_ORDER.CREATE_TIME,
                orderPrice
            )
            .from(FOOD_ORDER)
            .leftJoin(FOOD_ORDER_DETAIL).on(FOOD_ORDER_DETAIL.ORDER_ID.eq(FOOD_ORDER.ID))
            .where(queryConditions(query))
            .groupBy(
                FOOD_ORDER.ID,
                FOOD_ORDER.ORDER_CODE,
                FOOD_ORDER.DESK_NO,
                FOOD_ORDER.STATUS,
                FOOD_ORDER.ROOM_CODE,
                FOOD_ORDER.TABLE_ID,
                FOOD_ORDER.REMARK,
                FOOD_ORDER.CREATE_TIME
            )
            .orderBy(FOOD_ORDER.CREATE_TIME.desc())
            .fetch(record -> {
                FoodOrderDto dto = new FoodOrderDto();
                dto.setId(record.get(FOOD_ORDER.ID));
                dto.setOrderCode(record.get(FOOD_ORDER.ORDER_CODE));
                dto.setDeskNo(record.get(FOOD_ORDER.DESK_NO));
                dto.setStatus(record.get(FOOD_ORDER.STATUS));
                dto.setRoomCode(record.get(FOOD_ORDER.ROOM_CODE));
                dto.setTableId(record.get(FOOD_ORDER.TABLE_ID));
                dto.setRemark(record.get(FOOD_ORDER.REMARK));
                dto.setCreateTime(toDate(record.get(FOOD_ORDER.CREATE_TIME)));
                BigDecimal price = record.get(orderPrice);
                dto.setOrderPrice(price == null ? "0.00" : PRICE_FORMAT.format(price));
                return dto;
            });
    }

    @Override
    public void updateWorkStatus(FoodOrder order) {
        if (order == null || order.getId() == null) {
            return;
        }
        dsl.update(FOOD_ORDER)
            .set(FOOD_ORDER.STATUS, order.getStatus())
            .where(FOOD_ORDER.ID.eq(order.getId()))
            .execute();
    }

    private org.jooq.SelectOnConditionStep<? extends Record> select() {
        return dsl.select(
                FOOD_ORDER.ID,
                FOOD_ORDER.ORDER_CODE,
                FOOD_ORDER.DESK_NO,
                FOOD_ORDER.REMARK,
                FOOD_ORDER.STATUS,
                FOOD_ORDER.CARD_NO,
                FOOD_ORDER.CREATE_BY,
                FOOD_ORDER.CREATE_TIME,
                FOOD_ORDER.UPDATE_BY,
                FOOD_ORDER.UPDATE_TIME,
                FOOD_ORDER.ROOM_CODE,
                FOOD_ORDER.TABLE_ID,
                SYS_DEPT.DEPT_NAME
            )
            .from(FOOD_ORDER)
            .leftJoin(SYS_DEPT).on(FOOD_ORDER.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition conditions(FoodOrder order) {
        if (order == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(FOOD_ORDER.ORDER_CODE, order.getOrderCode()),
            eqIfPresent(FOOD_ORDER.DESK_NO, order.getDeskNo()),
            eqIfPresent(FOOD_ORDER.TABLE_ID, order.getTableId()),
            eqIfPresent(FOOD_ORDER.STATUS, order.getStatus()),
            eqIfPresent(FOOD_ORDER.CARD_NO, order.getCardNo()),
            eqIfPresent(FOOD_ORDER.ROOM_CODE, order.getRoomCode())
        );
    }

    private Condition queryConditions(FoodOrder order) {
        if (order == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            isBlank(order.getOrderCode()) ? DSL.noCondition() : FOOD_ORDER.ORDER_CODE.like("%" + order.getOrderCode() + "%"),
            eqIfPresent(FOOD_ORDER.STATUS, order.getStatus()),
            eqIfPresent(FOOD_ORDER.ROOM_CODE, order.getRoomCode()),
            eqIfPresent(FOOD_ORDER.DESK_NO, order.getDeskNo()),
            order.getCreateTime() == null ? DSL.noCondition() : FOOD_ORDER.CREATE_TIME.ge(toLocalDateTime(order.getCreateTime()))
        );
    }

    private Map<Field<?>, Object> writeValues(FoodOrder order) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, FOOD_ORDER.ID, order.getId());
        put(values, FOOD_ORDER.ORDER_CODE, order.getOrderCode());
        put(values, FOOD_ORDER.DESK_NO, order.getDeskNo());
        put(values, FOOD_ORDER.REMARK, order.getRemark());
        put(values, FOOD_ORDER.STATUS, order.getStatus());
        put(values, FOOD_ORDER.CARD_NO, order.getCardNo());
        put(values, FOOD_ORDER.CREATE_BY, order.getCreateBy());
        put(values, FOOD_ORDER.CREATE_TIME, toLocalDateTime(order.getCreateTime()));
        put(values, FOOD_ORDER.UPDATE_BY, order.getUpdateBy());
        put(values, FOOD_ORDER.UPDATE_TIME, toLocalDateTime(order.getUpdateTime()));
        put(values, FOOD_ORDER.ROOM_CODE, order.getRoomCode());
        put(values, FOOD_ORDER.TABLE_ID, order.getTableId());
        return values;
    }

    private FoodOrder mapFoodOrder(Record record) {
        FoodOrder order = new FoodOrder();
        order.setId(record.get(FOOD_ORDER.ID));
        order.setOrderCode(record.get(FOOD_ORDER.ORDER_CODE));
        order.setDeskNo(record.get(FOOD_ORDER.DESK_NO));
        order.setRemark(record.get(FOOD_ORDER.REMARK));
        order.setStatus(record.get(FOOD_ORDER.STATUS));
        order.setCardNo(record.get(FOOD_ORDER.CARD_NO));
        order.setCreateBy(record.get(FOOD_ORDER.CREATE_BY));
        order.setCreateTime(toDate(record.get(FOOD_ORDER.CREATE_TIME)));
        order.setUpdateBy(record.get(FOOD_ORDER.UPDATE_BY));
        order.setUpdateTime(toDate(record.get(FOOD_ORDER.UPDATE_TIME)));
        order.setRoomCode(record.get(FOOD_ORDER.ROOM_CODE));
        order.setTableId(record.get(FOOD_ORDER.TABLE_ID));
        order.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return order;
    }

    private Condition eqIfPresent(Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((Field<Object>) field).eq(value);
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null) {
            values.put(field, value);
        }
    }
}
