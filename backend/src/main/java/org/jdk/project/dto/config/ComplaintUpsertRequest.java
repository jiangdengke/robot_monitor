package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintUpsertRequest {
  private Long loungeId;
  private String userName;
  private String cardService;
  private String cardNo;
  private String complaintContent;
  private String complaintFeedback;
}
