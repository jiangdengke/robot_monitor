package org.jdk.project.controller;

import static org.jooq.generated.project.Tables.AREA;
import static org.jooq.generated.project.Tables.FLIGHT_INFO;
import static org.jooq.generated.project.Tables.GUIDE_LOG;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.PASSENGER_WARNING_LOG;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;
import static org.jooq.generated.project.Tables.ROBOT_TASK_LOG;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/DigitalTwin")
@RequiredArgsConstructor
public class DigitalTwinController {

  private static final DateTimeFormatter DATETIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final DSLContext dsl;

  @GetMapping("/selectRegionList")
  public Map<String, Object> selectRegionList(@RequestParam Map<String, String> query) {
    return response(listRegions(query.get("roomCode")), "区域点位已加载");
  }

  @GetMapping({"/all", "/getAll"})
  public Map<String, Object> all(@RequestParam Map<String, String> query) {
    String roomCode = query.get("roomCode");
    Map<String, Object> data = new LinkedHashMap<>();
    data.put("robotList", listRobots(roomCode));
    data.put("passengerList", listPassengers(roomCode));
    data.put("inspectionList", listInspections(roomCode));
    return response(data, "数字孪生数据已加载");
  }

  @GetMapping("/guide")
  public Map<String, Object> guide(@RequestParam Map<String, String> query) {
    String robotCode = trimToNull(query.get("robotId"));
    Long regionId = parseLong(firstNonBlank(query.get("regionId"), query.get("areaId")));
    Record robot = findRobot(robotCode);
    Long robotId = robot == null ? null : robot.get(ROBOT.ID);
    Long loungeId = robot == null ? null : robot.get(ROBOT.LOUNGE_ID);
    if (loungeId == null && regionId != null) {
      loungeId = dsl.select(REGION.LOUNGE_ID).from(REGION).where(REGION.ID.eq(regionId)).fetchOne(REGION.LOUNGE_ID);
    }
    String coordinate =
        regionId == null
            ? trimToNull(query.get("coordinate"))
            : dsl.select(REGION.COORDINATE)
                .from(REGION)
                .where(REGION.ID.eq(regionId))
                .fetchOne(REGION.COORDINATE);

    dsl.insertInto(GUIDE_LOG)
        .set(GUIDE_LOG.LOUNGE_ID, loungeId)
        .set(GUIDE_LOG.ROBOT_ID, robotId)
        .set(GUIDE_LOG.REGION_ID, regionId)
        .set(GUIDE_LOG.RESULT_STATUS, "SUCCESS")
        .set(GUIDE_LOG.COORDINATE, coordinate)
        .execute();
    return response(null, "区域引导任务已提交");
  }

  @GetMapping("/interruptGuideTask")
  public Map<String, Object> interruptGuideTask(@RequestParam Map<String, String> query) {
    Record robot = findRobot(trimToNull(query.get("robotId")));
    Long robotId = robot == null ? null : robot.get(ROBOT.ID);
    dsl.insertInto(ROBOT_TASK_LOG)
        .set(ROBOT_TASK_LOG.ROBOT_ID, robotId)
        .set(ROBOT_TASK_LOG.TASK_NAME, "停止当前任务")
        .set(ROBOT_TASK_LOG.TASK_TYPE, "引导")
        .set(ROBOT_TASK_LOG.TASK_STATUS, "已停止")
        .set(ROBOT_TASK_LOG.COMMAND_PAYLOAD, "{\"action\":\"interrupt\"}")
        .execute();
    return response(null, "机器人任务已停止");
  }

  @PostMapping("/manualNotice")
  public Map<String, Object> manualNotice(@RequestParam Map<String, String> query) {
    saveNotice(query, "MANUAL");
    return response(null, "人工提醒已完成");
  }

  @GetMapping("/notifyCustomer")
  public Map<String, Object> notifyCustomer(@RequestParam Map<String, String> query) {
    saveNotice(query, "ROBOT");
    return response(null, "机器人提醒任务已提交");
  }

  @PostMapping("/handleInspection")
  public Map<String, Object> handleInspection(@RequestParam Map<String, String> query) {
    return response(null, "巡检异常已处理");
  }

  private List<Map<String, Object>> listRegions(String roomCode) {
    Field<Integer> curCapacity =
        DSL.selectCount()
            .from(PASSENGER)
            .where(PASSENGER.REGION_ID.eq(REGION.ID).and(PASSENGER.ACCESS_STATUS.eq("IN")))
            .asField("cur_capacity");
    Condition condition = REGION.ENABLED.eq(true);
    String normalizedRoomCode = trimToNull(roomCode);
    if (normalizedRoomCode != null) {
      condition = condition.and(LOUNGE.CODE.eq(normalizedRoomCode));
    }
    return dsl.select(
            REGION.ID,
            REGION.NAME,
            REGION.COORDINATE,
            REGION.MAX_CAPACITY,
            LOUNGE.CODE,
            LOUNGE.NAME.as("lounge_name"),
            AREA.NAME.as("area_name"),
            curCapacity)
        .from(REGION)
        .leftJoin(LOUNGE)
        .on(REGION.LOUNGE_ID.eq(LOUNGE.ID))
        .leftJoin(AREA)
        .on(REGION.AREA_ID.eq(AREA.ID))
        .where(condition)
        .orderBy(REGION.ID.asc())
        .fetch(this::toRegionMap);
  }

  private List<Map<String, Object>> listRobots(String roomCode) {
    Condition condition = ROBOT.ENABLED.eq(true);
    String normalizedRoomCode = trimToNull(roomCode);
    if (normalizedRoomCode != null) {
      condition = condition.and(LOUNGE.CODE.eq(normalizedRoomCode));
    }
    return dsl.select(
            ROBOT.ID,
            ROBOT.ROBOT_CODE,
            ROBOT.NAME,
            ROBOT.REGION_ID,
            ROBOT.INITIAL_COORDINATE,
            ROBOT.WORKING_STATE,
            ROBOT.BATTERY_PERCENT,
            LOUNGE.CODE,
            REGION.NAME.as("region_name"))
        .from(ROBOT)
        .leftJoin(LOUNGE)
        .on(ROBOT.LOUNGE_ID.eq(LOUNGE.ID))
        .leftJoin(REGION)
        .on(ROBOT.REGION_ID.eq(REGION.ID))
        .where(condition)
        .orderBy(ROBOT.ID.asc())
        .fetch(this::toRobotMap);
  }

  private List<Map<String, Object>> listPassengers(String roomCode) {
    Map<Long, List<Map<String, Object>>> warnings = listWarnings();
    Condition condition = PASSENGER.ACCESS_STATUS.eq("IN");
    String normalizedRoomCode = trimToNull(roomCode);
    if (normalizedRoomCode != null) {
      condition = condition.and(LOUNGE.CODE.eq(normalizedRoomCode));
    }
    return dsl.select(
            PASSENGER.ID,
            PASSENGER.PASSENGER_NAME,
            PASSENGER.CARD_NO,
            PASSENGER.FLIGHT_NO,
            PASSENGER.FLIGHT_ID,
            PASSENGER.REGION_ID,
            PASSENGER.COORDINATE,
            PASSENGER.MEMBER_LEVEL,
            PASSENGER.ACCESS_STATUS,
            LOUNGE.CODE,
            FLIGHT_INFO.ESTIMATED_TAKEOFF_AT)
        .from(PASSENGER)
        .leftJoin(LOUNGE)
        .on(PASSENGER.LOUNGE_ID.eq(LOUNGE.ID))
        .leftJoin(FLIGHT_INFO)
        .on(PASSENGER.FLIGHT_ID.eq(FLIGHT_INFO.ID))
        .where(condition)
        .orderBy(PASSENGER.ID.asc())
        .fetch(record -> toPassengerMap(record, warnings));
  }

  private Map<Long, List<Map<String, Object>>> listWarnings() {
    Map<Long, List<Map<String, Object>>> warnings = new LinkedHashMap<>();
    dsl.select(
            PASSENGER_WARNING_LOG.ID,
            PASSENGER_WARNING_LOG.PASSENGER_ID,
            PASSENGER_WARNING_LOG.WARNING_TYPE,
            PASSENGER_WARNING_LOG.WARNING_INFO,
            PASSENGER_WARNING_LOG.NOTICE_TYPE,
            PASSENGER_WARNING_LOG.RESULT_STATUS,
            PASSENGER_WARNING_LOG.CREATED_AT)
        .from(PASSENGER_WARNING_LOG)
        .orderBy(PASSENGER_WARNING_LOG.ID.desc())
        .fetch(record -> {
          Long passengerId = record.get(PASSENGER_WARNING_LOG.PASSENGER_ID);
          warnings.computeIfAbsent(passengerId, key -> new ArrayList<>()).add(toWarningMap(record));
          return null;
        });
    return warnings;
  }

  private List<Map<String, Object>> listInspections(String roomCode) {
    Condition condition = ROBOT.ERROR_MESSAGE.isNotNull().and(ROBOT.ERROR_MESSAGE.ne(""));
    String normalizedRoomCode = trimToNull(roomCode);
    if (normalizedRoomCode != null) {
      condition = condition.and(LOUNGE.CODE.eq(normalizedRoomCode));
    }
    return dsl.select(
            ROBOT.ID,
            ROBOT.ROBOT_CODE,
            ROBOT.ERROR_MESSAGE,
            ROBOT.INITIAL_COORDINATE,
            LOUNGE.CODE,
            REGION.NAME.as("region_name"))
        .from(ROBOT)
        .leftJoin(LOUNGE)
        .on(ROBOT.LOUNGE_ID.eq(LOUNGE.ID))
        .leftJoin(REGION)
        .on(ROBOT.REGION_ID.eq(REGION.ID))
        .where(condition)
        .orderBy(ROBOT.ID.asc())
        .fetch(this::toInspectionMap);
  }

  private Map<String, Object> toRegionMap(Record record) {
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

  private Map<String, Object> toRobotMap(Record record) {
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

  private Map<String, Object> toPassengerMap(
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

  private Map<String, Object> toWarningMap(Record record) {
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

  private Map<String, Object> toInspectionMap(Record record) {
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

  private void saveNotice(Map<String, String> query, String noticeType) {
    Long warningId = parseLong(query.get("warningId"));
    Long passengerId = parseLong(query.get("passengerId"));
    String warningInfo =
        firstNonBlank(query.get("warningInfo"), "ROBOT".equals(noticeType) ? "机器人提醒" : "人工提醒");
    String warningType = firstNonBlank(query.get("warningType"), "SERVICE_NOTICE");
    if (warningId != null) {
      int updated =
          dsl.update(PASSENGER_WARNING_LOG)
              .set(PASSENGER_WARNING_LOG.NOTICE_TYPE, noticeType)
              .set(PASSENGER_WARNING_LOG.RESULT_STATUS, "SUCCESS")
              .set(PASSENGER_WARNING_LOG.WARNING_INFO, warningInfo)
              .where(PASSENGER_WARNING_LOG.ID.eq(warningId))
              .execute();
      if (updated > 0) {
        return;
      }
    }
    if (passengerId == null) {
      return;
    }
    Record passenger =
        dsl.select(PASSENGER.FLIGHT_ID, PASSENGER.REGION_ID)
            .from(PASSENGER)
            .where(PASSENGER.ID.eq(passengerId))
            .fetchOne();
    dsl.insertInto(PASSENGER_WARNING_LOG)
        .set(PASSENGER_WARNING_LOG.PASSENGER_ID, passengerId)
        .set(PASSENGER_WARNING_LOG.FLIGHT_ID, passenger == null ? null : passenger.get(PASSENGER.FLIGHT_ID))
        .set(PASSENGER_WARNING_LOG.REGION_ID, passenger == null ? null : passenger.get(PASSENGER.REGION_ID))
        .set(PASSENGER_WARNING_LOG.WARNING_TYPE, warningType)
        .set(PASSENGER_WARNING_LOG.WARNING_INFO, warningInfo)
        .set(PASSENGER_WARNING_LOG.NOTICE_TYPE, noticeType)
        .set(PASSENGER_WARNING_LOG.RESULT_STATUS, "SUCCESS")
        .execute();
  }

  private Record findRobot(String robotId) {
    if (robotId == null) {
      return null;
    }
    Condition condition = ROBOT.ROBOT_CODE.eq(robotId);
    Long id = parseLong(robotId);
    if (id != null) {
      condition = condition.or(ROBOT.ID.eq(id));
    }
    return dsl.select(ROBOT.ID, ROBOT.LOUNGE_ID).from(ROBOT).where(condition).fetchOne();
  }

  private Map<String, Object> response(Object data, String message) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("code", 200);
    body.put("msg", message);
    body.put("data", data);
    return body;
  }

  private String trimToNull(String value) {
    if (value == null || value.trim().isEmpty()) {
      return null;
    }
    return value.trim();
  }

  private String firstNonBlank(String... values) {
    for (String value : values) {
      String normalized = trimToNull(value);
      if (normalized != null) {
        return normalized;
      }
    }
    return null;
  }

  private Long parseLong(String value) {
    String normalized = trimToNull(value);
    if (normalized == null) {
      return null;
    }
    try {
      return Long.valueOf(normalized);
    } catch (NumberFormatException ignored) {
      return null;
    }
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
