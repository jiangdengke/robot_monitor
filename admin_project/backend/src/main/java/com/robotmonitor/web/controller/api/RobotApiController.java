/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.ai.service.RobotVoiceService
 *  com.robotmonitor.bot.domain.RobotHttpCmdRequest
 *  com.robotmonitor.bot.domain.RobotMoveRequest
 *  com.robotmonitor.bot.domain.RobotSimpleCmdRequest
 *  com.robotmonitor.bot.service.IRobotCmdLogService
 *  com.robotmonitor.bot.service.RobotService
 *  com.robotmonitor.bot.service.RobotTaskSchedulerService
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.robot.RobotCmdLog
 *  com.robotmonitor.common.core.domain.robot.RobotOnlineRequest
 *  com.robotmonitor.common.core.domain.robot.RobotTaskCheckRequest
 *  com.robotmonitor.common.core.domain.robot.RobotTaskEndRequest
 *  com.robotmonitor.common.core.domain.robot.RobotVoice
 *  com.robotmonitor.common.core.domain.robot.RobotVoiceResponse
 *  com.robotmonitor.config.service.IConfigRobotService
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  reactor.core.publisher.Mono
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.ai.service.RobotVoiceService;
import com.robotmonitor.bot.domain.RobotHttpCmdRequest;
import com.robotmonitor.bot.domain.RobotMoveRequest;
import com.robotmonitor.bot.domain.RobotSimpleCmdRequest;
import com.robotmonitor.bot.service.IRobotCmdLogService;
import com.robotmonitor.bot.service.RobotService;
import com.robotmonitor.bot.service.RobotTaskSchedulerService;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.robot.RobotCmdLog;
import com.robotmonitor.common.core.domain.robot.RobotOnlineRequest;
import com.robotmonitor.common.core.domain.robot.RobotTaskCheckRequest;
import com.robotmonitor.common.core.domain.robot.RobotTaskEndRequest;
import com.robotmonitor.common.core.domain.robot.RobotVoice;
import com.robotmonitor.common.core.domain.robot.RobotVoiceResponse;
import com.robotmonitor.config.service.IConfigRobotService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value={"/rest/robot"})
public class RobotApiController {
    private static final Logger log = LoggerFactory.getLogger(RobotApiController.class);
    @Autowired
    private RobotService robotService;
    @Autowired
    private RobotVoiceService robotVoiceService;
    @Autowired
    private IRobotCmdLogService robotCmdLogService;
    @Autowired
    private RobotTaskSchedulerService robotTaskSchedulerService;
    @Autowired
    private IConfigRobotService configRobotService;

    @PostMapping(value={"/cmd"})
    public AjaxResult sendCmd(@RequestBody RobotSimpleCmdRequest robotSimpleCmdRequest) {
        this.robotService.sendCmd(robotSimpleCmdRequest);
        return AjaxResult.success();
    }

    @PostMapping(value={"/move"})
    public AjaxResult sendMove(@RequestBody RobotMoveRequest robotMoveRequest) {
        try {
            return AjaxResult.success((Object)this.robotService.guide(robotMoveRequest));
        }
        catch (RuntimeException e) {
            return AjaxResult.error((String)e.getMessage());
        }
    }

    @GetMapping(value={"/get-position"})
    public AjaxResult getPosition(@RequestParam String robotId) {
        if (Strings.isNotBlank((String)robotId)) {
            if (robotId.contains(",")) {
                return AjaxResult.success((Object)this.robotService.getRobotPositions(robotId));
            }
            return AjaxResult.success((Object)this.robotService.getRobotPosition(robotId));
        }
        return AjaxResult.success();
    }

    @GetMapping(value={"/get-position-by-roomcode"})
    public AjaxResult getPositionByRoomCode(@RequestParam String roomCode) {
        return AjaxResult.success((Object)this.robotService.getPositionByRoomCode(roomCode));
    }

    @PostMapping(value={"/set-robot-state"})
    public AjaxResult setRobotState(@RequestBody RobotHttpCmdRequest robotHttpCmdRequest) {
        this.robotService.setRobotState(robotHttpCmdRequest);
        return AjaxResult.success();
    }

    @PostMapping(value={"/listen"})
    public Mono<RobotVoiceResponse> listen(@RequestBody RobotVoice robotVoice) {
        return this.robotVoiceService.listen(robotVoice);
    }

    @PostMapping(value={"/insertRobotCmdLog"})
    public int insertRobotCmdLog(@RequestBody RobotCmdLog robotCmdLog) {
        return this.robotCmdLogService.insertRobotCmdLog(robotCmdLog);
    }

    @PostMapping(value={"/endTask"})
    public void endTask(@RequestBody RobotTaskEndRequest robotTaskEndRequest) {
        this.robotTaskSchedulerService.stop("" + robotTaskEndRequest.getRobot_id(), robotTaskEndRequest.getRobot_task_id(), robotTaskEndRequest.getMessage());
    }

    @PostMapping(value={"/robotOnlineEvent"})
    public void robotOnlineEvent(@RequestBody RobotOnlineRequest robotOnlineRequest) {
        this.configRobotService.updateRobotOnlineStatus(robotOnlineRequest);
        if (robotOnlineRequest.isOnline()) {
            this.robotTaskSchedulerService.clearQueue(robotOnlineRequest.getRobotId(), "\u673a\u5668\u4eba\u8fde\u7ebf\uff0c\u6e05\u7a7a\u4efb\u52a1\u961f\u5217");
            this.robotTaskSchedulerService.setHomeStatus(robotOnlineRequest.getRobotId(), true);
        } else {
            this.robotTaskSchedulerService.clearQueue(robotOnlineRequest.getRobotId(), "\u673a\u5668\u4eba\u6389\u7ebf\uff0c\u6e05\u7a7a\u4efb\u52a1\u961f\u5217");
        }
    }

    @GetMapping(value={"/reset-robot-task-status"})
    public void resetRobotTaskStatus(@RequestParam String robotId) {
        this.robotTaskSchedulerService.clearQueue(robotId, "\u624b\u52a8\u91cd\u7f6e\u673a\u5668\u4eba\u72b6\u6001");
    }

    @GetMapping(value={"/reset-robot-home-status"})
    public void resetRobotHomeStatus(@RequestParam String robotId) {
        this.robotTaskSchedulerService.setHomeStatus(robotId, true);
    }

    @PostMapping(value={"/robotTaskCheckEvent"})
    public void robotTaskCheckEvent(@RequestBody RobotTaskCheckRequest robotTaskCheckRequest) {
        this.robotTaskSchedulerService.checkCurrentTask(robotTaskCheckRequest.getRobotId());
    }
}
