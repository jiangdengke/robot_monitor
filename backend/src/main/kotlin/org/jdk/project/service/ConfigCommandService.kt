package org.jdk.project.service

import org.jdk.project.dto.config.AreaUpsertRequest
import org.jdk.project.dto.config.AudioUpsertRequest
import org.jdk.project.dto.config.ComplaintUpsertRequest
import org.jdk.project.dto.config.DeviceRegionBindingUpsertRequest
import org.jdk.project.dto.config.DeviceUpsertRequest
import org.jdk.project.dto.config.ImageUpsertRequest
import org.jdk.project.dto.config.LoungeUpsertRequest
import org.jdk.project.dto.config.RegionUpsertRequest
import org.jdk.project.dto.config.RobotUpsertRequest
import org.jdk.project.dto.config.TaskUpsertRequest
import org.jdk.project.service.config.AreaCommandService
import org.jdk.project.service.config.ComplaintCommandService
import org.jdk.project.service.config.DeviceCommandService
import org.jdk.project.service.config.LoungeCommandService
import org.jdk.project.service.config.MediaCommandService
import org.jdk.project.service.config.RobotCommandService
import org.jdk.project.service.config.TaskCommandService
import org.springframework.stereotype.Service

@Service
class ConfigCommandService(
    private val loungeCommandService: LoungeCommandService,
    private val areaCommandService: AreaCommandService,
    private val mediaCommandService: MediaCommandService,
    private val deviceCommandService: DeviceCommandService,
    private val robotCommandService: RobotCommandService,
    private val taskCommandService: TaskCommandService,
    private val complaintCommandService: ComplaintCommandService,
) {
    fun createLounge(request: LoungeUpsertRequest): Long? = loungeCommandService.create(request)
    fun updateLounge(id: Long, request: LoungeUpsertRequest) = loungeCommandService.update(id, request)
    fun deleteLounge(id: Long) = loungeCommandService.delete(id)
    fun createRegion(request: RegionUpsertRequest): Long? = areaCommandService.createRegion(request)
    fun updateRegion(id: Long, request: RegionUpsertRequest) = areaCommandService.updateRegion(id, request)
    fun deleteRegion(id: Long) = areaCommandService.deleteRegion(id)
    fun createArea(request: AreaUpsertRequest): Long? = areaCommandService.createArea(request)
    fun updateArea(id: Long, request: AreaUpsertRequest) = areaCommandService.updateArea(id, request)
    fun deleteArea(id: Long) = areaCommandService.deleteArea(id)
    fun createImage(request: ImageUpsertRequest): Long? = mediaCommandService.createImage(request)
    fun updateImage(id: Long, request: ImageUpsertRequest) = mediaCommandService.updateImage(id, request)
    fun deleteImage(id: Long) = mediaCommandService.deleteImage(id)
    fun createAudio(request: AudioUpsertRequest): Long? = mediaCommandService.createAudio(request)
    fun updateAudio(id: Long, request: AudioUpsertRequest) = mediaCommandService.updateAudio(id, request)
    fun deleteAudio(id: Long) = mediaCommandService.deleteAudio(id)
    fun createDevice(request: DeviceUpsertRequest): Long? = deviceCommandService.createDevice(request)
    fun updateDevice(id: Long, request: DeviceUpsertRequest) = deviceCommandService.updateDevice(id, request)
    fun deleteDevice(id: Long) = deviceCommandService.deleteDevice(id)
    fun saveDeviceRegionBinding(request: DeviceRegionBindingUpsertRequest) = deviceCommandService.saveDeviceRegionBinding(request)
    fun deleteDeviceRegionBinding(deviceId: Long, regionId: Long) = deviceCommandService.deleteDeviceRegionBinding(deviceId, regionId)
    fun createRobot(request: RobotUpsertRequest): Long? = robotCommandService.create(request)
    fun updateRobot(id: Long, request: RobotUpsertRequest) = robotCommandService.update(id, request)
    fun deleteRobot(id: Long) = robotCommandService.delete(id)
    fun createTask(request: TaskUpsertRequest): Long? = taskCommandService.create(request)
    fun updateTask(id: Long, request: TaskUpsertRequest) = taskCommandService.update(id, request)
    fun deleteTask(id: Long) = taskCommandService.delete(id)
    fun runTask(id: Long): Long? = taskCommandService.run(id)
    fun createComplaint(request: ComplaintUpsertRequest): Long? = complaintCommandService.create(request)
    fun updateComplaint(id: Long, request: ComplaintUpsertRequest) = complaintCommandService.update(id, request)
    fun deleteComplaint(id: Long) = complaintCommandService.delete(id)
}
