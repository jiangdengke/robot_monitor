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
 *  com.robotmonitor.system.domain.SysOperLog
 *  com.robotmonitor.system.service.ISysOperLogService
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.monitor;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.system.domain.SysOperLog;
import com.robotmonitor.system.service.ISysOperLogService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/monitor/operlog"})
public class SysOperlogController
extends BaseController {
    @Autowired
    private ISysOperLogService operLogService;

    @PreAuthorize(value="@ss.hasPermi('monitor:operlog:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(SysOperLog operLog) {
        this.startPage();
        List list = this.operLogService.selectOperLogList(operLog);
        return this.getDataTable(list);
    }

    @Log(title="\u64cd\u4f5c\u65e5\u5fd7", businessType=BusinessType.EXPORT)
    @PreAuthorize(value="@ss.hasPermi('monitor:operlog:export')")
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, SysOperLog operLog) {
        List list = this.operLogService.selectOperLogList(operLog);
        ExcelUtil util = new ExcelUtil(SysOperLog.class);
        util.exportExcel(response, list, "\u64cd\u4f5c\u65e5\u5fd7");
    }

    @Log(title="\u64cd\u4f5c\u65e5\u5fd7", businessType=BusinessType.DELETE)
    @PreAuthorize(value="@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping(value={"/{operIds}"})
    public AjaxResult remove(@PathVariable Long[] operIds) {
        return this.toAjax(this.operLogService.deleteOperLogByIds(operIds));
    }

    @Log(title="\u64cd\u4f5c\u65e5\u5fd7", businessType=BusinessType.CLEAN)
    @PreAuthorize(value="@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping(value={"/clean"})
    public AjaxResult clean() {
        this.operLogService.cleanOperLog();
        return AjaxResult.success();
    }
}
