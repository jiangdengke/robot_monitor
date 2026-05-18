package org.jdk.project.service;

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
import org.jdk.project.dto.config.TaskDto;
import org.jdk.project.service.configquery.AreaConfigQueryService;
import org.jdk.project.service.configquery.ComplaintConfigQueryService;
import org.jdk.project.service.configquery.DeviceConfigQueryService;
import org.jdk.project.service.configquery.LoungeConfigQueryService;
import org.jdk.project.service.configquery.MediaConfigQueryService;
import org.jdk.project.service.configquery.RobotConfigQueryService;
import org.jdk.project.service.configquery.TaskConfigQueryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigQueryService {

  private final LoungeConfigQueryService loungeQueryService;
  private final AreaConfigQueryService areaQueryService;
  private final MediaConfigQueryService mediaQueryService;
  private final DeviceConfigQueryService deviceQueryService;
  private final RobotConfigQueryService robotQueryService;
  private final TaskConfigQueryService taskQueryService;
  private final ComplaintConfigQueryService complaintQueryService;

  public ListResponse<LoungeDto> listLounges() {
    return loungeQueryService.listLounges();
  }

  public ListResponse<RegionDto> listRegions() {
    return areaQueryService.listRegions();
  }

  public ListResponse<AreaDto> listAreas() {
    return areaQueryService.listAreas();
  }

  public ListResponse<ImageDto> listImages() {
    return mediaQueryService.listImages();
  }

  public ListResponse<AudioDto> listAudios(String category) {
    return mediaQueryService.listAudios(category);
  }

  public ListResponse<DeviceDto> listDevices() {
    return deviceQueryService.listDevices();
  }

  public ListResponse<RobotDto> listRobots() {
    return robotQueryService.listRobots();
  }

  public ListResponse<TaskDto> listTaskTemplates() {
    return taskQueryService.listTaskTemplates();
  }

  public ListResponse<ComplaintDto> listComplaints() {
    return complaintQueryService.listComplaints();
  }
}
