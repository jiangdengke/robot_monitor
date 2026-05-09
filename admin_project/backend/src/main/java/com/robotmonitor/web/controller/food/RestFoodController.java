/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.config.domain.ConfigTable
 *  com.robotmonitor.config.service.IConfigTableService
 *  com.robotmonitor.food.domain.FoodOrder
 *  com.robotmonitor.food.dto.FoodOrderDto
 *  com.robotmonitor.food.service.IFoodDailyService
 *  com.robotmonitor.food.service.IFoodOrderService
 *  com.robotmonitor.system.service.ISysDeptService
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.food;

import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.config.domain.ConfigTable;
import com.robotmonitor.config.service.IConfigTableService;
import com.robotmonitor.food.domain.FoodOrder;
import com.robotmonitor.food.dto.FoodOrderDto;
import com.robotmonitor.food.service.IFoodDailyService;
import com.robotmonitor.food.service.IFoodOrderService;
import com.robotmonitor.system.service.ISysDeptService;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/food"})
public class RestFoodController
extends BaseController {
    @Autowired
    private IFoodOrderService foodOrderService;
    @Autowired
    private IFoodDailyService foodDailyService;
    @Autowired
    private IConfigTableService configTableService;
    @Autowired
    private ISysDeptService deptService;

    @GetMapping(value={"/getTableInfo/{id}"})
    public AjaxResult getTableInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.configTableService.selectConfigTableById(id));
    }

    @GetMapping(value={"selectFoodDaily"})
    public AjaxResult selectFoodDaily(String foodDate, String roomCode) {
        List infoList = this.foodDailyService.selectFoodDailyList(foodDate, roomCode);
        if (ObjectUtils.isEmpty((Object)infoList)) {
            return new AjaxResult(500, "\u672a\u83b7\u53d6\u5230\u5f53\u65e5\u83dc\u5355", null);
        }
        return AjaxResult.success((Object)infoList);
    }

    @PostMapping(value={"createOrder"})
    public AjaxResult createOrder(@RequestBody FoodOrderDto paramter) {
        String orderCode;
        try {
            orderCode = this.foodOrderService.createOrder(paramter);
        }
        catch (Exception e) {
            return new AjaxResult(500, "\u521b\u5efa\u8ba2\u5355\u5931\u8d25\uff0c\u6e05\u91cd\u65b0\u5c1d\u8bd5\uff01", null);
        }
        return new AjaxResult(200, orderCode, null);
    }

    @PostMapping(value={"cancelOrder"})
    public AjaxResult cancelOrder(@RequestBody Long id) {
        FoodOrder order = this.foodOrderService.selectFoodOrderByFoodOrderId(id);
        if (ObjectUtils.isEmpty((Object)order)) {
            return new AjaxResult(500, "\u8ba2\u5355\u4e0d\u5b58\u5728", null);
        }
        if (order.getStatus().equals("2") || order.getStatus().equals("3")) {
            return new AjaxResult(500, "\u8ba2\u5355\u5df2\u5236\u4f5c\uff0c\u4e0d\u53ef\u53d6\u6d88", null);
        }
        order.setStatus("0");
        if (this.foodOrderService.updateFoodOrder(order) > 0) {
            return AjaxResult.success();
        }
        return new AjaxResult(500, "\u8ba2\u5355\u53d6\u6d88\u5931\u8d25\uff0c\u8bf7\u91cd\u65b0\u5c1d\u8bd5", null);
    }

    @PostMapping(value={"finishOrder"})
    public AjaxResult finishOrder(@RequestBody Long id) {
        FoodOrder order = this.foodOrderService.selectFoodOrderByFoodOrderId(id);
        if (ObjectUtils.isEmpty((Object)order)) {
            return new AjaxResult(500, "\u8ba2\u5355\u4e0d\u5b58\u5728", null);
        }
        if (order.getStatus().equals("0")) {
            return new AjaxResult(500, "\u8ba2\u5355\u5df2\u53d6\u6d88\uff0c\u4e0d\u53ef\u5b8c\u6210", null);
        }
        if (order.getStatus().equals("3")) {
            return new AjaxResult(500, "\u8ba2\u5355\u5df2\u5b8c\u6210\uff0c\u65e0\u9700\u64cd\u4f5c", null);
        }
        order.setStatus("3");
        if (this.foodOrderService.updateFoodOrder(order) > 0) {
            return AjaxResult.success();
        }
        return new AjaxResult(500, "\u8ba2\u5355\u5b8c\u6210\u5931\u8d25\uff0c\u8bf7\u91cd\u65b0\u5c1d\u8bd5", null);
    }

    @PostMapping(value={"/queryOrderList"})
    public TableDataInfo queryOrderList(FoodOrder foodOrder) {
        this.startPage();
        List list = this.foodOrderService.queryFoodOrderList(foodOrder);
        return this.getDataTable(list);
    }

    @PostMapping(value={"/roomlist"})
    public AjaxResult roomlist() {
        List depts = this.deptService.roomList();
        return AjaxResult.success((Object)depts);
    }

    @GetMapping(value={"/tableList"})
    public TableDataInfo tableList(ConfigTable configTable) {
        this.startPage();
        List list = this.configTableService.selectConfigTableList(configTable);
        return this.getDataTable(list);
    }
}
