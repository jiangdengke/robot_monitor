package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_REGION;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.mapper.ConfigRegionMapper;
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
public class JooqConfigRegionMapper extends GenericJooqCrudSupport<ConfigRegion> implements ConfigRegionMapper {
    public JooqConfigRegionMapper(DSLContext dsl) {
        super(dsl, CONFIG_REGION, CONFIG_REGION.ID, ConfigRegion.class);
    }

    @Override
    public ConfigRegion selectConfigRegionById(Long id) {
        return base()
            .where(CONFIG_REGION.ID.eq(id))
            .fetchOne(this::mapRegion);
    }

    @Override
    public List<ConfigRegion> selectConfigRegionList(ConfigRegion query) {
        return base()
            .where(regionConditions(query))
            .fetch(this::mapRegion);
    }

    @Override
    public int insertConfigRegion(ConfigRegion region) {
        return insert(region);
    }

    @Override
    public int updateConfigRegion(ConfigRegion region) {
        return update(region);
    }

    @Override
    public int deleteConfigRegionById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteConfigRegionByIds(Long[] ids) {
        return deleteByIds(ids);
    }

    @Override
    public List<ConfigRegion> selectIsGuideRegion(String roomCode) {
        return base()
            .where(CONFIG_REGION.IS_GUIDE.eq("1"))
            .and(CONFIG_REGION.ROOM_CODE.eq(roomCode))
            .fetch(this::mapRegion);
    }

    private org.jooq.SelectJoinStep<Record> base() {
        return dsl.select(CONFIG_REGION.fields())
            .select(SYS_DEPT.DEPT_NAME)
            .from(CONFIG_REGION)
            .leftJoin(SYS_DEPT).on(CONFIG_REGION.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition regionConditions(ConfigRegion region) {
        if (region == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            likeIfPresent(CONFIG_REGION.REGION_NAME, region.getRegionName()),
            eqIfPresent(CONFIG_REGION.COORDINATE, region.getCoordinate()),
            region.getEnable() == 0 ? DSL.noCondition() : CONFIG_REGION.ENABLE.eq(String.valueOf(region.getEnable())),
            eqIfPresent(CONFIG_REGION.ROOM_CODE, region.getRoomCode()),
            eqIfPresent(CONFIG_REGION.IMG_IDS, region.getImgIds()),
            eqIfPresent(CONFIG_REGION.AUDIO_KEYS, region.getAudioKeys()),
            eqIfPresent(CONFIG_REGION.IS_GUIDE, region.getIsGuide()),
            eqIfPresent(CONFIG_REGION.IS_SHOW, region.getIsShow()),
            eqIfPresent(CONFIG_REGION.AREA_ID, region.getAreaId())
        ));
    }

    private ConfigRegion mapRegion(Record record) {
        ConfigRegion region = map(record);
        region.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return region;
    }
}
