package org.jdk.project.dto.digitaltwin

data class DigitalTwinOverviewDto(
    var robotList: List<DigitalTwinRobotDto>? = null,
    var passengerList: List<DigitalTwinPassengerDto>? = null,
    var inspectionList: List<DigitalTwinInspectionDto>? = null,
)
