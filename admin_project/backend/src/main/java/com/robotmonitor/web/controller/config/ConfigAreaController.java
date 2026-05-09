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
 *  com.robotmonitor.config.domain.ConfigArea
 *  com.robotmonitor.config.service.IConfigAreaService
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
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
import com.robotmonitor.config.domain.ConfigArea;
import com.robotmonitor.config.service.IConfigAreaService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/config/area"})
public class ConfigAreaController
extends BaseController {
    @Autowired
    private IConfigAreaService configAreaService;

    @GetMapping(value={"/list"})
    public TableDataInfo list(ConfigArea configArea) {
        this.startPage();
        List list = this.configAreaService.selectConfigAreaList(configArea);
        return this.getDataTable(list);
    }

    @Log(title="\u8d35\u5bbe\u5ba4\u529f\u80fd\u533a\u7ba1\u7406", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, ConfigArea configArea) {
        List list = this.configAreaService.selectConfigAreaList(configArea);
        ExcelUtil util = new ExcelUtil(ConfigArea.class);
        util.exportExcel(response, list, "\u8d35\u5bbe\u5ba4\u529f\u80fd\u533a\u7ba1\u7406\u6570\u636e");
    }

    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.configAreaService.selectConfigAreaById(id));
    }

    @Log(title="\u8d35\u5bbe\u5ba4\u529f\u80fd\u533a\u7ba1\u7406", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigArea configArea) {
        return this.toAjax(this.configAreaService.insertConfigArea(configArea));
    }

    @Log(title="\u8d35\u5bbe\u5ba4\u529f\u80fd\u533a\u7ba1\u7406", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigArea configArea) {
        return this.toAjax(this.configAreaService.updateConfigArea(configArea));
    }

    @Log(title="\u8d35\u5bbe\u5ba4\u529f\u80fd\u533a\u7ba1\u7406", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{id}"})
    public AjaxResult remove(@PathVariable Long id) {
        return this.toAjax(this.configAreaService.deleteConfigAreaById(id));
    }
}
