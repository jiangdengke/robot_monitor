package org.jdk.project.service.configquery;

import static org.jooq.generated.project.Tables.AREA;
import static org.jooq.generated.project.Tables.COMPLAINT_RECORD;
import static org.jooq.generated.project.Tables.DEVICE;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.MEDIA_AUDIO;
import static org.jooq.generated.project.Tables.MEDIA_IMAGE;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;
import static org.jooq.generated.project.Tables.ROBOT_TASK_TEMPLATE;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.jdk.project.dto.config.AreaDetailDto;
import org.jdk.project.dto.config.AreaDto;
import org.jdk.project.dto.config.AudioDto;
import org.jdk.project.dto.config.ComplaintDto;
import org.jdk.project.dto.config.DeviceDto;
import org.jdk.project.dto.config.ImageDto;
import org.jdk.project.dto.config.LoungeDto;
import org.jdk.project.dto.config.RegionDto;
import org.jdk.project.dto.config.RobotDto;
import org.jdk.project.dto.config.TaskDto;
import org.jooq.Field;
import org.jooq.Record;
import org.springframework.stereotype.Component;

@Component
class ConfigQueryMapper {

  private static final DateTimeFormatter DATETIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  static final Field<String> AREA_NAME = AREA.NAME.as("area_name");
  static final Field<String> LOUNGE_NAME = LOUNGE.NAME.as("lounge_name");
  static final Field<String> REGION_NAME = REGION.NAME.as("region_name");
  static final Field<String> ROBOT_NAME = ROBOT.NAME.as("robot_name");

  LoungeDto toLoungeDto(Record record) {
    return LoungeDto.builder()
        .id(record.get(LOUNGE.ID))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get(LOUNGE.NAME))
        .terminal(record.get(LOUNGE.TERMINAL))
        .locationDesc(record.get(LOUNGE.LOCATION_DESC))
        .enabled(record.get(LOUNGE.ENABLED))
        .remark(record.get(LOUNGE.REMARK))
        .build();
  }

  RegionDto toRegionDto(Record record) {
    return RegionDto.builder()
        .id(record.get(REGION.ID))
        .regionName(record.get(REGION.NAME))
        .areaId(record.get(REGION.AREA_ID))
        .areaName(record.get(AREA_NAME))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get(LOUNGE_NAME))
        .coordinate(record.get(REGION.COORDINATE))
        .maxCapacity(record.get(REGION.MAX_CAPACITY))
        .isGuide(booleanFlag(record.get(REGION.GUIDE_ENABLED)))
        .isShow(booleanFlag(record.get(REGION.VISIBLE)))
        .enable(booleanNumber(record.get(REGION.ENABLED)))
        .remark(record.get(REGION.REMARK))
        .build();
  }

  AreaDto toAreaDto(Record record, List<AreaDetailDto> details) {
    return AreaDto.builder()
        .id(record.get(AREA.ID))
        .areaName(record.get(AREA.NAME))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get(LOUNGE_NAME))
        .coordinate(record.get(AREA.COORDINATE))
        .maxCapacity(record.get(AREA.MAX_CAPACITY))
        .isGuide(booleanFlag(record.get(AREA.GUIDE_ENABLED)))
        .isShow(booleanFlag(record.get(AREA.VISIBLE)))
        .enable(booleanNumber(record.get(AREA.ENABLED)))
        .remark(record.get(AREA.REMARK))
        .configAreaDetailList(details)
        .build();
  }

  ImageDto toImageDto(Record record) {
    return ImageDto.builder()
        .id(record.get(MEDIA_IMAGE.ID))
        .imgName(record.get(MEDIA_IMAGE.NAME))
        .imgType(record.get(MEDIA_IMAGE.CATEGORY))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get(LOUNGE_NAME))
        .width(record.get(MEDIA_IMAGE.WIDTH))
        .height(record.get(MEDIA_IMAGE.HEIGHT))
        .enable(booleanNumber(record.get(MEDIA_IMAGE.ENABLED)))
        .remark(record.get(MEDIA_IMAGE.REMARK))
        .build();
  }

  AudioDto toAudioDto(Record record) {
    return AudioDto.builder()
        .id(record.get(MEDIA_AUDIO.ID))
        .audioKey(record.get(MEDIA_AUDIO.AUDIO_KEY))
        .audioType(record.get(MEDIA_AUDIO.CATEGORY))
        .languageType(record.get(MEDIA_AUDIO.LANGUAGE_CODE))
        .textInfo(record.get(MEDIA_AUDIO.TEXT_CONTENT))
        .audioValue(record.get(MEDIA_AUDIO.AUDIO_CONTENT))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get(LOUNGE_NAME))
        .remark(record.get(MEDIA_AUDIO.REMARK))
        .build();
  }

  DeviceDto toDeviceDto(Record record) {
    return DeviceDto.builder()
        .id(record.get(DEVICE.ID))
        .deviceName(record.get(DEVICE.NAME))
        .deviceType(record.get(DEVICE.DEVICE_TYPE))
        .deepGlintDeviceId(record.get(DEVICE.EXTERNAL_DEVICE_ID))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get(LOUNGE_NAME))
        .enable(booleanNumber(record.get(DEVICE.ENABLED)))
        .remark(record.get(DEVICE.REMARK))
        .build();
  }

  RobotDto toRobotDto(Record record) {
    return RobotDto.builder()
        .id(record.get(ROBOT.ID))
        .robotId(record.get(ROBOT.ROBOT_CODE))
        .robotName(record.get(ROBOT.NAME))
        .mac(record.get(ROBOT.MAC))
        .robotIp(record.get(ROBOT.IP_ADDRESS))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get(LOUNGE_NAME))
        .regionId(record.get(ROBOT.REGION_ID))
        .regionName(record.get(REGION_NAME))
        .robotType(record.get(ROBOT.ROBOT_TYPE))
        .batteryState(record.get(ROBOT.BATTERY_PERCENT))
        .chargingState(record.get(ROBOT.CHARGING_STATE))
        .workingState(record.get(ROBOT.WORKING_STATE))
        .standbyState(record.get(ROBOT.STANDBY_STATE))
        .positioningState(record.get(ROBOT.POSITIONING_STATE))
        .enable(booleanNumber(record.get(ROBOT.ENABLED)))
        .oriCoordinate(record.get(ROBOT.INITIAL_COORDINATE))
        .adminMode(record.get(ROBOT.ADMIN_MODE))
        .remark(record.get(ROBOT.REMARK))
        .build();
  }

  TaskDto toTaskDto(Record record) {
    return TaskDto.builder()
        .id(record.get(ROBOT_TASK_TEMPLATE.ID))
        .taskName(record.get(ROBOT_TASK_TEMPLATE.NAME))
        .robotId(record.get(ROBOT_TASK_TEMPLATE.ROBOT_ID))
        .robotName(record.get(ROBOT_NAME))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get(LOUNGE_NAME))
        .command(record.get(ROBOT_TASK_TEMPLATE.COMMAND_CODE))
        .commandCn(record.get(ROBOT_TASK_TEMPLATE.COMMAND_NAME))
        .priority(record.get(ROBOT_TASK_TEMPLATE.PRIORITY))
        .executeType(record.get(ROBOT_TASK_TEMPLATE.EXECUTE_TYPE))
        .executeDay(record.get(ROBOT_TASK_TEMPLATE.EXECUTE_DAY))
        .executeTime(formatDateTime(record.get(ROBOT_TASK_TEMPLATE.EXECUTE_AT)))
        .taskType(record.get(ROBOT_TASK_TEMPLATE.TASK_TYPE))
        .taskSubtype(record.get(ROBOT_TASK_TEMPLATE.TASK_SUBTYPE))
        .taskMode(record.get(ROBOT_TASK_TEMPLATE.TASK_MODE))
        .directExecution(booleanFlag(record.get(ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION)))
        .isReturn(booleanFlag(record.get(ROBOT_TASK_TEMPLATE.RETURN_REQUIRED)))
        .enable(booleanNumber(record.get(ROBOT_TASK_TEMPLATE.ENABLED)))
        .remark(record.get(ROBOT_TASK_TEMPLATE.REMARK))
        .build();
  }

  ComplaintDto toComplaintDto(Record record) {
    return ComplaintDto.builder()
        .id(record.get(COMPLAINT_RECORD.ID))
        .userName(record.get(COMPLAINT_RECORD.PASSENGER_NAME))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get(LOUNGE_NAME))
        .cardService(record.get(COMPLAINT_RECORD.CARD_PROVIDER))
        .cardNo(record.get(COMPLAINT_RECORD.CARD_NO))
        .complaintContent(record.get(COMPLAINT_RECORD.CONTENT))
        .complaintFeedback(record.get(COMPLAINT_RECORD.FEEDBACK))
        .createTime(formatDateTime(record.get(COMPLAINT_RECORD.CREATED_AT)))
        .build();
  }

  private String booleanFlag(Boolean value) {
    return Boolean.TRUE.equals(value) ? "1" : "0";
  }

  private Integer booleanNumber(Boolean value) {
    return Boolean.TRUE.equals(value) ? 1 : 0;
  }

  private String formatDateTime(OffsetDateTime value) {
    return value == null ? null : value.toLocalDateTime().format(DATETIME_FORMATTER);
  }
}
