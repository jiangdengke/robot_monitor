/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.ai.PushMessage
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.robot.RobotChatRequest
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 *  com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest
 *  com.robotmonitor.common.core.domain.robot.RobotListenQwenRequestFunction
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.config.service.IConfigAudioService
 *  com.robotmonitor.config.service.IConfigRobotService
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.domain.AiChatLog;
import com.robotmonitor.ai.domain.AiFlightInfo;
import com.robotmonitor.ai.domain.ParamFlightInfo;
import com.robotmonitor.ai.domain.ParamPlaceName;
import com.robotmonitor.ai.domain.RobotChat;
import com.robotmonitor.ai.graph.AccessGraph;
import com.robotmonitor.ai.graph.FaqChatGraph;
import com.robotmonitor.ai.graph.FindPlaceGraph;
import com.robotmonitor.ai.graph.FlightGraph;
import com.robotmonitor.ai.graph.IntroducingPlacesGraph;
import com.robotmonitor.ai.service.AiAgentService;
import com.robotmonitor.ai.service.AiFlightService;
import com.robotmonitor.ai.service.CallCenterService;
import com.robotmonitor.ai.service.IAiChatLogService;
import com.robotmonitor.ai.service.PlaceService;
import com.robotmonitor.ai.utils.AiUtils;
import com.robotmonitor.common.core.domain.ai.PushMessage;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.robot.RobotChatRequest;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest;
import com.robotmonitor.common.core.domain.robot.RobotListenQwenRequestFunction;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.config.service.IConfigAudioService;
import com.robotmonitor.config.service.IConfigRobotService;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service(value="callCenterAiAgentServiceImpl")
public class CallCenterAiAgentServiceImpl
implements AiAgentService {
    private static final Logger log = LoggerFactory.getLogger(CallCenterAiAgentServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IAiChatLogService aiChatLogService;
    @Autowired
    private IConfigAudioService configAudioService;
    @Autowired
    private IConfigRobotService configRobotService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private AiFlightService flightService;
    @Autowired
    private CallCenterService callCenterService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public RobotChatResponse chat(RobotChatRequest robotChatRequest) {
        ConfigRobot configRobot = this.configRobotService.getConfigRobotByRobotId(robotChatRequest.getRobotId());
        robotChatRequest.setRemark(configRobot.getRemark());
        AiChatLog aiChatLog = this.saveQuestion(robotChatRequest.getRobotId(), robotChatRequest.getMessage(), robotChatRequest.getLanguage(), robotChatRequest.getSessionId());
        this.pushQuestionToFront(robotChatRequest);
        RobotChatResponse robotChatResponse = this.processChatRequest(robotChatRequest, configRobot);
        if (this.isFaq(robotChatRequest.getSessionId())) {
            this.pushAnswerToFront(robotChatResponse);
            this.saveAnswer(aiChatLog, robotChatResponse.getMessage(), robotChatResponse.getEventType());
            return robotChatResponse;
        }
        return new RobotChatResponse();
    }

    @Override
    public RobotChatResponse unitreeChat(RobotChatRequest robotChatRequest) {
        return null;
    }

    @Override
    public RobotChatResponse chatOnly(RobotChatRequest robotChatRequest) {
        return null;
    }

    @Override
    public RobotChatResponse intentDetection(RobotListenQwenRequest intentDetection) {
        try {
            Thread.sleep(100L);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        RobotChatRequest robotChatRequest = new RobotChatRequest();
        robotChatRequest.setRobotId(intentDetection.getRobotId());
        robotChatRequest.setLanguage(intentDetection.getLanguage());
        ConfigRobot configRobot = this.configRobotService.getConfigRobotByRobotId(robotChatRequest.getRobotId());
        RobotChatResponse robotChatResponse = this.processIntentDetection(intentDetection, robotChatRequest, configRobot);
        this.pushAnswerToFront(robotChatResponse);
        AiChatLog aiChatLog = (AiChatLog)((Object)this.redisCache.getCacheObject("robot_qwen_chat_ai_chat_log_id:" + intentDetection.getSessionId()));
        if (null != aiChatLog) {
            this.saveAnswer(aiChatLog, robotChatResponse.getMessage(), robotChatResponse.getEventType());
        }
        return robotChatResponse;
    }

    private RobotChatResponse processChatRequest(RobotChatRequest robotChatRequest, ConfigRobot configRobot) {
        log.info("\u5f00\u59cb\u673a\u5668\u4eba\u5bf9\u8bdd\u5904\u7406\uff0crobotId: {}, prompt: {}", (Object)robotChatRequest.getRobotId(), (Object)StringUtils.abbreviate((String)robotChatRequest.getPrompt(), (int)50));
        String esChatResponse = this.callCenterService.esChat(robotChatRequest.getMessage(), robotChatRequest.getLanguage(), robotChatRequest.getRobotId());
        return this.faq(esChatResponse, robotChatRequest, configRobot);
    }

    private RobotChatResponse processIntentDetection(RobotListenQwenRequest intentDetection, RobotChatRequest robotChatRequest, ConfigRobot configRobot) {
        RobotListenQwenRequestFunction function = intentDetection.getFunction();
        switch (function.getName()) {
            case "FINDING_PLACES": {
                return this.findingPlace(function.getParam(), robotChatRequest, configRobot);
            }
            case "INTRODUCING_PLACES": {
                return this.introducePlace(function.getParam(), robotChatRequest, configRobot);
            }
            case "FLIGHT": {
                return this.flight(function.getParam(), robotChatRequest, configRobot);
            }
            case "ACCESS": {
                return this.access(robotChatRequest, configRobot);
            }
        }
        return this.other(robotChatRequest, configRobot);
    }

    private boolean isFaq(String sessionId) {
        RobotListenQwenRequest intentDetection = (RobotListenQwenRequest)this.redisCache.getCacheObject("robot_qwen_chat_id:" + sessionId);
        this.redisCache.deleteObject("robot_qwen_chat_id:" + sessionId);
        log.info("intentDetection : {}", (Object)JsonUtils.obj2String((Object)intentDetection));
        return null == intentDetection || null == intentDetection.getFunction() || "FAQ".equals(intentDetection.getFunction().getName());
    }

    private RobotChatResponse faq(String esChatResponse, RobotChatRequest robotChatRequest, ConfigRobot configRobot) {
        return FaqChatGraph.faq(false, robotChatRequest.getRobotId(), esChatResponse, robotChatRequest.getLanguage(), configRobot.getRoomCode(), this.configAudioService);
    }

    private RobotChatResponse findingPlace(String param, RobotChatRequest robotChatRequest, ConfigRobot configRobot) {
        ParamPlaceName paramPlaceName;
        String placeName = "";
        if (Strings.isNotBlank((String)param) && null != (paramPlaceName = (ParamPlaceName)JsonUtils.string2Obj((String)param, ParamPlaceName.class)) && Strings.isNotBlank((String)paramPlaceName.getPlaceName())) {
            placeName = paramPlaceName.getPlaceName();
        }
        return FindPlaceGraph.findingPlace(robotChatRequest.getRobotId(), robotChatRequest.getLanguage(), configRobot.getRoomCode(), placeName, this.configAudioService, this.placeService);
    }

    private RobotChatResponse introducePlace(String param, RobotChatRequest robotChatRequest, ConfigRobot configRobot) {
        ParamPlaceName paramPlaceName;
        String placeName = "";
        if (Strings.isNotBlank((String)param) && null != (paramPlaceName = (ParamPlaceName)JsonUtils.string2Obj((String)param, ParamPlaceName.class)) && Strings.isNotBlank((String)paramPlaceName.getPlaceName())) {
            placeName = paramPlaceName.getPlaceName();
        }
        return IntroducingPlacesGraph.introducePlace(robotChatRequest.getRobotId(), robotChatRequest.getLanguage(), configRobot.getRoomCode(), placeName, this.configAudioService, this.placeService);
    }

    private RobotChatResponse flight(String param, RobotChatRequest robotChatRequest, ConfigRobot configRobot) {
        AiFlightInfo flight;
        ParamFlightInfo paramFlightInfo;
        String message = "";
        boolean isFindFlight = false;
        if (Strings.isNotBlank((String)param) && null != (paramFlightInfo = (ParamFlightInfo)JsonUtils.string2Obj((String)param, ParamFlightInfo.class)) && null != (flight = this.flightService.findFlight(paramFlightInfo.getFlightNo(), paramFlightInfo.getFlightDate()))) {
            isFindFlight = true;
            message = this.callCenterService.esChat("\u5f53\u524d\u822a\u73ed\u4fe1\u606f\u5982\u4e0b:%s\uff0c\u8bf7\u603b\u7ed3\u5e76\u56de\u590d".formatted(flight), robotChatRequest.getLanguage(), robotChatRequest.getRobotId());
        }
        return FlightGraph.flight(robotChatRequest.getRobotId(), robotChatRequest.getLanguage(), message, isFindFlight, this.configAudioService);
    }

    private RobotChatResponse access(RobotChatRequest robotChatRequest, ConfigRobot configRobot) {
        return AccessGraph.access(robotChatRequest.getRobotId(), robotChatRequest.getLanguage(), configRobot.getRoomCode(), this.configAudioService);
    }

    private RobotChatResponse other(RobotChatRequest robotChatRequest, ConfigRobot configRobot) {
        RobotChatResponse robotChatResponse = new RobotChatResponse();
        robotChatResponse.setRobotId(robotChatRequest.getRobotId());
        robotChatResponse.setLanguage(robotChatRequest.getLanguage());
        AiUtils.setDefaultMessageAndVoice(this.configAudioService, robotChatRequest.getLanguage(), configRobot.getRoomCode(), "KNOWLEDGE_CHAT_UNKNOWN", robotChatResponse);
        robotChatResponse.setEventType("OTHER");
        return robotChatResponse;
    }

    private AiChatLog saveQuestion(String robotId, String question, String language, String sessionId) {
        AiChatLog aiChatLog = new AiChatLog();
        aiChatLog.setRobotId(robotId);
        aiChatLog.setQuestion(question);
        aiChatLog.setLanguage(language);
        this.aiChatLogService.insertAiChatLog(aiChatLog);
        this.redisCache.setCacheObject("robot_qwen_chat_ai_chat_log_id:" + sessionId, (Object)aiChatLog, Integer.valueOf(15), TimeUnit.SECONDS);
        return aiChatLog;
    }

    private void saveAnswer(AiChatLog aiChatLog, String answer, String chatType) {
        if ("NONE".equals(chatType)) {
            this.aiChatLogService.deleteAiChatLogById(aiChatLog.getId());
        } else {
            aiChatLog.setAnswer(answer);
            aiChatLog.setChatType(chatType);
            this.aiChatLogService.updateAiChatLog(aiChatLog);
        }
    }

    private void pushQuestionToFront(RobotChatRequest robotChatRequest) {
        RobotChat question = new RobotChat("user", robotChatRequest.getMessage());
        PushMessage pushQuestion = new PushMessage(robotChatRequest.getRobotId(), "ai", JsonUtils.obj2String((Object)question));
        this.redisTemplate.convertAndSend("redis.websocket.push", (Object)JsonUtils.obj2String((Object)pushQuestion));
        log.info("\u63a8\u9001\u8be2\u95ee\u6d88\u606f\u5230\u524d\u7aef \uff1a {}", (Object)JsonUtils.obj2String((Object)pushQuestion));
    }

    private void pushAnswerToFront(RobotChatResponse robotChatResponse) {
        RobotChat answer = new RobotChat("assistant", robotChatResponse);
        PushMessage pushAnswer = new PushMessage(robotChatResponse.getRobotId(), "ai", JsonUtils.obj2String((Object)answer));
        this.redisTemplate.convertAndSend("redis.websocket.push", (Object)JsonUtils.obj2String((Object)pushAnswer));
        log.info("\u63a8\u9001\u56de\u7b54\u6d88\u606f\u5230\u524d\u7aef \uff1a {}", (Object)JsonUtils.obj2String((Object)pushAnswer));
    }
}
