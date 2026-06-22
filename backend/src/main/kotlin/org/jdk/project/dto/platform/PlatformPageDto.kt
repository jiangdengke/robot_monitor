package org.jdk.project.dto.platform

data class PlatformPageDto(
    var title: String? = null,
    var description: String? = null,
    var columns: List<Map<String, Any?>>? = null,
    var searchFields: List<Map<String, Any?>>? = null,
    var formFields: List<Map<String, Any?>>? = null,
    var defaults: Map<String, Any?>? = null,
)
