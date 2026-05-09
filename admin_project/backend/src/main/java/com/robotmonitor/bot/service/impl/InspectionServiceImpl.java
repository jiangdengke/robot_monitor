/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.ai.PushMessage
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.config.ConfigTask
 *  com.robotmonitor.common.core.domain.config.MessageLog
 *  com.robotmonitor.common.core.domain.insp.InspTaskResult
 *  com.robotmonitor.common.core.domain.insp.InspectionAlarm
 *  com.robotmonitor.common.core.domain.insp.InspectionResult
 *  com.robotmonitor.common.core.domain.insp.InspectionSummary
 *  com.robotmonitor.common.core.domain.robot.RobotCmd
 *  com.robotmonitor.common.core.domain.robot.RobotTask
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.config.service.IConfigRegionService
 *  com.robotmonitor.config.service.IConfigRobotService
 *  com.robotmonitor.config.service.IConfigTaskService
 *  com.robotmonitor.config.service.IMessageLogService
 *  com.robotmonitor.config.service.IPushService
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 *  org.springframework.util.CollectionUtils
 */
package com.robotmonitor.bot.service.impl;

import com.robotmonitor.bot.service.IInspTaskResultService;
import com.robotmonitor.bot.service.IRobotTaskService;
import com.robotmonitor.bot.service.InspectionService;
import com.robotmonitor.bot.service.RobotTaskSchedulerService;
import com.robotmonitor.common.core.domain.ai.PushMessage;
import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.core.domain.config.MessageLog;
import com.robotmonitor.common.core.domain.insp.InspTaskResult;
import com.robotmonitor.common.core.domain.insp.InspectionAlarm;
import com.robotmonitor.common.core.domain.insp.InspectionResult;
import com.robotmonitor.common.core.domain.insp.InspectionSummary;
import com.robotmonitor.common.core.domain.robot.RobotCmd;
import com.robotmonitor.common.core.domain.robot.RobotTask;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.config.service.IConfigRegionService;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.config.service.IConfigTaskService;
import com.robotmonitor.config.service.IMessageLogService;
import com.robotmonitor.config.service.IPushService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class InspectionServiceImpl
implements InspectionService {
    private static final Logger log = LoggerFactory.getLogger(InspectionServiceImpl.class);
    @Autowired
    private IConfigTaskService configTaskService;
    @Autowired
    private IConfigRegionService configRegionService;
    @Autowired
    private IRobotTaskService robotTaskService;
    @Autowired
    private RobotTaskSchedulerService robotTaskSchedulerService;
    @Autowired
    private IConfigRobotService configRobotService;
    @Autowired
    private IMessageLogService messageLogService;
    @Autowired
    private IInspTaskResultService inspTaskResultService;
    @Autowired
    private IPushService pushService;

    @Override
    public void run(ConfigTask configTask) {
        if (Strings.isBlank((String)configTask.getRegion())) {
            log.error("\u5de1\u68c0\u4e2d\u9700\u8981\u6267\u884c\u7684\u533a\u57df\u4e3a\u7a7a\uff0c\u4e0d\u6267\u884c\u4efb\u52a1");
            throw new RuntimeException("\u5de1\u68c0\u4e2d\u9700\u8981\u6267\u884c\u7684\u533a\u57df\u4e3a\u7a7a\uff0c\u4e0d\u6267\u884c\u4efb\u52a1");
        }
        ArrayList<String> points = new ArrayList<String>();
        for (String regionId : configTask.getRegion().split(",")) {
            ConfigRegion configRegion = this.configRegionService.selectConfigRegionById(Long.valueOf(Long.parseLong(regionId)));
            if (null == configRegion) continue;
            points.add(configRegion.getCoordinate());
        }
        this.createInspTask(configTask.getRobotId(), configTask.getId(), points);
    }

    @Override
    public void saveInspectionAlarm(InspectionAlarm inspectionAlarm) {
        log.info("\u66f4\u65b0\u5de1\u68c0\u544a\u8b66\uff1a{}", (Object)inspectionAlarm);
        InspTaskResult inspTaskResult = new InspTaskResult();
        inspTaskResult.setAbnormal("1");
        inspTaskResult.setInspTaskId(inspectionAlarm.getRobot_task_id());
        inspTaskResult.setAbnormalInfo(inspectionAlarm.getAbnormal_info());
        inspTaskResult.setImageBase64(inspectionAlarm.getImage_base64());
        inspTaskResult.setRobotId("" + inspectionAlarm.getRobot_id());
        inspTaskResult.setType("1");
        inspTaskResult.setPoint(inspectionAlarm.getPoint());
        MessageLog messageLog = new MessageLog();
        messageLog.setTitle("\u5de1\u68c0\u544a\u8b66");
        messageLog.setContent(inspectionAlarm.getAbnormal_info());
        messageLog.setStatus("0");
        ConfigRobot robot = this.configRobotService.getConfigRobotByRobotId("" + inspectionAlarm.getRobot_id());
        if (null != robot) {
            messageLog.setSource(robot.getRobotName());
            messageLog.setRoomCode(robot.getRoomCode());
        } else {
            messageLog.setSource("" + inspectionAlarm.getRobot_id());
        }
        this.saveAndPushMessage(messageLog, null != robot ? robot.getRoomCode() : null);
        this.inspTaskResultService.insertInspTaskResult(inspTaskResult);
    }

    @Override
    public void saveInspectionSummary(InspectionSummary inspectionSummary) {
        log.info("\u66f4\u65b0\u5de1\u68c0\u603b\u7ed3\uff1a{}", (Object)inspectionSummary);
        RobotTask robotTask = this.robotTaskService.selectRobotTaskById(inspectionSummary.getRobot_task_id());
        if (null == robotTask) {
            log.error("\u65e0\u6cd5\u627e\u5230\u5bf9\u5e94\u7684\u5de1\u68c0\u4efb\u52a1\uff0cinsp_task_id\uff1a{}", (Object)inspectionSummary.getRobot_task_id());
            return;
        }
        this.robotTaskSchedulerService.stop("" + inspectionSummary.getRobot_id(), inspectionSummary.getRobot_task_id(), "\u5de1\u68c0\u4efb\u52a1\u5df2\u7ed3\u675f");
        if (CollectionUtils.isEmpty((Collection)inspectionSummary.getResults())) {
            log.error("\u5de1\u68c0\u603b\u7ed3\u4e2d\u7684\u5185\u5bb9\u4e3a\u7a7a");
            return;
        }
        for (InspectionResult inspectionResult : inspectionSummary.getResults()) {
            InspTaskResult inspTaskResult = this.getInspTaskResult(inspectionSummary, inspectionResult);
            this.inspTaskResultService.insertInspTaskResult(inspTaskResult);
        }
        MessageLog messageLog = new MessageLog();
        messageLog.setTitle("\u5de1\u68c0\u7ed3\u675f");
        messageLog.setContent("\u5de1\u68c0\u4efb\u52a1\u5df2\u7ed3\u675f\uff0c\u8017\u65f6\uff1a" + this.getJobTime(robotTask.getStartTime()) + "ms");
        messageLog.setStatus("0");
        ConfigRobot robot = this.configRobotService.getConfigRobotByRobotId("" + inspectionSummary.getRobot_id());
        if (null != robot) {
            messageLog.setSource(robot.getRobotName());
            messageLog.setRoomCode(robot.getRoomCode());
        } else {
            messageLog.setSource(robotTask.getRobotId());
        }
        this.saveAndPushMessage(messageLog, null != robot ? robot.getRoomCode() : null);
        this.robotTaskSchedulerService.startNow("" + inspectionSummary.getRobot_id());
    }

    private String getJobTime(Date createTime) {
        return DateUtils.getDatePoorSecond((Date)new Date(), (Date)createTime);
    }

    private InspTaskResult getInspTaskResult(InspectionSummary inspectionSummary, InspectionResult inspectionResult) {
        InspTaskResult inspTaskResult = new InspTaskResult();
        inspTaskResult.setAbnormal(inspectionResult.isAbnormal() ? "1" : "0");
        inspTaskResult.setInspTaskId(inspectionSummary.getRobot_task_id());
        inspTaskResult.setAbnormalInfo(inspectionResult.getAbnormal_info());
        inspTaskResult.setImageBase64(inspectionResult.getImage_base64());
        inspTaskResult.setRobotId("" + inspectionSummary.getRobot_id());
        inspTaskResult.setType("0");
        inspTaskResult.setPoint(inspectionResult.getPoint());
        return inspTaskResult;
    }

    private void createInspTask(Long robotId, Long taskId, List<String> points) {
        RobotTask robotTask = new RobotTask();
        robotTask.setRobotId("" + robotId);
        robotTask.setTaskId(taskId);
        robotTask.setTaskName("\u5de1\u68c0\u4efb\u52a1");
        robotTask.setTaskType("1");
        robotTask.setTaskMode("0");
        robotTask.setTaskStatus("0");
        robotTask.setTaskSubtype("0");
        robotTask.setDirectExecution("0");
        RobotCmd robotCmd = new RobotCmd();
        robotCmd.setRobot_id(robotId.longValue());
        robotCmd.setTask_id(789018L);
        robotCmd.setPoints(points);
        robotTask.setCmd(JsonUtils.obj2String((Object)robotCmd));
        this.robotTaskSchedulerService.addQueue(robotTask);
    }

    private void saveAndPushMessage(MessageLog messageLog, String roomCode) {
        if (Strings.isNotBlank((String)roomCode)) {
            this.pushService.push(new PushMessage(roomCode, "notice", JsonUtils.obj2String((Object)messageLog)));
        }
        this.messageLogService.insertMessageLog(messageLog);
    }
}
