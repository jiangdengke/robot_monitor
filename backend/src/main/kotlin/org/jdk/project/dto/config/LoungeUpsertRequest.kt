package org.jdk.project.dto.config

data class LoungeUpsertRequest(
    var roomCode: String? = null,
    var deptName: String? = null,
    var terminal: String? = null,
    var locationDesc: String? = null,
    var enabled: Boolean? = null,
    var remark: String? = null,
)
