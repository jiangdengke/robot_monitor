/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.bot.service.IInspTaskResultService
 *  com.robotmonitor.bot.service.IInspTaskService
 *  com.robotmonitor.bot.service.InspectionService
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.insp.InspTask
 *  com.robotmonitor.common.core.domain.insp.InspTaskResult
 *  com.robotmonitor.common.core.domain.insp.InspectionAlarm
 *  com.robotmonitor.common.core.domain.insp.InspectionSummary
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.bot.service.IInspTaskResultService;
import com.robotmonitor.bot.service.IInspTaskService;
import com.robotmonitor.bot.service.InspectionService;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.insp.InspTask;
import com.robotmonitor.common.core.domain.insp.InspTaskResult;
import com.robotmonitor.common.core.domain.insp.InspectionAlarm;
import com.robotmonitor.common.core.domain.insp.InspectionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/insp"})
public class InspTaskApiController
extends BaseController {
    @Autowired
    private IInspTaskService inspTaskService;
    @Autowired
    private IInspTaskResultService inspTaskResultService;
    @Autowired
    private InspectionService inspectionService;

    @PostMapping(value={"/task/selectInspTaskById"})
    public InspTask selectInspTaskById(@RequestParam Long id) {
        return this.inspTaskService.selectInspTaskById(id);
    }

    @PostMapping(value={"/task/updateInspTask"})
    public int updateInspTask(@RequestBody InspTask inspTask) {
        return this.inspTaskService.updateInspTask(inspTask);
    }

    @PostMapping(value={"/result/insertInspTaskResult"})
    public int insertInspTaskResult(@RequestBody InspTaskResult inspTaskResult) {
        return this.inspTaskResultService.insertInspTaskResult(inspTaskResult);
    }

    @PostMapping(value={"/task/saveInspectionAlarm"})
    public void saveInspectionAlarm(@RequestBody InspectionAlarm inspectionAlarm) {
        this.inspectionService.saveInspectionAlarm(inspectionAlarm);
    }

    @PostMapping(value={"/task/saveInspectionSummary"})
    public void saveInspectionSummary(@RequestBody InspectionSummary inspectionSummary) {
        this.inspectionService.saveInspectionSummary(inspectionSummary);
    }
}
