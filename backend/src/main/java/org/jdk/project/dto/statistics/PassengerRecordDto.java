package org.jdk.project.dto.statistics;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PassengerRecordDto {
  private Long id;
  private String roomCode;
  private String deptName;
  private String passengerName;
  private String flightNo;
  private String flightDate;
  private String cardProvider;
  private String cardNo;
  private String accessType;
  private String accessStatus;
  private String checkInAt;
  private String checkOutAt;
  private String regionName;
  private String cabin;
  private String seatNo;
  private String starLevel;
  private String originalImageUrl;
}
