package org.jdk.project.service.statistics;

import static org.jdk.project.service.statistics.StatisticsQuerySupport.trimToNull;
import static org.jooq.generated.project.Tables.INQUIRY_STAT;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.ROBOT;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.statistics.InquiryStatDto;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryStatisticsService {

  private final DSLContext dsl;
  private final StatisticsMapper mapper;

  public ListResponse<InquiryStatDto> listInquiryStats(Map<String, String> query) {
    List<InquiryStatDto> rows =
        dsl.select(
                INQUIRY_STAT.ID,
                LOUNGE.NAME.as("lounge_name"),
                ROBOT.NAME.as("robot_name"),
                PASSENGER.PASSENGER_NAME.as("passenger_name"),
                INQUIRY_STAT.TOPIC,
                INQUIRY_STAT.ROBOT_RESPONSE,
                INQUIRY_STAT.CHANNEL,
                INQUIRY_STAT.CREATED_AT)
            .from(INQUIRY_STAT)
            .leftJoin(LOUNGE)
            .on(INQUIRY_STAT.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(ROBOT)
            .on(INQUIRY_STAT.ROBOT_ID.eq(ROBOT.ID))
            .leftJoin(PASSENGER)
            .on(INQUIRY_STAT.PASSENGER_ID.eq(PASSENGER.ID))
            .where(buildInquiryCondition(query))
            .orderBy(INQUIRY_STAT.ID.desc())
            .fetch(mapper::toInquiryStatDto);
    return ListResponse.of(rows.size(), rows);
  }

  private Condition buildInquiryCondition(Map<String, String> query) {
    Condition condition = DSL.trueCondition();
    String robotId = trimToNull(query.get("robotId"));
    if (robotId != null) {
      condition = condition.and(INQUIRY_STAT.ROBOT_ID.eq(Long.valueOf(robotId)));
    }
    return condition;
  }
}
