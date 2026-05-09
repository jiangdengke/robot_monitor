package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_AREA;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_AREA_DETAIL;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.domain.ConfigArea;
import com.robotmonitor.config.mapper.ConfigAreaMapper;
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
public class JooqConfigAreaMapper extends GenericJooqCrudSupport<ConfigArea> implements ConfigAreaMapper {
    public JooqConfigAreaMapper(DSLContext dsl) {
        super(dsl, CONFIG_AREA, CONFIG_AREA.ID, ConfigArea.class);
    }

    @Override
    public ConfigArea selectConfigAreaById(Long id) {
        return base()
            .where(CONFIG_AREA.ID.eq(id))
            .fetchOne(this::mapArea);
    }

    @Override
    public List<ConfigArea> selectConfigAreaList(ConfigArea query) {
        return base()
            .where(areaConditions(query))
            .fetch(this::mapArea);
    }

    @Override
    public int insertConfigArea(ConfigArea area) {
        return insert(area);
    }

    @Override
    public int updateConfigArea(ConfigArea area) {
        return update(area);
    }

    @Override
    public int deleteConfigAreaById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteConfigAreaByIds(Long[] ids) {
        return deleteByIds(ids);
    }

    @Override
    public List<ConfigArea> selectAreaByRoomCodeAndNameAndLanguage(String roomCode, String areaName, String languageType) {
        return dsl.select(CONFIG_AREA.fields())
            .from(CONFIG_AREA)
            .join(CONFIG_AREA_DETAIL).on(CONFIG_AREA.ID.eq(CONFIG_AREA_DETAIL.AREA_ID))
            .where(CONFIG_AREA.ROOM_CODE.eq(roomCode))
            .and(CONFIG_AREA_DETAIL.AREA_NAME.eq(areaName))
            .and(CONFIG_AREA_DETAIL.LANGUAGE_TYPE.eq(languageType))
            .fetch(this::mapArea);
    }

    private org.jooq.SelectJoinStep<Record> base() {
        return dsl.select(CONFIG_AREA.fields())
            .select(SYS_DEPT.DEPT_NAME)
            .from(CONFIG_AREA)
            .leftJoin(SYS_DEPT).on(CONFIG_AREA.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition areaConditions(ConfigArea area) {
        if (area == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            eqIfPresent(CONFIG_AREA.ROOM_CODE, area.getRoomCode()),
            eqIfPresent(CONFIG_AREA.IMG_IDS, area.getImgIds()),
            eqIfPresent(CONFIG_AREA.IS_SHOW, area.getIsShow()),
            eqIfPresent(CONFIG_AREA.IS_GUIDE, area.getIsGuide()),
            eqIfPresent(CONFIG_AREA.MAX_CAPACITY, area.getMaxCapacity()),
            eqIfPresent(CONFIG_AREA.COORDINATE, area.getCoordinate())
        ));
    }

    private ConfigArea mapArea(Record record) {
        ConfigArea area = map(record);
        area.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return area;
    }
}
