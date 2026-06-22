package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.LoungeDto
import org.jdk.project.dto.config.LoungeUpsertRequest
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
@RequestMapping("/config/lounges")
class LoungeConfigController(
    private val configCommandService: ConfigCommandService,
    private val configQueryService: ConfigQueryService,
) {
    @GetMapping
    fun listLounges(): ListResponse<LoungeDto> = configQueryService.listLounges()

    @PostMapping
    fun createLounge(@RequestBody request: LoungeUpsertRequest): Long? = configCommandService.createLounge(request)

    @PutMapping("/{id}")
    fun updateLounge(@PathVariable id: Long, @RequestBody request: LoungeUpsertRequest) {
        configCommandService.updateLounge(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteLounge(@PathVariable id: Long) {
        configCommandService.deleteLounge(id)
    }
}
