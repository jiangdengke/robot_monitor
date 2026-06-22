package org.jdk.project.dto.digitaltwin

data class DigitalTwinRobotDto(
    var id: Long? = null,
    var robotId: String? = null,
    var robotName: String? = null,
    var regionId: Long? = null,
    var regionName: String? = null,
    var roomCode: String? = null,
    var coordinate: String? = null,
    var workingState: String? = null,
    var batteryState: Int? = null,
)
