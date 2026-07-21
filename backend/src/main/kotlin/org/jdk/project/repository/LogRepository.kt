package org.jdk.project.repository

import org.jooq.DSLContext
import org.jooq.generated.project.Tables.LOGIN_LOG
import org.jooq.generated.project.Tables.OPERATION_LOG
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class LogRepository(
    private val dsl: DSLContext,
) {
    fun findAllLoginLogs(): List<LoginLogRow> =
        dsl
            .selectFrom(LOGIN_LOG)
            .orderBy(LOGIN_LOG.ID.desc())
            .fetch { record ->
                LoginLogRow(
                    id = record.id,
                    username = record.username,
                    successFlag = record.successFlag,
                    ipAddress = record.ipAddress,
                    location = record.location,
                    browser = record.browser,
                    os = record.os,
                    message = record.message,
                    createdAt = record.createdAt,
                )
            }

    fun findAllOperationLogs(): List<OperationLogRow> =
        dsl
            .selectFrom(OPERATION_LOG)
            .orderBy(OPERATION_LOG.ID.desc())
            .fetch { record ->
                OperationLogRow(
                    id = record.id,
                    moduleName = record.moduleName,
                    businessType = record.businessType,
                    methodName = record.methodName,
                    requestMethod = record.requestMethod,
                    operatorName = record.operatorName,
                    requestUrl = record.requestUrl,
                    ipAddress = record.ipAddress,
                    location = record.location,
                    requestPayload = record.requestPayload,
                    responsePayload = record.responsePayload,
                    successFlag = record.successFlag,
                    errorMessage = record.errorMessage,
                    costMillis = record.costMillis,
                    createdAt = record.createdAt,
                )
            }

    fun deleteAllLoginLogs(): Int = dsl.deleteFrom(LOGIN_LOG).execute()

    fun deleteAllOperationLogs(): Int = dsl.deleteFrom(OPERATION_LOG).execute()
}

data class LoginLogRow(
    val id: Long?,
    val username: String?,
    val successFlag: Boolean?,
    val ipAddress: String?,
    val location: String?,
    val browser: String?,
    val os: String?,
    val message: String?,
    val createdAt: OffsetDateTime?,
)

data class OperationLogRow(
    val id: Long?,
    val moduleName: String?,
    val businessType: Int?,
    val methodName: String?,
    val requestMethod: String?,
    val operatorName: String?,
    val requestUrl: String?,
    val ipAddress: String?,
    val location: String?,
    val requestPayload: String?,
    val responsePayload: String?,
    val successFlag: Boolean?,
    val errorMessage: String?,
    val costMillis: Long?,
    val createdAt: OffsetDateTime?,
)
