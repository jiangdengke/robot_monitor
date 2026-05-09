/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.config.domain.ConfigAreaLog
 *  com.robotmonitor.config.service.IConfigAreaLogService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.config;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.config.domain.ConfigAreaLog;
import com.robotmonitor.config.service.IConfigAreaLogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/areaLog"})
public class ConfigAreaLogController
extends BaseController {
    @Autowired
    private IConfigAreaLogService configAreaLogService;

    @PreAuthorize(value="@ss.hasPermi('config:log:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(ConfigAreaLog configAreaLog) {
        this.startPage();
        List list = this.configAreaLogService.selectConfigAreaLogList(configAreaLog);
        return this.getDataTable(list);
    }

    @Log(title="\u4f11\u606f\u5ba4\u4ecb\u7ecd\u57cb\u70b9", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigAreaLog configAreaLog) {
        return this.toAjax(this.configAreaLogService.insertConfigAreaLog(configAreaLog));
    }
}
