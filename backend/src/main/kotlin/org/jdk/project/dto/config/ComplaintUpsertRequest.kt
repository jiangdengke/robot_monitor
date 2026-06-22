package org.jdk.project.dto.config

data class ComplaintUpsertRequest(
    var loungeId: Long? = null,
    var userName: String? = null,
    var cardService: String? = null,
    var cardNo: String? = null,
    var complaintContent: String? = null,
    var complaintFeedback: String? = null,
)
