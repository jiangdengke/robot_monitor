package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.AreaDto
import org.jdk.project.dto.config.AreaUpsertRequest
import org.jdk.project.dto.config.RegionDto
import org.jdk.project.dto.config.RegionUpsertRequest
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
@RequestMapping("/config")
class AreaConfigController(
    private val configCommandService: ConfigCommandService,
    private val configQueryService: ConfigQueryService,
) {
    @GetMapping("/regions")
    fun listRegions(): ListResponse<RegionDto> = configQueryService.listRegions()

    @PostMapping("/regions")
    fun createRegion(@RequestBody request: RegionUpsertRequest): Long? = configCommandService.createRegion(request)

    @PutMapping("/regions/{id}")
    fun updateRegion(@PathVariable id: Long, @RequestBody request: RegionUpsertRequest) {
        configCommandService.updateRegion(id, request)
    }

    @DeleteMapping("/regions/{id}")
    fun deleteRegion(@PathVariable id: Long) {
        configCommandService.deleteRegion(id)
    }

    @GetMapping("/areas")
    fun listAreas(): ListResponse<AreaDto> = configQueryService.listAreas()

    @PostMapping("/areas")
    fun createArea(@RequestBody request: AreaUpsertRequest): Long? = configCommandService.createArea(request)

    @PutMapping("/areas/{id}")
    fun updateArea(@PathVariable id: Long, @RequestBody request: AreaUpsertRequest) {
        configCommandService.updateArea(id, request)
    }

    @DeleteMapping("/areas/{id}")
    fun deleteArea(@PathVariable id: Long) {
        configCommandService.deleteArea(id)
    }
}
