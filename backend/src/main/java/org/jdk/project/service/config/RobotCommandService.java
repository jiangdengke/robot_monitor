package org.jdk.project.service.config;

import static org.jdk.project.service.config.ConfigCommandSupport.defaultInt;
import static org.jdk.project.service.config.ConfigCommandSupport.defaultString;
import static org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated;
import static org.jdk.project.service.config.ConfigCommandSupport.requiredId;
import static org.jooq.generated.project.Tables.ROBOT;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.config.RobotUpsertRequest;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.Robot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RobotCommandService {

  private final DSLContext dsl;

  @Transactional
  public Long create(RobotUpsertRequest request) {
    Robot robot = new Robot();
    robot.setLoungeId(requiredId(request.getLoungeId(), "贵宾室不能为空"));
    robot.setRegionId(request.getRegionId());
    robot.setRobotCode(request.getRobotId());
    robot.setName(request.getRobotName());
    robot.setMac(defaultString(request.getMac(), ""));
    robot.setIpAddress(defaultString(request.getRobotIp(), ""));
    robot.setRobotType(defaultString(request.getRobotType(), ""));
    robot.setBatteryPercent(defaultInt(request.getBatteryState(), 0));
    robot.setChargingState(defaultString(request.getChargingState(), ""));
    robot.setWorkingState(defaultString(request.getWorkingState(), ""));
    robot.setStandbyState(defaultString(request.getStandbyState(), ""));
    robot.setPositioningState(defaultString(request.getPositioningState(), ""));
    robot.setEnabled(request.getEnable() == null || request.getEnable() == 1);
    robot.setInitialCoordinate(defaultString(request.getOriCoordinate(), ""));
    robot.setAdminMode(Boolean.TRUE.equals(request.getAdminMode()));
    robot.setErrorCode(defaultString(request.getErrorCode(), ""));
    robot.setErrorMessage(defaultString(request.getErrorMessage(), ""));
    robot.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(ROBOT)
        .set(dsl.newRecord(ROBOT, robot))
        .returningResult(ROBOT.ID)
        .fetchOne(ROBOT.ID);
  }

  @Transactional
  public void update(Long id, RobotUpsertRequest request) {
    ensureUpdated(
        dsl.update(ROBOT)
            .set(ROBOT.LOUNGE_ID, requiredId(request.getLoungeId(), "贵宾室不能为空"))
            .set(ROBOT.REGION_ID, request.getRegionId())
            .set(ROBOT.ROBOT_CODE, request.getRobotId())
            .set(ROBOT.NAME, request.getRobotName())
            .set(ROBOT.MAC, defaultString(request.getMac(), ""))
            .set(ROBOT.IP_ADDRESS, defaultString(request.getRobotIp(), ""))
            .set(ROBOT.ROBOT_TYPE, defaultString(request.getRobotType(), ""))
            .set(ROBOT.BATTERY_PERCENT, defaultInt(request.getBatteryState(), 0))
            .set(ROBOT.CHARGING_STATE, defaultString(request.getChargingState(), ""))
            .set(ROBOT.WORKING_STATE, defaultString(request.getWorkingState(), ""))
            .set(ROBOT.STANDBY_STATE, defaultString(request.getStandbyState(), ""))
            .set(ROBOT.POSITIONING_STATE, defaultString(request.getPositioningState(), ""))
            .set(ROBOT.ENABLED, request.getEnable() == null || request.getEnable() == 1)
            .set(ROBOT.INITIAL_COORDINATE, defaultString(request.getOriCoordinate(), ""))
            .set(ROBOT.ADMIN_MODE, Boolean.TRUE.equals(request.getAdminMode()))
            .set(ROBOT.ERROR_CODE, defaultString(request.getErrorCode(), ""))
            .set(ROBOT.ERROR_MESSAGE, defaultString(request.getErrorMessage(), ""))
            .set(ROBOT.REMARK, defaultString(request.getRemark(), ""))
            .where(ROBOT.ID.eq(id))
            .execute(),
        "机器人不存在");
  }

  @Transactional
  public void delete(Long id) {
    dsl.deleteFrom(ROBOT).where(ROBOT.ID.eq(id)).execute();
  }
}
