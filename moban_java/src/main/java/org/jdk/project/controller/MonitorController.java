package org.jdk.project.controller;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jooq.generated.project.tables.pojos.LoginLog;
import org.jooq.generated.project.tables.pojos.OperationLog;
import org.jdk.project.service.MonitorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor")
@RequiredArgsConstructor
public class MonitorController {

  private final MonitorService monitorService;

  @GetMapping("/login-logs")
  public ListResponse<LoginLog> listLoginLogs() {
    return monitorService.listLoginLogs();
  }

  @GetMapping("/operation-logs")
  public ListResponse<OperationLog> listOperationLogs() {
    return monitorService.listOperationLogs();
  }
}
