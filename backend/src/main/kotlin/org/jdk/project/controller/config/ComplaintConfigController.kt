package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.ComplaintDto
import org.jdk.project.dto.config.ComplaintUpsertRequest
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
@RequestMapping("/config/complaints")
class ComplaintConfigController(
    private val configCommandService: ConfigCommandService,
    private val configQueryService: ConfigQueryService,
) {
    @GetMapping
    fun listComplaints(): ListResponse<ComplaintDto> = configQueryService.listComplaints()

    @PostMapping
    fun createComplaint(@RequestBody request: ComplaintUpsertRequest): Long? = configCommandService.createComplaint(request)

    @PutMapping("/{id}")
    fun updateComplaint(@PathVariable id: Long, @RequestBody request: ComplaintUpsertRequest) {
        configCommandService.updateComplaint(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteComplaint(@PathVariable id: Long) {
        configCommandService.deleteComplaint(id)
    }
}
