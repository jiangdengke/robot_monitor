package org.jdk.project.dto.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaUpsertRequest {
  private Long loungeId;
  private String areaName;
  private String coordinate;
  private Integer maxCapacity;
  private String isGuide;
  private String isShow;
  private Integer enable;
  private String remark;
  private List<AreaDetailUpsertRequest> configAreaDetailList;
}
