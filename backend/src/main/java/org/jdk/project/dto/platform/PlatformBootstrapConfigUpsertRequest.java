package org.jdk.project.dto.platform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformBootstrapConfigUpsertRequest {
  private String configKey;
  private String configJson;
  private Boolean enabled;
  private String remark;
}
