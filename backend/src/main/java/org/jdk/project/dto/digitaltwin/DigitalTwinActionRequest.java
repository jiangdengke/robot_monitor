package org.jdk.project.dto.digitaltwin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DigitalTwinActionRequest {
  private String robotId;
  private Long regionId;
  private Long areaId;
  private String coordinate;
  private Long warningId;
  private Long passengerId;
  private String warningInfo;
  private String warningType;
}
