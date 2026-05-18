package org.jdk.project.service.statistics;

import static org.jdk.project.service.statistics.StatisticsQuerySupport.trimToNull;
import static org.jooq.generated.project.Tables.GUIDE_LOG;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.statistics.GuideLogDto;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuideStatisticsService {

  private final DSLContext dsl;
  private final StatisticsMapper mapper;

  public ListResponse<GuideLogDto> listGuideLogs(Map<String, String> query) {
    List<GuideLogDto> rows =
        dsl.select(
                GUIDE_LOG.ID,
                LOUNGE.NAME.as("lounge_name"),
                ROBOT.NAME.as("robot_name"),
                PASSENGER.PASSENGER_NAME.as("passenger_name"),
                REGION.NAME.as("region_name"),
                GUIDE_LOG.RESULT_STATUS,
                GUIDE_LOG.COORDINATE,
                GUIDE_LOG.CREATED_AT)
            .from(GUIDE_LOG)
            .leftJoin(LOUNGE)
            .on(GUIDE_LOG.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(ROBOT)
            .on(GUIDE_LOG.ROBOT_ID.eq(ROBOT.ID))
            .leftJoin(PASSENGER)
            .on(GUIDE_LOG.PASSENGER_ID.eq(PASSENGER.ID))
            .leftJoin(REGION)
            .on(GUIDE_LOG.REGION_ID.eq(REGION.ID))
            .where(buildGuideCondition(query))
            .orderBy(GUIDE_LOG.ID.desc())
            .fetch(mapper::toGuideLogDto);
    return ListResponse.of(rows.size(), rows);
  }

  private Condition buildGuideCondition(Map<String, String> query) {
    Condition condition = DSL.trueCondition();
    String robotId = trimToNull(query.get("robotId"));
    if (robotId != null) {
      condition = condition.and(GUIDE_LOG.ROBOT_ID.eq(Long.valueOf(robotId)));
    }
    String resultStatus = trimToNull(query.get("resultStatus"));
    if (resultStatus != null) {
      condition = condition.and(GUIDE_LOG.RESULT_STATUS.eq(resultStatus));
    }
    return condition;
  }
}
