package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.TaskLogDto
import org.jdk.project.service.TaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/config/task-logs")
class TaskLogConfigController(
    private val taskService: TaskService,
) {
    @GetMapping
    fun listTaskLogs(): ListResponse<TaskLogDto> = taskService.listTaskLogs()
}
