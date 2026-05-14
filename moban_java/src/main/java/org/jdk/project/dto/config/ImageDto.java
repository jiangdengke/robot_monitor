package org.jdk.project.dto.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageDto {
  private Long id;
  private String imgName;
  private String imgType;
  private String roomCode;
  private String deptName;
  private Integer width;
  private Integer height;
  private Integer enable;
  private String remark;
}
