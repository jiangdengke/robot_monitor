package org.jdk.project.dto.digitaltwin

data class DigitalTwinActionRequest(
    var robotId: String? = null,
    var regionId: Long? = null,
    var areaId: Long? = null,
    var coordinate: String? = null,
    var warningId: Long? = null,
    var passengerId: Long? = null,
    var warningInfo: String? = null,
    var warningType: String? = null,
)
