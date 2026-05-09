/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.bot.service.RobotCmdService
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.robot.RobotVideoStreamCmd
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.messaging.simp.SimpMessagingTemplate
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.bot.service.RobotCmdService;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.robot.RobotVideoStreamCmd;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/video"})
public class VideoStreamController {
    private static final Logger log = LoggerFactory.getLogger(VideoStreamController.class);
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RobotCmdService robotCmdService;
    private final Map<String, String> activeStreams = new ConcurrentHashMap<String, String>();

    @PostMapping(value={"/start"})
    public AjaxResult startVideoStream(@RequestBody Map<String, String> params) {
        String robotId = params.get("robotId");
        String userId = params.get("userId");
        if (robotId == null || userId == null) {
            return AjaxResult.error((String)"\u53c2\u6570\u7f3a\u5931");
        }
        if (this.activeStreams.containsKey(robotId)) {
            return AjaxResult.error((String)"\u89c6\u9891\u6d41\u5df2\u5728\u4f20\u8f93\u4e2d");
        }
        RobotVideoStreamCmd robotVideoStreamCmd = new RobotVideoStreamCmd(robotId, "1");
        this.robotCmdService.sendVideoStreamCmd(robotVideoStreamCmd);
        this.activeStreams.put(robotId, userId);
        log.info("\u89c6\u9891\u6d41\u5f00\u59cb: robotId={}, userId={}", (Object)robotId, (Object)userId);
        return AjaxResult.success((String)"\u89c6\u9891\u6d41\u5f00\u59cb");
    }

    @PostMapping(value={"/stop"})
    public AjaxResult stopVideoStream(@RequestBody Map<String, String> params) {
        String robotId = params.get("robotId");
        if (robotId == null) {
            return AjaxResult.error((String)"\u53c2\u6570\u7f3a\u5931");
        }
        String userId = this.activeStreams.remove(robotId);
        if (userId != null) {
            RobotVideoStreamCmd robotVideoStreamCmd = new RobotVideoStreamCmd(robotId, "0");
            this.robotCmdService.sendVideoStreamCmd(robotVideoStreamCmd);
            this.messagingTemplate.convertAndSendToUser(userId, "/queue/videoStop", Map.of("robotId", robotId, "message", "\u89c6\u9891\u6d41\u5df2\u505c\u6b62"));
            log.info("\u89c6\u9891\u6d41\u505c\u6b62: robotId={}, userId={}", (Object)robotId, (Object)userId);
        }
        return AjaxResult.success((String)"\u89c6\u9891\u6d41\u505c\u6b62");
    }

    @PostMapping(value={"/frame"})
    public AjaxResult receiveVideoFrame(@RequestHeader(value="X-Robot-Id") String robotId, @RequestBody byte[] frameData) {
        String userId = this.activeStreams.get(robotId);
        if (userId == null) {
            return AjaxResult.error((String)"\u89c6\u9891\u6d41\u672a\u6fc0\u6d3b");
        }
        try {
            String base64Image = Base64.getEncoder().encodeToString(frameData);
            this.messagingTemplate.convertAndSendToUser(userId, "/queue/videoFrame", (Object)base64Image);
            return AjaxResult.success((String)"\u5e27\u63a5\u6536\u6210\u529f");
        }
        catch (Exception e) {
            log.error("\u8f6c\u53d1\u89c6\u9891\u5e27\u5931\u8d25: robotId={}, error={}", new Object[]{robotId, e.getMessage(), e});
            return AjaxResult.error((String)"\u8f6c\u53d1\u5931\u8d25");
        }
    }

    private Map<String, Object> createHeaders(MessageType messageType) {
        HashMap<String, Object> headers = new HashMap<String, Object>();
        if (messageType == MessageType.BINARY) {
            headers.put("content-type", "application/octet-stream");
        }
        return headers;
    }

    @GetMapping(value={"/active"})
    public AjaxResult getActiveStreams() {
        return AjaxResult.success(this.activeStreams);
    }

    static enum MessageType {
        TEXT,
        BINARY;

    }
}
