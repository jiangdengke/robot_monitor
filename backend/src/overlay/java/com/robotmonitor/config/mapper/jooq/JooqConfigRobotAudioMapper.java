package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_ROBOT_AUDIO;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.core.domain.config.ConfigRobotAudio;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.mapper.ConfigRobotAudioMapper;
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
public class JooqConfigRobotAudioMapper extends GenericJooqCrudSupport<ConfigRobotAudio> implements ConfigRobotAudioMapper {
    public JooqConfigRobotAudioMapper(DSLContext dsl) {
        super(dsl, CONFIG_ROBOT_AUDIO, CONFIG_ROBOT_AUDIO.ID, ConfigRobotAudio.class);
    }

    @Override
    public ConfigRobotAudio selectConfigRobotAudioById(Long id) {
        return base(true)
            .where(CONFIG_ROBOT_AUDIO.ID.eq(id))
            .fetchOne(this::mapAudio);
    }

    @Override
    public List<ConfigRobotAudio> selectConfigRobotAudioList(ConfigRobotAudio query) {
        return base(false)
            .where(audioConditions(query))
            .fetch(this::mapAudio);
    }

    @Override
    public int insertConfigRobotAudio(ConfigRobotAudio audio) {
        return insert(audio);
    }

    @Override
    public int updateConfigRobotAudio(ConfigRobotAudio audio) {
        return update(audio);
    }

    @Override
    public int deleteConfigRobotAudioById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteConfigRobotAudioByIds(Long[] ids) {
        return deleteByIds(ids);
    }

    @Override
    public Long getCntByKey(ConfigRobotAudio audio) {
        Condition condition = CONFIG_ROBOT_AUDIO.AUDIO_KEY.eq(audio.getAudioKey())
            .and(CONFIG_ROBOT_AUDIO.ROOM_CODE.eq(audio.getRoomCode()))
            .and(CONFIG_ROBOT_AUDIO.LANGUAGE_TYPE.eq(audio.getLanguageType()));
        if (audio.getId() != null) {
            condition = condition.and(CONFIG_ROBOT_AUDIO.ID.ne(audio.getId()));
        }
        return dsl.selectCount()
            .from(CONFIG_ROBOT_AUDIO)
            .where(condition)
            .fetchOne(0, Long.class);
    }

    @Override
    public List<ConfigRobotAudio> getNewRobotAudio(ConfigRobotAudio audio) {
        return base(true)
            .where(CONFIG_ROBOT_AUDIO.ROOM_CODE.eq(audio.getRoomCode()))
            .and(CONFIG_ROBOT_AUDIO.UPDATE_TIME.gt(toLocalDateTime(audio.getUpdateTime())))
            .fetch(this::mapAudio);
    }

    private org.jooq.SelectJoinStep<Record> base(boolean includeAudioValue) {
        if (includeAudioValue) {
            return dsl.select(CONFIG_ROBOT_AUDIO.fields())
                .select(SYS_DEPT.DEPT_NAME)
                .from(CONFIG_ROBOT_AUDIO)
                .leftJoin(SYS_DEPT).on(CONFIG_ROBOT_AUDIO.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
        }
        return dsl.select(
                CONFIG_ROBOT_AUDIO.ID,
                CONFIG_ROBOT_AUDIO.AUDIO_KEY,
                CONFIG_ROBOT_AUDIO.LANGUAGE_TYPE,
                CONFIG_ROBOT_AUDIO.TEXT_INFO,
                CONFIG_ROBOT_AUDIO.REMARK,
                CONFIG_ROBOT_AUDIO.CREATE_BY,
                CONFIG_ROBOT_AUDIO.CREATE_TIME,
                CONFIG_ROBOT_AUDIO.UPDATE_BY,
                CONFIG_ROBOT_AUDIO.UPDATE_TIME,
                CONFIG_ROBOT_AUDIO.ROOM_CODE
            )
            .select(SYS_DEPT.DEPT_NAME)
            .from(CONFIG_ROBOT_AUDIO)
            .leftJoin(SYS_DEPT).on(CONFIG_ROBOT_AUDIO.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition audioConditions(ConfigRobotAudio audio) {
        if (audio == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            eqIfPresent(CONFIG_ROBOT_AUDIO.AUDIO_KEY, audio.getAudioKey()),
            eqIfPresent(CONFIG_ROBOT_AUDIO.AUDIO_VALUE, audio.getAudioValue()),
            eqIfPresent(CONFIG_ROBOT_AUDIO.LANGUAGE_TYPE, audio.getLanguageType()),
            eqIfPresent(CONFIG_ROBOT_AUDIO.TEXT_INFO, audio.getTextInfo()),
            eqIfPresent(CONFIG_ROBOT_AUDIO.ROOM_CODE, audio.getRoomCode())
        ));
    }

    private ConfigRobotAudio mapAudio(Record record) {
        ConfigRobotAudio audio = map(record);
        audio.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return audio;
    }
}
