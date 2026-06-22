package org.jdk.project.controller

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.platform.PlatformBootstrapConfigDto
import org.jdk.project.dto.platform.PlatformBootstrapConfigUpsertRequest
import org.jdk.project.dto.platform.PlatformBootstrapDto
import org.jdk.project.service.platform.PlatformConfigService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/platform")
class PlatformController(
    private val platformConfigService: PlatformConfigService,
) {
    @GetMapping("/bootstrap")
    fun bootstrap(): PlatformBootstrapDto = platformConfigService.getBootstrap()

    @GetMapping("/bootstrap-configs")
    fun listBootstrapConfigs(): ListResponse<PlatformBootstrapConfigDto> =
        platformConfigService.listBootstrapConfigs()

    @PostMapping("/bootstrap-configs")
    fun saveBootstrapConfig(
        @RequestBody request: PlatformBootstrapConfigUpsertRequest,
    ): Long = platformConfigService.saveBootstrapConfig(request)
}
