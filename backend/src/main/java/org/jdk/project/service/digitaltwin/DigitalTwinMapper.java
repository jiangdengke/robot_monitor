package org.jdk.project.service.digitaltwin;

import static org.jooq.generated.project.Tables.AREA;
import static org.jooq.generated.project.Tables.FLIGHT_INFO;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.PASSENGER_WARNING_LOG;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.jdk.project.dto.digitaltwin.DigitalTwinInspectionDto;
import org.jdk.project.dto.digitaltwin.DigitalTwinPassengerDto;
import org.jdk.project.dto.digitaltwin.DigitalTwinRegionDto;
import org.jdk.project.dto.digitaltwin.DigitalTwinRobotDto;
import org.jdk.project.dto.digitaltwin.DigitalTwinWarningDto;
import org.jooq.Field;
import org.jooq.Record;
import org.springframework.stereotype.Component;

@Component
class DigitalTwinMapper {

  private static final DateTimeFormatter DATETIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  static final Field<String> AREA_NAME = AREA.NAME.as("area_name");
  static final Field<String> LOUNGE_NAME = LOUNGE.NAME.as("lounge_name");
  static final Field<String> REGION_NAME = REGION.NAME.as("region_name");

  DigitalTwinRegionDto toRegionDto(Record record, Field<Integer> curCapacity) {
    return DigitalTwinRegionDto.builder()
        .id(record.get(REGION.ID))
        .regionName(record.get(REGION.NAME))
        .areaName(record.get(AREA_NAME))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get(LOUNGE_NAME))
        .coordinate(record.get(REGION.COORDINATE))
        .maxCapacity(record.get(REGION.MAX_CAPACITY))
        .curCapacity(record.get(curCapacity))
        .build();
  }

  DigitalTwinRobotDto toRobotDto(Record record) {
    return DigitalTwinRobotDto.builder()
        .id(record.get(ROBOT.ID))
        .robotId(record.get(ROBOT.ROBOT_CODE))
        .robotName(record.get(ROBOT.NAME))
        .regionId(record.get(ROBOT.REGION_ID))
        .regionName(record.get(REGION_NAME))
        .roomCode(record.get(LOUNGE.CODE))
        .coordinate(record.get(ROBOT.INITIAL_COORDINATE))
        .workingState(robotStateText(record.get(ROBOT.WORKING_STATE)))
        .batteryState(record.get(ROBOT.BATTERY_PERCENT))
        .build();
  }

  DigitalTwinPassengerDto toPassengerDto(
      Record record, Map<Long, List<DigitalTwinWarningDto>> warnings) {
    Long passengerId = record.get(PASSENGER.ID);
    return DigitalTwinPassengerDto.builder()
        .id(passengerId)
        .userName(record.get(PASSENGER.PASSENGER_NAME))
        .cardNo(record.get(PASSENGER.CARD_NO))
        .flightNo(record.get(PASSENGER.FLIGHT_NO))
        .flightId(record.get(PASSENGER.FLIGHT_ID))
        .estmTakeOffTime(formatDateTime(record.get(FLIGHT_INFO.ESTIMATED_TAKEOFF_AT)))
        .latestOffStatus(passengerStatusText(record.get(PASSENGER.ACCESS_STATUS)))
        .regionId(record.get(PASSENGER.REGION_ID))
        .roomCode(record.get(LOUNGE.CODE))
        .coordinate(record.get(PASSENGER.COORDINATE))
        .memLevel(record.get(PASSENGER.MEMBER_LEVEL))
        .warningLogList(warnings.getOrDefault(passengerId, List.of()))
        .build();
  }

  DigitalTwinWarningDto toWarningDto(Record record) {
    String status = record.get(PASSENGER_WARNING_LOG.RESULT_STATUS);
    return DigitalTwinWarningDto.builder()
        .id(record.get(PASSENGER_WARNING_LOG.ID))
        .warningType(warningTypeText(record.get(PASSENGER_WARNING_LOG.WARNING_TYPE)))
        .warningInfo(record.get(PASSENGER_WARNING_LOG.WARNING_INFO))
        .noticeType(noticeTypeText(record.get(PASSENGER_WARNING_LOG.NOTICE_TYPE)))
        .resultStatus(resultStatusText(status))
        .isSuccess("SUCCESS".equals(status) ? "1" : "0")
        .createdAt(formatDateTime(record.get(PASSENGER_WARNING_LOG.CREATED_AT)))
        .build();
  }

  DigitalTwinInspectionDto toInspectionDto(Record record) {
    return DigitalTwinInspectionDto.builder()
        .id(record.get(ROBOT.ID))
        .inspTaskId(record.get(ROBOT.ID))
        .robotId(record.get(ROBOT.ROBOT_CODE))
        .areaName(record.get(REGION_NAME))
        .roomCode(record.get(LOUNGE.CODE))
        .abnormal("待处理")
        .abnormalInfo(record.get(ROBOT.ERROR_MESSAGE))
        .coordinate(record.get(ROBOT.INITIAL_COORDINATE))
        .build();
  }

  private String formatDateTime(OffsetDateTime value) {
    return value == null ? "" : value.toLocalDateTime().format(DATETIME_FORMATTER);
  }

  private String passengerStatusText(String value) {
    return switch (String.valueOf(value)) {
      case "IN" -> "在舱";
      case "OUT" -> "已出舱";
      default -> value;
    };
  }

  private String robotStateText(String value) {
    return switch (String.valueOf(value)) {
      case "0", "IDLE" -> "空闲";
      case "1", "WORKING" -> "工作中";
      case "RUNNING" -> "运行中";
      case "CHARGING" -> "充电中";
      case "ERROR" -> "异常";
      default -> value == null || value.isBlank() ? "空闲" : value;
    };
  }

  private String resultStatusText(String value) {
    return switch (String.valueOf(value)) {
      case "SUCCESS" -> "成功";
      case "FAILED" -> "失败";
      case "CREATED" -> "已创建";
      case "PENDING" -> "待处理";
      default -> value;
    };
  }

  private String noticeTypeText(String value) {
    return switch (String.valueOf(value)) {
      case "ROBOT" -> "机器人";
      case "MANUAL" -> "人工";
      default -> value;
    };
  }

  private String warningTypeText(String value) {
    return switch (String.valueOf(value)) {
      case "BOARDING" -> "登机提醒";
      case "GATE_CHANGE" -> "登机口变更";
      case "SERVICE_NOTICE" -> "服务提醒";
      default -> value;
    };
  }
}
