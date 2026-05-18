package org.jdk.project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ApiResponse;
import org.jdk.project.dto.digitaltwin.DigitalTwinActionRequest;
import org.jdk.project.dto.digitaltwin.DigitalTwinOverviewDto;
import org.jdk.project.dto.digitaltwin.DigitalTwinQueryRequest;
import org.jdk.project.dto.digitaltwin.DigitalTwinRegionDto;
import org.jdk.project.service.digitaltwin.DigitalTwinCommandService;
import org.jdk.project.service.digitaltwin.DigitalTwinQueryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DigitalTwinService {

  private final DigitalTwinQueryService queryService;
  private final DigitalTwinCommandService commandService;

  public ApiResponse<List<DigitalTwinRegionDto>> selectRegionList(DigitalTwinQueryRequest query) {
    return ApiResponse.ok(queryService.listRegions(query.getRoomCode()), "区域点位已加载");
  }

  public ApiResponse<DigitalTwinOverviewDto> all(DigitalTwinQueryRequest query) {
    String roomCode = query.getRoomCode();
    DigitalTwinOverviewDto data =
        DigitalTwinOverviewDto.builder()
            .robotList(queryService.listRobots(roomCode))
            .passengerList(queryService.listPassengers(roomCode))
            .inspectionList(queryService.listInspections(roomCode))
            .build();
    return ApiResponse.ok(data, "数字孪生数据已加载");
  }

  public ApiResponse<Void> guide(DigitalTwinActionRequest request) {
    commandService.createGuideTask(request);
    return ApiResponse.ok(null, "区域引导任务已提交");
  }

  public ApiResponse<Void> interruptGuideTask(DigitalTwinActionRequest request) {
    commandService.interruptGuideTask(request);
    return ApiResponse.ok(null, "机器人任务已停止");
  }

  public ApiResponse<Void> manualNotice(DigitalTwinActionRequest request) {
    commandService.saveManualNotice(request);
    return ApiResponse.ok(null, "人工提醒已完成");
  }

  public ApiResponse<Void> notifyCustomer(DigitalTwinActionRequest request) {
    commandService.saveRobotNotice(request);
    return ApiResponse.ok(null, "机器人提醒任务已提交");
  }

  public ApiResponse<Void> handleInspection() {
    return ApiResponse.ok(null, "巡检异常已处理");
  }
}
