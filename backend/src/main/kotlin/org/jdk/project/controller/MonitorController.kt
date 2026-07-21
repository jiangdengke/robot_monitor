package org.jdk.project.controller

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.log.LoginLogDto
import org.jdk.project.dto.log.OperationLogDto
import org.jdk.project.service.LogService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/monitor")
class MonitorController(
    private val logService: LogService,
) {
    @GetMapping("/login-logs")
    fun listLoginLogs(): ListResponse<LoginLogDto> = logService.listLoginLogs()

    @GetMapping("/operation-logs")
    fun listOperationLogs(): ListResponse<OperationLogDto> = logService.listOperationLogs()

    @DeleteMapping("/login-logs")
    fun clearLoginLogs() {
        logService.clearLoginLogs()
    }

    @DeleteMapping("/operation-logs")
    fun clearOperationLogs() {
        logService.clearOperationLogs()
    }
}
