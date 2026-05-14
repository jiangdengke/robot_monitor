package org.jdk.project.dto.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComplaintDto {
  private Long id;
  private String userName;
  private String roomCode;
  private String deptName;
  private String cardService;
  private String cardNo;
  private String complaintContent;
  private String complaintFeedback;
  private String createTime;
}
