package org.jdk.project.dto.config

data class DeviceRegionBindingUpsertRequest(
    var deviceId: Long? = null,
    var regionId: Long? = null,
    var imageId: Long? = null,
    var coordinate: String? = null,
    var remark: String? = null,
)
