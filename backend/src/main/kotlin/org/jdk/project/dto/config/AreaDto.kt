package org.jdk.project.dto.config

data class AreaDto(
    var id: Long? = null,
    var siteId: Long? = null,
    var siteCode: String? = null,
    var siteName: String? = null,
    var areaName: String? = null,
    var coordinate: String? = null,
    var maxCapacity: Int? = null,
    var isShow: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
