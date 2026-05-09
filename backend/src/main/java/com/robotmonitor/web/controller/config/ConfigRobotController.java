/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.config.service.IConfigRobotService
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
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.config.service.IConfigRobotService;
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
@RequestMapping(value={"/config/robot"})
public class ConfigRobotController
extends BaseController {
    @Autowired
    private IConfigRobotService configRobotService;

    @GetMapping(value={"/list"})
    public TableDataInfo list(ConfigRobot configRobot) {
        this.startPage();
        List list = this.configRobotService.selectConfigRobotList(configRobot);
        return this.getDataTable(list);
    }

    @Log(title="\u673a\u5668\u4eba\u914d\u7f6e", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, ConfigRobot configRobot) {
        List list = this.configRobotService.selectConfigRobotList(configRobot);
        ExcelUtil util = new ExcelUtil(ConfigRobot.class);
        util.exportExcel(response, list, "\u673a\u5668\u4eba\u914d\u7f6e\u6570\u636e");
    }

    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.configRobotService.selectConfigRobotById(id));
    }

    @GetMapping(value={"/getInfoByRobotId/{robotid}"})
    public AjaxResult getInfoByRobotId(@PathVariable(value="robotid") String id) {
        return AjaxResult.success((Object)this.configRobotService.selectConfigRobotByRobotId(id));
    }

    @Log(title="\u673a\u5668\u4eba\u914d\u7f6e", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigRobot configRobot) {
        return this.toAjax(this.configRobotService.insertConfigRobot(configRobot));
    }

    @Log(title="\u673a\u5668\u4eba\u914d\u7f6e", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigRobot configRobot) {
        return this.toAjax(this.configRobotService.updateConfigRobot(configRobot));
    }

    @Log(title="\u673a\u5668\u4eba\u914d\u7f6e", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long id) {
        return this.toAjax(this.configRobotService.deleteConfigRobotById(id));
    }
}
