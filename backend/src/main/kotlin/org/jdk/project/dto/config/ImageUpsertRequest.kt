package org.jdk.project.dto.config

data class ImageUpsertRequest(
    var loungeId: Long? = null,
    var imgName: String? = null,
    var imgType: String? = null,
    var img: String? = null,
    var width: Int? = null,
    var height: Int? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
