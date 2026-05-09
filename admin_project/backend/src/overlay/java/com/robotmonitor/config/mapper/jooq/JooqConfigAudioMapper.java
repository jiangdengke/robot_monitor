package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_AUDIO;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.core.domain.config.ConfigAudio;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.mapper.ConfigAudioMapper;
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
public class JooqConfigAudioMapper extends GenericJooqCrudSupport<ConfigAudio> implements ConfigAudioMapper {
    public JooqConfigAudioMapper(DSLContext dsl) {
        super(dsl, CONFIG_AUDIO, CONFIG_AUDIO.ID, ConfigAudio.class);
    }

    @Override
    public ConfigAudio selectConfigAudioById(Long id) {
        return base(true)
            .where(CONFIG_AUDIO.ID.eq(id))
            .fetchOne(this::mapAudio);
    }

    @Override
    public List<ConfigAudio> selectConfigAudioList(ConfigAudio query) {
        return base(false)
            .where(audioConditions(query))
            .fetch(this::mapAudio);
    }

    @Override
    public int insertConfigAudio(ConfigAudio audio) {
        return insert(audio);
    }

    @Override
    public int updateConfigAudio(ConfigAudio audio) {
        return update(audio);
    }

    @Override
    public int deleteConfigAudioById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteConfigAudioByIds(Long[] ids) {
        return deleteByIds(ids);
    }

    @Override
    public Long getCntByKey(ConfigAudio audio) {
        Condition condition = CONFIG_AUDIO.AUDIO_KEY.eq(audio.getAudioKey())
            .and(CONFIG_AUDIO.ROOM_CODE.eq(audio.getRoomCode()))
            .and(CONFIG_AUDIO.LANGUAGE_TYPE.eq(audio.getLanguageType()));
        if (audio.getId() != null) {
            condition = condition.and(CONFIG_AUDIO.ID.ne(audio.getId()));
        }
        return dsl.selectCount()
            .from(CONFIG_AUDIO)
            .where(condition)
            .fetchOne(0, Long.class);
    }

    private org.jooq.SelectJoinStep<Record> base(boolean includeAudioValue) {
        if (includeAudioValue) {
            return dsl.select(CONFIG_AUDIO.fields())
                .select(SYS_DEPT.DEPT_NAME)
                .from(CONFIG_AUDIO)
                .leftJoin(SYS_DEPT).on(CONFIG_AUDIO.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
        }
        return dsl.select(
                CONFIG_AUDIO.ID,
                CONFIG_AUDIO.AUDIO_KEY,
                CONFIG_AUDIO.LANGUAGE_TYPE,
                CONFIG_AUDIO.TEXT_INFO,
                CONFIG_AUDIO.REMARK,
                CONFIG_AUDIO.CREATE_BY,
                CONFIG_AUDIO.CREATE_TIME,
                CONFIG_AUDIO.UPDATE_BY,
                CONFIG_AUDIO.UPDATE_TIME,
                CONFIG_AUDIO.ROOM_CODE,
                CONFIG_AUDIO.AUDIO_TYPE
            )
            .select(SYS_DEPT.DEPT_NAME)
            .from(CONFIG_AUDIO)
            .leftJoin(SYS_DEPT).on(CONFIG_AUDIO.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition audioConditions(ConfigAudio audio) {
        if (audio == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            eqIfPresent(CONFIG_AUDIO.AUDIO_KEY, audio.getAudioKey()),
            eqIfPresent(CONFIG_AUDIO.AUDIO_VALUE, audio.getAudioValue()),
            eqIfPresent(CONFIG_AUDIO.LANGUAGE_TYPE, audio.getLanguageType()),
            eqIfPresent(CONFIG_AUDIO.TEXT_INFO, audio.getTextInfo()),
            eqIfPresent(CONFIG_AUDIO.ROOM_CODE, audio.getRoomCode()),
            eqIfPresent(CONFIG_AUDIO.AUDIO_TYPE, audio.getAudioType())
        ));
    }

    private ConfigAudio mapAudio(Record record) {
        ConfigAudio audio = map(record);
        audio.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return audio;
    }
}
