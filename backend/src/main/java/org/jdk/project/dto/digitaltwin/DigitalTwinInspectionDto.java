package org.jdk.project.dto.digitaltwin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DigitalTwinInspectionDto {
  private Long id;
  private Long inspTaskId;
  private String robotId;
  private String areaName;
  private String roomCode;
  private String abnormal;
  private String abnormalInfo;
  private String coordinate;
}
