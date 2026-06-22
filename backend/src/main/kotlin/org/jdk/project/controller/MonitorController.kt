package org.jdk.project.controller

import org.jdk.project.dto.ListResponse
import org.jdk.project.service.MonitorService
import org.jooq.generated.project.tables.pojos.LoginLog
import org.jooq.generated.project.tables.pojos.OperationLog
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/monitor")
class MonitorController(
    private val monitorService: MonitorService,
) {
    @GetMapping("/login-logs")
    fun listLoginLogs(): ListResponse<LoginLog> = monitorService.listLoginLogs()

    @GetMapping("/operation-logs")
    fun listOperationLogs(): ListResponse<OperationLog> = monitorService.listOperationLogs()

    @DeleteMapping("/login-logs")
    fun clearLoginLogs() {
        monitorService.clearLoginLogs()
    }

    @DeleteMapping("/operation-logs")
    fun clearOperationLogs() {
        monitorService.clearOperationLogs()
    }
}
