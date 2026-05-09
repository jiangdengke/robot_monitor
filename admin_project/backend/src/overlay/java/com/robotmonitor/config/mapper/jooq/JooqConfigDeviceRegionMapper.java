package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_DEVICE_REGION;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_REGION;

import com.robotmonitor.config.domain.ConfigDeviceRegion;
import com.robotmonitor.config.mapper.ConfigDeviceRegionMapper;
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
public class JooqConfigDeviceRegionMapper implements ConfigDeviceRegionMapper {
    private final DSLContext dsl;

    public JooqConfigDeviceRegionMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<ConfigDeviceRegion> selectConfigDeviceRegionByDeviceId(Long deviceId) {
        return joinedBase()
            .where(CONFIG_DEVICE_REGION.DEVICE_ID.eq(deviceId))
            .orderBy(CONFIG_REGION.REGION_NAME.asc())
            .fetch(this::map);
    }

    @Override
    public ConfigDeviceRegion selectConfigDeviceRegionByDeviceIdRegionId(Long deviceId, Long regionId) {
        return joinedBase()
            .where(CONFIG_DEVICE_REGION.DEVICE_ID.eq(deviceId))
            .and(CONFIG_DEVICE_REGION.REGION_ID.eq(regionId))
            .fetchOne(this::map);
    }

    @Override
    public List<ConfigDeviceRegion> selectConfigDeviceRegionList(ConfigDeviceRegion query) {
        Condition condition = DSL.noCondition();
        if (query != null) {
            if (query.getImgId() != null) {
                condition = condition.and(CONFIG_DEVICE_REGION.IMG_ID.eq(query.getImgId()));
            }
            if (query.getCoordinate() != null && !query.getCoordinate().isBlank()) {
                condition = condition.and(CONFIG_DEVICE_REGION.COORDINATE.eq(query.getCoordinate()));
            }
        }
        return dsl.select(CONFIG_DEVICE_REGION.fields())
            .from(CONFIG_DEVICE_REGION)
            .where(condition)
            .fetch(this::map);
    }

    @Override
    public int insertConfigDeviceRegion(ConfigDeviceRegion relation) {
        return dsl.insertInto(CONFIG_DEVICE_REGION)
            .set(CONFIG_DEVICE_REGION.DEVICE_ID, relation.getDeviceId())
            .set(CONFIG_DEVICE_REGION.REGION_ID, relation.getRegionId())
            .set(CONFIG_DEVICE_REGION.IMG_ID, relation.getImgId())
            .set(CONFIG_DEVICE_REGION.COORDINATE, relation.getCoordinate())
            .execute();
    }

    @Override
    public int updateConfigDeviceRegion(ConfigDeviceRegion relation) {
        return dsl.update(CONFIG_DEVICE_REGION)
            .set(CONFIG_DEVICE_REGION.IMG_ID, relation.getImgId())
            .set(CONFIG_DEVICE_REGION.COORDINATE, relation.getCoordinate())
            .where(CONFIG_DEVICE_REGION.DEVICE_ID.eq(relation.getDeviceId()))
            .and(CONFIG_DEVICE_REGION.REGION_ID.eq(relation.getRegionId()))
            .execute();
    }

    @Override
    public int deleteConfigDeviceRegionByDeviceId(Long deviceId) {
        return dsl.deleteFrom(CONFIG_DEVICE_REGION)
            .where(CONFIG_DEVICE_REGION.DEVICE_ID.eq(deviceId))
            .execute();
    }

    @Override
    public int deleteConfigDeviceRegionByDeviceIds(Long[] deviceIds) {
        if (deviceIds == null || deviceIds.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(CONFIG_DEVICE_REGION)
            .where(CONFIG_DEVICE_REGION.DEVICE_ID.in(Arrays.asList(deviceIds)))
            .execute();
    }

    @Override
    public List<ConfigDeviceRegion> selectByDeviceId(Long deviceId) {
        return dsl.select(CONFIG_DEVICE_REGION.fields())
            .from(CONFIG_DEVICE_REGION)
            .where(CONFIG_DEVICE_REGION.DEVICE_ID.eq(deviceId))
            .orderBy(CONFIG_DEVICE_REGION.REGION_ID.asc())
            .fetch(this::map);
    }

    @Override
    public int deleteConfigDeviceRegion(ConfigDeviceRegion relation) {
        return dsl.deleteFrom(CONFIG_DEVICE_REGION)
            .where(CONFIG_DEVICE_REGION.DEVICE_ID.eq(relation.getDeviceId()))
            .and(CONFIG_DEVICE_REGION.REGION_ID.eq(relation.getRegionId()))
            .execute();
    }

    private org.jooq.SelectJoinStep<Record> joinedBase() {
        return dsl.select(CONFIG_DEVICE_REGION.fields())
            .select(CONFIG_REGION.REGION_NAME, CONFIG_REGION.REMARK)
            .from(CONFIG_DEVICE_REGION)
            .leftJoin(CONFIG_REGION).on(CONFIG_DEVICE_REGION.REGION_ID.eq(CONFIG_REGION.ID))
            ;
    }

    private ConfigDeviceRegion map(Record record) {
        ConfigDeviceRegion relation = new ConfigDeviceRegion();
        relation.setDeviceId(record.get(CONFIG_DEVICE_REGION.DEVICE_ID));
        relation.setRegionId(record.get(CONFIG_DEVICE_REGION.REGION_ID));
        relation.setImgId(record.get(CONFIG_DEVICE_REGION.IMG_ID));
        relation.setCoordinate(record.get(CONFIG_DEVICE_REGION.COORDINATE));
        relation.setRegionName(record.get(CONFIG_REGION.REGION_NAME));
        relation.setRemark(record.get(CONFIG_REGION.REMARK));
        if (relation.getRegionName() == null) {
            relation.setRegionName(record.get(CONFIG_DEVICE_REGION.REGION_NAME));
        }
        if (relation.getRemark() == null) {
            relation.setRemark(record.get(CONFIG_DEVICE_REGION.REMARK));
        }
        return relation;
    }
}
