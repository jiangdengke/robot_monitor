package org.jdk.project.dto.digitaltwin;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DigitalTwinPassengerDto {
  private Long id;
  private String userName;
  private String cardNo;
  private String flightNo;
  private String flightId;
  private String estmTakeOffTime;
  private String latestOffStatus;
  private Long regionId;
  private String roomCode;
  private String coordinate;
  private String memLevel;
  private List<DigitalTwinWarningDto> warningLogList;
}
