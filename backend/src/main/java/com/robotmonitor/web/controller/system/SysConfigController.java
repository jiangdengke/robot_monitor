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
 *  com.robotmonitor.system.domain.SysConfig
 *  com.robotmonitor.system.service.ISysConfigService
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
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.system.domain.SysConfig;
import com.robotmonitor.system.service.ISysConfigService;
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
@RequestMapping(value={"/system/config"})
public class SysConfigController
extends BaseController {
    @Autowired
    private ISysConfigService configService;

    @PreAuthorize(value="@ss.hasPermi('system:config:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(SysConfig config) {
        this.startPage();
        List list = this.configService.selectConfigList(config);
        return this.getDataTable(list);
    }

    @Log(title="\u53c2\u6570\u7ba1\u7406", businessType=BusinessType.EXPORT)
    @PreAuthorize(value="@ss.hasPermi('system:config:export')")
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, SysConfig config) {
        List list = this.configService.selectConfigList(config);
        ExcelUtil util = new ExcelUtil(SysConfig.class);
        util.exportExcel(response, list, "\u53c2\u6570\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('system:config:query')")
    @GetMapping(value={"/{configId}"})
    public AjaxResult getInfo(@PathVariable Long configId) {
        return AjaxResult.success((Object)this.configService.selectConfigById(configId));
    }

    @GetMapping(value={"/configKey/{configKey}"})
    public AjaxResult getConfigKey(@PathVariable String configKey) {
        return AjaxResult.success((String)this.configService.selectConfigByKey(configKey));
    }

    @PreAuthorize(value="@ss.hasPermi('system:config:add')")
    @Log(title="\u53c2\u6570\u7ba1\u7406", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysConfig config) {
        if ("1".equals(this.configService.checkConfigKeyUnique(config))) {
            return AjaxResult.error((String)("\u65b0\u589e\u53c2\u6570'" + config.getConfigName() + "'\u5931\u8d25\uff0c\u53c2\u6570\u952e\u540d\u5df2\u5b58\u5728"));
        }
        config.setCreateBy(this.getUsername());
        return this.toAjax(this.configService.insertConfig(config));
    }

    @PreAuthorize(value="@ss.hasPermi('system:config:edit')")
    @Log(title="\u53c2\u6570\u7ba1\u7406", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysConfig config) {
        if ("1".equals(this.configService.checkConfigKeyUnique(config))) {
            return AjaxResult.error((String)("\u4fee\u6539\u53c2\u6570'" + config.getConfigName() + "'\u5931\u8d25\uff0c\u53c2\u6570\u952e\u540d\u5df2\u5b58\u5728"));
        }
        config.setUpdateBy(this.getUsername());
        return this.toAjax(this.configService.updateConfig(config));
    }

    @PreAuthorize(value="@ss.hasPermi('system:config:remove')")
    @Log(title="\u53c2\u6570\u7ba1\u7406", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{configIds}"})
    public AjaxResult remove(@PathVariable Long[] configIds) {
        this.configService.deleteConfigByIds(configIds);
        return this.success();
    }

    @PreAuthorize(value="@ss.hasPermi('system:config:remove')")
    @Log(title="\u53c2\u6570\u7ba1\u7406", businessType=BusinessType.CLEAN)
    @DeleteMapping(value={"/refreshCache"})
    public AjaxResult refreshCache() {
        this.configService.resetConfigCache();
        return AjaxResult.success();
    }
}
