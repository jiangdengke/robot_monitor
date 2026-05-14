package org.jdk.project.dto.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskDto {
  private Long id;
  private String taskName;
  private Long robotId;
  private String robotName;
  private String roomCode;
  private String deptName;
  private Long command;
  private String commandCn;
  private String priority;
  private String executeType;
  private String executeDay;
  private String executeTime;
  private String taskType;
  private String taskSubtype;
  private String taskMode;
  private String directExecution;
  private String isReturn;
  private Integer enable;
  private String remark;
}
