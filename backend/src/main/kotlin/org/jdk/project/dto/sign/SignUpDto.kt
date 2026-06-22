package org.jdk.project.dto.sign

import jakarta.validation.constraints.NotEmpty

data class SignUpDto(
    @field:NotEmpty
    var username: String? = null,
    @field:NotEmpty
    var password: String? = null,
)
