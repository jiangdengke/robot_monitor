package org.jdk.project.dto.config

data class ComplaintDto(
    var id: Long? = null,
    var userName: String? = null,
    var roomCode: String? = null,
    var deptName: String? = null,
    var cardService: String? = null,
    var cardNo: String? = null,
    var complaintContent: String? = null,
    var complaintFeedback: String? = null,
    var createTime: String? = null,
)
