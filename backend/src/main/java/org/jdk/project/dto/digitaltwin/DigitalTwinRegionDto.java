package org.jdk.project.dto.digitaltwin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DigitalTwinRegionDto {
  private Long id;
  private String regionName;
  private String areaName;
  private String roomCode;
  private String deptName;
  private String coordinate;
  private Integer maxCapacity;
  private Integer curCapacity;
}
