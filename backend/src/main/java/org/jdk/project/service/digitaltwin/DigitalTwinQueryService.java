package org.jdk.project.service.digitaltwin;

import static org.jdk.project.service.digitaltwin.DigitalTwinSupport.trimToNull;
import static org.jooq.generated.project.Tables.AREA;
import static org.jooq.generated.project.Tables.FLIGHT_INFO;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.PASSENGER_WARNING_LOG;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DigitalTwinQueryService {

  private final DSLContext dsl;
  private final DigitalTwinMapper mapper;

  public List<Map<String, Object>> listRegions(String roomCode) {
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
        .fetch(mapper::toRegionMap);
  }

  public List<Map<String, Object>> listRobots(String roomCode) {
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
        .fetch(mapper::toRobotMap);
  }

  public List<Map<String, Object>> listPassengers(String roomCode) {
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
        .fetch(record -> mapper.toPassengerMap(record, warnings));
  }

  public List<Map<String, Object>> listInspections(String roomCode) {
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
        .fetch(mapper::toInspectionMap);
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
        .fetch(
            record -> {
              Long passengerId = record.get(PASSENGER_WARNING_LOG.PASSENGER_ID);
              warnings.computeIfAbsent(passengerId, key -> new ArrayList<>()).add(mapper.toWarningMap(record));
              return null;
            });
    return warnings;
  }
}
