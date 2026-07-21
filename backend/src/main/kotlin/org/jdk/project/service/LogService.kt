package org.jdk.project.service

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.log.LoginLogDto
import org.jdk.project.dto.log.OperationLogDto
import org.jdk.project.repository.LogRepository
import org.jdk.project.repository.LoginLogRow
import org.jdk.project.repository.OperationLogRow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LogService(
    private val logRepository: LogRepository,
) {
    fun listLoginLogs(): ListResponse<LoginLogDto> {
        val rows = logRepository.findAllLoginLogs().map(::toLoginLogDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    fun listOperationLogs(): ListResponse<OperationLogDto> {
        val rows = logRepository.findAllOperationLogs().map(::toOperationLogDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    @Transactional
    fun clearLoginLogs() {
        logRepository.deleteAllLoginLogs()
    }

    @Transactional
    fun clearOperationLogs() {
        logRepository.deleteAllOperationLogs()
    }

    private fun toLoginLogDto(row: LoginLogRow): LoginLogDto =
        LoginLogDto(
            id = row.id,
            username = row.username,
            successFlag = row.successFlag,
            ipAddress = row.ipAddress,
            location = row.location,
            browser = row.browser,
            os = row.os,
            message = row.message,
            createdAt = row.createdAt,
        )

    private fun toOperationLogDto(row: OperationLogRow): OperationLogDto =
        OperationLogDto(
            id = row.id,
            moduleName = row.moduleName,
            businessType = row.businessType,
            methodName = row.methodName,
            requestMethod = row.requestMethod,
            operatorName = row.operatorName,
            requestUrl = row.requestUrl,
            ipAddress = row.ipAddress,
            location = row.location,
            requestPayload = row.requestPayload,
            responsePayload = row.responsePayload,
            successFlag = row.successFlag,
            errorMessage = row.errorMessage,
            costMillis = row.costMillis,
            createdAt = row.createdAt,
        )
}
