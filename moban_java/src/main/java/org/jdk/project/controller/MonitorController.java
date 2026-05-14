package org.jdk.project.controller;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jooq.generated.project.tables.pojos.KnowledgeBase;
import org.jooq.generated.project.tables.pojos.LoginLog;
import org.jooq.generated.project.tables.pojos.OperationLog;
import org.jdk.project.dto.knowledge.KnowledgeUpsertRequest;
import org.jdk.project.service.MonitorService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/knowledge")
  public ListResponse<KnowledgeBase> listKnowledge() {
    return monitorService.listKnowledge();
  }

  @PostMapping("/knowledge")
  public Long createKnowledge(@RequestBody KnowledgeUpsertRequest request) {
    return monitorService.createKnowledge(request);
  }

  @PutMapping("/knowledge/{id}")
  public void updateKnowledge(@PathVariable Long id, @RequestBody KnowledgeUpsertRequest request) {
    monitorService.updateKnowledge(id, request);
  }

  @DeleteMapping("/knowledge/{id}")
  public void deleteKnowledge(@PathVariable Long id) {
    monitorService.deleteKnowledge(id);
  }
}
