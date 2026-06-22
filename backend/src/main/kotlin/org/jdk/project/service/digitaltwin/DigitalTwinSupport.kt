package org.jdk.project.service.digitaltwin

object DigitalTwinSupport {
    @JvmStatic
    fun trimToNull(value: String?): String? = value?.trim()?.takeIf { it.isNotEmpty() }

    @JvmStatic
    fun firstNonBlank(vararg values: String?): String? = values.firstNotNullOfOrNull { trimToNull(it) }

    @JvmStatic
    fun parseLong(value: String?): Long? = trimToNull(value)?.toLongOrNull()
}
