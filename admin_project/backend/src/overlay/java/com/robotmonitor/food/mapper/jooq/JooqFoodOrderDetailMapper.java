package com.robotmonitor.food.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.FOOD_ORDER_DETAIL;

import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.food.domain.FoodOrderDetail;
import com.robotmonitor.food.mapper.FoodOrderDetailMapper;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqFoodOrderDetailMapper extends GenericJooqCrudSupport<FoodOrderDetail> implements FoodOrderDetailMapper {
    public JooqFoodOrderDetailMapper(DSLContext dsl) {
        super(dsl, FOOD_ORDER_DETAIL, FOOD_ORDER_DETAIL.ID, FoodOrderDetail.class);
    }

    @Override
    public FoodOrderDetail selectFoodOrderDetailByFoodOrderDetailId(Long id) {
        return selectById(id);
    }

    @Override
    public List<FoodOrderDetail> selectFoodOrderDetailList(FoodOrderDetail query) {
        return selectList(query);
    }

    @Override
    public int insertFoodOrderDetail(FoodOrderDetail detail) {
        return insert(detail);
    }

    @Override
    public int updateFoodOrderDetail(FoodOrderDetail detail) {
        return update(detail);
    }

    @Override
    public int deleteFoodOrderDetailByFoodOrderDetailId(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteFoodOrderDetailByFoodOrderDetailIds(Long[] ids) {
        return deleteByIds(ids);
    }
}
