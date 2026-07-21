package org.jdk.project.dto.config

data class AreaUpsertRequest(
    var siteId: Long? = null,
    var areaName: String? = null,
    var coordinate: String? = null,
    var maxCapacity: Int? = null,
    var isShow: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
