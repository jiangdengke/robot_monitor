package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_CONFIG;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.system.domain.SysConfig;
import com.robotmonitor.system.mapper.SysConfigMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysConfigMapper implements SysConfigMapper {
    private final DSLContext dsl;

    public JooqSysConfigMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public SysConfig selectConfig(SysConfig config) {
        return this.dsl.selectFrom(SYS_CONFIG)
            .where(equalsIfPresent(SYS_CONFIG.CONFIG_ID, config == null ? null : config.getConfigId()))
            .and(equalsIfPresent(SYS_CONFIG.CONFIG_KEY, config == null ? null : config.getConfigKey()))
            .limit(1)
            .fetchOne(this::map);
    }

    @Override
    public List<SysConfig> selectConfigList(SysConfig config) {
        return this.dsl.selectFrom(SYS_CONFIG)
            .where(contains(SYS_CONFIG.CONFIG_NAME, config == null ? null : config.getConfigName()))
            .and(equalsIfPresent(SYS_CONFIG.CONFIG_TYPE, config == null ? null : config.getConfigType()))
            .and(contains(SYS_CONFIG.CONFIG_KEY, config == null ? null : config.getConfigKey()))
            .orderBy(SYS_CONFIG.CONFIG_ID.asc())
            .fetch(this::map);
    }

    @Override
    public SysConfig checkConfigKeyUnique(String configKey) {
        return this.dsl.selectFrom(SYS_CONFIG)
            .where(SYS_CONFIG.CONFIG_KEY.eq(configKey))
            .limit(1)
            .fetchOne(this::map);
    }

    @Override
    public int insertConfig(SysConfig config) {
        return this.dsl.insertInto(SYS_CONFIG)
            .set(SYS_CONFIG.CONFIG_NAME, config.getConfigName())
            .set(SYS_CONFIG.CONFIG_KEY, config.getConfigKey())
            .set(SYS_CONFIG.CONFIG_VALUE, config.getConfigValue())
            .set(SYS_CONFIG.CONFIG_TYPE, config.getConfigType())
            .set(SYS_CONFIG.CREATE_BY, config.getCreateBy())
            .set(SYS_CONFIG.CREATE_TIME, toLocalDateTime(config.getCreateTime()))
            .set(SYS_CONFIG.REMARK, config.getRemark())
            .execute();
    }

    @Override
    public int updateConfig(SysConfig config) {
        return this.dsl.update(SYS_CONFIG)
            .set(SYS_CONFIG.CONFIG_NAME, config.getConfigName())
            .set(SYS_CONFIG.CONFIG_KEY, config.getConfigKey())
            .set(SYS_CONFIG.CONFIG_VALUE, config.getConfigValue())
            .set(SYS_CONFIG.CONFIG_TYPE, config.getConfigType())
            .set(SYS_CONFIG.UPDATE_BY, config.getUpdateBy())
            .set(SYS_CONFIG.UPDATE_TIME, toLocalDateTime(config.getUpdateTime()))
            .set(SYS_CONFIG.REMARK, config.getRemark())
            .where(SYS_CONFIG.CONFIG_ID.eq(config.getConfigId()))
            .execute();
    }

    @Override
    public int deleteConfigById(Long configId) {
        return this.dsl.deleteFrom(SYS_CONFIG)
            .where(SYS_CONFIG.CONFIG_ID.eq(configId))
            .execute();
    }

    @Override
    public int deleteConfigByIds(Long[] configIds) {
        return this.dsl.deleteFrom(SYS_CONFIG)
            .where(SYS_CONFIG.CONFIG_ID.in(Arrays.asList(configIds)))
            .execute();
    }

    private SysConfig map(Record record) {
        SysConfig config = new SysConfig();
        config.setConfigId(record.get(SYS_CONFIG.CONFIG_ID));
        config.setConfigName(record.get(SYS_CONFIG.CONFIG_NAME));
        config.setConfigKey(record.get(SYS_CONFIG.CONFIG_KEY));
        config.setConfigValue(record.get(SYS_CONFIG.CONFIG_VALUE));
        config.setConfigType(record.get(SYS_CONFIG.CONFIG_TYPE));
        config.setCreateBy(record.get(SYS_CONFIG.CREATE_BY));
        config.setCreateTime(toDate(record.get(SYS_CONFIG.CREATE_TIME)));
        config.setUpdateBy(record.get(SYS_CONFIG.UPDATE_BY));
        config.setUpdateTime(toDate(record.get(SYS_CONFIG.UPDATE_TIME)));
        config.setRemark(record.get(SYS_CONFIG.REMARK));
        return config;
    }
}
