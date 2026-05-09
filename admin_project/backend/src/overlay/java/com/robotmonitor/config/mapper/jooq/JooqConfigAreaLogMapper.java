package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_AREA_LOG;

import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.domain.ConfigAreaLog;
import com.robotmonitor.config.mapper.ConfigAreaLogMapper;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqConfigAreaLogMapper extends GenericJooqCrudSupport<ConfigAreaLog> implements ConfigAreaLogMapper {
    public JooqConfigAreaLogMapper(DSLContext dsl) {
        super(dsl, CONFIG_AREA_LOG, CONFIG_AREA_LOG.ID, ConfigAreaLog.class);
    }

    @Override
    public ConfigAreaLog selectConfigAreaLogById(Long id) {
        return selectById(id);
    }

    @Override
    public List<ConfigAreaLog> selectConfigAreaLogList(ConfigAreaLog query) {
        return selectList(query);
    }

    @Override
    public int insertConfigAreaLog(ConfigAreaLog log) {
        return insert(log);
    }

    @Override
    public int updateConfigAreaLog(ConfigAreaLog log) {
        return update(log);
    }

    @Override
    public int deleteConfigAreaLogById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteConfigAreaLogByIds(Long[] ids) {
        return deleteByIds(ids);
    }
}
