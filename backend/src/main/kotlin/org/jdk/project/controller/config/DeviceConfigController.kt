package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.DeviceDto
import org.jdk.project.dto.config.DeviceRegionBindingUpsertRequest
import org.jdk.project.dto.config.DeviceUpsertRequest
import org.jdk.project.service.ConfigCommandService
import org.jdk.project.service.ConfigQueryService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/config")
class DeviceConfigController(
    private val configCommandService: ConfigCommandService,
    private val configQueryService: ConfigQueryService,
) {
    @GetMapping("/devices")
    fun listDevices(): ListResponse<DeviceDto> = configQueryService.listDevices()

    @PostMapping("/devices")
    fun createDevice(@RequestBody request: DeviceUpsertRequest): Long? = configCommandService.createDevice(request)

    @PutMapping("/devices/{id}")
    fun updateDevice(@PathVariable id: Long, @RequestBody request: DeviceUpsertRequest) {
        configCommandService.updateDevice(id, request)
    }

    @DeleteMapping("/devices/{id}")
    fun deleteDevice(@PathVariable id: Long) {
        configCommandService.deleteDevice(id)
    }

    @PostMapping("/device-region-bindings")
    fun saveDeviceRegionBinding(@RequestBody request: DeviceRegionBindingUpsertRequest) {
        configCommandService.saveDeviceRegionBinding(request)
    }

    @DeleteMapping("/device-region-bindings")
    fun deleteDeviceRegionBinding(
        @RequestParam deviceId: Long,
        @RequestParam regionId: Long,
    ) {
        configCommandService.deleteDeviceRegionBinding(deviceId, regionId)
    }
}
