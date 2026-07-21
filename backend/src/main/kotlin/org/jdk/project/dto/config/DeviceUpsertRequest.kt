package org.jdk.project.dto.config

data class DeviceUpsertRequest(
    var siteId: Long? = null,
    var deviceName: String? = null,
    var deviceType: String? = null,
    var deepGlintDeviceId: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
