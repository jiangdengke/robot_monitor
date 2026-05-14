package org.jdk.project.controller;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.AreaDto;
import org.jdk.project.dto.config.AudioDto;
import org.jdk.project.dto.config.ComplaintDto;
import org.jdk.project.dto.config.DeviceDto;
import org.jdk.project.dto.config.ImageDto;
import org.jdk.project.dto.config.LoungeDto;
import org.jdk.project.dto.config.RegionDto;
import org.jdk.project.dto.config.RobotDto;
import org.jdk.project.dto.config.TableDto;
import org.jdk.project.dto.config.TaskDto;
import org.jdk.project.service.ConfigQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigController {

  private final ConfigQueryService configQueryService;

  @GetMapping("/lounges")
  public ListResponse<LoungeDto> listLounges() {
    return configQueryService.listLounges();
  }

  @GetMapping("/regions")
  public ListResponse<RegionDto> listRegions() {
    return configQueryService.listRegions();
  }

  @GetMapping("/areas")
  public ListResponse<AreaDto> listAreas() {
    return configQueryService.listAreas();
  }

  @GetMapping("/images")
  public ListResponse<ImageDto> listImages() {
    return configQueryService.listImages();
  }

  @GetMapping("/audios")
  public ListResponse<AudioDto> listAudios() {
    return configQueryService.listAudios("COMMON");
  }

  @GetMapping("/robot-audios")
  public ListResponse<AudioDto> listRobotAudios() {
    return configQueryService.listAudios("ROBOT");
  }

  @GetMapping("/devices")
  public ListResponse<DeviceDto> listDevices() {
    return configQueryService.listDevices();
  }

  @GetMapping("/tables")
  public ListResponse<TableDto> listTables() {
    return configQueryService.listTables();
  }

  @GetMapping("/robots")
  public ListResponse<RobotDto> listRobots() {
    return configQueryService.listRobots();
  }

  @GetMapping("/tasks")
  public ListResponse<TaskDto> listTasks() {
    return configQueryService.listTaskTemplates();
  }

  @GetMapping("/complaints")
  public ListResponse<ComplaintDto> listComplaints() {
    return configQueryService.listComplaints();
  }
}
