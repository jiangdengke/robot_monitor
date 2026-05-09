package com.robotmonitor.web.controller.api;

import com.robotmonitor.common.core.domain.AjaxResult;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/video")
public class VideoStreamController {
    private final Map<String, StreamSession> activeStreams = new ConcurrentHashMap<>();

    @PostMapping("/start")
    public AjaxResult startVideoStream(@RequestBody Map<String, String> params) {
        String robotId = trim(params.get("robotId"));
        String userId = trim(params.get("userId"));
        if (robotId.isBlank() || userId.isBlank()) {
            return AjaxResult.error("参数缺失");
        }
        StreamSession previous = activeStreams.putIfAbsent(robotId, new StreamSession(robotId, userId, LocalDateTime.now(), "mock-stream"));
        if (previous != null) {
            return AjaxResult.error("视频流已在传输中");
        }
        AjaxResult result = AjaxResult.success("视频流已启动");
        result.put("data", activeRows());
        return result;
    }

    @PostMapping("/stop")
    public AjaxResult stopVideoStream(@RequestBody Map<String, String> params) {
        String robotId = trim(params.get("robotId"));
        if (robotId.isBlank()) {
            return AjaxResult.error("参数缺失");
        }
        activeStreams.remove(robotId);
        AjaxResult result = AjaxResult.success("视频流已停止");
        result.put("data", activeRows());
        return result;
    }

    @PostMapping("/frame")
    public AjaxResult receiveVideoFrame(@RequestHeader(value = "X-Robot-Id", required = false) String robotId, @RequestBody(required = false) byte[] frameData) {
        String id = trim(robotId);
        if (id.isBlank() || !activeStreams.containsKey(id)) {
            return AjaxResult.error("视频流未激活");
        }
        StreamSession session = activeStreams.get(id);
        session.lastFrameAt = LocalDateTime.now();
        session.frameCount++;
        AjaxResult result = AjaxResult.success("帧接收成功");
        result.put("data", Map.of(
            "robotId", id,
            "bytes", frameData == null ? 0 : frameData.length,
            "frameCount", session.frameCount,
            "lastFrameAt", session.lastFrameAt
        ));
        return result;
    }

    @GetMapping("/active")
    public AjaxResult getActiveStreams() {
        return AjaxResult.success(activeRows());
    }

    private Map<String, Object> activeRows() {
        Map<String, Object> rows = new LinkedHashMap<>();
        activeStreams.forEach((robotId, session) -> rows.put(robotId, session.toMap()));
        return rows;
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private static final class StreamSession {
        private final String robotId;
        private final String userId;
        private final LocalDateTime startTime;
        private final String mode;
        private LocalDateTime lastFrameAt;
        private long frameCount;

        private StreamSession(String robotId, String userId, LocalDateTime startTime, String mode) {
            this.robotId = robotId;
            this.userId = userId;
            this.startTime = startTime;
            this.mode = mode;
        }

        private Map<String, Object> toMap() {
            return Map.of(
                "robotId", robotId,
                "userId", userId,
                "startTime", startTime,
                "mode", mode,
                "lastFrameAt", lastFrameAt == null ? "" : lastFrameAt,
                "frameCount", frameCount
            );
        }
    }
}
