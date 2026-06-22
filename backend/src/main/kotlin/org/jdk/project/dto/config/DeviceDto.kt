package org.jdk.project.dto.config

data class DeviceDto(
    var id: Long? = null,
    var deviceName: String? = null,
    var deviceType: String? = null,
    var deepGlintDeviceId: String? = null,
    var roomCode: String? = null,
    var deptName: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
