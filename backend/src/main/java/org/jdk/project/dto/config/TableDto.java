package org.jdk.project.dto.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TableDto {
  private Long id;
  private String tableNo;
  private String roomCode;
  private String deptName;
  private Long regionId;
  private String regionName;
  private Long deviceId;
  private String cameraCoordinates;
  private String status;
  private String isEnable;
  private String remark;
}
