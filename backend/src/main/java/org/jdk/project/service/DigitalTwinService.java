package org.jdk.project.service;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jdk.project.service.digitaltwin.DigitalTwinCommandService;
import org.jdk.project.service.digitaltwin.DigitalTwinQueryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DigitalTwinService {

  private final DigitalTwinQueryService queryService;
  private final DigitalTwinCommandService commandService;

  public Map<String, Object> selectRegionList(Map<String, String> query) {
    return response(queryService.listRegions(query.get("roomCode")), "区域点位已加载");
  }

  public Map<String, Object> all(Map<String, String> query) {
    String roomCode = query.get("roomCode");
    Map<String, Object> data = new LinkedHashMap<>();
    data.put("robotList", queryService.listRobots(roomCode));
    data.put("passengerList", queryService.listPassengers(roomCode));
    data.put("inspectionList", queryService.listInspections(roomCode));
    return response(data, "数字孪生数据已加载");
  }

  public Map<String, Object> guide(Map<String, String> query) {
    commandService.createGuideTask(query);
    return response(null, "区域引导任务已提交");
  }

  public Map<String, Object> interruptGuideTask(Map<String, String> query) {
    commandService.interruptGuideTask(query);
    return response(null, "机器人任务已停止");
  }

  public Map<String, Object> manualNotice(Map<String, String> query) {
    commandService.saveManualNotice(query);
    return response(null, "人工提醒已完成");
  }

  public Map<String, Object> notifyCustomer(Map<String, String> query) {
    commandService.saveRobotNotice(query);
    return response(null, "机器人提醒任务已提交");
  }

  public Map<String, Object> handleInspection() {
    return response(null, "巡检异常已处理");
  }

  private Map<String, Object> response(Object data, String message) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("code", 200);
    body.put("msg", message);
    body.put("data", data);
    return body;
  }
}
