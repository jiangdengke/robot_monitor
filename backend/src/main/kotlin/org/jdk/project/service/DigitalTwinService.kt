package org.jdk.project.service

import org.jdk.project.dto.ApiResponse
import org.jdk.project.dto.digitaltwin.DigitalTwinActionRequest
import org.jdk.project.dto.digitaltwin.DigitalTwinOverviewDto
import org.jdk.project.dto.digitaltwin.DigitalTwinQueryRequest
import org.jdk.project.dto.digitaltwin.DigitalTwinRegionDto
import org.jdk.project.service.digitaltwin.DigitalTwinCommandService
import org.jdk.project.service.digitaltwin.DigitalTwinQueryService
import org.springframework.stereotype.Service

@Service
class DigitalTwinService(
    private val queryService: DigitalTwinQueryService,
    private val commandService: DigitalTwinCommandService,
) {
    fun selectRegionList(query: DigitalTwinQueryRequest): ApiResponse<List<DigitalTwinRegionDto>> =
        ApiResponse.ok(queryService.listRegions(query.roomCode), "区域点位已加载")

    fun all(query: DigitalTwinQueryRequest): ApiResponse<DigitalTwinOverviewDto> {
        val roomCode = query.roomCode
        val data =
            DigitalTwinOverviewDto(
                robotList = queryService.listRobots(roomCode),
                passengerList = queryService.listPassengers(roomCode),
                inspectionList = queryService.listInspections(roomCode),
            )
        return ApiResponse.ok(data, "数字孪生数据已加载")
    }

    fun guide(request: DigitalTwinActionRequest): ApiResponse<Void> {
        commandService.createGuideTask(request)
        return ApiResponse.ok(null, "区域引导任务已提交")
    }

    fun interruptGuideTask(request: DigitalTwinActionRequest): ApiResponse<Void> {
        commandService.interruptGuideTask(request)
        return ApiResponse.ok(null, "机器人任务已停止")
    }

    fun manualNotice(request: DigitalTwinActionRequest): ApiResponse<Void> {
        commandService.saveManualNotice(request)
        return ApiResponse.ok(null, "人工提醒已完成")
    }

    fun notifyCustomer(request: DigitalTwinActionRequest): ApiResponse<Void> {
        commandService.saveRobotNotice(request)
        return ApiResponse.ok(null, "机器人提醒任务已提交")
    }

    fun handleInspection(): ApiResponse<Void> = ApiResponse.ok(null, "巡检异常已处理")
}
