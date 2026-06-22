package org.jdk.project.dto.platform

data class PlatformMenuDto(
    var title: String? = null,
    var path: String? = null,
    var icon: String? = null,
    var module: String? = null,
    var permission: String? = null,
    var enabled: Boolean? = null,
    var pluginPage: Boolean? = null,
    var sort: Int? = null,
    var children: List<PlatformMenuDto>? = null,
)
