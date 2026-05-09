package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.MESSAGE_LOG;

import com.robotmonitor.common.core.domain.config.MessageLog;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.mapper.MessageLogMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqMessageLogMapper extends GenericJooqCrudSupport<MessageLog> implements MessageLogMapper {
    public JooqMessageLogMapper(DSLContext dsl) {
        super(dsl, MESSAGE_LOG, MESSAGE_LOG.ID, MessageLog.class);
    }

    @Override
    public MessageLog selectMessageLogById(Long id) {
        return selectById(id);
    }

    @Override
    public List<MessageLog> selectMessageLogList(MessageLog query) {
        return dsl.select(MESSAGE_LOG.fields())
            .from(MESSAGE_LOG)
            .where(messageConditions(query))
            .fetch(this::map);
    }

    @Override
    public int insertMessageLog(MessageLog messageLog) {
        return insert(messageLog);
    }

    @Override
    public int updateMessageLog(MessageLog messageLog) {
        return update(messageLog);
    }

    @Override
    public int deleteMessageLogById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteMessageLogByIds(Long[] ids) {
        return deleteByIds(ids);
    }

    private Condition messageConditions(MessageLog log) {
        if (log == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            eqIfPresent(MESSAGE_LOG.TITLE, log.getTitle()),
            eqIfPresent(MESSAGE_LOG.CONTENT, log.getContent()),
            eqIfPresent(MESSAGE_LOG.STATUS, log.getStatus()),
            eqIfPresent(MESSAGE_LOG.SOURCE, log.getSource()),
            eqIfPresent(MESSAGE_LOG.ROOM_CODE, log.getRoomCode()),
            eqIfPresent(MESSAGE_LOG.PROCESSOR, log.getProcessor())
        ));
    }
}
