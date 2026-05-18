package org.jdk.project.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;
import org.jdk.project.dto.video.VideoStreamDto;
import org.jdk.project.dto.video.VideoStreamsResponse;
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

  private final Map<String, VideoStreamDto> activeStreams = new ConcurrentHashMap<>();

  @GetMapping("/active")
  public VideoStreamsResponse activeStreams() {
    return response("活跃视频流已加载");
  }

  @PostMapping("/start")
  public VideoStreamsResponse startStream(@RequestBody VideoStreamRequest request) {
    String robotId = trimToDefault(request.getRobotId(), "ROBOT-001");
    String userId = trimToDefault(request.getUserId(), "admin");
    String now = LocalDateTime.now().format(DATETIME_FORMATTER);
    VideoStreamDto stream =
        VideoStreamDto.builder()
            .userId(userId)
            .mode("mock")
            .startTime(now)
            .frameCount(0)
            .lastFrameAt(now)
            .build();
    activeStreams.put(robotId, stream);
    return response("视频流已启动");
  }

  @PostMapping("/stop")
  public VideoStreamsResponse stopStream(@RequestBody VideoStreamRequest request) {
    String robotId = trimToDefault(request.getRobotId(), "");
    if (robotId.isEmpty()) {
      activeStreams.clear();
      return response("全部视频流已停止");
    }
    if (!robotId.isEmpty()) {
      activeStreams.remove(robotId);
    }
    return response("视频流已停止");
  }

  private VideoStreamsResponse response(String message) {
    return VideoStreamsResponse.builder()
        .data(new LinkedHashMap<>(activeStreams))
        .msg(message)
        .build();
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
