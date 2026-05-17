package org.jdk.project.service;

import static org.jooq.generated.project.Tables.AREA;
import static org.jooq.generated.project.Tables.AREA_I18N;
import static org.jooq.generated.project.Tables.COMPLAINT_RECORD;
import static org.jooq.generated.project.Tables.DEVICE;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.MEDIA_AUDIO;
import static org.jooq.generated.project.Tables.MEDIA_IMAGE;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;
import static org.jooq.generated.project.Tables.ROBOT_TASK_TEMPLATE;

import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
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
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigQueryService {

  private static final DateTimeFormatter DATETIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final DSLContext dsl;

  public ListResponse<LoungeDto> listLounges() {
    List<LoungeDto> rows =
        dsl.selectFrom(LOUNGE).orderBy(LOUNGE.ID.asc()).fetch(this::toLoungeDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<RegionDto> listRegions() {
    List<RegionDto> rows =
        dsl.select(
                REGION.ID,
                REGION.NAME,
                REGION.AREA_ID,
                AREA.NAME.as("area_name"),
                LOUNGE.CODE,
                LOUNGE.NAME.as("lounge_name"),
                REGION.COORDINATE,
                REGION.MAX_CAPACITY,
                REGION.GUIDE_ENABLED,
                REGION.VISIBLE,
                REGION.ENABLED,
                REGION.REMARK)
            .from(REGION)
            .join(LOUNGE)
            .on(REGION.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(AREA)
            .on(REGION.AREA_ID.eq(AREA.ID))
            .orderBy(REGION.ID.asc())
            .fetch(this::toRegionDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<AreaDto> listAreas() {
    List<AreaDto> rows =
        dsl.select(
                AREA.ID,
                AREA.NAME,
                LOUNGE.CODE,
                LOUNGE.NAME.as("lounge_name"),
                AREA.COORDINATE,
                AREA.MAX_CAPACITY,
                AREA.GUIDE_ENABLED,
                AREA.VISIBLE,
                AREA.ENABLED,
                AREA.REMARK)
            .from(AREA)
            .join(LOUNGE)
            .on(AREA.LOUNGE_ID.eq(LOUNGE.ID))
            .orderBy(AREA.ID.asc())
            .fetch(
                record ->
                    AreaDto.builder()
                        .id(record.get(AREA.ID))
                        .areaName(record.get(AREA.NAME))
                        .roomCode(record.get(LOUNGE.CODE))
                        .deptName(record.get("lounge_name", String.class))
                        .coordinate(record.get(AREA.COORDINATE))
                        .maxCapacity(record.get(AREA.MAX_CAPACITY))
                        .isGuide(booleanFlag(record.get(AREA.GUIDE_ENABLED)))
                        .isShow(booleanFlag(record.get(AREA.VISIBLE)))
                        .enable(booleanNumber(record.get(AREA.ENABLED)))
                        .remark(record.get(AREA.REMARK))
                        .configAreaDetailList(loadAreaDetails(record.get(AREA.ID)))
                        .build());
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<ImageDto> listImages() {
    List<ImageDto> rows =
        dsl.select(MEDIA_IMAGE.ID, MEDIA_IMAGE.NAME, MEDIA_IMAGE.CATEGORY, LOUNGE.CODE, LOUNGE.NAME.as("lounge_name"), MEDIA_IMAGE.WIDTH, MEDIA_IMAGE.HEIGHT, MEDIA_IMAGE.ENABLED, MEDIA_IMAGE.REMARK)
            .from(MEDIA_IMAGE)
            .leftJoin(LOUNGE)
            .on(MEDIA_IMAGE.LOUNGE_ID.eq(LOUNGE.ID))
            .orderBy(MEDIA_IMAGE.ID.asc())
            .fetch(this::toImageDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<AudioDto> listAudios(String category) {
    var condition = category == null ? MEDIA_AUDIO.ID.isNotNull() : MEDIA_AUDIO.CATEGORY.eq(category);
    List<AudioDto> rows =
        dsl.select(MEDIA_AUDIO.ID, MEDIA_AUDIO.AUDIO_KEY, MEDIA_AUDIO.CATEGORY, MEDIA_AUDIO.LANGUAGE_CODE, MEDIA_AUDIO.TEXT_CONTENT, MEDIA_AUDIO.AUDIO_CONTENT, LOUNGE.CODE, LOUNGE.NAME.as("lounge_name"), MEDIA_AUDIO.REMARK)
            .from(MEDIA_AUDIO)
            .leftJoin(LOUNGE)
            .on(MEDIA_AUDIO.LOUNGE_ID.eq(LOUNGE.ID))
            .where(condition)
            .orderBy(MEDIA_AUDIO.ID.asc())
            .fetch(this::toAudioDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<DeviceDto> listDevices() {
    List<DeviceDto> rows =
        dsl.select(DEVICE.ID, DEVICE.NAME, DEVICE.DEVICE_TYPE, DEVICE.EXTERNAL_DEVICE_ID, LOUNGE.CODE, LOUNGE.NAME.as("lounge_name"), DEVICE.ENABLED, DEVICE.REMARK)
            .from(DEVICE)
            .join(LOUNGE)
            .on(DEVICE.LOUNGE_ID.eq(LOUNGE.ID))
            .orderBy(DEVICE.ID.asc())
            .fetch(this::toDeviceDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<RobotDto> listRobots() {
    List<RobotDto> rows =
        dsl.select(ROBOT.ID, ROBOT.ROBOT_CODE, ROBOT.NAME, ROBOT.MAC, ROBOT.IP_ADDRESS, LOUNGE.CODE, LOUNGE.NAME.as("lounge_name"), ROBOT.REGION_ID, REGION.NAME.as("region_name"), ROBOT.ROBOT_TYPE, ROBOT.BATTERY_PERCENT, ROBOT.CHARGING_STATE, ROBOT.WORKING_STATE, ROBOT.STANDBY_STATE, ROBOT.POSITIONING_STATE, ROBOT.ENABLED, ROBOT.INITIAL_COORDINATE, ROBOT.ADMIN_MODE, ROBOT.REMARK)
            .from(ROBOT)
            .join(LOUNGE)
            .on(ROBOT.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(REGION)
            .on(ROBOT.REGION_ID.eq(REGION.ID))
            .orderBy(ROBOT.ID.asc())
            .fetch(this::toRobotDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<TaskDto> listTaskTemplates() {
    List<TaskDto> rows =
        dsl.select(ROBOT_TASK_TEMPLATE.ID, ROBOT_TASK_TEMPLATE.NAME, ROBOT_TASK_TEMPLATE.ROBOT_ID, ROBOT.NAME.as("robot_name"), LOUNGE.CODE, LOUNGE.NAME.as("lounge_name"), ROBOT_TASK_TEMPLATE.COMMAND_CODE, ROBOT_TASK_TEMPLATE.COMMAND_NAME, ROBOT_TASK_TEMPLATE.PRIORITY, ROBOT_TASK_TEMPLATE.EXECUTE_TYPE, ROBOT_TASK_TEMPLATE.EXECUTE_DAY, ROBOT_TASK_TEMPLATE.EXECUTE_AT, ROBOT_TASK_TEMPLATE.TASK_TYPE, ROBOT_TASK_TEMPLATE.TASK_SUBTYPE, ROBOT_TASK_TEMPLATE.TASK_MODE, ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION, ROBOT_TASK_TEMPLATE.RETURN_REQUIRED, ROBOT_TASK_TEMPLATE.ENABLED, ROBOT_TASK_TEMPLATE.REMARK)
            .from(ROBOT_TASK_TEMPLATE)
            .join(LOUNGE)
            .on(ROBOT_TASK_TEMPLATE.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(ROBOT)
            .on(ROBOT_TASK_TEMPLATE.ROBOT_ID.eq(ROBOT.ID))
            .orderBy(ROBOT_TASK_TEMPLATE.ID.asc())
            .fetch(this::toTaskDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<ComplaintDto> listComplaints() {
    List<ComplaintDto> rows =
        dsl.select(COMPLAINT_RECORD.ID, COMPLAINT_RECORD.PASSENGER_NAME, LOUNGE.CODE, LOUNGE.NAME.as("lounge_name"), COMPLAINT_RECORD.CARD_PROVIDER, COMPLAINT_RECORD.CARD_NO, COMPLAINT_RECORD.CONTENT, COMPLAINT_RECORD.FEEDBACK, COMPLAINT_RECORD.CREATED_AT)
            .from(COMPLAINT_RECORD)
            .leftJoin(LOUNGE)
            .on(COMPLAINT_RECORD.LOUNGE_ID.eq(LOUNGE.ID))
            .orderBy(COMPLAINT_RECORD.ID.desc())
            .fetch(this::toComplaintDto);
    return ListResponse.of(rows.size(), rows);
  }

  private LoungeDto toLoungeDto(Record record) {
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

  private RegionDto toRegionDto(Record record) {
    return RegionDto.builder()
        .id(record.get(REGION.ID))
        .regionName(record.get(REGION.NAME))
        .areaId(record.get(REGION.AREA_ID))
        .areaName(record.get("area_name", String.class))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get("lounge_name", String.class))
        .coordinate(record.get(REGION.COORDINATE))
        .maxCapacity(record.get(REGION.MAX_CAPACITY))
        .isGuide(booleanFlag(record.get(REGION.GUIDE_ENABLED)))
        .isShow(booleanFlag(record.get(REGION.VISIBLE)))
        .enable(booleanNumber(record.get(REGION.ENABLED)))
        .remark(record.get(REGION.REMARK))
        .build();
  }

  private ImageDto toImageDto(Record record) {
    return ImageDto.builder()
        .id(record.get(MEDIA_IMAGE.ID))
        .imgName(record.get(MEDIA_IMAGE.NAME))
        .imgType(record.get(MEDIA_IMAGE.CATEGORY))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get("lounge_name", String.class))
        .width(record.get(MEDIA_IMAGE.WIDTH))
        .height(record.get(MEDIA_IMAGE.HEIGHT))
        .enable(booleanNumber(record.get(MEDIA_IMAGE.ENABLED)))
        .remark(record.get(MEDIA_IMAGE.REMARK))
        .build();
  }

  private AudioDto toAudioDto(Record record) {
    return AudioDto.builder()
        .id(record.get(MEDIA_AUDIO.ID))
        .audioKey(record.get(MEDIA_AUDIO.AUDIO_KEY))
        .audioType(record.get(MEDIA_AUDIO.CATEGORY))
        .languageType(record.get(MEDIA_AUDIO.LANGUAGE_CODE))
        .textInfo(record.get(MEDIA_AUDIO.TEXT_CONTENT))
        .audioValue(record.get(MEDIA_AUDIO.AUDIO_CONTENT))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get("lounge_name", String.class))
        .remark(record.get(MEDIA_AUDIO.REMARK))
        .build();
  }

  private DeviceDto toDeviceDto(Record record) {
    return DeviceDto.builder()
        .id(record.get(DEVICE.ID))
        .deviceName(record.get(DEVICE.NAME))
        .deviceType(record.get(DEVICE.DEVICE_TYPE))
        .deepGlintDeviceId(record.get(DEVICE.EXTERNAL_DEVICE_ID))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get("lounge_name", String.class))
        .enable(booleanNumber(record.get(DEVICE.ENABLED)))
        .remark(record.get(DEVICE.REMARK))
        .build();
  }

  private RobotDto toRobotDto(Record record) {
    return RobotDto.builder()
        .id(record.get(ROBOT.ID))
        .robotId(record.get(ROBOT.ROBOT_CODE))
        .robotName(record.get(ROBOT.NAME))
        .mac(record.get(ROBOT.MAC))
        .robotIp(record.get(ROBOT.IP_ADDRESS))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get("lounge_name", String.class))
        .regionId(record.get(ROBOT.REGION_ID))
        .regionName(record.get("region_name", String.class))
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

  private TaskDto toTaskDto(Record record) {
    return TaskDto.builder()
        .id(record.get(ROBOT_TASK_TEMPLATE.ID))
        .taskName(record.get(ROBOT_TASK_TEMPLATE.NAME))
        .robotId(record.get(ROBOT_TASK_TEMPLATE.ROBOT_ID))
        .robotName(record.get("robot_name", String.class))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get("lounge_name", String.class))
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

  private ComplaintDto toComplaintDto(Record record) {
    return ComplaintDto.builder()
        .id(record.get(COMPLAINT_RECORD.ID))
        .userName(record.get(COMPLAINT_RECORD.PASSENGER_NAME))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get("lounge_name", String.class))
        .cardService(record.get(COMPLAINT_RECORD.CARD_PROVIDER))
        .cardNo(record.get(COMPLAINT_RECORD.CARD_NO))
        .complaintContent(record.get(COMPLAINT_RECORD.CONTENT))
        .complaintFeedback(record.get(COMPLAINT_RECORD.FEEDBACK))
        .createTime(formatDateTime(record.get(COMPLAINT_RECORD.CREATED_AT)))
        .build();
  }

  private List<AreaDetailDto> loadAreaDetails(Long areaId) {
    return dsl.selectFrom(AREA_I18N)
        .where(AREA_I18N.AREA_ID.eq(areaId))
        .orderBy(AREA_I18N.ID.asc())
        .fetch(
            record ->
                AreaDetailDto.builder()
                    .id(record.get(AREA_I18N.ID))
                    .languageType(record.get(AREA_I18N.LANGUAGE_CODE))
                    .areaName(record.get(AREA_I18N.DISPLAY_NAME))
                    .label(record.get(AREA_I18N.LABEL_TEXT))
                    .arrText(record.get(AREA_I18N.ARRIVAL_TEXT))
                    .remark(record.get(AREA_I18N.SPEECH_TEXT))
                    .build());
  }

  private String booleanFlag(Boolean value) {
    return Boolean.TRUE.equals(value) ? "1" : "0";
  }

  private Integer booleanNumber(Boolean value) {
    return Boolean.TRUE.equals(value) ? 1 : 0;
  }

  private String formatDateTime(java.time.OffsetDateTime value) {
    return value == null ? null : value.toLocalDateTime().format(DATETIME_FORMATTER);
  }
}
