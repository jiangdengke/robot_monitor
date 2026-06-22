package org.jdk.project.dto.statistics

data class PassengerStatisticsQuery(
    var roomCode: String? = null,
    var flightDate: String? = null,
    var cardNo: String? = null,
    var accessType: String? = null,
    var status: String? = null,
)
