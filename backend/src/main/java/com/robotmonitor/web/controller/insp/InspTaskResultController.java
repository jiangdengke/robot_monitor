/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.bot.service.IInspTaskResultService
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.insp.InspTaskResult
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

import com.robotmonitor.bot.service.IInspTaskResultService;
import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.insp.InspTaskResult;
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
@RequestMapping(value={"/insp/result"})
public class InspTaskResultController
extends BaseController {
    @Autowired
    private IInspTaskResultService inspTaskResultService;

    @PreAuthorize(value="@ss.hasPermi('config:result:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(InspTaskResult inspTaskResult) {
        this.startPage();
        List list = this.inspTaskResultService.selectInspTaskResultList(inspTaskResult);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('config:result:export')")
    @Log(title="\u5de1\u68c0\u7ed3\u679c", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, InspTaskResult inspTaskResult) {
        List list = this.inspTaskResultService.selectInspTaskResultList(inspTaskResult);
        ExcelUtil util = new ExcelUtil(InspTaskResult.class);
        util.exportExcel(response, list, "\u5de1\u68c0\u7ed3\u679c\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('config:result:query')")
    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.inspTaskResultService.selectInspTaskResultById(id));
    }

    @PreAuthorize(value="@ss.hasPermi('config:result:add')")
    @Log(title="\u5de1\u68c0\u7ed3\u679c", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InspTaskResult inspTaskResult) {
        return this.toAjax(this.inspTaskResultService.insertInspTaskResult(inspTaskResult));
    }

    @PreAuthorize(value="@ss.hasPermi('config:result:edit')")
    @Log(title="\u5de1\u68c0\u7ed3\u679c", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InspTaskResult inspTaskResult) {
        return this.toAjax(this.inspTaskResultService.updateInspTaskResult(inspTaskResult));
    }

    @PreAuthorize(value="@ss.hasPermi('config:result:remove')")
    @Log(title="\u5de1\u68c0\u7ed3\u679c", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.inspTaskResultService.deleteInspTaskResultByIds(ids));
    }

    @PostMapping(value={"/insertInspTaskResult"})
    public int insertInspTaskResult(@RequestBody InspTaskResult inspTaskResult) {
        return this.inspTaskResultService.insertInspTaskResult(inspTaskResult);
    }
}
