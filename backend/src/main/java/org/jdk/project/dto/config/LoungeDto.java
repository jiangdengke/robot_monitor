package org.jdk.project.dto.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoungeDto {
  private Long id;
  private String roomCode;
  private String deptName;
  private String terminal;
  private String locationDesc;
  private Boolean enabled;
  private String remark;
}
