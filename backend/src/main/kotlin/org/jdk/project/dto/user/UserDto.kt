package org.jdk.project.dto.user

data class UserDto(
    var id: Long? = null,
    var username: String? = null,
    var nickname: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var sex: String? = null,
    var avatarUrl: String? = null,
    var enable: Boolean? = null,
    var remark: String? = null,
    var createTime: String? = null,
    var updateTime: String? = null,
)
