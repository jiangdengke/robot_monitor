package org.jdk.project.dto.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RobotDto {
  private Long id;
  private String robotId;
  private String robotName;
  private String mac;
  private String robotIp;
  private String roomCode;
  private String deptName;
  private Long regionId;
  private String regionName;
  private String robotType;
  private Integer batteryState;
  private String chargingState;
  private String workingState;
  private String standbyState;
  private String positioningState;
  private Integer enable;
  private String oriCoordinate;
  private Boolean adminMode;
  private String remark;
}
