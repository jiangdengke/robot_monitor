package org.jdk.project.dto.statistics;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GuideLogDto {
  private Long id;
  private String deptName;
  private String robotName;
  private String passengerName;
  private String regionName;
  private String resultStatus;
  private String coordinate;
  private String createdAt;
}
