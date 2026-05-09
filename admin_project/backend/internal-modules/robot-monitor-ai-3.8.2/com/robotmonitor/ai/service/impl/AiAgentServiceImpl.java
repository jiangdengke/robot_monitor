/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.cloud.ai.graph.CompiledGraph
 *  com.alibaba.cloud.ai.graph.OverAllState
 *  com.alibaba.cloud.ai.graph.RunnableConfig
 *  com.alibaba.cloud.ai.graph.exception.GraphStateException
 *  com.robotmonitor.common.core.domain.ai.PushMessage
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.robot.RobotChatRequest
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 *  com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.config.service.IConfigAudioService
 *  com.robotmonitor.config.service.IConfigRobotService
 *  jakarta.annotation.PostConstruct
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.ai.service.impl;

import com.alibaba.cloud.ai.graph.CompiledGraph;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import com.robotmonitor.ai.domain.AiChatLog;
import com.robotmonitor.ai.domain.RobotChat;
import com.robotmonitor.ai.graph.FaqChatGraph;
import com.robotmonitor.ai.graph.RobotChatGraph;
import com.robotmonitor.ai.graph.UnitreeRobotChatGraph;
import com.robotmonitor.ai.service.AiAgentService;
import com.robotmonitor.ai.service.AiFlightService;
import com.robotmonitor.ai.service.ChatClientService;
import com.robotmonitor.ai.service.IAiChatLogService;
import com.robotmonitor.ai.service.PlaceService;
import com.robotmonitor.common.core.domain.ai.PushMessage;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.robot.RobotChatRequest;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.config.service.IConfigAudioService;
import com.robotmonitor.config.service.IConfigRobotService;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service(value="aiAgentServiceImpl")
public class AiAgentServiceImpl
implements AiAgentService {
    private static final Logger log = LoggerFactory.getLogger(AiAgentServiceImpl.class);
    @Autowired
    private ChatClientService chatClientService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private AiFlightService flightService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IAiChatLogService aiChatLogService;
    @Autowired
    private IConfigAudioService configAudioService;
    @Autowired
    private IConfigRobotService configRobotService;
    private CompiledGraph mainGraph;
    private CompiledGraph faqGraph;
    private CompiledGraph unitreeGraph;

    @PostConstruct
    public void init() {
        try {
            this.mainGraph = new RobotChatGraph().build(this.chatClientService, this.placeService, this.flightService, this.configAudioService);
            this.faqGraph = new FaqChatGraph().build(this.chatClientService, this.configAudioService, true);
            this.unitreeGraph = new UnitreeRobotChatGraph().build(this.chatClientService, this.placeService, this.flightService, this.configAudioService);
        }
        catch (GraphStateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RobotChatResponse chat(RobotChatRequest robotChatRequest) {
        ConfigRobot configRobot = this.configRobotService.getConfigRobotByRobotId(robotChatRequest.getRobotId());
        robotChatRequest.setRemark(configRobot.getRemark());
        AiChatLog aiChatLog = this.saveQuestion(robotChatRequest.getRobotId(), robotChatRequest.getMessage(), robotChatRequest.getLanguage());
        this.pushQuestionToFront(robotChatRequest);
        RobotChatResponse robotChatResponse = this.processChatRequest(robotChatRequest, configRobot, this.mainGraph);
        this.pushAnswerToFront(robotChatResponse);
        this.saveAnswer(aiChatLog, robotChatResponse.getMessage(), robotChatResponse.getEventType());
        return robotChatResponse;
    }

    @Override
    public RobotChatResponse unitreeChat(RobotChatRequest robotChatRequest) {
        return this.simpleChat(robotChatRequest, this.unitreeGraph);
    }

    @Override
    public RobotChatResponse chatOnly(RobotChatRequest robotChatRequest) {
        return this.simpleChat(robotChatRequest, this.faqGraph);
    }

    @Override
    public RobotChatResponse intentDetection(RobotListenQwenRequest intentDetection) {
        return null;
    }

    private RobotChatResponse simpleChat(RobotChatRequest robotChatRequest, CompiledGraph graph) {
        ConfigRobot configRobot = this.configRobotService.getConfigRobotByRobotId(robotChatRequest.getRobotId());
        robotChatRequest.setRemark(configRobot.getRemark());
        AiChatLog aiChatLog = this.saveQuestion(robotChatRequest.getRobotId(), robotChatRequest.getMessage(), robotChatRequest.getLanguage());
        RobotChatResponse robotChatResponse = this.processChatRequest(robotChatRequest, configRobot, graph);
        this.saveAnswer(aiChatLog, robotChatResponse.getMessage(), robotChatResponse.getEventType());
        return robotChatResponse;
    }

    private RobotChatResponse processChatRequest(RobotChatRequest robotChatRequest, ConfigRobot configRobot, CompiledGraph graph) {
        log.info("\u5f00\u59cb\u673a\u5668\u4eba\u5bf9\u8bdd\u5904\u7406\uff0crobotId: {}, prompt: {}", (Object)robotChatRequest.getRobotId(), (Object)StringUtils.abbreviate((String)robotChatRequest.getPrompt(), (int)50));
        HashMap<String, String> input = new HashMap<String, String>(6);
        input.put("user_input", robotChatRequest.getPrompt());
        input.put("user_message", robotChatRequest.getMessage());
        input.put("robot_id", robotChatRequest.getRobotId());
        input.put("location", configRobot.getRoomCode());
        input.put("language", robotChatRequest.getLanguage());
        String placeNames = this.placeService.getPlaceNames(configRobot.getRoomCode(), robotChatRequest.getLanguage());
        input.put("place_name", placeNames);
        try {
            Optional stateOpt = graph.call(input, RunnableConfig.builder().threadId(robotChatRequest.getRobotId()).build());
            OverAllState state = (OverAllState)stateOpt.orElseThrow(() -> new RuntimeException("AI Graph returned empty state"));
            Object resultObj = state.value("response").orElse(null);
            RobotChatResponse robotChatResponse = new RobotChatResponse();
            if (resultObj instanceof RobotChatResponse) {
                robotChatResponse = resultObj;
            } else if (resultObj instanceof Map) {
                robotChatResponse = new RobotChatResponse((Map)resultObj);
            }
            robotChatResponse.setNeedVoice(robotChatRequest.isNeedVoice());
            return robotChatResponse;
        }
        catch (Exception e) {
            log.error("\u5904\u7406\u673a\u5668\u4eba\u5bf9\u8bdd\u8bf7\u6c42\u5931\u8d25\uff0crobotId: {}, error: {}", new Object[]{robotChatRequest.getRobotId(), e.getMessage(), e});
            throw new RuntimeException("\u5904\u7406\u673a\u5668\u4eba\u5bf9\u8bdd\u8bf7\u6c42\u5931\u8d25", e);
        }
    }

    private AiChatLog saveQuestion(String robotId, String question, String language) {
        AiChatLog aiChatLog = new AiChatLog();
        aiChatLog.setRobotId(robotId);
        aiChatLog.setQuestion(question);
        aiChatLog.setLanguage(language);
        this.aiChatLogService.insertAiChatLog(aiChatLog);
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
