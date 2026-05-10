package com.robotmonitor.web.controller.food;

import static com.robotmonitor.jooq.generated.Tables.FOOD_CONFIG;
import static com.robotmonitor.jooq.generated.Tables.FOOD_DAILY;
import static com.robotmonitor.jooq.generated.Tables.FOOD_ORDER;
import static com.robotmonitor.jooq.generated.Tables.FOOD_ORDER_DETAIL;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.PageDomain;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.core.page.TableSupport;
import com.robotmonitor.food.domain.FoodConfig;
import com.robotmonitor.food.domain.FoodDaily;
import com.robotmonitor.food.domain.FoodOrder;
import com.robotmonitor.food.domain.FoodOrderDetail;
import com.robotmonitor.food.domain.FoodPlan;
import com.robotmonitor.food.dto.FoodOrderDto;
import com.robotmonitor.food.service.IFoodConfigService;
import com.robotmonitor.food.service.IFoodDailyService;
import com.robotmonitor.food.service.IFoodOrderDetailService;
import com.robotmonitor.food.service.IFoodOrderService;
import com.robotmonitor.food.service.IFoodPlanService;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/food"})
public class FoodController extends BaseController {
    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("0.00");

    @Autowired
    private IFoodOrderService foodOrderService;
    @Autowired
    private IFoodOrderDetailService foodOrderDetailService;
    @Autowired
    private IFoodConfigService foodConfigService;
    @Autowired
    private IFoodPlanService foodPlanService;
    @Autowired
    private IFoodDailyService foodDailyService;
    @Autowired
    private DSLContext dsl;

    @PostMapping({"/insertFoodConfig"})
    public AjaxResult insertFoodConfig(@RequestBody FoodConfig foodConfig) {
        return toAjax(foodConfigService.insertFoodConfig(foodConfig));
    }

    @PostMapping({"/updateFoodConfig"})
    public AjaxResult updateFoodConfig(@RequestBody FoodConfig foodConfig) {
        return toAjax(foodConfigService.updateFoodConfig(foodConfig));
    }

    @PostMapping({"/deleteFoodConfigByFoodIds"})
    public AjaxResult deleteFoodConfigByFoodIds(@RequestBody Long[] foodIds) {
        return toAjax(foodConfigService.deleteFoodConfigByFoodIds(foodIds));
    }

    @PostMapping({"/selectFoodConfigList"})
    public TableDataInfo selectFoodConfigList(FoodConfig foodConfig) {
        startPage();
        List<FoodConfig> list = foodConfigService.selectFoodConfigList(foodConfig);
        return getDataTable(list);
    }

    @PostMapping({"/queryFoodConfigList"})
    public TableDataInfo queryFoodConfigList() {
        startPage();
        return getDataTable(foodConfigService.queryFoodConfigList());
    }

    @GetMapping({"getFoodConfigById"})
    public AjaxResult getFoodConfigById(Long foodId) {
        return AjaxResult.success(foodConfigService.selectFoodConfigByFoodId(foodId));
    }

    @PostMapping({"/insertFoodPlan"})
    public AjaxResult insertFoodPlan(@RequestBody FoodPlan foodPlan) {
        return toAjax(foodPlanService.insertFoodPlan(foodPlan));
    }

    @PostMapping({"/selectFoodPlanList"})
    public TableDataInfo selectFoodPlanList(FoodPlan foodPlan) {
        startPage();
        return getDataTable(foodPlanService.selectFoodPlanList(foodPlan));
    }

    @GetMapping({"getFoodPlanById"})
    public AjaxResult getFoodPlanById(Long id) {
        FoodPlan info = foodPlanService.selectFoodPlanById(id);
        return info == null ? AjaxResult.error("未获取到餐食计划") : AjaxResult.success(info);
    }

    @PostMapping({"updateFoodPlan"})
    public AjaxResult updateFoodPlan(@RequestBody FoodPlan foodPlan) {
        return toAjax(foodPlanService.updateFoodPlan(foodPlan));
    }

    @PostMapping({"deleteFoodPlan"})
    public AjaxResult deleteFoodPlan(@RequestBody Long[] ids) {
        return toAjax(foodPlanService.deleteFoodPlanByIds(ids));
    }

    @GetMapping({"selectFoodDaily"})
    public AjaxResult selectFoodDaily(
        @RequestParam(value = "foodDate", required = false) String foodDate,
        @RequestParam(value = "roomCode", required = false) String roomCode
    ) {
        return AjaxResult.success(queryDailyRows(foodDate, roomCode, null, null, 0, Integer.MAX_VALUE));
    }

    @PostMapping({"selectFoodDailyList"})
    public TableDataInfo selectFoodDailyList(
        @RequestParam(value = "foodDate", required = false) String foodDate,
        @RequestParam(value = "roomCode", required = false) String roomCode,
        @RequestParam(value = "status", required = false) String status,
        @RequestParam(value = "foodName", required = false) String foodName
    ) {
        PageDomain page = TableSupport.getPageDomain();
        int pageNum = page.getPageNum() == null ? 1 : page.getPageNum();
        int pageSize = page.getPageSize() == null ? 20 : page.getPageSize();
        Condition condition = dailyCondition(foodDate, roomCode, status, foodName);
        long total = dsl.selectCount()
            .from(FOOD_DAILY)
            .leftJoin(FOOD_CONFIG).on(FOOD_CONFIG.FOOD_ID.eq(FOOD_DAILY.FOOD_ID))
            .where(condition)
            .fetchOne(0, long.class);
        List<Map<String, Object>> rows = queryDailyRows(foodDate, roomCode, status, foodName, (pageNum - 1) * pageSize, pageSize);
        return table(rows, total);
    }

    @GetMapping({"getFoodDailyById"})
    public AjaxResult getFoodDailyById(Long id) {
        FoodDaily info = foodDailyService.selectFoodDailyById(id);
        return info == null ? AjaxResult.error("未获取到菜单") : AjaxResult.success(info);
    }

    @PostMapping({"insertFoodDaily"})
    public AjaxResult insertFoodDaily(@RequestBody FoodDaily foodDaily) {
        if (isBlank(foodDaily.getStatus())) {
            foodDaily.setStatus("1");
        }
        return toAjax(foodDailyService.insertFoodDaily(foodDaily));
    }

    @PostMapping({"updateFoodDaily"})
    public AjaxResult updateFoodDaily(@RequestBody FoodDaily foodDaily) {
        return toAjax(foodDailyService.updateFoodDaily(foodDaily));
    }

    @PostMapping({"deleteFoodDaily"})
    public AjaxResult deleteFoodDaily(@RequestBody Long[] ids) {
        return toAjax(foodDailyService.deleteFoodDailyByIds(ids));
    }

    @PostMapping({"createOrder"})
    public AjaxResult createOrder(@RequestBody FoodOrderDto parameter) {
        try {
            String orderCode = foodOrderService.createOrder(parameter);
            return new AjaxResult(200, orderCode, null);
        } catch (Exception ex) {
            return new AjaxResult(500, ex.getMessage() == null ? "创建订单失败，请重新尝试！" : ex.getMessage(), null);
        }
    }

    @PostMapping({"cancelOrder"})
    public AjaxResult cancelOrder(@RequestBody Long id) {
        FoodOrder order = foodOrderService.selectFoodOrderByFoodOrderId(id);
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        if (Objects.equals(order.getStatus(), "2") || Objects.equals(order.getStatus(), "3")) {
            return AjaxResult.error("订单已制作，不可取消");
        }
        order.setStatus("0");
        return toAjax(foodOrderService.updateFoodOrder(order));
    }

    @PostMapping({"receiveOrder"})
    public AjaxResult receiveOrder(@RequestBody Long id) {
        FoodOrder order = foodOrderService.selectFoodOrderByFoodOrderId(id);
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        if (!Objects.equals(order.getStatus(), "1")) {
            return AjaxResult.error("只有已下单订单可以接单");
        }
        order.setStatus("2");
        return toAjax(foodOrderService.updateFoodOrder(order));
    }

    @PostMapping({"finishOrder"})
    public AjaxResult finishOrder(@RequestBody Long id) {
        FoodOrder order = foodOrderService.selectFoodOrderByFoodOrderId(id);
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        if (Objects.equals(order.getStatus(), "0")) {
            return AjaxResult.error("订单已取消，不可完成");
        }
        if (Objects.equals(order.getStatus(), "3")) {
            return AjaxResult.error("订单已完成，无需操作");
        }
        order.setStatus("3");
        return toAjax(foodOrderService.updateFoodOrder(order));
    }

    @PostMapping({"/queryOrderList"})
    public TableDataInfo queryOrderList(FoodOrder foodOrder) {
        startPage();
        return getDataTable(foodOrderService.queryFoodOrderList(foodOrder));
    }

    @GetMapping({"getOrderById"})
    public AjaxResult getOrderById(Long id) {
        FoodOrder order = foodOrderService.selectFoodOrderByFoodOrderId(id);
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        List<FoodOrderDetail> details = foodOrderDetailService.selectFoodOrderDetailListByOrderId(id);
        FoodOrderDto dto = new FoodOrderDto();
        dto.setId(order.getId());
        dto.setOrderCode(order.getOrderCode());
        dto.setTableId(order.getTableId());
        dto.setDeskNo(order.getDeskNo());
        dto.setStatus(order.getStatus());
        dto.setRemark(order.getRemark());
        dto.setCardNo(order.getCardNo());
        dto.setRoomCode(order.getRoomCode());
        dto.setCreateTime(order.getCreateTime());
        dto.setOrderDetailList(details);
        dto.setOrderPrice(PRICE_FORMAT.format(sumPrice(details)));
        return AjaxResult.success(dto);
    }

    @PostMapping({"deleteOrder"})
    public AjaxResult deleteOrder(@RequestBody Long[] ids) {
        if (ids == null || ids.length == 0) {
            return AjaxResult.error("请选择订单");
        }
        int rows = dsl.transactionResult(configuration -> {
            DSLContext tx = DSL.using(configuration);
            tx.deleteFrom(FOOD_ORDER_DETAIL).where(FOOD_ORDER_DETAIL.ORDER_ID.in(Arrays.asList(ids))).execute();
            return tx.deleteFrom(FOOD_ORDER).where(FOOD_ORDER.ID.in(Arrays.asList(ids))).execute();
        });
        return toAjax(rows);
    }

    private List<Map<String, Object>> queryDailyRows(String foodDate, String roomCode, String status, String foodName, int offset, int limit) {
        Condition condition = dailyCondition(foodDate, roomCode, status, foodName);
        return dsl.select(
                FOOD_DAILY.ID,
                FOOD_DAILY.FOOD_DATE,
                FOOD_DAILY.FOOD_ID,
                FOOD_DAILY.STATUS,
                FOOD_DAILY.ROOM_CODE,
                FOOD_CONFIG.NAME,
                FOOD_CONFIG.DIC_TYPE_CODE,
                FOOD_CONFIG.PRICE,
                FOOD_CONFIG.CALORIE,
                FOOD_CONFIG.REMARK,
                FOOD_CONFIG.IMG_IDS,
                SYS_DEPT.DEPT_NAME
            )
            .from(FOOD_DAILY)
            .leftJoin(FOOD_CONFIG).on(FOOD_CONFIG.FOOD_ID.eq(FOOD_DAILY.FOOD_ID))
            .leftJoin(SYS_DEPT).on(SYS_DEPT.ROOM_CODE.eq(FOOD_DAILY.ROOM_CODE))
            .where(condition)
            .orderBy(FOOD_DAILY.FOOD_DATE.desc(), FOOD_DAILY.ID.desc())
            .limit(limit)
            .offset(offset)
            .fetch(this::dailyRow);
    }

    private Condition dailyCondition(String foodDate, String roomCode, String status, String foodName) {
        Condition condition = DSL.noCondition();
        if (!isBlank(foodDate)) {
            condition = condition.and(FOOD_DAILY.FOOD_DATE.eq(LocalDate.parse(foodDate.substring(0, Math.min(10, foodDate.length())))));
        }
        if (!isBlank(roomCode)) {
            condition = condition.and(FOOD_DAILY.ROOM_CODE.eq(roomCode));
        }
        if (!isBlank(status)) {
            condition = condition.and(FOOD_DAILY.STATUS.eq(status));
        }
        if (!isBlank(foodName)) {
            condition = condition.and(FOOD_CONFIG.NAME.like("%" + foodName + "%"));
        }
        return condition;
    }

    private Map<String, Object> dailyRow(Record record) {
        Map<String, Object> row = new LinkedHashMap<>();
        LocalDate foodDate = record.get(FOOD_DAILY.FOOD_DATE);
        String imgIds = record.get(FOOD_CONFIG.IMG_IDS);
        row.put("id", record.get(FOOD_DAILY.ID));
        row.put("foodDate", foodDate == null ? null : foodDate.toString());
        row.put("foodId", record.get(FOOD_DAILY.FOOD_ID));
        row.put("status", record.get(FOOD_DAILY.STATUS));
        row.put("roomCode", record.get(FOOD_DAILY.ROOM_CODE));
        row.put("foodName", record.get(FOOD_CONFIG.NAME));
        row.put("foodType", record.get(FOOD_CONFIG.DIC_TYPE_CODE));
        row.put("price", record.get(FOOD_CONFIG.PRICE));
        row.put("calorie", record.get(FOOD_CONFIG.CALORIE));
        row.put("remark", record.get(FOOD_CONFIG.REMARK));
        row.put("imgIds", imgIds);
        row.put("imgUrlList", imageUrls(imgIds));
        row.put("deptName", record.get(SYS_DEPT.DEPT_NAME));
        return row;
    }

    private List<String> imageUrls(String imgIds) {
        if (isBlank(imgIds)) {
            return List.of();
        }
        return Arrays.stream(imgIds.split(","))
            .filter(value -> !isBlank(value))
            .map(value -> "/api/rest/image/config/" + value.trim())
            .toList();
    }

    private BigDecimal sumPrice(List<FoodOrderDetail> details) {
        return details.stream()
            .map(detail -> BigDecimal.valueOf(detail.getPrice() == null ? 0 : detail.getPrice())
                .multiply(BigDecimal.valueOf(detail.getNum() == null ? 0 : detail.getNum())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private TableDataInfo table(List<?> rows, long total) {
        TableDataInfo data = new TableDataInfo();
        data.setCode(200);
        data.setMsg("查询成功");
        data.setRows(rows);
        data.setTotal(total);
        return data;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
