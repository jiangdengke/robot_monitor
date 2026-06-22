package org.jdk.project.service

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.AreaDto
import org.jdk.project.dto.config.AudioDto
import org.jdk.project.dto.config.ComplaintDto
import org.jdk.project.dto.config.DeviceDto
import org.jdk.project.dto.config.ImageDto
import org.jdk.project.dto.config.LoungeDto
import org.jdk.project.dto.config.RegionDto
import org.jdk.project.dto.config.RobotDto
import org.jdk.project.dto.config.TaskDto
import org.jdk.project.service.configquery.AreaConfigQueryService
import org.jdk.project.service.configquery.ComplaintConfigQueryService
import org.jdk.project.service.configquery.DeviceConfigQueryService
import org.jdk.project.service.configquery.LoungeConfigQueryService
import org.jdk.project.service.configquery.MediaConfigQueryService
import org.jdk.project.service.configquery.RobotConfigQueryService
import org.jdk.project.service.configquery.TaskConfigQueryService
import org.springframework.stereotype.Service

@Service
class ConfigQueryService(
    private val loungeQueryService: LoungeConfigQueryService,
    private val areaQueryService: AreaConfigQueryService,
    private val mediaQueryService: MediaConfigQueryService,
    private val deviceQueryService: DeviceConfigQueryService,
    private val robotQueryService: RobotConfigQueryService,
    private val taskQueryService: TaskConfigQueryService,
    private val complaintQueryService: ComplaintConfigQueryService,
) {
    fun listLounges(): ListResponse<LoungeDto> = loungeQueryService.listLounges()
    fun listRegions(): ListResponse<RegionDto> = areaQueryService.listRegions()
    fun listAreas(): ListResponse<AreaDto> = areaQueryService.listAreas()
    fun listImages(): ListResponse<ImageDto> = mediaQueryService.listImages()
    fun listAudios(category: String?): ListResponse<AudioDto> = mediaQueryService.listAudios(category)
    fun listDevices(): ListResponse<DeviceDto> = deviceQueryService.listDevices()
    fun listRobots(): ListResponse<RobotDto> = robotQueryService.listRobots()
    fun listTaskTemplates(): ListResponse<TaskDto> = taskQueryService.listTaskTemplates()
    fun listComplaints(): ListResponse<ComplaintDto> = complaintQueryService.listComplaints()
}
