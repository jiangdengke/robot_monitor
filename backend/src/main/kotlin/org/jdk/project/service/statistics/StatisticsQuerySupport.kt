package org.jdk.project.service.statistics

object StatisticsQuerySupport {
    @JvmStatic
    fun trimToNull(value: String?): String? = value?.trim()?.takeIf { it.isNotEmpty() }

    @JvmStatic
    fun normalizeAccessType(value: String?): String? =
        when (value) {
            null -> null
            "1" -> "ID_CARD"
            "2" -> "QRCODE"
            "3" -> "FACE"
            else -> value
        }

    @JvmStatic
    fun normalizePassengerStatus(value: String?): String? =
        when (value) {
            null -> null
            "1" -> "IN"
            "0" -> "OUT"
            else -> value
        }
}
