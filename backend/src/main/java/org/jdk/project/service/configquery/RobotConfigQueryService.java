package org.jdk.project.service.configquery;

import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.RobotDto;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RobotConfigQueryService {

  private final DSLContext dsl;
  private final ConfigQueryMapper mapper;

  public ListResponse<RobotDto> listRobots() {
    List<RobotDto> rows =
        dsl.select(
                ROBOT.ID,
                ROBOT.ROBOT_CODE,
                ROBOT.NAME,
                ROBOT.MAC,
                ROBOT.IP_ADDRESS,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                ROBOT.REGION_ID,
                ConfigQueryMapper.REGION_NAME,
                ROBOT.ROBOT_TYPE,
                ROBOT.BATTERY_PERCENT,
                ROBOT.CHARGING_STATE,
                ROBOT.WORKING_STATE,
                ROBOT.STANDBY_STATE,
                ROBOT.POSITIONING_STATE,
                ROBOT.ENABLED,
                ROBOT.INITIAL_COORDINATE,
                ROBOT.ADMIN_MODE,
                ROBOT.REMARK)
            .from(ROBOT)
            .join(LOUNGE)
            .on(ROBOT.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(REGION)
            .on(ROBOT.REGION_ID.eq(REGION.ID))
            .orderBy(ROBOT.ID.asc())
            .fetch(mapper::toRobotDto);
    return ListResponse.of(rows.size(), rows);
  }
}
