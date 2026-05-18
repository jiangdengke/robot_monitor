package org.jdk.project.dto.statistics;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InquiryStatDto {
  private Long id;
  private String deptName;
  private String robotName;
  private String passengerName;
  private String topic;
  private String robotResponse;
  private String channel;
  private String createdAt;
}
