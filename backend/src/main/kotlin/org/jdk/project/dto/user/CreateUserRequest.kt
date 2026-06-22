package org.jdk.project.dto.user

import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank
    var username: String? = null,
    @field:NotBlank
    var password: String? = null,
    var nickname: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var sex: String? = null,
    var avatarUrl: String? = null,
    var enable: Boolean? = null,
    var remark: String? = null,
)
