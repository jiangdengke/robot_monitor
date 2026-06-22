package org.jdk.project.dto.platform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformBootstrapConfigDto {
  private Long id;
  private String configKey;
  private String configJson;
  private Boolean enabled;
  private String remark;
}
