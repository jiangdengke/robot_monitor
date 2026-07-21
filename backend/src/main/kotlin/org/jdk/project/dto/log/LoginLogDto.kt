package org.jdk.project.dto.log

import java.time.OffsetDateTime

data class LoginLogDto(
    var id: Long? = null,
    var username: String? = null,
    var successFlag: Boolean? = null,
    var ipAddress: String? = null,
    var location: String? = null,
    var browser: String? = null,
    var os: String? = null,
    var message: String? = null,
    var createdAt: OffsetDateTime? = null,
)
