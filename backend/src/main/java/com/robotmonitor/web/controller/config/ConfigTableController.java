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
 *  com.robotmonitor.config.domain.ConfigTable
 *  com.robotmonitor.config.service.IConfigTableService
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
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.config.domain.ConfigTable;
import com.robotmonitor.config.service.IConfigTableService;
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
@RequestMapping(value={"/config/table"})
public class ConfigTableController
extends BaseController {
    @Autowired
    private IConfigTableService configTableService;

    @GetMapping(value={"/list"})
    public TableDataInfo list(ConfigTable configTable) {
        this.startPage();
        List list = this.configTableService.selectConfigTableList(configTable);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('config:table:export')")
    @Log(title="\u9910\u684c\u914d\u7f6e", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, ConfigTable configTable) {
        List list = this.configTableService.selectConfigTableList(configTable);
        ExcelUtil util = new ExcelUtil(ConfigTable.class);
        util.exportExcel(response, list, "\u9910\u684c\u914d\u7f6e\u6570\u636e");
    }

    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.configTableService.selectConfigTableById(id));
    }

    @Log(title="\u9910\u684c\u914d\u7f6e", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigTable configTable) {
        int re = this.configTableService.insertConfigTable(configTable);
        if (re == 99) {
            return new AjaxResult(500, "\u684c\u53f7\u5df2\u5b58\u5728\uff0c\u8bf7\u4fee\u6539\u684c\u53f7\uff01", null);
        }
        return AjaxResult.success();
    }

    @Log(title="\u9910\u684c\u914d\u7f6e", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigTable configTable) {
        return this.toAjax(this.configTableService.updateConfigTable(configTable));
    }

    @Log(title="\u9910\u684c\u914d\u7f6e", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{id}"})
    public AjaxResult remove(@PathVariable Long id) {
        return this.toAjax(this.configTableService.deleteConfigTableById(id));
    }
}
