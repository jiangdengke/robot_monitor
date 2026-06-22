package org.jdk.project.dto.config

data class ImageDto(
    var id: Long? = null,
    var imgName: String? = null,
    var imgType: String? = null,
    var roomCode: String? = null,
    var deptName: String? = null,
    var width: Int? = null,
    var height: Int? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
