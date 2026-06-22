package org.jdk.project.dto.config

data class AudioDto(
    var id: Long? = null,
    var audioKey: String? = null,
    var audioType: String? = null,
    var languageType: String? = null,
    var textInfo: String? = null,
    var audioValue: String? = null,
    var roomCode: String? = null,
    var deptName: String? = null,
    var remark: String? = null,
)
