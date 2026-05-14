package org.jdk.project.dto.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AreaDetailDto {
  private Long id;
  private String languageType;
  private String areaName;
  private String label;
  private String arrText;
  private String remark;
}
