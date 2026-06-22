package org.jdk.project.dto.digitaltwin

data class DigitalTwinInspectionDto(
    var id: Long? = null,
    var inspTaskId: Long? = null,
    var robotId: String? = null,
    var areaName: String? = null,
    var roomCode: String? = null,
    var abnormal: String? = null,
    var abnormalInfo: String? = null,
    var coordinate: String? = null,
)
