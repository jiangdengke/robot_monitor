/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.ai.service.AccessService
 *  com.robotmonitor.ai.service.AiAgentService
 *  com.robotmonitor.ai.service.IAiChatLogService
 *  com.robotmonitor.ai.service.RobotChatService
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.robot.Admittance
 *  com.robotmonitor.common.core.domain.robot.RobotChatRequest
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 *  com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest
 *  com.robotmonitor.common.core.page.TableDataInfo
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.flight.domain.CollectInResponse2
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.ai.service.AccessService;
import com.robotmonitor.ai.service.AiAgentService;
import com.robotmonitor.ai.service.IAiChatLogService;
import com.robotmonitor.ai.service.RobotChatService;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.robot.Admittance;
import com.robotmonitor.common.core.domain.robot.RobotChatRequest;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.flight.domain.CollectInResponse2;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/ai"})
public class AiApiController
extends BaseController {
    @Autowired
    private RobotChatService robotChatService;
    @Autowired
    private IAiChatLogService aiChatLogService;
    @Autowired
    @Qualifier(value="aiAgentServiceImpl")
    private AiAgentService aiAgentService;
    @Autowired
    @Qualifier(value="callCenterAiAgentServiceImpl")
    private AiAgentService qwenAiAgentService;
    @Autowired
    private AccessService accessService;
    @Autowired
    private RedisCache redisCache;

    @PostMapping(value={"/robot-chat"})
    public RobotChatResponse chat(@RequestBody RobotChatRequest robotChatRequest) {
        this.logger.info("/robot-chat \u5165\u53c2\uff1a{}", (Object)JsonUtils.obj2String((Object)robotChatRequest));
        return this.aiAgentService.chat(robotChatRequest);
    }

    @PostMapping(value={"/robot-qwen-chat"})
    public RobotChatResponse qwenChat(@RequestBody RobotChatRequest robotChatRequest) {
        this.logger.info("/robot-qwen-chat \u5165\u53c2\uff1a{}", (Object)JsonUtils.obj2String((Object)robotChatRequest));
        return this.qwenAiAgentService.chat(robotChatRequest);
    }

    @PostMapping(value={"/robot-qwen-chat-intent-detection"})
    public RobotChatResponse qwenChatIntentDetection(@RequestBody RobotListenQwenRequest intentDetection) {
        this.logger.info("/robot-qwen-chat-intent-detection \u5165\u53c2\uff1a{}", (Object)JsonUtils.obj2String((Object)intentDetection));
        return this.qwenAiAgentService.intentDetection(intentDetection);
    }

    @PostMapping(value={"/normal-chat"})
    public RobotChatResponse normalChat(@RequestBody RobotChatRequest robotChatRequest) {
        this.logger.info("/normal-chat \u5165\u53c2\uff1a{}", (Object)JsonUtils.obj2String((Object)robotChatRequest));
        return this.aiAgentService.chat(robotChatRequest);
    }

    @PostMapping(value={"/unitree-robot-chat"})
    public RobotChatResponse unitreeChat(@RequestBody RobotChatRequest robotChatRequest) {
        this.logger.info("/unitree-robot-chat \u5165\u53c2\uff1a{}", (Object)JsonUtils.obj2String((Object)robotChatRequest));
        return this.aiAgentService.unitreeChat(robotChatRequest);
    }

    @PostMapping(value={"/robot-chat-only"})
    public RobotChatResponse robotChatOnly(@RequestBody RobotChatRequest robotChatRequest) {
        this.logger.info("/robot-chat-only \u5165\u53c2\uff1a{}", (Object)JsonUtils.obj2String((Object)robotChatRequest));
        return this.aiAgentService.chatOnly(robotChatRequest);
    }

    @GetMapping(value={"/robot-reset-memory"})
    public boolean resetMemory(@RequestParam(value="chatId") String chatId) {
        this.logger.info("/robot-reset-memory \u5165\u53c2\uff1a{}", (Object)chatId);
        return this.robotChatService.resetMemory(chatId, true);
    }

    @PostMapping(value={"/validate-admittance"})
    public RobotChatResponse validateAdmittance(@RequestBody Admittance admittance) {
        this.logger.info("/validate-admittance \u5165\u53c2\uff1a{}", (Object)admittance);
        return this.accessService.validateAdmittance(admittance);
    }

    @GetMapping(value={"/run-ai-auto-classification"})
    public void runAiAutoClassification() {
        this.aiChatLogService.runAiAutoClassification();
    }

    @GetMapping(value={"/ai-question-stat-list"})
    public TableDataInfo selectAiQuestionStatListGet(@RequestParam(value="robotId", required=false) String robotId, @RequestParam(value="question", required=false) String question, @RequestParam(value="chatType", required=false) String chatType, @RequestParam(value="startTime", required=false) String startTime, @RequestParam(value="endTime", required=false) String endTime) {
        this.startPage();
        List aiQuestionStatDTOS = this.aiChatLogService.selectAiQuestionStatList(robotId, question, chatType, startTime, endTime);
        return this.getDataTable(aiQuestionStatDTOS);
    }

    @PostMapping(value={"/prepare-host-admittance"})
    public AjaxResult prepareHostAdmittance(@RequestParam(value="robotId") String robotId) {
        this.logger.info("/prepare-host-admittance \u51c6\u5907\u4e3b\u65c5\u5ba2\u51c6\u5165\uff0crobotId\uff1a{}", (Object)robotId);
        try {
            String typeKey = "admittance:" + robotId + ":type";
            this.redisCache.setCacheObject(typeKey, (Object)"HOST", Integer.valueOf(5), TimeUnit.MINUTES);
            String hostCollectIdKey = "admittance:" + robotId + ":hostCollectId";
            this.redisCache.deleteObject(hostCollectIdKey);
            this.logger.info("\u51c6\u5907\u4e3b\u65c5\u5ba2\u51c6\u5165\u6210\u529f\uff0crobotId:{}", (Object)robotId);
            return AjaxResult.success((String)"\u51c6\u5907\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error("\u51c6\u5907\u4e3b\u65c5\u5ba2\u51c6\u5165\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            return AjaxResult.error((String)("\u51c6\u5907\u5931\u8d25\uff1a" + e.getMessage()));
        }
    }

    @PostMapping(value={"/prepare-follower-admittance"})
    public AjaxResult prepareFollowerAdmittance(@RequestParam(value="robotId") String robotId, @RequestParam(value="hostCollectId") String hostCollectId) {
        this.logger.info("/prepare-follower-admittance \u51c6\u5907\u968f\u5458\u51c6\u5165\uff0crobotId\uff1a{}\uff0chostCollectId\uff1a{}", (Object)robotId, (Object)hostCollectId);
        try {
            String redisKey = "follower:host:" + hostCollectId;
            CollectInResponse2 hostInfo = (CollectInResponse2)this.redisCache.getCacheObject(redisKey);
            if (hostInfo == null) {
                this.logger.error("\u51c6\u5907\u968f\u5458\u51c6\u5165\u5931\u8d25\uff0c\u672a\u627e\u5230\u4e3b\u65c5\u5ba2\u4fe1\u606f\uff0chostCollectId:{}", (Object)hostCollectId);
                return AjaxResult.error((String)"\u672a\u627e\u5230\u4e3b\u65c5\u5ba2\u4fe1\u606f\uff0c\u53ef\u80fd\u5df2\u8d85\u65f6\u6216\u4e3b\u65c5\u5ba2\u672a\u51c6\u5165");
            }
            String typeKey = "admittance:" + robotId + ":type";
            this.redisCache.setCacheObject(typeKey, (Object)"FOLLOWER", Integer.valueOf(5), TimeUnit.MINUTES);
            String hostCollectIdKey = "admittance:" + robotId + ":hostCollectId";
            this.redisCache.setCacheObject(hostCollectIdKey, (Object)hostCollectId, Integer.valueOf(5), TimeUnit.MINUTES);
            this.logger.info("\u51c6\u5907\u968f\u5458\u51c6\u5165\u6210\u529f\uff0crobotId:{}\uff0chostCollectId:{}", (Object)robotId, (Object)hostCollectId);
            return AjaxResult.success((String)"\u51c6\u5907\u6210\u529f", (Object)hostInfo);
        }
        catch (Exception e) {
            this.logger.error("\u51c6\u5907\u968f\u5458\u51c6\u5165\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            return AjaxResult.error((String)("\u51c6\u5907\u5931\u8d25\uff1a" + e.getMessage()));
        }
    }
}
