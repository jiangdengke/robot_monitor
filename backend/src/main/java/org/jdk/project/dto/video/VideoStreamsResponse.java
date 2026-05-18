package org.jdk.project.dto.video;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VideoStreamsResponse {
  private Map<String, VideoStreamDto> data;
  private String msg;
}
