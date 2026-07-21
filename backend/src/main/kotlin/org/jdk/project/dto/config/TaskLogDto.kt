package org.jdk.project.dto.config

data class TaskLogDto(
    var id: Long? = null,
    var robotId: Long? = null,
    var robotName: String? = null,
    var taskTemplateId: Long? = null,
    var taskName: String? = null,
    var taskType: String? = null,
    var taskSubtype: String? = null,
    var taskMode: String? = null,
    var taskStatus: String? = null,
    var directExecution: Boolean? = null,
    var commandPayload: String? = null,
    var returnPayload: String? = null,
    var createdAt: String? = null,
    var startedAt: String? = null,
    var finishedAt: String? = null,
)
