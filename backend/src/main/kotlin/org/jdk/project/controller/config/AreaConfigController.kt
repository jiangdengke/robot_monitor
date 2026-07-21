package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.AreaDto
import org.jdk.project.dto.config.AreaUpsertRequest
import org.jdk.project.dto.config.PointDto
import org.jdk.project.dto.config.PointUpsertRequest
import org.jdk.project.service.SpaceService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/config")
class AreaConfigController(
    private val spaceService: SpaceService,
) {
    @GetMapping("/points")
    fun listPoints(): ListResponse<PointDto> = spaceService.listPoints()

    @PostMapping("/points")
    fun createPoint(
        @RequestBody request: PointUpsertRequest,
    ): Long? = spaceService.createPoint(request)

    @PutMapping("/points/{id}")
    fun updatePoint(
        @PathVariable id: Long,
        @RequestBody request: PointUpsertRequest,
    ) {
        spaceService.updatePoint(id, request)
    }

    @DeleteMapping("/points/{id}")
    fun deletePoint(
        @PathVariable id: Long,
    ) {
        spaceService.deletePoint(id)
    }

    @GetMapping("/areas")
    fun listAreas(): ListResponse<AreaDto> = spaceService.listAreas()

    @PostMapping("/areas")
    fun createArea(
        @RequestBody request: AreaUpsertRequest,
    ): Long? = spaceService.createArea(request)

    @PutMapping("/areas/{id}")
    fun updateArea(
        @PathVariable id: Long,
        @RequestBody request: AreaUpsertRequest,
    ) {
        spaceService.updateArea(id, request)
    }

    @DeleteMapping("/areas/{id}")
    fun deleteArea(
        @PathVariable id: Long,
    ) {
        spaceService.deleteArea(id)
    }
}
