package org.jdk.project.dto.config

data class PointDto(
    var id: Long? = null,
    var siteId: Long? = null,
    var siteCode: String? = null,
    var siteName: String? = null,
    var areaId: Long? = null,
    var areaName: String? = null,
    var pointName: String? = null,
    var coordinate: String? = null,
    var maxCapacity: Int? = null,
    var isShow: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
