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
 *  com.robotmonitor.config.domain.ConfigDevice
 *  com.robotmonitor.config.service.IConfigDeviceService
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
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.service.IConfigDeviceService;
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
@RequestMapping(value={"/config/device"})
public class ConfigDeviceController
extends BaseController {
    @Autowired
    private IConfigDeviceService configDeviceService;

    @GetMapping(value={"/list"})
    public TableDataInfo list(ConfigDevice configDevice) {
        this.startPage();
        List list = this.configDeviceService.selectConfigDeviceList(configDevice);
        return this.getDataTable(list);
    }

    @Log(title="\u8bbe\u5907\u914d\u7f6e", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, ConfigDevice configDevice) {
        List list = this.configDeviceService.selectConfigDeviceList(configDevice);
        ExcelUtil util = new ExcelUtil(ConfigDevice.class);
        util.exportExcel(response, list, "\u8bbe\u5907\u914d\u7f6e\u6570\u636e");
    }

    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.configDeviceService.selectConfigDeviceById(id));
    }

    @Log(title="\u8bbe\u5907\u914d\u7f6e", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigDevice configDevice) {
        return this.toAjax(this.configDeviceService.insertConfigDevice(configDevice));
    }

    @Log(title="\u8bbe\u5907\u914d\u7f6e", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigDevice configDevice) {
        return this.toAjax(this.configDeviceService.updateConfigDevice(configDevice));
    }

    @Log(title="\u8bbe\u5907\u914d\u7f6e", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.configDeviceService.deleteConfigDeviceByIds(ids));
    }
}
