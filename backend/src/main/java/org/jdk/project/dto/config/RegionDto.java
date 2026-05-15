package org.jdk.project.dto.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionDto {
  private Long id;
  private String regionName;
  private Long areaId;
  private String areaName;
  private String roomCode;
  private String deptName;
  private String coordinate;
  private Integer maxCapacity;
  private String isGuide;
  private String isShow;
  private Integer enable;
  private String remark;
}
