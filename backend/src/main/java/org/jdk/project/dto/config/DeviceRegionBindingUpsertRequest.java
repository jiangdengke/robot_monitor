package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRegionBindingUpsertRequest {
  private Long deviceId;
  private Long regionId;
  private Long imageId;
  private String coordinate;
  private String remark;
}
