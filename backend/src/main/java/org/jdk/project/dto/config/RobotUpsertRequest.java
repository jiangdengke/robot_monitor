package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RobotUpsertRequest {
  private Long loungeId;
  private Long regionId;
  private String robotId;
  private String robotName;
  private String mac;
  private String robotIp;
  private String robotType;
  private Integer batteryState;
  private String chargingState;
  private String workingState;
  private String standbyState;
  private String positioningState;
  private Integer enable;
  private String oriCoordinate;
  private Boolean adminMode;
  private String errorCode;
  private String errorMessage;
  private String remark;
}
