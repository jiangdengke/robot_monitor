package org.jdk.project.dto.config

data class SiteUpsertRequest(
    var siteCode: String? = null,
    var siteName: String? = null,
    var locationDesc: String? = null,
    var enabled: Boolean? = null,
    var remark: String? = null,
)
