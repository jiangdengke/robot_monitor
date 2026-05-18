package org.jdk.project.dto.digitaltwin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DigitalTwinWarningDto {
  private Long id;
  private String warningType;
  private String warningInfo;
  private String noticeType;
  private String resultStatus;
  private String isSuccess;
  private String createdAt;
}
