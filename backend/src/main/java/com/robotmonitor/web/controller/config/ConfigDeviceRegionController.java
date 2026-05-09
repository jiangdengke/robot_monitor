/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.config.domain.ConfigDeviceRegion
 *  com.robotmonitor.config.service.IConfigDeviceRegionService
 *  jakarta.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
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
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.config.domain.ConfigDeviceRegion;
import com.robotmonitor.config.service.IConfigDeviceRegionService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/config/deviceregion"})
public class ConfigDeviceRegionController
extends BaseController {
    @Autowired
    private IConfigDeviceRegionService configDeviceRegionService;

    @Log(title="\u8bbe\u5907-\u533a\u57df\u5173\u8054", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, ConfigDeviceRegion configDeviceRegion) {
        List list = this.configDeviceRegionService.selectConfigDeviceRegionList(configDeviceRegion);
        ExcelUtil util = new ExcelUtil(ConfigDeviceRegion.class);
        util.exportExcel(response, list, "\u8bbe\u5907-\u533a\u57df\u5173\u8054\u6570\u636e");
    }

    @GetMapping(value={"/{deviceId}/{regionId}"})
    public AjaxResult getInfo(@PathVariable(value="deviceId") Long deviceId, @PathVariable(value="regionId") Long regionId) {
        return AjaxResult.success((Object)this.configDeviceRegionService.selectConfigDeviceRegionByDeviceIdRegionId(deviceId, regionId));
    }

    @GetMapping(value={"/list/{deviceId}"})
    public AjaxResult getInfoList(@PathVariable(value="deviceId") Long deviceId) {
        return AjaxResult.success((Object)this.configDeviceRegionService.selectConfigDeviceRegionByDeviceId(deviceId));
    }

    @Log(title="\u8bbe\u5907-\u533a\u57df\u5173\u8054", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigDeviceRegion configDeviceRegion) {
        ConfigDeviceRegion info = this.configDeviceRegionService.selectConfigDeviceRegionByDeviceIdRegionId(configDeviceRegion.getDeviceId(), configDeviceRegion.getRegionId());
        if (ObjectUtils.isNotEmpty((Object)info)) {
            return new AjaxResult(502, "\u8be5\u8bbe\u5907\u5df2\u914d\u7f6e\u8be5\u533a\u57df\uff0c\u8bf7\u68c0\u67e5\uff01");
        }
        return this.toAjax(this.configDeviceRegionService.insertConfigDeviceRegion(configDeviceRegion));
    }

    @Log(title="\u8bbe\u5907-\u533a\u57df\u5173\u8054", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigDeviceRegion configDeviceRegion) {
        return this.toAjax(this.configDeviceRegionService.updateConfigDeviceRegion(configDeviceRegion));
    }

    @Log(title="\u8bbe\u5907-\u533a\u57df\u5173\u8054", businessType=BusinessType.DELETE)
    @PostMapping(value={"/delete"})
    public AjaxResult remove(@RequestBody ConfigDeviceRegion configDeviceRegion) {
        return this.toAjax(this.configDeviceRegionService.deleteConfigDeviceRegion(configDeviceRegion));
    }
}
