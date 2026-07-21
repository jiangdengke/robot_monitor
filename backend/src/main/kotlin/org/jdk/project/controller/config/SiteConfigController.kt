package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.SiteDto
import org.jdk.project.dto.config.SiteUpsertRequest
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
@RequestMapping("/config/sites")
class SiteConfigController(
    private val spaceService: SpaceService,
) {
    @GetMapping
    fun listSites(): ListResponse<SiteDto> = spaceService.listSites()

    @PostMapping
    fun createSite(
        @RequestBody request: SiteUpsertRequest,
    ): Long? = spaceService.createSite(request)

    @PutMapping("/{id}")
    fun updateSite(
        @PathVariable id: Long,
        @RequestBody request: SiteUpsertRequest,
    ) {
        spaceService.updateSite(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteSite(
        @PathVariable id: Long,
    ) {
        spaceService.deleteSite(id)
    }
}
