package org.jdk.project.dto.digitaltwin

data class DigitalTwinWarningDto(
    var id: Long? = null,
    var warningType: String? = null,
    var warningInfo: String? = null,
    var noticeType: String? = null,
    var resultStatus: String? = null,
    var isSuccess: String? = null,
    var createdAt: String? = null,
)
