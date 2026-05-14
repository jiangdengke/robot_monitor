package org.jdk.project.dto.food;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodDailyUpsertRequest {
  private Long loungeId;
  private String foodDate;
  private Long foodItemId;
  private Boolean enabled;
}
