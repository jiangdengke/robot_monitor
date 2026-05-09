package com.robotmonitor.ai.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.AI_CHAT_LOG;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_ROBOT;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.ai.domain.AiChatLog;
import com.robotmonitor.ai.dto.AiQuestionStatDTO;
import com.robotmonitor.ai.mapper.AiChatLogMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqAiChatLogMapper implements AiChatLogMapper {
    private final DSLContext dsl;

    public JooqAiChatLogMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public AiChatLog selectAiChatLogById(Long id) {
        return dsl.select(AI_CHAT_LOG.fields())
            .from(AI_CHAT_LOG)
            .where(AI_CHAT_LOG.ID.eq(id))
            .fetchOne(this::mapLog);
    }

    @Override
    public List<AiChatLog> selectAiChatLogList(AiChatLog query) {
        return dsl.select(AI_CHAT_LOG.fields())
            .from(AI_CHAT_LOG)
            .where(conditions(query))
            .orderBy(AI_CHAT_LOG.CREATE_TIME.desc())
            .fetch(this::mapLog);
    }

    @Override
    public int insertAiChatLog(AiChatLog log) {
        Long id = dsl.insertInto(AI_CHAT_LOG)
            .set(writeValues(log))
            .returningResult(AI_CHAT_LOG.ID)
            .fetchOne(AI_CHAT_LOG.ID);
        log.setId(id);
        return id == null ? 0 : 1;
    }

    @Override
    public int updateAiChatLog(AiChatLog log) {
        if (log.getId() == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(log);
        values.remove(AI_CHAT_LOG.ID);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(AI_CHAT_LOG)
            .set(values)
            .where(AI_CHAT_LOG.ID.eq(log.getId()))
            .execute();
    }

    @Override
    public int deleteAiChatLogById(Long id) {
        return dsl.deleteFrom(AI_CHAT_LOG).where(AI_CHAT_LOG.ID.eq(id)).execute();
    }

    @Override
    public int deleteAiChatLogByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(AI_CHAT_LOG).where(AI_CHAT_LOG.ID.in(Arrays.asList(ids))).execute();
    }

    @Override
    public List<AiChatLog> findNeedAutoClassificationLog() {
        return dsl.select(AI_CHAT_LOG.fields())
            .from(AI_CHAT_LOG)
            .where(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION.isNull()
                .or(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION.eq("")))
            .orderBy(AI_CHAT_LOG.CREATE_TIME.asc())
            .limit(10)
            .fetch(this::mapLog);
    }

    @Override
    public String findAllTags() {
        return dsl.selectDistinct(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION)
            .from(AI_CHAT_LOG)
            .where(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION.isNotNull())
            .and(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION.ne(""))
            .orderBy(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION.asc())
            .fetch(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION)
            .stream()
            .collect(Collectors.joining(","));
    }

    @Override
    public List<AiQuestionStatDTO> selectAiQuestionStatList(String robotId, String question, String chatType, String startTime, String endTime) {
        return dsl.select(
                AI_CHAT_LOG.ROBOT_ID,
                CONFIG_ROBOT.ROBOT_NAME,
                SYS_DEPT.DEPT_NAME,
                AI_CHAT_LOG.AI_AUTO_CLASSIFICATION,
                AI_CHAT_LOG.QUESTION,
                AI_CHAT_LOG.ANSWER,
                AI_CHAT_LOG.CHAT_TYPE,
                AI_CHAT_LOG.CREATE_TIME
            )
            .from(AI_CHAT_LOG)
            .leftJoin(CONFIG_ROBOT).on(AI_CHAT_LOG.ROBOT_ID.eq(CONFIG_ROBOT.ROBOT_ID))
            .leftJoin(SYS_DEPT).on(CONFIG_ROBOT.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE))
            .where(DSL.and(
                eqIfPresent(AI_CHAT_LOG.ROBOT_ID, robotId),
                isBlank(question) ? DSL.noCondition() : AI_CHAT_LOG.QUESTION.like("%" + question + "%"),
                chatTypeCondition(chatType),
                isBlank(startTime) ? DSL.noCondition() : AI_CHAT_LOG.CREATE_TIME.ge(parseDateTime(startTime)),
                isBlank(endTime) ? DSL.noCondition() : AI_CHAT_LOG.CREATE_TIME.le(parseDateTime(endTime))
            ))
            .orderBy(AI_CHAT_LOG.CREATE_TIME.desc())
            .fetch(this::mapStat);
    }

    private Condition conditions(AiChatLog log) {
        if (log == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            eqIfPresent(AI_CHAT_LOG.ROBOT_ID, log.getRobotId()),
            eqIfPresent(AI_CHAT_LOG.QUESTION, log.getQuestion()),
            eqIfPresent(AI_CHAT_LOG.ANSWER, log.getAnswer()),
            eqIfPresent(AI_CHAT_LOG.LANGUAGE, log.getLanguage()),
            eqIfPresent(AI_CHAT_LOG.CHAT_TYPE, log.getChatType()),
            eqIfPresent(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION, log.getAiAutoClassification())
        );
    }

    private Map<Field<?>, Object> writeValues(AiChatLog log) {
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, AI_CHAT_LOG.ID, log.getId());
        put(values, AI_CHAT_LOG.ROBOT_ID, log.getRobotId());
        put(values, AI_CHAT_LOG.QUESTION, log.getQuestion());
        put(values, AI_CHAT_LOG.ANSWER, log.getAnswer());
        put(values, AI_CHAT_LOG.LANGUAGE, log.getLanguage());
        put(values, AI_CHAT_LOG.CHAT_TYPE, log.getChatType());
        put(values, AI_CHAT_LOG.AI_AUTO_CLASSIFICATION, log.getAiAutoClassification());
        put(values, AI_CHAT_LOG.CREATE_TIME, toLocalDateTime(log.getCreateTime()));
        return values;
    }

    private AiChatLog mapLog(Record record) {
        AiChatLog log = new AiChatLog();
        log.setId(record.get(AI_CHAT_LOG.ID));
        log.setRobotId(record.get(AI_CHAT_LOG.ROBOT_ID));
        log.setQuestion(record.get(AI_CHAT_LOG.QUESTION));
        log.setAnswer(record.get(AI_CHAT_LOG.ANSWER));
        log.setLanguage(record.get(AI_CHAT_LOG.LANGUAGE));
        log.setChatType(record.get(AI_CHAT_LOG.CHAT_TYPE));
        log.setAiAutoClassification(record.get(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION));
        log.setCreateTime(toDate(record.get(AI_CHAT_LOG.CREATE_TIME)));
        return log;
    }

    private AiQuestionStatDTO mapStat(Record record) {
        AiQuestionStatDTO dto = new AiQuestionStatDTO();
        dto.setRobotId(record.get(AI_CHAT_LOG.ROBOT_ID));
        dto.setRobotName(record.get(CONFIG_ROBOT.ROBOT_NAME));
        dto.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        dto.setAiAutoClassification(record.get(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION));
        dto.setQuestion(record.get(AI_CHAT_LOG.QUESTION));
        dto.setAnswer(record.get(AI_CHAT_LOG.ANSWER));
        dto.setChatType(record.get(AI_CHAT_LOG.CHAT_TYPE));
        dto.setCreateTime(toDate(record.get(AI_CHAT_LOG.CREATE_TIME)));
        return dto;
    }

    private Condition chatTypeCondition(String chatType) {
        if (isBlank(chatType)) {
            return DSL.noCondition();
        }
        if ("OTHER".equals(chatType)) {
            return AI_CHAT_LOG.CHAT_TYPE.isNull().or(AI_CHAT_LOG.CHAT_TYPE.eq(""));
        }
        return AI_CHAT_LOG.CHAT_TYPE.eq(chatType);
    }

    private LocalDateTime parseDateTime(String value) {
        String text = value.trim();
        if (text.length() == 10) {
            return LocalDate.parse(text).atStartOfDay();
        }
        if (text.length() == 19) {
            return LocalDateTime.parse(text.replace(' ', 'T'));
        }
        if (text.length() == 14 && text.chars().allMatch(Character::isDigit)) {
            return LocalDateTime.parse(text, java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }
        return LocalDateTime.parse(text.replace(' ', 'T'));
    }

    private LocalDateTime toLocalDateTime(Date value) {
        return value == null ? null : LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }

    private Date toDate(LocalDateTime value) {
        return value == null ? null : Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Condition eqIfPresent(Field<?> field, Object value) {
        if (value == null || value instanceof String text && text.isBlank()) {
            return DSL.noCondition();
        }
        return ((Field<Object>) field).eq(value);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null) {
            values.put(field, value);
        }
    }
}
