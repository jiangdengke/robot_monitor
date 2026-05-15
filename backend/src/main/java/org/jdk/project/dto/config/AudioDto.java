package org.jdk.project.dto.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AudioDto {
  private Long id;
  private String audioKey;
  private String audioType;
  private String languageType;
  private String textInfo;
  private String audioValue;
  private String roomCode;
  private String deptName;
  private String remark;
}
