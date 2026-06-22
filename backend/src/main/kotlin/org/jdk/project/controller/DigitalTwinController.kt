package org.jdk.project.controller

import org.jdk.project.dto.ApiResponse
import org.jdk.project.dto.digitaltwin.DigitalTwinActionRequest
import org.jdk.project.dto.digitaltwin.DigitalTwinOverviewDto
import org.jdk.project.dto.digitaltwin.DigitalTwinQueryRequest
import org.jdk.project.dto.digitaltwin.DigitalTwinRegionDto
import org.jdk.project.service.DigitalTwinService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/DigitalTwin")
class DigitalTwinController(
    private val digitalTwinService: DigitalTwinService,
) {
    @GetMapping("/selectRegionList")
    fun selectRegionList(@ModelAttribute query: DigitalTwinQueryRequest): ApiResponse<List<DigitalTwinRegionDto>> =
        digitalTwinService.selectRegionList(query)

    @GetMapping("/all", "/getAll")
    fun all(@ModelAttribute query: DigitalTwinQueryRequest): ApiResponse<DigitalTwinOverviewDto> =
        digitalTwinService.all(query)

    @GetMapping("/guide")
    fun guide(@ModelAttribute request: DigitalTwinActionRequest): ApiResponse<Void> =
        digitalTwinService.guide(request)

    @GetMapping("/interruptGuideTask")
    fun interruptGuideTask(@ModelAttribute request: DigitalTwinActionRequest): ApiResponse<Void> =
        digitalTwinService.interruptGuideTask(request)

    @PostMapping("/manualNotice")
    fun manualNotice(@ModelAttribute request: DigitalTwinActionRequest): ApiResponse<Void> =
        digitalTwinService.manualNotice(request)

    @GetMapping("/notifyCustomer")
    fun notifyCustomer(@ModelAttribute request: DigitalTwinActionRequest): ApiResponse<Void> =
        digitalTwinService.notifyCustomer(request)

    @PostMapping("/handleInspection")
    fun handleInspection(): ApiResponse<Void> = digitalTwinService.handleInspection()
}
