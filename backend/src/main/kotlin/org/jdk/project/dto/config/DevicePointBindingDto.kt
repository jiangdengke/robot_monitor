package org.jdk.project.dto.config

data class DevicePointBindingDto(
    var deviceId: Long? = null,
    var deviceName: String? = null,
    var pointId: Long? = null,
    var pointName: String? = null,
    var coordinate: String? = null,
    var remark: String? = null,
)
