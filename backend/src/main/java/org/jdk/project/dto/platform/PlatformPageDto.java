package org.jdk.project.dto.platform;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformPageDto {
  private String title;
  private String description;
  private List<Map<String, Object>> columns;
  private List<Map<String, Object>> searchFields;
  private List<Map<String, Object>> formFields;
  private Map<String, Object> defaults;
}
