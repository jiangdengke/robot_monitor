package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoungeUpsertRequest {
  private String roomCode;
  private String deptName;
  private String terminal;
  private String locationDesc;
  private Boolean enabled;
  private String remark;
}
