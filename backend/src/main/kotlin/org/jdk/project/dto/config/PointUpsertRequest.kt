package org.jdk.project.dto.config

data class PointUpsertRequest(
    var siteId: Long? = null,
    var areaId: Long? = null,
    var pointName: String? = null,
    var coordinate: String? = null,
    var maxCapacity: Int? = null,
    var isShow: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
