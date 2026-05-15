package org.jdk.project.dto.food;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodPlanUpsertRequest {
  private Long loungeId;
  private String startDate;
  private String endDate;
  private List<Long> foodItemIds;
}
