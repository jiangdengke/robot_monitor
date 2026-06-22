package org.jdk.project.dto.statistics

data class PassengerRecordDto(
    var id: Long? = null,
    var roomCode: String? = null,
    var deptName: String? = null,
    var passengerName: String? = null,
    var flightNo: String? = null,
    var flightDate: String? = null,
    var cardProvider: String? = null,
    var cardNo: String? = null,
    var accessType: String? = null,
    var accessStatus: String? = null,
    var checkInAt: String? = null,
    var checkOutAt: String? = null,
    var regionName: String? = null,
    var cabin: String? = null,
    var seatNo: String? = null,
    var starLevel: String? = null,
    var originalImageUrl: String? = null,
)
