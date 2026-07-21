package org.jdk.project.dto.config

data class DeviceDto(
    var id: Long? = null,
    var siteId: Long? = null,
    var siteCode: String? = null,
    var siteName: String? = null,
    var deviceName: String? = null,
    var deviceType: String? = null,
    var deepGlintDeviceId: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
