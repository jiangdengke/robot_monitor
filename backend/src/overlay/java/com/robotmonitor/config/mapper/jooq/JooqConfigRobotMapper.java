package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_ROBOT;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.mapper.ConfigRobotMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqConfigRobotMapper extends GenericJooqCrudSupport<ConfigRobot> implements ConfigRobotMapper {
    public JooqConfigRobotMapper(DSLContext dsl) {
        super(dsl, CONFIG_ROBOT, CONFIG_ROBOT.ID, ConfigRobot.class);
    }

    @Override
    public ConfigRobot selectConfigRobotById(Long id) {
        return base()
            .where(CONFIG_ROBOT.ID.eq(id))
            .fetchOne(this::mapRobot);
    }

    @Override
    public ConfigRobot selectConfigRobotByRobotId(String robotId) {
        return base()
            .where(CONFIG_ROBOT.ROBOT_ID.eq(robotId))
            .fetchOne(this::mapRobot);
    }

    @Override
    public List<ConfigRobot> selectConfigRobotList(ConfigRobot query) {
        return base()
            .where(robotConditions(query).and(CONFIG_ROBOT.IS_DELETE.eq("0")))
            .fetch(this::mapRobot);
    }

    @Override
    public int insertConfigRobot(ConfigRobot robot) {
        return insert(robot);
    }

    @Override
    public int updateConfigRobot(ConfigRobot robot) {
        return update(robot);
    }

    @Override
    public int deleteConfigRobotById(Long id) {
        return softDeleteById(id, CONFIG_ROBOT.IS_DELETE, "1");
    }

    @Override
    public int deleteConfigRobotByIds(Long[] ids) {
        return softDeleteByIds(ids, CONFIG_ROBOT.IS_DELETE, "1");
    }

    @Override
    public int updateRobotIp(Long id, String ip) {
        return dsl.update(CONFIG_ROBOT)
            .set(CONFIG_ROBOT.ROBOT_IP, ip)
            .where(CONFIG_ROBOT.ID.eq(id))
            .execute();
    }

    @Override
    public int updateRobotStatus(
        String robotId,
        Long taskId,
        String chargingState,
        String workingState,
        String standbyState,
        String positioningState,
        String robotError,
        String errorMessages,
        Long batteryState
    ) {
        return dsl.update(CONFIG_ROBOT)
            .set(CONFIG_ROBOT.CHARGING_STATE, chargingState)
            .set(CONFIG_ROBOT.WORKING_STATE, workingState)
            .set(CONFIG_ROBOT.STANDBY_STATE, standbyState)
            .set(CONFIG_ROBOT.POSITIONING_STATE, positioningState)
            .set(CONFIG_ROBOT.BATTERY_STATE, intValue(batteryState))
            .set(CONFIG_ROBOT.ROBOT_ERROR, robotError)
            .set(CONFIG_ROBOT.ERROR_MESSAGES, errorMessages)
            .set(CONFIG_ROBOT.TASK_ID, taskId)
            .set(CONFIG_ROBOT.UPDATE_TIME, LocalDateTime.now())
            .where(CONFIG_ROBOT.ROBOT_ID.eq(robotId))
            .execute();
    }

    @Override
    public int updateRobotOnlineStatus(String robotId, Long onlineMode) {
        return updateRobotNetworkBits(robotId, onlineMode, true);
    }

    @Override
    public int updateRobotOfflineStatus(String robotId, Long onlineMode) {
        return updateRobotNetworkBits(robotId, onlineMode, false);
    }

    private int updateRobotNetworkBits(String robotId, Long mode, boolean online) {
        Integer bit = intValue(mode);
        return dsl.update(CONFIG_ROBOT)
            .set(
                CONFIG_ROBOT.NETWORK,
                online
                    ? CONFIG_ROBOT.NETWORK.bitOr(bit)
                    : CONFIG_ROBOT.NETWORK.bitAnd(DSL.bitNot(DSL.inline(bit)))
            )
            .set(CONFIG_ROBOT.UPDATE_TIME, LocalDateTime.now())
            .where(CONFIG_ROBOT.ROBOT_ID.eq(robotId))
            .execute();
    }

    private org.jooq.SelectJoinStep<Record> base() {
        return dsl.select(CONFIG_ROBOT.fields())
            .select(SYS_DEPT.DEPT_NAME)
            .from(CONFIG_ROBOT)
            .leftJoin(SYS_DEPT).on(CONFIG_ROBOT.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition robotConditions(ConfigRobot robot) {
        if (robot == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            eqIfPresent(CONFIG_ROBOT.ROBOT_ID, robot.getRobotId()),
            likeIfPresent(CONFIG_ROBOT.ROBOT_NAME, robot.getRobotName()),
            eqIfPresent(CONFIG_ROBOT.MAC, robot.getMac()),
            eqIfPresent(CONFIG_ROBOT.ROBOT_IP, robot.getRobotIp()),
            eqIfPresent(CONFIG_ROBOT.CHARGING_STATE, robot.getChargingState()),
            eqIfPresent(CONFIG_ROBOT.WORKING_STATE, robot.getWorkingState()),
            eqIfPresent(CONFIG_ROBOT.STANDBY_STATE, robot.getStandbyState()),
            eqIfPresent(CONFIG_ROBOT.POSITIONING_STATE, robot.getPositioningState()),
            robot.getRegionId() == null || robot.getRegionId() == 0L ? DSL.noCondition() : CONFIG_ROBOT.REGION_ID.eq(robot.getRegionId()),
            eqIfPresent(CONFIG_ROBOT.BATTERY_STATE, robot.getBatteryState()),
            eqIfPresent(CONFIG_ROBOT.NETWORK, robot.getNetwork()),
            eqIfPresent(CONFIG_ROBOT.ROBOT_ERROR, robot.getRobotError()),
            eqIfPresent(CONFIG_ROBOT.ERROR_MESSAGES, robot.getErrorMessages()),
            eqIfPresent(CONFIG_ROBOT.ROBOT_TYPE, robot.getRobotType()),
            eqIfPresent(CONFIG_ROBOT.BELONGED_COMPANY, robot.getBelongedCompany()),
            eqIfPresent(CONFIG_ROBOT.ENABLE, robot.getEnable()),
            eqIfPresent(CONFIG_ROBOT.ROOM_CODE, robot.getRoomCode()),
            eqIfPresent(CONFIG_ROBOT.TASK_ID, robot.getTaskId()),
            eqIfPresent(CONFIG_ROBOT.TASK_STATUS, robot.getTaskStatus()),
            eqIfPresent(CONFIG_ROBOT.IMG_IDS, robot.getImgIds()),
            eqIfPresent(CONFIG_ROBOT.AUDIT_KEYS, robot.getAuditKeys()),
            eqIfPresent(CONFIG_ROBOT.EMPLOYEE_NO, robot.getEmployeeNo()),
            eqIfPresent(CONFIG_ROBOT.ORI_COORDINATE, robot.getOriCoordinate())
        ));
    }

    private ConfigRobot mapRobot(Record record) {
        ConfigRobot robot = map(record);
        robot.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return robot;
    }
}
