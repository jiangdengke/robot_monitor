package org.jdk.project.dto.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageUpsertRequest {
  private Long loungeId;
  private String imgName;
  private String imgType;
  private String img;
  private Integer width;
  private Integer height;
  private Integer enable;
  private String remark;
}
