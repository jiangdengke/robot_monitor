/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.flight.service.IFlightChangeService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.flight.service.IFlightChangeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/flight"})
public class FlightApiController
extends BaseController {
    @Autowired
    private IFlightChangeService flightChangeService;

    @GetMapping(value={"/queryList"})
    public TableDataInfo queryList() {
        List list = this.flightChangeService.queryList();
        return this.getDataTable(list);
    }
}
