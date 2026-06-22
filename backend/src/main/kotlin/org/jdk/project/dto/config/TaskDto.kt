package org.jdk.project.dto.config

data class TaskDto(
    var id: Long? = null,
    var taskName: String? = null,
    var robotId: Long? = null,
    var robotName: String? = null,
    var roomCode: String? = null,
    var deptName: String? = null,
    var command: Long? = null,
    var commandCn: String? = null,
    var priority: String? = null,
    var executeType: String? = null,
    var executeDay: String? = null,
    var executeTime: String? = null,
    var taskType: String? = null,
    var taskSubtype: String? = null,
    var taskMode: String? = null,
    var directExecution: String? = null,
    var isReturn: String? = null,
    var enable: Int? = null,
    var remark: String? = null,
)
