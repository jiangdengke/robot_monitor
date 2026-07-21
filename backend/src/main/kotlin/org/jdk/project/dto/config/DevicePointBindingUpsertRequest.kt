package org.jdk.project.dto.config

data class DevicePointBindingUpsertRequest(
    var deviceId: Long? = null,
    var pointId: Long? = null,
    var coordinate: String? = null,
    var remark: String? = null,
)
