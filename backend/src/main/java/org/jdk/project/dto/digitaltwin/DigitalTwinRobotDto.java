package org.jdk.project.dto.digitaltwin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DigitalTwinRobotDto {
  private Long id;
  private String robotId;
  private String robotName;
  private Long regionId;
  private String regionName;
  private String roomCode;
  private String coordinate;
  private String workingState;
  private Integer batteryState;
}
