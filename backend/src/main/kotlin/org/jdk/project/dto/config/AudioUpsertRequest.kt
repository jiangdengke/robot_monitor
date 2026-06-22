package org.jdk.project.dto.config

data class AudioUpsertRequest(
    var loungeId: Long? = null,
    var audioKey: String? = null,
    var audioType: String? = null,
    var languageType: String? = null,
    var textInfo: String? = null,
    var audioValue: String? = null,
    var remark: String? = null,
)
