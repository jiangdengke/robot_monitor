package org.jdk.project.dto.food;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodOrderItemUpsertRequest {
  private Long foodItemId;
  private String foodName;
  private Integer quantity;
  private java.math.BigDecimal unitPrice;
}
