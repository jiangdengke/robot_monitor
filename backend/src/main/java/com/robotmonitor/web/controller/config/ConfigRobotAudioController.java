/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.config.ConfigRobotAudio
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.config.dto.RobotAudioRequest
 *  com.robotmonitor.config.service.IConfigRobotAudioService
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
import com.robotmonitor.common.core.domain.config.ConfigRobotAudio;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.config.dto.RobotAudioRequest;
import com.robotmonitor.config.service.IConfigRobotAudioService;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
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
@RequestMapping(value={"/config/robotAudio"})
public class ConfigRobotAudioController
extends BaseController {
    @Autowired
    private IConfigRobotAudioService configRobotAudioService;

    @GetMapping(value={"/list"})
    public TableDataInfo list(ConfigRobotAudio configRobotAudio) {
        this.startPage();
        List list = this.configRobotAudioService.selectConfigRobotAudioList(configRobotAudio);
        return this.getDataTable(list);
    }

    @Log(title="\u673a\u5668\u4eba\u8bed\u97f3", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, ConfigRobotAudio configRobotAudio) {
        List list = this.configRobotAudioService.selectConfigRobotAudioList(configRobotAudio);
        ExcelUtil util = new ExcelUtil(ConfigRobotAudio.class);
        util.exportExcel(response, list, "\u673a\u5668\u4eba\u8bed\u97f3\u6570\u636e");
    }

    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.configRobotAudioService.selectConfigRobotAudioById(id));
    }

    @Log(title="\u673a\u5668\u4eba\u8bed\u97f3", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigRobotAudio configRobotAudio) {
        int reCnt = this.configRobotAudioService.insertConfigRobotAudio(configRobotAudio);
        if (reCnt > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error((String)"\u65b0\u589e\u5931\u8d25\uff0c\u97f3\u9891key\u4e0d\u80fd\u91cd\u590d\uff0c\u8bf7\u68c0\u67e5\u6570\u636e");
    }

    @Log(title="\u673a\u5668\u4eba\u8bed\u97f3", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigRobotAudio configRobotAudio) {
        int reCnt = this.configRobotAudioService.updateConfigRobotAudio(configRobotAudio);
        if (reCnt > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error((String)"\u4fee\u6539\u5931\u8d25\uff0c\u97f3\u9891key\u4e0d\u80fd\u91cd\u590d\uff0c\u8bf7\u68c0\u67e5\u6570\u636e");
    }

    @Log(title="\u673a\u5668\u4eba\u8bed\u97f3", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.configRobotAudioService.deleteConfigRobotAudioByIds(ids));
    }

    @GetMapping(value={"/getNewRobotAudio"})
    public AjaxResult getNewRobotAudio(RobotAudioRequest param) throws ParseException {
        try {
            return AjaxResult.success((Object)this.configRobotAudioService.getNewRobotAudio(param.getRobotId(), param.getLastUpdateTime()));
        }
        catch (ParseException e) {
            return AjaxResult.error((String)"\u540c\u6b65\u97f3\u9891\u5931\u8d25");
        }
    }
}
