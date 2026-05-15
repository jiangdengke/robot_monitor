package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUpsertRequest {
  private Long loungeId;
  private Long robotId;
  private String taskName;
  private Long commandCode;
  private String commandName;
  private String targetRegion;
  private String priority;
  private String executeType;
  private String executeDay;
  private String executeAt;
  private String taskType;
  private String taskSubtype;
  private String taskMode;
  private Boolean directExecution;
  private Boolean returnRequired;
  private Boolean enabled;
  private String remark;
}
