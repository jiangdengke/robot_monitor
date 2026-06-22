package org.jdk.project.dto.user

data class UpdateProfileRequest(
    var nickname: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var sex: String? = null,
)
