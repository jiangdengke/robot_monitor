/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.entity.SysDictType
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
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
import com.robotmonitor.common.core.domain.entity.SysDictType;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.system.service.ISysDictTypeService;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping(value={"/system/dict/type"})
public class SysDictTypeController
extends BaseController {
    @Autowired
    private ISysDictTypeService dictTypeService;

    @PreAuthorize(value="@ss.hasPermi('system:dict:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(SysDictType dictType) {
        this.startPage();
        List list = this.dictTypeService.selectDictTypeList(dictType);
        return this.getDataTable(list);
    }

    @Log(title="\u5b57\u5178\u7c7b\u578b", businessType=BusinessType.EXPORT)
    @PreAuthorize(value="@ss.hasPermi('system:dict:export')")
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, SysDictType dictType) {
        List list = this.dictTypeService.selectDictTypeList(dictType);
        ExcelUtil util = new ExcelUtil(SysDictType.class);
        util.exportExcel(response, list, "\u5b57\u5178\u7c7b\u578b");
    }

    @PreAuthorize(value="@ss.hasPermi('system:dict:query')")
    @GetMapping(value={"/{dictId}"})
    public AjaxResult getInfo(@PathVariable Long dictId) {
        return AjaxResult.success((Object)this.dictTypeService.selectDictTypeById(dictId));
    }

    @PreAuthorize(value="@ss.hasPermi('system:dict:add')")
    @Log(title="\u5b57\u5178\u7c7b\u578b", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictType dict) {
        if ("1".equals(this.dictTypeService.checkDictTypeUnique(dict))) {
            return AjaxResult.error((String)("\u65b0\u589e\u5b57\u5178'" + dict.getDictName() + "'\u5931\u8d25\uff0c\u5b57\u5178\u7c7b\u578b\u5df2\u5b58\u5728"));
        }
        dict.setCreateBy(this.getUsername());
        return this.toAjax(this.dictTypeService.insertDictType(dict));
    }

    @PreAuthorize(value="@ss.hasPermi('system:dict:edit')")
    @Log(title="\u5b57\u5178\u7c7b\u578b", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictType dict) {
        if ("1".equals(this.dictTypeService.checkDictTypeUnique(dict))) {
            return AjaxResult.error((String)("\u4fee\u6539\u5b57\u5178'" + dict.getDictName() + "'\u5931\u8d25\uff0c\u5b57\u5178\u7c7b\u578b\u5df2\u5b58\u5728"));
        }
        dict.setUpdateBy(this.getUsername());
        return this.toAjax(this.dictTypeService.updateDictType(dict));
    }

    @PreAuthorize(value="@ss.hasPermi('system:dict:remove')")
    @Log(title="\u5b57\u5178\u7c7b\u578b", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{dictIds}"})
    public AjaxResult remove(@PathVariable Long[] dictIds) {
        this.dictTypeService.deleteDictTypeByIds(dictIds);
        return this.success();
    }

    @PreAuthorize(value="@ss.hasPermi('system:dict:remove')")
    @Log(title="\u5b57\u5178\u7c7b\u578b", businessType=BusinessType.CLEAN)
    @DeleteMapping(value={"/refreshCache"})
    public AjaxResult refreshCache() {
        this.dictTypeService.resetDictCache();
        return AjaxResult.success();
    }

    @GetMapping(value={"/optionselect"})
    public AjaxResult optionselect() {
        List dictTypes = this.dictTypeService.selectDictTypeAll();
        return AjaxResult.success((Object)dictTypes);
    }
}
