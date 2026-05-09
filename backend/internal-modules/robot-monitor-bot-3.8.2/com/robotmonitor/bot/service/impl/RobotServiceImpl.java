/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.core.domain.config.ConfigTask
 *  com.robotmonitor.common.core.domain.robot.RobotCmd
 *  com.robotmonitor.common.core.domain.robot.RobotHttpCmd
 *  com.robotmonitor.common.core.domain.robot.RobotPosition
 *  com.robotmonitor.common.core.domain.robot.RobotStatus
 *  com.robotmonitor.common.core.domain.robot.RobotTask
 *  com.robotmonitor.common.core.domain.robot.RobotTaskCmd
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.config.domain.ConfigArea
 *  com.robotmonitor.config.domain.ConfigAreaDetail
 *  com.robotmonitor.config.service.IConfigAreaService
 *  com.robotmonitor.config.service.IConfigRegionService
 *  com.robotmonitor.config.service.IConfigRobotService
 *  com.robotmonitor.config.service.IConfigTaskService
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 *  org.springframework.util.CollectionUtils
 */
package com.robotmonitor.bot.service.impl;

import com.robotmonitor.bot.domain.CustomerNotificationRequest;
import com.robotmonitor.bot.domain.GuideLog;
import com.robotmonitor.bot.domain.RobotHttpCmdRequest;
import com.robotmonitor.bot.domain.RobotMoveRequest;
import com.robotmonitor.bot.domain.RobotSimpleCmdRequest;
import com.robotmonitor.bot.service.IGuideLogService;
import com.robotmonitor.bot.service.RobotCmdService;
import com.robotmonitor.bot.service.RobotService;
import com.robotmonitor.bot.service.RobotTaskSchedulerService;
import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.core.domain.robot.RobotCmd;
import com.robotmonitor.common.core.domain.robot.RobotHttpCmd;
import com.robotmonitor.common.core.domain.robot.RobotPosition;
import com.robotmonitor.common.core.domain.robot.RobotStatus;
import com.robotmonitor.common.core.domain.robot.RobotTask;
import com.robotmonitor.common.core.domain.robot.RobotTaskCmd;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.config.domain.ConfigArea;
import com.robotmonitor.config.domain.ConfigAreaDetail;
import com.robotmonitor.config.service.IConfigAreaService;
import com.robotmonitor.config.service.IConfigRegionService;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.config.service.IConfigTaskService;
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
public class RobotServiceImpl
implements RobotService {
    private static final Logger log = LoggerFactory.getLogger(RobotServiceImpl.class);
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IConfigAreaService configAreaService;
    @Autowired
    private IGuideLogService guideLogService;
    @Autowired
    private IConfigRobotService configRobotService;
    @Autowired
    private RobotCmdService robotCmdService;
    @Autowired
    private RobotTaskSchedulerService robotTaskSchedulerService;
    @Autowired
    private IConfigTaskService configTaskService;
    @Autowired
    private IConfigRegionService configRegionService;

    @Override
    public void sendCmd(RobotSimpleCmdRequest robotSimpleCmdRequest) {
        RobotTaskCmd robotCmd = this.getCmdByConfig(robotSimpleCmdRequest);
        this.robotCmdService.sendCmd(robotCmd);
    }

    @Override
    public RobotTask guide(RobotMoveRequest robotMoveRequest) {
        log.info("\u5f00\u59cb\u6267\u884c\u5bfc\u822a, robotMoveRequest : " + JsonUtils.obj2String((Object)robotMoveRequest));
        ConfigTask configTask = this.configTaskService.findDefaultTask(Long.valueOf(-2L));
        ConfigTask configTaskVoice = this.configTaskService.findDefaultTask(Long.valueOf(-1L));
        RobotTaskCmd robotCmd = this.getMoveCmd(robotMoveRequest, configTask);
        if (Strings.isNotBlank((String)robotMoveRequest.getAreaId())) {
            ConfigAreaDetail configAreaDetail;
            ConfigArea configArea = this.configAreaService.selectConfigAreaById(Long.valueOf(Long.parseLong(robotMoveRequest.getAreaId())));
            log.info("\u5f00\u59cb\u6267\u884c\u5bfc\u822a, configArea : " + JsonUtils.obj2String((Object)configArea));
            if (null == configArea) {
                log.error("\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u529f\u80fd\u533a\u57df\uff0c\u65e0\u6cd5\u79fb\u52a8");
                throw new RuntimeException("\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u533a\u57df\uff0c\u65e0\u6cd5\u79fb\u52a8");
            }
            robotCmd.setLocation_information(configArea.getCoordinate());
            if (robotMoveRequest.isNeedVoice() && null != (configAreaDetail = (ConfigAreaDetail)configArea.getConfigAreaDetailList().stream().filter(detail -> detail.getLanguageType().equals(robotMoveRequest.getLanguage())).findFirst().orElse(null))) {
                if (Strings.isNotBlank((String)configAreaDetail.getArrText())) {
                    RobotTaskCmd robotTaskCmd = this.getVoiceCmd(robotMoveRequest.getRobotId(), "/api/voice/area?id=" + configAreaDetail.getId() + "&type=0", configTaskVoice);
                    this.startTask(robotTaskCmd, configTaskVoice);
                }
                if (Strings.isNotBlank((String)configAreaDetail.getRemark())) {
                    robotCmd.setVoiceUrl("/api/voice/area?id=" + configAreaDetail.getId() + "&type=1");
                }
            }
            this.saveMoveLog((RobotCmd)robotCmd, configArea.getId());
        } else {
            this.saveMoveLog((RobotCmd)robotCmd, null);
        }
        return this.startTask(robotCmd, configTask);
    }

    private RobotTask startTask(RobotTaskCmd robotCmd, ConfigTask configTask) {
        RobotTask robotTask = new RobotTask("" + robotCmd.getRobot_id(), configTask, robotCmd);
        this.robotTaskSchedulerService.addQueue(robotTask);
        return robotTask;
    }

    private void saveMoveLog(RobotCmd robotCmd, Long configAreaId) {
        GuideLog guideLog = new GuideLog();
        guideLog.setCoordinate(robotCmd.getLocation_information());
        guideLog.setRegionId(configAreaId);
        guideLog.setRobotId("" + robotCmd.getRobot_id());
        guideLog.setCreateTime(new Date());
        this.guideLogService.insertGuideLog(guideLog);
    }

    @Override
    public void setRobotState(RobotHttpCmdRequest robotHttpCmdRequest) {
        RobotHttpCmd robotHttpCmd = new RobotHttpCmd(robotHttpCmdRequest.getRobotId(), robotHttpCmdRequest.getState());
        this.robotCmdService.sendHttpCmd(robotHttpCmd);
        this.robotTaskSchedulerService.setRobotInUseFlag("" + robotHttpCmdRequest.getRobotId(), "" + robotHttpCmdRequest.getState());
        if ("1".equals("" + robotHttpCmdRequest.getState())) {
            this.robotTaskSchedulerService.startNow("" + robotHttpCmdRequest.getRobotId());
            this.robotTaskSchedulerService.scheduleGoHomeIfIdle("" + robotHttpCmdRequest.getRobotId());
        }
    }

    @Override
    public RobotTask notifyCustomer(CustomerNotificationRequest customerNotificationRequest) {
        ConfigTask configTask = this.configTaskService.findDefaultTask(Long.valueOf(-4L));
        RobotTaskCmd robotCmd = new RobotTaskCmd(Long.parseLong(customerNotificationRequest.getRobotId()), configTask.getCommand().longValue(), configTask.getExecuteType(), configTask.getPriority(), customerNotificationRequest.getCoordinate(), Boolean.valueOf(false), null, customerNotificationRequest.getText(), customerNotificationRequest.getLanguage(), null);
        return this.startTask(robotCmd, configTask);
    }

    @Override
    public RobotPosition getRobotPosition(String robotId) {
        RobotStatus robotStatus = (RobotStatus)this.redisCache.getCacheObject("robot_current_status:robot_id_" + robotId);
        if (null == robotStatus) {
            return new RobotPosition(robotId);
        }
        return new RobotPosition(robotId, robotStatus.getLocation());
    }

    @Override
    public List<RobotPosition> getRobotPositions(String robotId) {
        ArrayList<RobotPosition> robotPositions = new ArrayList<RobotPosition>();
        for (String id : robotId.split(",")) {
            RobotPosition robotPosition = this.getRobotPosition(id);
            if (null == robotPosition) {
                robotPosition = new RobotPosition();
                robotPosition.setRobotId(id);
            }
            robotPositions.add(robotPosition);
        }
        return robotPositions;
    }

    @Override
    public List<RobotPosition> getPositionByRoomCode(String roomCode) {
        List robotIds = this.configRobotService.getRobotIdsByRoomCode(roomCode);
        if (!CollectionUtils.isEmpty((Collection)robotIds)) {
            return this.getRobotPositions(String.join((CharSequence)",", robotIds));
        }
        return null;
    }

    @Override
    public void interruptGuideTask(String robotId) {
        ConfigTask configTask = this.configTaskService.findDefaultTask(Long.valueOf(-6L));
        RobotTaskCmd robotCmd = new RobotTaskCmd(configTask, Long.parseLong(robotId));
        this.robotCmdService.sendCmd(robotCmd);
    }

    @Override
    public void runConfigTask(ConfigTask configTask) {
        RobotTaskCmd robotCmd = new RobotTaskCmd(configTask, configTask.getRobotId().longValue());
        if (Strings.isNotBlank((String)configTask.getRegion())) {
            if (configTask.getCommand().equals(789110L)) {
                if (configTask.getRegion().contains(",")) {
                    ArrayList<String> points = new ArrayList<String>();
                    for (String regionId : configTask.getRegion().split(",")) {
                        ConfigRegion configRegion = this.configRegionService.selectConfigRegionById(Long.valueOf(Long.parseLong(regionId)));
                        if (null == configRegion) continue;
                        points.add(configRegion.getCoordinate());
                    }
                    robotCmd.setPoints(points);
                } else {
                    ConfigRegion configRegion = this.configRegionService.selectConfigRegionById(Long.valueOf(Long.parseLong(configTask.getRegion())));
                    if (null != configRegion) {
                        robotCmd.setLocation_information(configRegion.getCoordinate());
                    }
                }
            } else {
                ConfigRegion configRegion = this.configRegionService.selectConfigRegionById(Long.valueOf(Long.parseLong(configTask.getRegion())));
                if (null != configRegion) {
                    robotCmd.setLocation_information(configRegion.getCoordinate());
                }
            }
        }
        if (configTask.getCommand().equals(789113L)) {
            robotCmd.setTime_sleep(Strings.isNotBlank((String)configTask.getRemark()) ? configTask.getRemark() : "2000");
        }
        if (Strings.isNotBlank((String)configTask.getImgIds())) {
            robotCmd.setImgIds(configTask.getImgIds());
        }
        if (Strings.isNotBlank((String)configTask.getAuditIds())) {
            robotCmd.setVideoId(configTask.getAuditIds());
        }
        this.robotCmdService.sendCmd(robotCmd);
    }

    private RobotTaskCmd getCmdByConfig(RobotSimpleCmdRequest robotSimpleCmdRequest) {
        return new RobotTaskCmd(robotSimpleCmdRequest.getRobotId(), robotSimpleCmdRequest.getTaskId(), "immediately", "High", null, Boolean.valueOf(false), "home/test/path", null, null, null);
    }

    private RobotTaskCmd getMoveCmd(RobotMoveRequest robotMoveRequest, ConfigTask configTask) {
        return new RobotTaskCmd(robotMoveRequest.getRobotId(), configTask.getCommand().longValue(), configTask.getExecuteType(), configTask.getPriority(), robotMoveRequest.getLocationInfo(), Boolean.valueOf(false), null, null, null, null);
    }

    private RobotTaskCmd getVoiceCmd(long robotId, String audioUrl, ConfigTask configTask) {
        return new RobotTaskCmd(robotId, configTask.getCommand().longValue(), configTask.getExecuteType(), configTask.getPriority(), null, Boolean.valueOf(false), audioUrl, null, null, audioUrl);
    }
}
