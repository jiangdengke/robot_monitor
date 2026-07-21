package org.jdk.project.controller.config

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.DeviceDto
import org.jdk.project.dto.config.DevicePointBindingDto
import org.jdk.project.dto.config.DevicePointBindingUpsertRequest
import org.jdk.project.dto.config.DeviceUpsertRequest
import org.jdk.project.service.DeviceService
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
    private val deviceService: DeviceService,
) {
    @GetMapping("/devices")
    fun listDevices(): ListResponse<DeviceDto> = deviceService.listDevices()

    @PostMapping("/devices")
    fun createDevice(
        @RequestBody request: DeviceUpsertRequest,
    ): Long? = deviceService.createDevice(request)

    @PutMapping("/devices/{id}")
    fun updateDevice(
        @PathVariable id: Long,
        @RequestBody request: DeviceUpsertRequest,
    ) {
        deviceService.updateDevice(id, request)
    }

    @DeleteMapping("/devices/{id}")
    fun deleteDevice(
        @PathVariable id: Long,
    ) {
        deviceService.deleteDevice(id)
    }

    @GetMapping("/device-point-bindings")
    fun listDevicePointBindings(
        @RequestParam(required = false) deviceId: Long?,
        @RequestParam(required = false) pointId: Long?,
    ): ListResponse<DevicePointBindingDto> = deviceService.listDevicePointBindings(deviceId, pointId)

    @PostMapping("/device-point-bindings")
    fun saveDevicePointBinding(
        @RequestBody request: DevicePointBindingUpsertRequest,
    ) {
        deviceService.saveDevicePointBinding(request)
    }

    @DeleteMapping("/device-point-bindings")
    fun deleteDevicePointBinding(
        @RequestParam deviceId: Long,
        @RequestParam pointId: Long,
    ) {
        deviceService.deleteDevicePointBinding(deviceId, pointId)
    }
}
