/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.config.MessageLog
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.config.service.IMessageLogService
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
package com.robotmonitor.web.controller.config;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.config.MessageLog;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.config.service.IMessageLogService;
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
@RequestMapping(value={"/config/msg"})
public class MessageLogController
extends BaseController {
    @Autowired
    private IMessageLogService messageLogService;

    @PreAuthorize(value="@ss.hasPermi('config:msg:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(MessageLog messageLog) {
        this.startPage();
        List list = this.messageLogService.selectMessageLogList(messageLog);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('config:msg:export')")
    @Log(title="\u6d88\u606f\u65e5\u5fd7", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, MessageLog messageLog) {
        List list = this.messageLogService.selectMessageLogList(messageLog);
        ExcelUtil util = new ExcelUtil(MessageLog.class);
        util.exportExcel(response, list, "\u6d88\u606f\u65e5\u5fd7\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('config:msg:query')")
    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.messageLogService.selectMessageLogById(id));
    }

    @PreAuthorize(value="@ss.hasPermi('config:msg:add')")
    @Log(title="\u6d88\u606f\u65e5\u5fd7", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MessageLog messageLog) {
        return this.toAjax(this.messageLogService.insertMessageLog(messageLog));
    }

    @PreAuthorize(value="@ss.hasPermi('config:msg:edit')")
    @Log(title="\u6d88\u606f\u65e5\u5fd7", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MessageLog messageLog) {
        return this.toAjax(this.messageLogService.updateMessageLog(messageLog));
    }

    @PreAuthorize(value="@ss.hasPermi('config:msg:remove')")
    @Log(title="\u6d88\u606f\u65e5\u5fd7", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.messageLogService.deleteMessageLogByIds(ids));
    }
}
