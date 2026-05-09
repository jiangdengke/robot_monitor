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
 *  com.robotmonitor.system.domain.SysLogininfor
 *  com.robotmonitor.system.service.ISysLogininforService
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
import com.robotmonitor.system.domain.SysLogininfor;
import com.robotmonitor.system.service.ISysLogininforService;
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
@RequestMapping(value={"/monitor/logininfor"})
public class SysLogininforController
extends BaseController {
    @Autowired
    private ISysLogininforService logininforService;

    @PreAuthorize(value="@ss.hasPermi('monitor:logininfor:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(SysLogininfor logininfor) {
        this.startPage();
        List list = this.logininforService.selectLogininforList(logininfor);
        return this.getDataTable(list);
    }

    @Log(title="\u767b\u5f55\u65e5\u5fd7", businessType=BusinessType.EXPORT)
    @PreAuthorize(value="@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, SysLogininfor logininfor) {
        List list = this.logininforService.selectLogininforList(logininfor);
        ExcelUtil util = new ExcelUtil(SysLogininfor.class);
        util.exportExcel(response, list, "\u767b\u5f55\u65e5\u5fd7");
    }

    @PreAuthorize(value="@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title="\u767b\u5f55\u65e5\u5fd7", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{infoIds}"})
    public AjaxResult remove(@PathVariable Long[] infoIds) {
        return this.toAjax(this.logininforService.deleteLogininforByIds(infoIds));
    }

    @PreAuthorize(value="@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title="\u767b\u5f55\u65e5\u5fd7", businessType=BusinessType.CLEAN)
    @DeleteMapping(value={"/clean"})
    public AjaxResult clean() {
        this.logininforService.cleanLogininfor();
        return AjaxResult.success();
    }
}
