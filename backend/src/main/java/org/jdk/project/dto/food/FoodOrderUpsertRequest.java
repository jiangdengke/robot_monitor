package org.jdk.project.dto.food;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodOrderUpsertRequest {
  private Long loungeId;
  private Long diningTableId;
  private String orderCode;
  private String deskNo;
  private String cardNo;
  private java.math.BigDecimal totalAmount;
  private String remark;
  private List<FoodOrderItemUpsertRequest> items;
}
