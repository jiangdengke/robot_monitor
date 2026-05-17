package org.jdk.project.controller;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.AreaDto;
import org.jdk.project.dto.config.AreaUpsertRequest;
import org.jdk.project.dto.config.AudioDto;
import org.jdk.project.dto.config.AudioUpsertRequest;
import org.jdk.project.dto.config.ComplaintDto;
import org.jdk.project.dto.config.ComplaintUpsertRequest;
import org.jdk.project.dto.config.DeviceDto;
import org.jdk.project.dto.config.DeviceRegionBindingUpsertRequest;
import org.jdk.project.dto.config.DeviceUpsertRequest;
import org.jdk.project.dto.config.ImageDto;
import org.jdk.project.dto.config.ImageUpsertRequest;
import org.jdk.project.dto.config.LoungeDto;
import org.jdk.project.dto.config.LoungeUpsertRequest;
import org.jdk.project.dto.config.RegionDto;
import org.jdk.project.dto.config.RegionUpsertRequest;
import org.jdk.project.dto.config.RobotDto;
import org.jdk.project.dto.config.RobotUpsertRequest;
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
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigController {

  private final ConfigCommandService configCommandService;
  private final ConfigQueryService configQueryService;

  @GetMapping("/lounges")
  public ListResponse<LoungeDto> listLounges() {
    return configQueryService.listLounges();
  }

  @PostMapping("/lounges")
  public Long createLounge(@RequestBody LoungeUpsertRequest request) {
    return configCommandService.createLounge(request);
  }

  @PutMapping("/lounges/{id}")
  public void updateLounge(@PathVariable Long id, @RequestBody LoungeUpsertRequest request) {
    configCommandService.updateLounge(id, request);
  }

  @DeleteMapping("/lounges/{id}")
  public void deleteLounge(@PathVariable Long id) {
    configCommandService.deleteLounge(id);
  }

  @GetMapping("/regions")
  public ListResponse<RegionDto> listRegions() {
    return configQueryService.listRegions();
  }

  @PostMapping("/regions")
  public Long createRegion(@RequestBody RegionUpsertRequest request) {
    return configCommandService.createRegion(request);
  }

  @PutMapping("/regions/{id}")
  public void updateRegion(@PathVariable Long id, @RequestBody RegionUpsertRequest request) {
    configCommandService.updateRegion(id, request);
  }

  @DeleteMapping("/regions/{id}")
  public void deleteRegion(@PathVariable Long id) {
    configCommandService.deleteRegion(id);
  }

  @GetMapping("/areas")
  public ListResponse<AreaDto> listAreas() {
    return configQueryService.listAreas();
  }

  @PostMapping("/areas")
  public Long createArea(@RequestBody AreaUpsertRequest request) {
    return configCommandService.createArea(request);
  }

  @PutMapping("/areas/{id}")
  public void updateArea(@PathVariable Long id, @RequestBody AreaUpsertRequest request) {
    configCommandService.updateArea(id, request);
  }

  @DeleteMapping("/areas/{id}")
  public void deleteArea(@PathVariable Long id) {
    configCommandService.deleteArea(id);
  }

  @GetMapping("/images")
  public ListResponse<ImageDto> listImages() {
    return configQueryService.listImages();
  }

  @PostMapping("/images")
  public Long createImage(@RequestBody ImageUpsertRequest request) {
    return configCommandService.createImage(request);
  }

  @PutMapping("/images/{id}")
  public void updateImage(@PathVariable Long id, @RequestBody ImageUpsertRequest request) {
    configCommandService.updateImage(id, request);
  }

  @DeleteMapping("/images/{id}")
  public void deleteImage(@PathVariable Long id) {
    configCommandService.deleteImage(id);
  }

  @GetMapping("/audios")
  public ListResponse<AudioDto> listAudios() {
    return configQueryService.listAudios("COMMON");
  }

  @PostMapping("/audios")
  public Long createAudio(@RequestBody AudioUpsertRequest request) {
    return configCommandService.createAudio(request);
  }

  @PutMapping("/audios/{id}")
  public void updateAudio(@PathVariable Long id, @RequestBody AudioUpsertRequest request) {
    configCommandService.updateAudio(id, request);
  }

  @DeleteMapping("/audios/{id}")
  public void deleteAudio(@PathVariable Long id) {
    configCommandService.deleteAudio(id);
  }

  @GetMapping("/robot-audios")
  public ListResponse<AudioDto> listRobotAudios() {
    return configQueryService.listAudios("ROBOT");
  }

  @GetMapping("/devices")
  public ListResponse<DeviceDto> listDevices() {
    return configQueryService.listDevices();
  }

  @PostMapping("/devices")
  public Long createDevice(@RequestBody DeviceUpsertRequest request) {
    return configCommandService.createDevice(request);
  }

  @PutMapping("/devices/{id}")
  public void updateDevice(@PathVariable Long id, @RequestBody DeviceUpsertRequest request) {
    configCommandService.updateDevice(id, request);
  }

  @DeleteMapping("/devices/{id}")
  public void deleteDevice(@PathVariable Long id) {
    configCommandService.deleteDevice(id);
  }

  @PostMapping("/device-region-bindings")
  public void saveDeviceRegionBinding(@RequestBody DeviceRegionBindingUpsertRequest request) {
    configCommandService.saveDeviceRegionBinding(request);
  }

  @DeleteMapping("/device-region-bindings/{deviceId}/{regionId}")
  public void deleteDeviceRegionBinding(@PathVariable Long deviceId, @PathVariable Long regionId) {
    configCommandService.deleteDeviceRegionBinding(deviceId, regionId);
  }

  @GetMapping("/robots")
  public ListResponse<RobotDto> listRobots() {
    return configQueryService.listRobots();
  }

  @PostMapping("/robots")
  public Long createRobot(@RequestBody RobotUpsertRequest request) {
    return configCommandService.createRobot(request);
  }

  @PutMapping("/robots/{id}")
  public void updateRobot(@PathVariable Long id, @RequestBody RobotUpsertRequest request) {
    configCommandService.updateRobot(id, request);
  }

  @DeleteMapping("/robots/{id}")
  public void deleteRobot(@PathVariable Long id) {
    configCommandService.deleteRobot(id);
  }

  @GetMapping("/tasks")
  public ListResponse<TaskDto> listTasks() {
    return configQueryService.listTaskTemplates();
  }

  @PostMapping("/tasks")
  public Long createTask(@RequestBody TaskUpsertRequest request) {
    return configCommandService.createTask(request);
  }

  @PutMapping("/tasks/{id}")
  public void updateTask(@PathVariable Long id, @RequestBody TaskUpsertRequest request) {
    configCommandService.updateTask(id, request);
  }

  @DeleteMapping("/tasks/{id}")
  public void deleteTask(@PathVariable Long id) {
    configCommandService.deleteTask(id);
  }

  @PostMapping("/tasks/{id}/run")
  public Long runTask(@PathVariable Long id) {
    return configCommandService.runTask(id);
  }

  @GetMapping("/complaints")
  public ListResponse<ComplaintDto> listComplaints() {
    return configQueryService.listComplaints();
  }

  @PostMapping("/complaints")
  public Long createComplaint(@RequestBody ComplaintUpsertRequest request) {
    return configCommandService.createComplaint(request);
  }

  @PutMapping("/complaints/{id}")
  public void updateComplaint(@PathVariable Long id, @RequestBody ComplaintUpsertRequest request) {
    configCommandService.updateComplaint(id, request);
  }

  @DeleteMapping("/complaints/{id}")
  public void deleteComplaint(@PathVariable Long id) {
    configCommandService.deleteComplaint(id);
  }
}
