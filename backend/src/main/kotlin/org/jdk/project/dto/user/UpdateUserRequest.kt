package org.jdk.project.dto.user

import jakarta.validation.constraints.NotNull

data class UpdateUserRequest(
    @field:NotNull
    var id: Long? = null,
    var nickname: String? = null,
    var password: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var sex: String? = null,
    var avatarUrl: String? = null,
    var enable: Boolean? = null,
    var remark: String? = null,
)
