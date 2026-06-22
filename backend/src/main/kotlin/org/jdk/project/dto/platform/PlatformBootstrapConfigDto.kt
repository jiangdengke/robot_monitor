package org.jdk.project.dto.platform

data class PlatformBootstrapConfigDto(
    var id: Long? = null,
    var configKey: String? = null,
    var configJson: String? = null,
    var enabled: Boolean? = null,
    var remark: String? = null,
)
