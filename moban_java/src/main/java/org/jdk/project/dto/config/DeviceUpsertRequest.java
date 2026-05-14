package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceUpsertRequest {
  private Long loungeId;
  private String deviceName;
  private String deviceType;
  private String deepGlintDeviceId;
  private Integer enable;
  private String remark;
}
