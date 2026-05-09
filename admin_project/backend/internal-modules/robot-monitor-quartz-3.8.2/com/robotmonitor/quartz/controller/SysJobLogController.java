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
package com.robotmonitor.quartz.controller;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.quartz.domain.SysJobLog;
import com.robotmonitor.quartz.service.ISysJobLogService;
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
@RequestMapping(value={"/monitor/jobLog"})
public class SysJobLogController
extends BaseController {
    @Autowired
    private ISysJobLogService jobLogService;

    @PreAuthorize(value="@ss.hasPermi('monitor:job:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(SysJobLog sysJobLog) {
        this.startPage();
        List<SysJobLog> list = this.jobLogService.selectJobLogList(sysJobLog);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('monitor:job:export')")
    @Log(title="\u4efb\u52a1\u8c03\u5ea6\u65e5\u5fd7", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, SysJobLog sysJobLog) {
        List<SysJobLog> list = this.jobLogService.selectJobLogList(sysJobLog);
        ExcelUtil util = new ExcelUtil(SysJobLog.class);
        util.exportExcel(response, list, "\u8c03\u5ea6\u65e5\u5fd7");
    }

    @PreAuthorize(value="@ss.hasPermi('monitor:job:query')")
    @GetMapping(value={"/{configId}"})
    public AjaxResult getInfo(@PathVariable Long jobLogId) {
        return AjaxResult.success((Object)((Object)this.jobLogService.selectJobLogById(jobLogId)));
    }

    @PreAuthorize(value="@ss.hasPermi('monitor:job:remove')")
    @Log(title="\u5b9a\u65f6\u4efb\u52a1\u8c03\u5ea6\u65e5\u5fd7", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{jobLogIds}"})
    public AjaxResult remove(@PathVariable Long[] jobLogIds) {
        return this.toAjax(this.jobLogService.deleteJobLogByIds(jobLogIds));
    }

    @PreAuthorize(value="@ss.hasPermi('monitor:job:remove')")
    @Log(title="\u8c03\u5ea6\u65e5\u5fd7", businessType=BusinessType.CLEAN)
    @DeleteMapping(value={"/clean"})
    public AjaxResult clean() {
        this.jobLogService.cleanJobLog();
        return AjaxResult.success();
    }
}
