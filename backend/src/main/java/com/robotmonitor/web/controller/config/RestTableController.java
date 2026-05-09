/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.config.domain.ConfigTable
 *  com.robotmonitor.config.service.IConfigTableService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.config;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.config.domain.ConfigTable;
import com.robotmonitor.config.service.IConfigTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/table"})
public class RestTableController
extends BaseController {
    @Autowired
    private IConfigTableService configTableService;

    @Log(title="\u8bbe\u7f6e\u7ffb\u53f0\u72b6\u6001", businessType=BusinessType.UPDATE)
    @PostMapping(value={""})
    public AjaxResult setTableStatus(@RequestBody ConfigTable configTable) {
        return this.toAjax(this.configTableService.setTableStatus(configTable));
    }
}
