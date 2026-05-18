package org.jdk.project.service.digitaltwin;

import static org.jooq.generated.project.Tables.FLIGHT_INFO;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.PASSENGER_WARNING_LOG;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jooq.Record;
import org.springframework.stereotype.Component;

@Component
class DigitalTwinMapper {

  private static final DateTimeFormatter DATETIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  Map<String, Object> toRegionMap(Record record) {
    Map<String, Object> row = new LinkedHashMap<>();
    row.put("id", record.get(REGION.ID));
    row.put("regionName", record.get(REGION.NAME));
    row.put("areaName", record.get("area_name", String.class));
    row.put("roomCode", record.get(LOUNGE.CODE));
    row.put("deptName", record.get("lounge_name", String.class));
    row.put("coordinate", record.get(REGION.COORDINATE));
    row.put("maxCapacity", record.get(REGION.MAX_CAPACITY));
    row.put("curCapacity", record.get("cur_capacity", Integer.class));
    return row;
  }

  Map<String, Object> toRobotMap(Record record) {
    Map<String, Object> row = new LinkedHashMap<>();
    row.put("id", record.get(ROBOT.ID));
    row.put("robotId", record.get(ROBOT.ROBOT_CODE));
    row.put("robotName", record.get(ROBOT.NAME));
    row.put("regionId", record.get(ROBOT.REGION_ID));
    row.put("regionName", record.get("region_name", String.class));
    row.put("roomCode", record.get(LOUNGE.CODE));
    row.put("coordinate", record.get(ROBOT.INITIAL_COORDINATE));
    row.put("workingState", robotStateText(record.get(ROBOT.WORKING_STATE)));
    row.put("batteryState", record.get(ROBOT.BATTERY_PERCENT));
    return row;
  }

  Map<String, Object> toPassengerMap(
      Record record, Map<Long, List<Map<String, Object>>> warnings) {
    Long passengerId = record.get(PASSENGER.ID);
    Map<String, Object> row = new LinkedHashMap<>();
    row.put("id", passengerId);
    row.put("userName", record.get(PASSENGER.PASSENGER_NAME));
    row.put("cardNo", record.get(PASSENGER.CARD_NO));
    row.put("flightNo", record.get(PASSENGER.FLIGHT_NO));
    row.put("flightId", record.get(PASSENGER.FLIGHT_ID));
    row.put("estmTakeOffTime", formatDateTime(record.get(FLIGHT_INFO.ESTIMATED_TAKEOFF_AT)));
    row.put("latestOffStatus", passengerStatusText(record.get(PASSENGER.ACCESS_STATUS)));
    row.put("regionId", record.get(PASSENGER.REGION_ID));
    row.put("roomCode", record.get(LOUNGE.CODE));
    row.put("coordinate", record.get(PASSENGER.COORDINATE));
    row.put("memLevel", record.get(PASSENGER.MEMBER_LEVEL));
    row.put("warningLogList", warnings.getOrDefault(passengerId, List.of()));
    return row;
  }

  Map<String, Object> toWarningMap(Record record) {
    String status = record.get(PASSENGER_WARNING_LOG.RESULT_STATUS);
    Map<String, Object> row = new LinkedHashMap<>();
    row.put("id", record.get(PASSENGER_WARNING_LOG.ID));
    row.put("warningType", warningTypeText(record.get(PASSENGER_WARNING_LOG.WARNING_TYPE)));
    row.put("warningInfo", record.get(PASSENGER_WARNING_LOG.WARNING_INFO));
    row.put("noticeType", noticeTypeText(record.get(PASSENGER_WARNING_LOG.NOTICE_TYPE)));
    row.put("resultStatus", resultStatusText(status));
    row.put("isSuccess", "SUCCESS".equals(status) ? "1" : "0");
    row.put("createdAt", formatDateTime(record.get(PASSENGER_WARNING_LOG.CREATED_AT)));
    return row;
  }

  Map<String, Object> toInspectionMap(Record record) {
    Map<String, Object> row = new LinkedHashMap<>();
    row.put("id", record.get(ROBOT.ID));
    row.put("inspTaskId", record.get(ROBOT.ID));
    row.put("robotId", record.get(ROBOT.ROBOT_CODE));
    row.put("areaName", record.get("region_name", String.class));
    row.put("roomCode", record.get(LOUNGE.CODE));
    row.put("abnormal", "待处理");
    row.put("abnormalInfo", record.get(ROBOT.ERROR_MESSAGE));
    row.put("coordinate", record.get(ROBOT.INITIAL_COORDINATE));
    return row;
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
