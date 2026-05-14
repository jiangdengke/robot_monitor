package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaDetailUpsertRequest {
  private String languageType;
  private String areaName;
  private String label;
  private String arrText;
  private String remark;
}
