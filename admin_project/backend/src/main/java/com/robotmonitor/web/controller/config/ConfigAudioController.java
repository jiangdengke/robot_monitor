/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.config.ConfigAudio
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.config.service.IConfigAudioService
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
import com.robotmonitor.common.core.domain.config.ConfigAudio;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.config.service.IConfigAudioService;
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
@RequestMapping(value={"/config/audio"})
public class ConfigAudioController
extends BaseController {
    @Autowired
    private IConfigAudioService configAudioService;

    @GetMapping(value={"/list"})
    public TableDataInfo list(ConfigAudio configAudio) {
        this.startPage();
        List list = this.configAudioService.selectConfigAudioList(configAudio);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('config:audio:export')")
    @Log(title="\u97f3\u9891\u914d\u7f6e", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, ConfigAudio configAudio) {
        List list = this.configAudioService.selectConfigAudioList(configAudio);
        ExcelUtil util = new ExcelUtil(ConfigAudio.class);
        util.exportExcel(response, list, "\u97f3\u9891\u914d\u7f6e\u6570\u636e");
    }

    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.configAudioService.selectConfigAudioById(id));
    }

    @Log(title="\u97f3\u9891\u914d\u7f6e", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigAudio configAudio) {
        int reCnt = this.configAudioService.insertConfigAudio(configAudio);
        if (reCnt > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error((String)"\u65b0\u589e\u5931\u8d25\uff0c\u97f3\u9891\u540d\u79f0\u4e0d\u80fd\u91cd\u590d\uff0c\u8bf7\u68c0\u67e5\u6570\u636e");
    }

    @Log(title="\u97f3\u9891\u914d\u7f6e", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigAudio configAudio) {
        int reCnt = this.configAudioService.updateConfigAudio(configAudio);
        if (reCnt > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error((String)"\u4fee\u6539\u5931\u8d25\uff0c\u97f3\u9891\u540d\u79f0\u4e0d\u80fd\u91cd\u590d\uff0c\u8bf7\u68c0\u67e5\u6570\u636e");
    }

    @Log(title="\u97f3\u9891\u914d\u7f6e", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.configAudioService.deleteConfigAudioByIds(ids));
    }
}
