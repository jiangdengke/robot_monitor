package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_AREA;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_AREA_DETAIL;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_REGION;

import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.domain.ConfigAreaDetail;
import com.robotmonitor.config.mapper.ConfigAreaDetailMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqConfigAreaDetailMapper extends GenericJooqCrudSupport<ConfigAreaDetail> implements ConfigAreaDetailMapper {
    public JooqConfigAreaDetailMapper(DSLContext dsl) {
        super(dsl, CONFIG_AREA_DETAIL, CONFIG_AREA_DETAIL.ID, ConfigAreaDetail.class);
    }

    @Override
    public ConfigAreaDetail selectConfigAreaDetailById(Long id) {
        return selectById(id);
    }

    @Override
    public List<ConfigAreaDetail> selectConfigAreaDetailList(ConfigAreaDetail query) {
        return dsl.select(CONFIG_AREA_DETAIL.fields())
            .from(CONFIG_AREA_DETAIL)
            .where(detailConditions(query))
            .fetch(this::map);
    }

    @Override
    public int insertConfigAreaDetail(ConfigAreaDetail detail) {
        return insert(detail);
    }

    @Override
    public int updateConfigAreaDetail(ConfigAreaDetail detail) {
        return update(detail);
    }

    @Override
    public int deleteConfigAreaDetailById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteConfigAreaDetailByIds(Long[] ids) {
        return deleteByIds(ids);
    }

    @Override
    public int deleteByAreaId(Long areaId) {
        return dsl.deleteFrom(CONFIG_AREA_DETAIL)
            .where(CONFIG_AREA_DETAIL.AREA_ID.eq(areaId))
            .execute();
    }

    @Override
    public List<ConfigAreaDetail> selectDetailListByAreaId(Long areaId) {
        return selectByAreaId(areaId);
    }

    @Override
    public List<ConfigAreaDetail> selectDetailListWithoutAudioByAreaId(Long areaId) {
        return selectByAreaId(areaId);
    }

    @Override
    public String findAreaNameByRoomCodeAndLanguageType(String roomCode, String languageType) {
        return dsl.select(DSL.groupConcat(CONFIG_AREA_DETAIL.AREA_NAME).separator(","))
            .from(CONFIG_AREA)
            .join(CONFIG_AREA_DETAIL).on(CONFIG_AREA.ID.eq(CONFIG_AREA_DETAIL.AREA_ID))
            .where(CONFIG_AREA_DETAIL.LANGUAGE_TYPE.eq(languageType))
            .and(CONFIG_AREA.ROOM_CODE.eq(roomCode))
            .fetchOne(0, String.class);
    }

    @Override
    public List<ConfigAreaDetail> selectAreaByRegionId(Long regionId) {
        return dsl.select(CONFIG_AREA_DETAIL.fields())
            .from(CONFIG_AREA)
            .leftJoin(CONFIG_AREA_DETAIL).on(CONFIG_AREA_DETAIL.AREA_ID.eq(CONFIG_AREA.ID))
            .leftJoin(CONFIG_REGION).on(CONFIG_REGION.AREA_ID.eq(CONFIG_AREA.ID))
            .where(CONFIG_REGION.ID.eq(regionId))
            .and(CONFIG_AREA_DETAIL.LANGUAGE_TYPE.eq("CN"))
            .fetch(this::map);
    }

    private List<ConfigAreaDetail> selectByAreaId(Long areaId) {
        return dsl.select(CONFIG_AREA_DETAIL.fields())
            .from(CONFIG_AREA_DETAIL)
            .where(CONFIG_AREA_DETAIL.AREA_ID.eq(areaId))
            .fetch(this::map);
    }

    private Condition detailConditions(ConfigAreaDetail detail) {
        if (detail == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            eqIfPresent(CONFIG_AREA_DETAIL.AREA_ID, detail.getAreaId()),
            eqIfPresent(CONFIG_AREA_DETAIL.LANGUAGE_TYPE, detail.getLanguageType()),
            likeIfPresent(CONFIG_AREA_DETAIL.AREA_NAME, detail.getAreaName()),
            eqIfPresent(CONFIG_AREA_DETAIL.LABEL, detail.getLabel()),
            eqIfPresent(CONFIG_AREA_DETAIL.AUDIO, detail.getAudio()),
            eqIfPresent(CONFIG_AREA_DETAIL.ARR_TEXT, detail.getArrText()),
            eqIfPresent(CONFIG_AREA_DETAIL.ARR_AUDIO, detail.getArrAudio())
        ));
    }
}
