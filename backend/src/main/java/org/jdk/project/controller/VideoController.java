package org.jdk.project.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/video")
public class VideoController {

  private static final DateTimeFormatter DATETIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final Map<String, Map<String, Object>> activeStreams = new ConcurrentHashMap<>();

  @GetMapping("/active")
  public Map<String, Object> activeStreams() {
    return response("活跃视频流已加载");
  }

  @PostMapping("/start")
  public Map<String, Object> startStream(@RequestBody VideoStreamRequest request) {
    String robotId = trimToDefault(request.getRobotId(), "ROBOT-001");
    String userId = trimToDefault(request.getUserId(), "admin");
    String now = LocalDateTime.now().format(DATETIME_FORMATTER);
    Map<String, Object> stream = new LinkedHashMap<>();
    stream.put("userId", userId);
    stream.put("mode", "mock");
    stream.put("startTime", now);
    stream.put("frameCount", 0);
    stream.put("lastFrameAt", now);
    activeStreams.put(robotId, stream);
    return response("视频流已启动");
  }

  @PostMapping("/stop")
  public Map<String, Object> stopStream(@RequestBody VideoStreamRequest request) {
    String robotId = trimToDefault(request.getRobotId(), "");
    if (!robotId.isEmpty()) {
      activeStreams.remove(robotId);
    }
    return response("视频流已停止");
  }

  private Map<String, Object> response(String message) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("data", new LinkedHashMap<>(activeStreams));
    body.put("msg", message);
    return body;
  }

  private String trimToDefault(String value, String defaultValue) {
    if (value == null || value.trim().isEmpty()) {
      return defaultValue;
    }
    return value.trim();
  }

  @Getter
  @Setter
  public static class VideoStreamRequest {
    private String robotId;
    private String userId;
  }
}
