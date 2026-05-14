package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AudioUpsertRequest {
  private Long loungeId;
  private String audioKey;
  private String audioType;
  private String languageType;
  private String textInfo;
  private String audioValue;
  private String remark;
}
