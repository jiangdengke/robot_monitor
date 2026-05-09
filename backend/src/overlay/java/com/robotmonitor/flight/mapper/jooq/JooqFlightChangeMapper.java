package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.flight.mapper.jooq.JooqFlightMapperSupport.toDate;
import static com.robotmonitor.jooq.generated.Tables.SYS_DICT_DATA;
import static com.robotmonitor.jooq.generated.Tables.T_FLIGHT_CHANGE;

import com.robotmonitor.flight.domain.FlightChange;
import com.robotmonitor.flight.dto.CmdItemDto;
import com.robotmonitor.flight.mapper.FlightChangeMapper;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqFlightChangeMapper implements FlightChangeMapper {
    private final DSLContext dsl;

    public JooqFlightChangeMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<FlightChange> queryList() {
        return dsl.select(T_FLIGHT_CHANGE.fields())
            .select(SYS_DICT_DATA.DICT_LABEL)
            .from(T_FLIGHT_CHANGE)
            .leftJoin(SYS_DICT_DATA)
            .on(T_FLIGHT_CHANGE.CHANGE_STATUS.eq(SYS_DICT_DATA.DICT_VALUE)
                .and(SYS_DICT_DATA.DICT_TYPE.eq("flight_chage_type")))
            .fetch(this::mapFlightChange);
    }

    @Override
    public List<CmdItemDto> queryCmdList() {
        return List.of(defaultCmd("1", "通知提醒", "登机提醒", "notice", "boarding", 1),
            defaultCmd("1", "通知提醒", "航班变更提醒", "notice", "flight-change", 2),
            defaultCmd("2", "机器人动作", "引导旅客", "robot", "guide", 1));
    }

    private CmdItemDto defaultCmd(String type, String name, String btnName, String dataType, String taskType, int sort) {
        CmdItemDto dto = new CmdItemDto();
        dto.setType(type);
        dto.setName(name);
        dto.setBtnName(btnName);
        dto.setDataType(dataType);
        dto.setTaskType(taskType);
        dto.setDataSort(sort);
        return dto;
    }

    private FlightChange mapFlightChange(Record record) {
        FlightChange change = new FlightChange();
        change.setId(record.get(T_FLIGHT_CHANGE.ID));
        change.setName(record.get(T_FLIGHT_CHANGE.NAME));
        change.setCarrier(record.get(T_FLIGHT_CHANGE.CARRIER));
        change.setFlightNo(record.get(T_FLIGHT_CHANGE.FLIGHT_NO));
        change.setDeptName(record.get(T_FLIGHT_CHANGE.DEPT_NAME));
        change.setArrName(record.get(T_FLIGHT_CHANGE.ARR_NAME));
        change.setDeptTime(toDate(record.get(T_FLIGHT_CHANGE.DEPT_TIME)));
        change.setArrTime(toDate(record.get(T_FLIGHT_CHANGE.ARR_TIME)));
        change.setChangeStatus(record.get(T_FLIGHT_CHANGE.CHANGE_STATUS));
        String label = record.get(SYS_DICT_DATA.DICT_LABEL);
        change.setChangeStatusCn(label == null ? record.get(T_FLIGHT_CHANGE.CHANGESTATUSCN) : label);
        return change;
    }
}
