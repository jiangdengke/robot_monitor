package org.jdk.project.service.statistics

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.statistics.GuideLogDto
import org.jdk.project.dto.statistics.GuideStatisticsQuery
import org.jdk.project.service.statistics.StatisticsQuerySupport.trimToNull
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.GUIDE_LOG
import org.jooq.generated.project.Tables.LOUNGE
import org.jooq.generated.project.Tables.PASSENGER
import org.jooq.generated.project.Tables.REGION
import org.jooq.generated.project.Tables.ROBOT
import org.jooq.impl.DSL
import org.springframework.stereotype.Service

@Service
class GuideStatisticsService(
    private val dsl: DSLContext,
    private val mapper: StatisticsMapper,
) {
    fun listGuideLogs(query: GuideStatisticsQuery): ListResponse<GuideLogDto> {
        val rows =
            dsl.select(
                GUIDE_LOG.ID,
                StatisticsMapper.LOUNGE_NAME,
                StatisticsMapper.ROBOT_NAME,
                StatisticsMapper.PASSENGER_NAME,
                StatisticsMapper.REGION_NAME,
                GUIDE_LOG.RESULT_STATUS,
                GUIDE_LOG.COORDINATE,
                GUIDE_LOG.CREATED_AT,
            ).from(GUIDE_LOG)
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
                .fetch { record -> mapper.toGuideLogDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }

    private fun buildGuideCondition(query: GuideStatisticsQuery): Condition {
        var condition: Condition = DSL.trueCondition()
        val robotId = trimToNull(query.robotId)
        if (robotId != null) {
            condition = condition.and(GUIDE_LOG.ROBOT_ID.eq(robotId.toLong()))
        }
        val resultStatus = trimToNull(query.resultStatus)
        if (resultStatus != null) {
            condition = condition.and(GUIDE_LOG.RESULT_STATUS.eq(resultStatus))
        }
        return condition
    }
}
