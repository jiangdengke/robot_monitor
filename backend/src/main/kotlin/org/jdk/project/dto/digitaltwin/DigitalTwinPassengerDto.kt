package org.jdk.project.dto.digitaltwin

data class DigitalTwinPassengerDto(
    var id: Long? = null,
    var userName: String? = null,
    var cardNo: String? = null,
    var flightNo: String? = null,
    var flightId: String? = null,
    var estmTakeOffTime: String? = null,
    var latestOffStatus: String? = null,
    var regionId: Long? = null,
    var roomCode: String? = null,
    var coordinate: String? = null,
    var memLevel: String? = null,
    var warningLogList: List<DigitalTwinWarningDto>? = null,
)
