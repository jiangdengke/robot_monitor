package org.jdk.project.controller.config;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.RobotDto;
import org.jdk.project.dto.config.RobotUpsertRequest;
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
@RequestMapping("/config/robots")
@RequiredArgsConstructor
public class RobotConfigController {

  private final ConfigCommandService configCommandService;
  private final ConfigQueryService configQueryService;

  @GetMapping
  public ListResponse<RobotDto> listRobots() {
    return configQueryService.listRobots();
  }

  @PostMapping
  public Long createRobot(@RequestBody RobotUpsertRequest request) {
    return configCommandService.createRobot(request);
  }

  @PutMapping("/{id}")
  public void updateRobot(@PathVariable Long id, @RequestBody RobotUpsertRequest request) {
    configCommandService.updateRobot(id, request);
  }

  @DeleteMapping("/{id}")
  public void deleteRobot(@PathVariable Long id) {
    configCommandService.deleteRobot(id);
  }
}
