package org.jdk.project.dto.statistics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerStatisticsQuery {
  private String roomCode;
  private String flightDate;
  private String cardNo;
  private String accessType;
  private String status;
}
