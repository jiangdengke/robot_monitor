package org.jdk.project.dto.platform

data class PlatformBootstrapDto(
    var projectCode: String? = null,
    var projectName: String? = null,
    var customerName: String? = null,
    var systemTitle: String? = null,
    var brandTitle: String? = null,
    var logoUrl: String? = null,
    var templateCode: String? = null,
    var templateName: String? = null,
    var homePath: String? = null,
    var themeColor: String? = null,
    var modules: Map<String, Boolean>? = null,
    var terms: Map<String, String>? = null,
    var menus: List<PlatformMenuDto>? = null,
    var pages: Map<String, PlatformPageDto>? = null,
)
