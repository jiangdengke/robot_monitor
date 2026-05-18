package org.jdk.project.service;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.config.AreaUpsertRequest;
import org.jdk.project.dto.config.AudioUpsertRequest;
import org.jdk.project.dto.config.ComplaintUpsertRequest;
import org.jdk.project.dto.config.DeviceRegionBindingUpsertRequest;
import org.jdk.project.dto.config.DeviceUpsertRequest;
import org.jdk.project.dto.config.ImageUpsertRequest;
import org.jdk.project.dto.config.LoungeUpsertRequest;
import org.jdk.project.dto.config.RegionUpsertRequest;
import org.jdk.project.dto.config.RobotUpsertRequest;
import org.jdk.project.dto.config.TaskUpsertRequest;
import org.jdk.project.service.config.AreaCommandService;
import org.jdk.project.service.config.ComplaintCommandService;
import org.jdk.project.service.config.DeviceCommandService;
import org.jdk.project.service.config.LoungeCommandService;
import org.jdk.project.service.config.MediaCommandService;
import org.jdk.project.service.config.RobotCommandService;
import org.jdk.project.service.config.TaskCommandService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigCommandService {

  private final LoungeCommandService loungeCommandService;
  private final AreaCommandService areaCommandService;
  private final MediaCommandService mediaCommandService;
  private final DeviceCommandService deviceCommandService;
  private final RobotCommandService robotCommandService;
  private final TaskCommandService taskCommandService;
  private final ComplaintCommandService complaintCommandService;

  public Long createLounge(LoungeUpsertRequest request) {
    return loungeCommandService.create(request);
  }

  public void updateLounge(Long id, LoungeUpsertRequest request) {
    loungeCommandService.update(id, request);
  }

  public void deleteLounge(Long id) {
    loungeCommandService.delete(id);
  }

  public Long createRegion(RegionUpsertRequest request) {
    return areaCommandService.createRegion(request);
  }

  public void updateRegion(Long id, RegionUpsertRequest request) {
    areaCommandService.updateRegion(id, request);
  }

  public void deleteRegion(Long id) {
    areaCommandService.deleteRegion(id);
  }

  public Long createArea(AreaUpsertRequest request) {
    return areaCommandService.createArea(request);
  }

  public void updateArea(Long id, AreaUpsertRequest request) {
    areaCommandService.updateArea(id, request);
  }

  public void deleteArea(Long id) {
    areaCommandService.deleteArea(id);
  }

  public Long createImage(ImageUpsertRequest request) {
    return mediaCommandService.createImage(request);
  }

  public void updateImage(Long id, ImageUpsertRequest request) {
    mediaCommandService.updateImage(id, request);
  }

  public void deleteImage(Long id) {
    mediaCommandService.deleteImage(id);
  }

  public Long createAudio(AudioUpsertRequest request) {
    return mediaCommandService.createAudio(request);
  }

  public void updateAudio(Long id, AudioUpsertRequest request) {
    mediaCommandService.updateAudio(id, request);
  }

  public void deleteAudio(Long id) {
    mediaCommandService.deleteAudio(id);
  }

  public Long createDevice(DeviceUpsertRequest request) {
    return deviceCommandService.createDevice(request);
  }

  public void updateDevice(Long id, DeviceUpsertRequest request) {
    deviceCommandService.updateDevice(id, request);
  }

  public void deleteDevice(Long id) {
    deviceCommandService.deleteDevice(id);
  }

  public void saveDeviceRegionBinding(DeviceRegionBindingUpsertRequest request) {
    deviceCommandService.saveDeviceRegionBinding(request);
  }

  public void deleteDeviceRegionBinding(Long deviceId, Long regionId) {
    deviceCommandService.deleteDeviceRegionBinding(deviceId, regionId);
  }

  public Long createRobot(RobotUpsertRequest request) {
    return robotCommandService.create(request);
  }

  public void updateRobot(Long id, RobotUpsertRequest request) {
    robotCommandService.update(id, request);
  }

  public void deleteRobot(Long id) {
    robotCommandService.delete(id);
  }

  public Long createTask(TaskUpsertRequest request) {
    return taskCommandService.create(request);
  }

  public void updateTask(Long id, TaskUpsertRequest request) {
    taskCommandService.update(id, request);
  }

  public void deleteTask(Long id) {
    taskCommandService.delete(id);
  }

  public Long runTask(Long id) {
    return taskCommandService.run(id);
  }

  public Long createComplaint(ComplaintUpsertRequest request) {
    return complaintCommandService.create(request);
  }

  public void updateComplaint(Long id, ComplaintUpsertRequest request) {
    complaintCommandService.update(id, request);
  }

  public void deleteComplaint(Long id) {
    complaintCommandService.delete(id);
  }
}
