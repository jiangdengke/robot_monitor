package org.jdk.project.dto.platform;

import java.util.List;
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
public class PlatformMenuDto {
  private String title;
  private String path;
  private String icon;
  private String module;
  private String permission;
  private Boolean enabled;
  private Boolean pluginPage;
  private Integer sort;
  private List<PlatformMenuDto> children;
}
