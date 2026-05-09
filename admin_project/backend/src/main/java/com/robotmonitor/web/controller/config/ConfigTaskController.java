/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.bot.service.InspectionService
 *  com.robotmonitor.bot.service.RobotService
 *  com.robotmonitor.common.annotation.Log
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.config.ConfigTask
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.enums.BusinessType
 *  com.robotmonitor.common.utils.poi.ExcelUtil
 *  com.robotmonitor.config.service.IConfigTaskService
 *  jakarta.servlet.http.HttpServletResponse
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
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

import com.robotmonitor.bot.service.InspectionService;
import com.robotmonitor.bot.service.RobotService;
import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.common.utils.poi.ExcelUtil;
import com.robotmonitor.config.service.IConfigTaskService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value={"/config/task"})
public class ConfigTaskController
extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ConfigTaskController.class);
    @Autowired
    private IConfigTaskService configTaskService;
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private RobotService robotService;

    @PreAuthorize(value="@ss.hasPermi('config:task:list')")
    @GetMapping(value={"/list"})
    public TableDataInfo list(ConfigTask configTask) {
        this.startPage();
        List list = this.configTaskService.selectConfigTaskList(configTask);
        return this.getDataTable(list);
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:export')")
    @Log(title="\u4efb\u52a1\u914d\u7f6e", businessType=BusinessType.EXPORT)
    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, ConfigTask configTask) {
        List list = this.configTaskService.selectConfigTaskList(configTask);
        ExcelUtil util = new ExcelUtil(ConfigTask.class);
        util.exportExcel(response, list, "\u4efb\u52a1\u914d\u7f6e\u6570\u636e");
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:query')")
    @GetMapping(value={"/{id}"})
    public AjaxResult getInfo(@PathVariable(value="id") Long id) {
        return AjaxResult.success((Object)this.configTaskService.selectConfigTaskById(id));
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:add')")
    @Log(title="\u4efb\u52a1\u914d\u7f6e", businessType=BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigTask configTask) {
        return this.toAjax(this.configTaskService.insertConfigTask(configTask));
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:edit')")
    @Log(title="\u4efb\u52a1\u914d\u7f6e", businessType=BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigTask configTask) {
        return this.toAjax(this.configTaskService.updateConfigTask(configTask));
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:remove')")
    @Log(title="\u4efb\u52a1\u914d\u7f6e", businessType=BusinessType.DELETE)
    @DeleteMapping(value={"/{ids}"})
    public AjaxResult remove(@PathVariable Long[] ids) {
        return this.toAjax(this.configTaskService.deleteConfigTaskByIds(ids));
    }

    @PreAuthorize(value="@ss.hasPermi('config:task:query')")
    @GetMapping(value={"/run/{id}"})
    public AjaxResult run(@PathVariable(value="id") Long id) {
        try {
            ConfigTask configTask = this.configTaskService.selectConfigTaskById(id);
            if (null == configTask) {
                log.error("\u673a\u5668\u4eba\u4efb\u52a1\u4e0d\u5b58\u5728\uff0c\u4e0d\u6267\u884c\u4efb\u52a1");
                throw new RuntimeException("\u673a\u5668\u4eba\u4efb\u52a1\u4e0d\u5b58\u5728\uff0c\u4e0d\u6267\u884c\u4efb\u52a1");
            }
            if (null != configTask.getCommand()) {
                if (configTask.getCommand().equals(789018L)) {
                    this.inspectionService.run(configTask);
                } else {
                    this.robotService.runConfigTask(configTask);
                }
            }
        }
        catch (RuntimeException e) {
            return AjaxResult.error((String)e.getMessage());
        }
        return AjaxResult.success();
    }
}
