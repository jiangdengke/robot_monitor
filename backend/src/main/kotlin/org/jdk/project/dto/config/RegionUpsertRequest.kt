package org.jdk.project.dto.config

data class RegionUpsertRequest(
    var areaId: Long? = null,
    var loungeId: Long? = null,
    var regionName: String? = null,
    var coordinate: String? = null,
    var maxCapacity: Int? = null,
    var isGuide: String? = null,
    var isShow: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
