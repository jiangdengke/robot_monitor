package org.jdk.project.dto.sign

import java.time.OffsetDateTime

data class AuthUserDto(
    var id: Long? = null,
    var username: String? = null,
    var nickname: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var sex: String? = null,
    var avatarUrl: String? = null,
    var createTime: OffsetDateTime? = null,
    var updateTime: OffsetDateTime? = null,
    var password: String? = null,
    var enable: Boolean? = null,
    var remark: String? = null,
)
