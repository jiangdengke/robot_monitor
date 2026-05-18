package org.jdk.project.dto.video;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VideoStreamDto {
  private String userId;
  private String mode;
  private String startTime;
  private Integer frameCount;
  private String lastFrameAt;
}
