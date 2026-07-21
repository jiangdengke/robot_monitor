package org.jdk.project.service

import org.jdk.project.exception.BusinessException

internal object ServiceSupport {
    fun requireId(
        value: Long?,
        message: String,
    ): Long = value ?: throw BusinessException(message)

    fun defaultInt(
        value: Int?,
        fallback: Int,
    ): Int = value ?: fallback

    fun defaultString(
        value: String?,
        fallback: String,
    ): String = value ?: fallback

    fun ensureUpdated(
        updatedRows: Int,
        message: String,
    ) {
        if (updatedRows == 0) {
            throw BusinessException(message)
        }
    }
}
