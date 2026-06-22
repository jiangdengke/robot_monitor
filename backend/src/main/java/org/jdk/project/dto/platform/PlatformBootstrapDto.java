package org.jdk.project.dto.platform;

import java.util.List;
import java.util.Map;
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
public class PlatformBootstrapDto {
  private String projectCode;
  private String projectName;
  private String customerName;
  private String systemTitle;
  private String brandTitle;
  private String logoUrl;
  private String templateCode;
  private String templateName;
  private String homePath;
  private String themeColor;
  private Map<String, Boolean> modules;
  private Map<String, String> terms;
  private List<PlatformMenuDto> menus;
  private Map<String, PlatformPageDto> pages;
}
