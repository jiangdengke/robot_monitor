package org.jdk.project.dto.digitaltwin

data class DigitalTwinRegionDto(
    var id: Long? = null,
    var regionName: String? = null,
    var areaName: String? = null,
    var roomCode: String? = null,
    var deptName: String? = null,
    var coordinate: String? = null,
    var maxCapacity: Int? = null,
    var curCapacity: Int? = null,
)
