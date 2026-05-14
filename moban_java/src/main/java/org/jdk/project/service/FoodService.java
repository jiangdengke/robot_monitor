package org.jdk.project.service;

import static org.jooq.generated.project.Tables.DINING_TABLE;
import static org.jooq.generated.project.Tables.FOOD_DAILY_MENU;
import static org.jooq.generated.project.Tables.FOOD_ITEM;
import static org.jooq.generated.project.Tables.FOOD_ITEM_IMAGE;
import static org.jooq.generated.project.Tables.FOOD_ORDER;
import static org.jooq.generated.project.Tables.FOOD_ORDER_ITEM;
import static org.jooq.generated.project.Tables.FOOD_PLAN;
import static org.jooq.generated.project.Tables.FOOD_PLAN_ITEM;
import static org.jooq.generated.project.Tables.LOUNGE;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.LoungeDto;
import org.jdk.project.dto.food.FoodDailyUpsertRequest;
import org.jdk.project.dto.food.FoodItemUpsertRequest;
import org.jdk.project.dto.food.FoodOrderItemUpsertRequest;
import org.jdk.project.dto.food.FoodOrderUpsertRequest;
import org.jdk.project.dto.food.FoodPlanUpsertRequest;
import org.jdk.project.exception.BusinessException;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.FoodDailyMenu;
import org.jooq.generated.project.tables.pojos.FoodItem;
import org.jooq.generated.project.tables.pojos.FoodOrder;
import org.jooq.generated.project.tables.pojos.FoodOrderItem;
import org.jooq.generated.project.tables.pojos.FoodPlan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodService {

  private final DSLContext dsl;

  public ListResponse<FoodItem> listFoodItems() {
    List<FoodItem> rows =
        dsl.selectFrom(FOOD_ITEM).orderBy(FOOD_ITEM.ID.desc()).fetchInto(FoodItem.class);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<FoodDailyMenu> listFoodDailies() {
    List<FoodDailyMenu> rows =
        dsl.selectFrom(FOOD_DAILY_MENU).orderBy(FOOD_DAILY_MENU.ID.desc()).fetchInto(FoodDailyMenu.class);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<FoodPlan> listFoodPlans() {
    List<FoodPlan> rows =
        dsl.selectFrom(FOOD_PLAN).orderBy(FOOD_PLAN.ID.desc()).fetchInto(FoodPlan.class);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<FoodOrder> listFoodOrders() {
    List<FoodOrder> rows =
        dsl.selectFrom(FOOD_ORDER).orderBy(FOOD_ORDER.ID.desc()).fetchInto(FoodOrder.class);
    return ListResponse.of(rows.size(), rows);
  }

  @Transactional
  public Long createFoodItem(FoodItemUpsertRequest request) {
    FoodItem food = new FoodItem();
    food.setLoungeId(request.getLoungeId());
    food.setName(request.getName());
    food.setCategory(request.getCategory());
    food.setPrice(request.getPrice());
    food.setCalorie(request.getCalorie());
    food.setRemark(request.getRemark());
    return dsl.insertInto(FOOD_ITEM)
        .set(dsl.newRecord(FOOD_ITEM, food))
        .returningResult(FOOD_ITEM.ID)
        .fetchOne(FOOD_ITEM.ID);
  }

  @Transactional
  public void updateFoodItem(Long id, FoodItemUpsertRequest request) {
    ensureUpdated(
        dsl.update(FOOD_ITEM)
            .set(FOOD_ITEM.LOUNGE_ID, request.getLoungeId())
            .set(FOOD_ITEM.NAME, request.getName())
            .set(FOOD_ITEM.CATEGORY, request.getCategory())
            .set(FOOD_ITEM.PRICE, request.getPrice())
            .set(FOOD_ITEM.CALORIE, request.getCalorie())
            .set(FOOD_ITEM.REMARK, request.getRemark())
            .where(FOOD_ITEM.ID.eq(id))
            .execute(),
        "菜品不存在");
  }

  @Transactional
  public void deleteFoodItem(Long id) {
    dsl.deleteFrom(FOOD_ITEM).where(FOOD_ITEM.ID.eq(id)).execute();
    dsl.deleteFrom(FOOD_ITEM_IMAGE).where(FOOD_ITEM_IMAGE.FOOD_ITEM_ID.eq(id)).execute();
  }

  @Transactional
  public Long createFoodDaily(FoodDailyUpsertRequest request) {
    FoodDailyMenu daily = new FoodDailyMenu();
    daily.setLoungeId(request.getLoungeId());
    daily.setMenuDate(LocalDate.parse(request.getFoodDate()));
    daily.setFoodItemId(request.getFoodItemId());
    daily.setEnabled(request.getEnabled() == null || request.getEnabled());
    return dsl.insertInto(FOOD_DAILY_MENU)
        .set(dsl.newRecord(FOOD_DAILY_MENU, daily))
        .returningResult(FOOD_DAILY_MENU.ID)
        .fetchOne(FOOD_DAILY_MENU.ID);
  }

  @Transactional
  public void updateFoodDaily(Long id, FoodDailyUpsertRequest request) {
    ensureUpdated(
        dsl.update(FOOD_DAILY_MENU)
            .set(FOOD_DAILY_MENU.LOUNGE_ID, request.getLoungeId())
            .set(FOOD_DAILY_MENU.MENU_DATE, LocalDate.parse(request.getFoodDate()))
            .set(FOOD_DAILY_MENU.FOOD_ITEM_ID, request.getFoodItemId())
            .set(FOOD_DAILY_MENU.ENABLED, request.getEnabled() == null || request.getEnabled())
            .where(FOOD_DAILY_MENU.ID.eq(id))
            .execute(),
        "每日菜单不存在");
  }

  @Transactional
  public void deleteFoodDaily(Long id) {
    dsl.deleteFrom(FOOD_DAILY_MENU).where(FOOD_DAILY_MENU.ID.eq(id)).execute();
  }

  @Transactional
  public Long createFoodPlan(FoodPlanUpsertRequest request) {
    FoodPlan plan = new FoodPlan();
    plan.setLoungeId(request.getLoungeId());
    plan.setStartDate(LocalDate.parse(request.getStartDate()));
    plan.setEndDate(LocalDate.parse(request.getEndDate()));
    Long planId =
        dsl.insertInto(FOOD_PLAN)
            .set(dsl.newRecord(FOOD_PLAN, plan))
            .returningResult(FOOD_PLAN.ID)
            .fetchOne(FOOD_PLAN.ID);
    replacePlanItems(planId, request.getFoodItemIds());
    return planId;
  }

  @Transactional
  public void updateFoodPlan(Long id, FoodPlanUpsertRequest request) {
    ensureUpdated(
        dsl.update(FOOD_PLAN)
            .set(FOOD_PLAN.LOUNGE_ID, request.getLoungeId())
            .set(FOOD_PLAN.START_DATE, LocalDate.parse(request.getStartDate()))
            .set(FOOD_PLAN.END_DATE, LocalDate.parse(request.getEndDate()))
            .where(FOOD_PLAN.ID.eq(id))
            .execute(),
        "菜单计划不存在");
    replacePlanItems(id, request.getFoodItemIds());
  }

  @Transactional
  public void deleteFoodPlan(Long id) {
    dsl.deleteFrom(FOOD_PLAN_ITEM).where(FOOD_PLAN_ITEM.FOOD_PLAN_ID.eq(id)).execute();
    dsl.deleteFrom(FOOD_PLAN).where(FOOD_PLAN.ID.eq(id)).execute();
  }

  @Transactional
  public Long createFoodOrder(FoodOrderUpsertRequest request) {
    FoodOrder order = new FoodOrder();
    order.setLoungeId(request.getLoungeId());
    order.setDiningTableId(request.getDiningTableId());
    order.setOrderCode(request.getOrderCode());
    order.setDeskNo(request.getDeskNo());
    order.setCardNo(request.getCardNo());
    order.setStatus("CREATED");
    order.setTotalAmount(request.getTotalAmount());
    order.setRemark(request.getRemark());
    Long orderId =
        dsl.insertInto(FOOD_ORDER)
            .set(dsl.newRecord(FOOD_ORDER, order))
            .returningResult(FOOD_ORDER.ID)
            .fetchOne(FOOD_ORDER.ID);
    replaceOrderItems(orderId, request.getItems());
    return orderId;
  }

  @Transactional
  public void updateFoodOrder(Long id, FoodOrderUpsertRequest request) {
    ensureUpdated(
        dsl.update(FOOD_ORDER)
            .set(FOOD_ORDER.LOUNGE_ID, request.getLoungeId())
            .set(FOOD_ORDER.DINING_TABLE_ID, request.getDiningTableId())
            .set(FOOD_ORDER.ORDER_CODE, request.getOrderCode())
            .set(FOOD_ORDER.DESK_NO, request.getDeskNo())
            .set(FOOD_ORDER.CARD_NO, request.getCardNo())
            .set(FOOD_ORDER.TOTAL_AMOUNT, request.getTotalAmount())
            .set(FOOD_ORDER.REMARK, request.getRemark())
            .where(FOOD_ORDER.ID.eq(id))
            .execute(),
        "订单不存在");
    replaceOrderItems(id, request.getItems());
  }

  @Transactional
  public void deleteFoodOrder(Long id) {
    dsl.deleteFrom(FOOD_ORDER_ITEM).where(FOOD_ORDER_ITEM.ORDER_ID.eq(id)).execute();
    dsl.deleteFrom(FOOD_ORDER).where(FOOD_ORDER.ID.eq(id)).execute();
  }

  private void replacePlanItems(Long planId, List<Long> foodItemIds) {
    dsl.deleteFrom(FOOD_PLAN_ITEM).where(FOOD_PLAN_ITEM.FOOD_PLAN_ID.eq(planId)).execute();
    if (foodItemIds == null) return;
    foodItemIds.forEach(foodItemId -> dsl.insertInto(FOOD_PLAN_ITEM)
        .set(FOOD_PLAN_ITEM.FOOD_PLAN_ID, planId)
        .set(FOOD_PLAN_ITEM.FOOD_ITEM_ID, foodItemId)
        .execute());
  }

  private void replaceOrderItems(Long orderId, List<FoodOrderItemUpsertRequest> items) {
    dsl.deleteFrom(FOOD_ORDER_ITEM).where(FOOD_ORDER_ITEM.ORDER_ID.eq(orderId)).execute();
    if (items == null) return;
    for (FoodOrderItemUpsertRequest item : items) {
      FoodOrderItem orderItem = new FoodOrderItem();
      orderItem.setOrderId(orderId);
      orderItem.setFoodItemId(item.getFoodItemId());
      orderItem.setFoodName(item.getFoodName());
      orderItem.setQuantity(item.getQuantity());
      orderItem.setUnitPrice(item.getUnitPrice());
      dsl.insertInto(FOOD_ORDER_ITEM).set(dsl.newRecord(FOOD_ORDER_ITEM, orderItem)).execute();
    }
  }

  private void ensureUpdated(int updated, String message) {
    if (updated == 0) throw new BusinessException(message);
  }
}
