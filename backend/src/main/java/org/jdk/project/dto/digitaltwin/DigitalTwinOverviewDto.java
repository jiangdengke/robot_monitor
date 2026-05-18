package org.jdk.project.dto.digitaltwin;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DigitalTwinOverviewDto {
  private List<DigitalTwinRobotDto> robotList;
  private List<DigitalTwinPassengerDto> passengerList;
  private List<DigitalTwinInspectionDto> inspectionList;
}
