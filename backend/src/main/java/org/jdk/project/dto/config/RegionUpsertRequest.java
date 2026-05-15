package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionUpsertRequest {
  private Long areaId;
  private Long loungeId;
  private String regionName;
  private String coordinate;
  private Integer maxCapacity;
  private String isGuide;
  private String isShow;
  private Integer enable;
  private String remark;
}
