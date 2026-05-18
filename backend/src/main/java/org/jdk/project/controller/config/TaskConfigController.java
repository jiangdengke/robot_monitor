package org.jdk.project.controller.config;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.TaskDto;
import org.jdk.project.dto.config.TaskUpsertRequest;
import org.jdk.project.service.ConfigCommandService;
import org.jdk.project.service.ConfigQueryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config/tasks")
@RequiredArgsConstructor
public class TaskConfigController {

  private final ConfigCommandService configCommandService;
  private final ConfigQueryService configQueryService;

  @GetMapping
  public ListResponse<TaskDto> listTasks() {
    return configQueryService.listTaskTemplates();
  }

  @PostMapping
  public Long createTask(@RequestBody TaskUpsertRequest request) {
    return configCommandService.createTask(request);
  }

  @PutMapping("/{id}")
  public void updateTask(@PathVariable Long id, @RequestBody TaskUpsertRequest request) {
    configCommandService.updateTask(id, request);
  }

  @DeleteMapping("/{id}")
  public void deleteTask(@PathVariable Long id) {
    configCommandService.deleteTask(id);
  }

  @PostMapping("/{id}/run")
  public Long runTask(@PathVariable Long id) {
    return configCommandService.runTask(id);
  }
}
