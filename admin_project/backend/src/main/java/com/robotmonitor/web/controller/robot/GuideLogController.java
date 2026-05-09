/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.bot.domain.GuideLog
 *  com.robotmonitor.bot.domain.GuideLogInfoRequest
 *  com.robotmonitor.bot.service.IGuideLogService
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
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.robot;

import com.robotmonitor.bot.domain.GuideLog;
import com.robotmonitor.bot.domain.GuideLogInfoRequest;
import com.robotmonitor.bot.service.IGuideLogService;
import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
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
@RequestMapping(value={"/ai/log"})
public class GuideLogController
extends BaseController {
    @Autowired
    private IGuideLogService guideLogService;

    @PreAuthorize(value="@ss.hasPermi('ai:log:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(GuideLog guideLog) {
        this.startPage();
        List list = this.guideLogService.selectGuideLogList(guideLog);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('ai:log:list')")
    @GetMapping(value={"/infoList"})
    public TableDataInfo infoList(GuideLogInfoRequest guideLogInfoRequest) {
        this.startPage();
        List list = this.guideLogService.selectGuideLogInfoList(guideLogInfoRequest);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('ai:log:export')")
    @Log(title="\u5f15\u5bfc\u65e5\u5fd7", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, GuideLog guideLog) {
        List list = this.guideLogService.selectGuideLogList(guideLog);
        ExcelUtil util = new ExcelUtil(GuideLog.class);
        util.exportExcel(response, list, "\u5f15\u5bfc\u65e5\u5fd7\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('ai:log:query')")
    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.guideLogService.selectGuideLogById(id));
    }

    @PreAuthorize(value="@ss.hasPermi('ai:log:add')")
    @Log(title="\u5f15\u5bfc\u65e5\u5fd7", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GuideLog guideLog) {
        return this.toAjax(this.guideLogService.insertGuideLog(guideLog));
    }

    @PreAuthorize(value="@ss.hasPermi('ai:log:edit')")
    @Log(title="\u5f15\u5bfc\u65e5\u5fd7", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GuideLog guideLog) {
        return this.toAjax(this.guideLogService.updateGuideLog(guideLog));
    }

    @PreAuthorize(value="@ss.hasPermi('ai:log:remove')")
    @Log(title="\u5f15\u5bfc\u65e5\u5fd7", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.guideLogService.deleteGuideLogByIds(ids));
    }
}
