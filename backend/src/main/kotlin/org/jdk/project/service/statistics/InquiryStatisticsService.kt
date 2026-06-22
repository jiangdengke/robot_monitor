package org.jdk.project.service.statistics

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.statistics.InquiryStatDto
import org.jdk.project.dto.statistics.InquiryStatisticsQuery
import org.jdk.project.service.statistics.StatisticsQuerySupport.trimToNull
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.INQUIRY_STAT
import org.jooq.generated.project.Tables.LOUNGE
import org.jooq.generated.project.Tables.PASSENGER
import org.jooq.generated.project.Tables.ROBOT
import org.jooq.impl.DSL
import org.springframework.stereotype.Service

@Service
class InquiryStatisticsService(
    private val dsl: DSLContext,
    private val mapper: StatisticsMapper,
) {
    fun listInquiryStats(query: InquiryStatisticsQuery): ListResponse<InquiryStatDto> {
        val rows =
            dsl.select(
                INQUIRY_STAT.ID,
                StatisticsMapper.LOUNGE_NAME,
                StatisticsMapper.ROBOT_NAME,
                StatisticsMapper.PASSENGER_NAME,
                INQUIRY_STAT.TOPIC,
                INQUIRY_STAT.ROBOT_RESPONSE,
                INQUIRY_STAT.CHANNEL,
                INQUIRY_STAT.CREATED_AT,
            ).from(INQUIRY_STAT)
                .leftJoin(LOUNGE)
                .on(INQUIRY_STAT.LOUNGE_ID.eq(LOUNGE.ID))
                .leftJoin(ROBOT)
                .on(INQUIRY_STAT.ROBOT_ID.eq(ROBOT.ID))
                .leftJoin(PASSENGER)
                .on(INQUIRY_STAT.PASSENGER_ID.eq(PASSENGER.ID))
                .where(buildInquiryCondition(query))
                .orderBy(INQUIRY_STAT.ID.desc())
                .fetch { record -> mapper.toInquiryStatDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }

    private fun buildInquiryCondition(query: InquiryStatisticsQuery): Condition {
        var condition: Condition = DSL.trueCondition()
        val robotId = trimToNull(query.robotId)
        if (robotId != null) {
            condition = condition.and(INQUIRY_STAT.ROBOT_ID.eq(robotId.toLong()))
        }
        return condition
    }
}
