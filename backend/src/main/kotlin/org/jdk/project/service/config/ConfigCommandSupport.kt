package org.jdk.project.service.config

import org.jdk.project.exception.BusinessException

internal object ConfigCommandSupport {
    @JvmStatic
    fun requiredId(value: Long?, message: String): Long = value ?: throw BusinessException(message)

    @JvmStatic
    fun defaultInt(value: Int?, fallback: Int): Int = value ?: fallback

    @JvmStatic
    fun defaultString(value: String?, fallback: String): String = value ?: fallback

    @JvmStatic
    fun ensureUpdated(updated: Int, message: String) {
        if (updated == 0) {
            throw BusinessException(message)
        }
    }
}
