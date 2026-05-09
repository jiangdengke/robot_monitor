/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.food.service.IFoodConfigService
 *  com.robotmonitor.food.service.IFoodOrderService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.food.service.IFoodConfigService;
import com.robotmonitor.food.service.IFoodOrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/food"})
public class FoodApiController
extends BaseController {
    @Autowired
    private IFoodConfigService foodConfigService;
    @Autowired
    private IFoodOrderService foodOrderService;

    @GetMapping(value={"/queryFoodConfigList"})
    public TableDataInfo queryFoodConfigList() {
        List list = this.foodConfigService.queryFoodConfigList();
        return this.getDataTable(list);
    }
}
