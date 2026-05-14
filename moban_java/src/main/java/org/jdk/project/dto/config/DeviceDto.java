package org.jdk.project.dto.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeviceDto {
  private Long id;
  private String deviceName;
  private String deviceType;
  private String deepGlintDeviceId;
  private String roomCode;
  private String deptName;
  private Integer enable;
  private String remark;
}
