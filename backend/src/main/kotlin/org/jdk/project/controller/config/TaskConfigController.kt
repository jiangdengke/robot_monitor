package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.TaskDto
import org.jdk.project.dto.config.TaskUpsertRequest
import org.jdk.project.service.ConfigCommandService
import org.jdk.project.service.ConfigQueryService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/config/tasks")
class TaskConfigController(
    private val configCommandService: ConfigCommandService,
    private val configQueryService: ConfigQueryService,
) {
    @GetMapping
    fun listTaskTemplates(): ListResponse<TaskDto> = configQueryService.listTaskTemplates()

    @PostMapping
    fun createTask(@RequestBody request: TaskUpsertRequest): Long? = configCommandService.createTask(request)

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody request: TaskUpsertRequest) {
        configCommandService.updateTask(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long) {
        configCommandService.deleteTask(id)
    }

    @PostMapping("/{id}/run")
    fun runTask(@PathVariable id: Long): Long? = configCommandService.runTask(id)
}
