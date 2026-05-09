/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.flight.domain.PassengerWarningLog
 *  com.robotmonitor.flight.service.IPassengerWarningLogService
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.flight;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.flight.domain.PassengerWarningLog;
import com.robotmonitor.flight.service.IPassengerWarningLogService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/flight/passengerWarningLog"})
public class PassengerWarningLogController
extends BaseController {
    @Autowired
    private IPassengerWarningLogService passengerWarningLogService;

    @GetMapping(value={"/list"})
    public TableDataInfo list(PassengerWarningLog passengerWarningLog) {
        this.startPage();
        List list = this.passengerWarningLogService.selectPassengerWarningLogList(passengerWarningLog);
        return this.getDataTable(list);
    }

    @Log(title="\u65c5\u5ba2\u63d0\u9192\u65e5\u5fd7", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, PassengerWarningLog passengerWarningLog) {
        List list = this.passengerWarningLogService.selectPassengerWarningLogList(passengerWarningLog);
        ExcelUtil util = new ExcelUtil(PassengerWarningLog.class);
        util.exportExcel(response, list, "\u65c5\u5ba2\u63d0\u9192\u65e5\u5fd7\u6570\u636e");
    }

    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.passengerWarningLogService.selectPassengerWarningLogById(id));
    }

    @PreAuthorize(value="@ss.hasPermi('flight:passengerWarningLog:add')")
    @Log(title="\u65c5\u5ba2\u63d0\u9192\u65e5\u5fd7", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PassengerWarningLog passengerWarningLog) {
        return this.toAjax(this.passengerWarningLogService.insertPassengerWarningLog(passengerWarningLog));
    }

    @Log(title="\u65c5\u5ba2\u63d0\u9192\u65e5\u5fd7", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PassengerWarningLog passengerWarningLog) {
        return this.toAjax(this.passengerWarningLogService.updatePassengerWarningLog(passengerWarningLog));
    }

    @PreAuthorize(value="@ss.hasPermi('flight:passengerWarningLog:remove')")
    @Log(title="\u65c5\u5ba2\u63d0\u9192\u65e5\u5fd7", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.passengerWarningLogService.deletePassengerWarningLogByIds(ids));
    }
}
