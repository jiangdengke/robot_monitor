package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_DEVICE;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.mapper.ConfigDeviceMapper;
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
public class JooqConfigDeviceMapper extends GenericJooqCrudSupport<ConfigDevice> implements ConfigDeviceMapper {
    public JooqConfigDeviceMapper(DSLContext dsl) {
        super(dsl, CONFIG_DEVICE, CONFIG_DEVICE.ID, ConfigDevice.class);
    }

    @Override
    public ConfigDevice selectConfigDeviceById(Long id) {
        return base()
            .where(CONFIG_DEVICE.ID.eq(id))
            .fetchOne(this::mapDevice);
    }

    @Override
    public List<ConfigDevice> selectConfigDeviceList(ConfigDevice query) {
        return base()
            .where(deviceConditions(query))
            .fetch(this::mapDevice);
    }

    @Override
    public int insertConfigDevice(ConfigDevice device) {
        return insert(device);
    }

    @Override
    public int updateConfigDevice(ConfigDevice device) {
        return update(device);
    }

    @Override
    public int deleteConfigDeviceById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteConfigDeviceByIds(Long[] ids) {
        return deleteByIds(ids);
    }

    @Override
    public ConfigDevice selectConfigDeviceByDeepGlintDeviceId(String deepGlintDeviceId) {
        return base()
            .where(CONFIG_DEVICE.DEEP_GLINT_DEVICE_ID.eq(deepGlintDeviceId))
            .and(CONFIG_DEVICE.IS_DELETE.eq("0"))
            .fetchOne(this::mapDevice);
    }

    @Override
    public List<ConfigDevice> selectExitDevices() {
        return base()
            .where(CONFIG_DEVICE.DEVICE_NAME.like("%出口%"))
            .and(CONFIG_DEVICE.IS_DELETE.eq("0"))
            .and(CONFIG_DEVICE.ENABLE.eq("1"))
            .fetch(this::mapDevice);
    }

    private org.jooq.SelectJoinStep<Record> base() {
        return dsl.select(CONFIG_DEVICE.fields())
            .select(SYS_DEPT.DEPT_NAME)
            .from(CONFIG_DEVICE)
            .leftJoin(SYS_DEPT).on(CONFIG_DEVICE.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition deviceConditions(ConfigDevice device) {
        if (device == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            likeIfPresent(CONFIG_DEVICE.DEVICE_NAME, device.getDeviceName()),
            eqIfPresent(CONFIG_DEVICE.DEVICE_TYPE, device.getDeviceType()),
            eqIfPresent(CONFIG_DEVICE.ENABLE, device.getEnable()),
            eqIfPresent(CONFIG_DEVICE.ROOM_CODE, device.getRoomCode()),
            eqIfPresent(CONFIG_DEVICE.IS_DELETE, device.getIsDelete())
        ));
    }

    private ConfigDevice mapDevice(Record record) {
        ConfigDevice device = map(record);
        device.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return device;
    }
}
