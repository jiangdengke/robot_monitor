package org.jdk.project.dto.config

data class AreaDto(
    var id: Long? = null,
    var areaName: String? = null,
    var roomCode: String? = null,
    var deptName: String? = null,
    var coordinate: String? = null,
    var maxCapacity: Int? = null,
    var isGuide: String? = null,
    var isShow: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
    var configAreaDetailList: List<AreaDetailDto>? = null,
)
