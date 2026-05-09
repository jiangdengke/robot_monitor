/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.config.service.IConfigRegionService
 *  com.robotmonitor.flight.mapper.PassengerLocationLogMapper
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
import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.config.service.IConfigRegionService;
import com.robotmonitor.flight.mapper.PassengerLocationLogMapper;
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
@RequestMapping(value={"/config/region"})
public class ConfigRegionController
extends BaseController {
    @Autowired
    private IConfigRegionService configRegionService;
    @Autowired
    private PassengerLocationLogMapper locationLogMapper;

    @GetMapping(value={"/list"})
    public TableDataInfo list(ConfigRegion configRegion) {
        this.startPage();
        List list = this.configRegionService.selectConfigRegionList(configRegion);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('config:region:export')")
    @Log(title="\u533a\u57df\u914d\u7f6e", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, ConfigRegion configRegion) {
        List list = this.configRegionService.selectConfigRegionList(configRegion);
        ExcelUtil util = new ExcelUtil(ConfigRegion.class);
        util.exportExcel(response, list, "\u533a\u57df\u914d\u7f6e\u6570\u636e");
    }

    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.configRegionService.selectConfigRegionById(id));
    }

    @Log(title="\u533a\u57df\u914d\u7f6e", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigRegion configRegion) {
        return this.toAjax(this.configRegionService.insertConfigRegion(configRegion));
    }

    @Log(title="\u533a\u57df\u914d\u7f6e", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigRegion configRegion) {
        return this.toAjax(this.configRegionService.updateConfigRegion(configRegion));
    }

    @Log(title="\u533a\u57df\u914d\u7f6e", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.configRegionService.deleteConfigRegionByIds(ids));
    }
}
