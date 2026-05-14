package org.jdk.project.dto.knowledge;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeUpsertRequest {
  private String title;
  private String content;
  private String source;
  private String knowledgeType;
  private String processStatus;
  private Boolean enabled;
  private String vectorRef;
  private Long createdBy;
  private String remark;
}
