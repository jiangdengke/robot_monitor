package org.jdk.project.dto.config;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AreaDto {
  private Long id;
  private String areaName;
  private String roomCode;
  private String deptName;
  private String coordinate;
  private Integer maxCapacity;
  private String isGuide;
  private String isShow;
  private Integer enable;
  private String remark;
  private List<AreaDetailDto> configAreaDetailList;
}
