package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.RobotDto
import org.jdk.project.dto.config.RobotUpsertRequest
import org.jdk.project.service.RobotService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/config/robots")
class RobotConfigController(
    private val robotService: RobotService,
) {
    @GetMapping
    fun listRobots(): ListResponse<RobotDto> = robotService.listRobots()

    @PostMapping
    fun createRobot(
        @RequestBody request: RobotUpsertRequest,
    ): Long? = robotService.createRobot(request)

    @PutMapping("/{id}")
    fun updateRobot(
        @PathVariable id: Long,
        @RequestBody request: RobotUpsertRequest,
    ) {
        robotService.updateRobot(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteRobot(
        @PathVariable id: Long,
    ) {
        robotService.deleteRobot(id)
    }
}
