package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableUpsertRequest {
  private Long loungeId;
  private Long regionId;
  private Long deviceId;
  private String tableNo;
  private String cameraCoordinates;
  private String status;
  private String isEnable;
  private String remark;
}
