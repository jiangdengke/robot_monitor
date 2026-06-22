package org.jdk.project.dto.config

data class RegionDto(
    var id: Long? = null,
    var regionName: String? = null,
    var areaId: Long? = null,
    var areaName: String? = null,
    var roomCode: String? = null,
    var deptName: String? = null,
    var coordinate: String? = null,
    var maxCapacity: Int? = null,
    var isGuide: String? = null,
    var isShow: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
