package com.robotmonitor.bot.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.PASSENGER_WARNING_LOG;

import com.robotmonitor.bot.mapper.UpdateNoticeResultMapper;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqUpdateNoticeResultMapper implements UpdateNoticeResultMapper {
    private final DSLContext dsl;

    public JooqUpdateNoticeResultMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public int updatePassengerWarningStatus(Long robotTaskId, String status) {
        return dsl.update(PASSENGER_WARNING_LOG)
            .set(PASSENGER_WARNING_LOG.IS_SUCCESS, status)
            .set(PASSENGER_WARNING_LOG.UPDATE_TIME, LocalDateTime.now())
            .where(PASSENGER_WARNING_LOG.ROBOT_TASK_ID.eq(robotTaskId))
            .execute();
    }
}
