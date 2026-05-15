package org.jdk.project.dto.food;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodItemUpsertRequest {
  private Long loungeId;
  private String name;
  private String category;
  private String imgIds;
  private java.math.BigDecimal price;
  private Integer calorie;
  private String remark;
}
