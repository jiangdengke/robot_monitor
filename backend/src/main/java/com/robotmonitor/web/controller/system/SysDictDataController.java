/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.entity.SysDictData
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.system.service.ISysDictDataService
 *  com.robotmonitor.system.service.ISysDictTypeService
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.validation.annotation.Validated
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.system;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.entity.SysDictData;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.system.service.ISysDictDataService;
import com.robotmonitor.system.service.ISysDictTypeService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/system/dict/data"})
public class SysDictDataController
extends BaseController {
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private ISysDictTypeService dictTypeService;

    @PreAuthorize(value="@ss.hasPermi('system:dict:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(SysDictData dictData) {
        this.startPage();
        List list = this.dictDataService.selectDictDataList(dictData);
        return this.getDataTable(list);
    }

    @Log(title="\u5b57\u5178\u6570\u636e", businessType=BusinessType.EXPORT)
    @PreAuthorize(value="@ss.hasPermi('system:dict:export')")
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, SysDictData dictData) {
        List list = this.dictDataService.selectDictDataList(dictData);
        ExcelUtil util = new ExcelUtil(SysDictData.class);
        util.exportExcel(response, list, "\u5b57\u5178\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('system:dict:query')")
    @GetMapping(value={"/{dictCode}"})
    public AjaxResult getInfo(@PathVariable Long dictCode) {
        return AjaxResult.success((Object)this.dictDataService.selectDictDataById(dictCode));
    }

    @GetMapping(value={"/type/{dictType}"})
    public AjaxResult dictType(@PathVariable String dictType) {
        List<SysDictData> data = this.dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull((Object)data)) {
            data = new ArrayList<SysDictData>();
        }
        return AjaxResult.success((Object)data);
    }

    @PreAuthorize(value="@ss.hasPermi('system:dict:add')")
    @Log(title="\u5b57\u5178\u6570\u636e", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictData dict) {
        dict.setCreateBy(this.getUsername());
        return this.toAjax(this.dictDataService.insertDictData(dict));
    }

    @PreAuthorize(value="@ss.hasPermi('system:dict:edit')")
    @Log(title="\u5b57\u5178\u6570\u636e", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictData dict) {
        dict.setUpdateBy(this.getUsername());
        return this.toAjax(this.dictDataService.updateDictData(dict));
    }

    @PreAuthorize(value="@ss.hasPermi('system:dict:remove')")
    @Log(title="\u5b57\u5178\u7c7b\u578b", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{dictCodes}"})
    public AjaxResult remove(@PathVariable Long[] dictCodes) {
        this.dictDataService.deleteDictDataByIds(dictCodes);
        return this.success();
    }
}
