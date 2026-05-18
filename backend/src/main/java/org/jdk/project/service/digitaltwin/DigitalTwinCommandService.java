package org.jdk.project.service.digitaltwin;

import static org.jdk.project.service.digitaltwin.DigitalTwinSupport.firstNonBlank;
import static org.jdk.project.service.digitaltwin.DigitalTwinSupport.parseLong;
import static org.jdk.project.service.digitaltwin.DigitalTwinSupport.trimToNull;
import static org.jooq.generated.project.Tables.GUIDE_LOG;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.PASSENGER_WARNING_LOG;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;
import static org.jooq.generated.project.Tables.ROBOT_TASK_LOG;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DigitalTwinCommandService {

  private final DSLContext dsl;

  @Transactional
  public void createGuideTask(Map<String, String> query) {
    String robotCode = trimToNull(query.get("robotId"));
    Long regionId = parseLong(firstNonBlank(query.get("regionId"), query.get("areaId")));
    Record robot = findRobot(robotCode);
    Long robotId = robot == null ? null : robot.get(ROBOT.ID);
    Long loungeId = robot == null ? null : robot.get(ROBOT.LOUNGE_ID);
    if (loungeId == null && regionId != null) {
      loungeId =
          dsl.select(REGION.LOUNGE_ID)
              .from(REGION)
              .where(REGION.ID.eq(regionId))
              .fetchOne(REGION.LOUNGE_ID);
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
  }

  @Transactional
  public void interruptGuideTask(Map<String, String> query) {
    Record robot = findRobot(trimToNull(query.get("robotId")));
    Long robotId = robot == null ? null : robot.get(ROBOT.ID);
    dsl.insertInto(ROBOT_TASK_LOG)
        .set(ROBOT_TASK_LOG.ROBOT_ID, robotId)
        .set(ROBOT_TASK_LOG.TASK_NAME, "停止当前任务")
        .set(ROBOT_TASK_LOG.TASK_TYPE, "引导")
        .set(ROBOT_TASK_LOG.TASK_STATUS, "已停止")
        .set(ROBOT_TASK_LOG.COMMAND_PAYLOAD, "{\"action\":\"interrupt\"}")
        .execute();
  }

  @Transactional
  public void saveManualNotice(Map<String, String> query) {
    saveNotice(query, "MANUAL");
  }

  @Transactional
  public void saveRobotNotice(Map<String, String> query) {
    saveNotice(query, "ROBOT");
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
}
