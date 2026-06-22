package org.jdk.project.dto.platform

data class PlatformBootstrapConfigUpsertRequest(
    var configKey: String? = null,
    var configJson: String? = null,
    var enabled: Boolean? = null,
    var remark: String? = null,
)
