/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.bot.service.IInspTaskService
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.insp.InspTask
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
package com.robotmonitor.web.controller.insp;

import com.robotmonitor.bot.service.IInspTaskService;
import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.insp.InspTask;
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
@RequestMapping(value={"/insp/task"})
public class InspTaskController
extends BaseController {
    @Autowired
    private IInspTaskService inspTaskService;

    @PreAuthorize(value="@ss.hasPermi('config:task:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(InspTask inspTask) {
        this.startPage();
        List list = this.inspTaskService.selectInspTaskList(inspTask);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:export')")
    @Log(title="\u5de1\u68c0\u4efb\u52a1", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, InspTask inspTask) {
        List list = this.inspTaskService.selectInspTaskList(inspTask);
        ExcelUtil util = new ExcelUtil(InspTask.class);
        util.exportExcel(response, list, "\u5de1\u68c0\u4efb\u52a1\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:query')")
    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.inspTaskService.selectInspTaskById(id));
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:add')")
    @Log(title="\u5de1\u68c0\u4efb\u52a1", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InspTask inspTask) {
        return this.toAjax(this.inspTaskService.insertInspTask(inspTask));
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:edit')")
    @Log(title="\u5de1\u68c0\u4efb\u52a1", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InspTask inspTask) {
        return this.toAjax(this.inspTaskService.updateInspTask(inspTask));
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:remove')")
    @Log(title="\u5de1\u68c0\u4efb\u52a1", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.inspTaskService.deleteInspTaskByIds(ids));
    }
}
