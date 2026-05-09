package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_TABLE;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.domain.ConfigTable;
import com.robotmonitor.config.mapper.ConfigTableMapper;
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
public class JooqConfigTableMapper extends GenericJooqCrudSupport<ConfigTable> implements ConfigTableMapper {
    public JooqConfigTableMapper(DSLContext dsl) {
        super(dsl, CONFIG_TABLE, CONFIG_TABLE.ID, ConfigTable.class);
    }

    @Override
    public ConfigTable selectConfigTableById(Long id) {
        return base()
            .where(CONFIG_TABLE.ID.eq(id))
            .fetchOne(this::mapTable);
    }

    @Override
    public List<ConfigTable> selectConfigTableList(ConfigTable query) {
        return base()
            .where(tableConditions(query))
            .fetch(this::mapTable);
    }

    @Override
    public int insertConfigTable(ConfigTable table) {
        return insert(table);
    }

    @Override
    public int updateConfigTable(ConfigTable table) {
        return update(table);
    }

    @Override
    public int deleteConfigTableById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteConfigTableByIds(Long[] ids) {
        return deleteByIds(ids);
    }

    @Override
    public int setTableStatus(ConfigTable table) {
        return dsl.update(CONFIG_TABLE)
            .set(CONFIG_TABLE.STATUS, table.getStatus())
            .where(CONFIG_TABLE.ID.eq(table.getId()))
            .execute();
    }

    private org.jooq.SelectJoinStep<Record> base() {
        return dsl.select(CONFIG_TABLE.fields())
            .select(SYS_DEPT.DEPT_NAME)
            .from(CONFIG_TABLE)
            .leftJoin(SYS_DEPT).on(CONFIG_TABLE.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition tableConditions(ConfigTable table) {
        if (table == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            eqIfPresent(CONFIG_TABLE.TABLE_NO, table.getTableNo()),
            eqIfPresent(CONFIG_TABLE.REGION_ID, table.getRegionId()),
            eqIfPresent(CONFIG_TABLE.ROOM_CODE, table.getRoomCode()),
            eqIfPresent(CONFIG_TABLE.IS_ENABLE, table.getIsEnable()),
            eqIfPresent(CONFIG_TABLE.DEVICE_ID, table.getDeviceId()),
            eqIfPresent(CONFIG_TABLE.CAMERA_COORDINATES, table.getCameraCoordinates()),
            eqIfPresent(CONFIG_TABLE.STATUS, table.getStatus())
        ));
    }

    private ConfigTable mapTable(Record record) {
        ConfigTable table = map(record);
        table.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return table;
    }
}
