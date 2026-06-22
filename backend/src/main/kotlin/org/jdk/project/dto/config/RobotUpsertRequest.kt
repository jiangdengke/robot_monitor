package org.jdk.project.dto.config

data class RobotUpsertRequest(
    var loungeId: Long? = null,
    var regionId: Long? = null,
    var robotId: String? = null,
    var robotName: String? = null,
    var mac: String? = null,
    var robotIp: String? = null,
    var robotType: String? = null,
    var batteryState: Int? = null,
    var chargingState: String? = null,
    var workingState: String? = null,
    var standbyState: String? = null,
    var positioningState: String? = null,
    var enable: Int? = null,
    var oriCoordinate: String? = null,
    var adminMode: Boolean? = null,
    var errorCode: String? = null,
    var errorMessage: String? = null,
    var remark: String? = null,
)
