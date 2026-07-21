package org.jdk.project.dto.log

import java.time.OffsetDateTime

data class OperationLogDto(
    var id: Long? = null,
    var moduleName: String? = null,
    var businessType: Int? = null,
    var methodName: String? = null,
    var requestMethod: String? = null,
    var operatorName: String? = null,
    var requestUrl: String? = null,
    var ipAddress: String? = null,
    var location: String? = null,
    var requestPayload: String? = null,
    var responsePayload: String? = null,
    var successFlag: Boolean? = null,
    var errorMessage: String? = null,
    var costMillis: Long? = null,
    var createdAt: OffsetDateTime? = null,
)
