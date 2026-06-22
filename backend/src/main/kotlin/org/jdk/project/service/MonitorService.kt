package org.jdk.project.service

import org.jdk.project.dto.ListResponse
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.LOGIN_LOG
import org.jooq.generated.project.Tables.OPERATION_LOG
import org.jooq.generated.project.tables.pojos.LoginLog
import org.jooq.generated.project.tables.pojos.OperationLog
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MonitorService(
    private val dsl: DSLContext,
) {
    fun listLoginLogs(): ListResponse<LoginLog> {
        val rows = dsl.selectFrom(LOGIN_LOG).orderBy(LOGIN_LOG.ID.desc()).fetchInto(LoginLog::class.java)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    fun listOperationLogs(): ListResponse<OperationLog> {
        val rows = dsl.selectFrom(OPERATION_LOG).orderBy(OPERATION_LOG.ID.desc()).fetchInto(OperationLog::class.java)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    @Transactional
    fun clearLoginLogs() {
        dsl.deleteFrom(LOGIN_LOG).execute()
    }

    @Transactional
    fun clearOperationLogs() {
        dsl.deleteFrom(OPERATION_LOG).execute()
    }
}
