package com.robotmonitor.flight.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_TASK;
import static com.robotmonitor.jooq.generated.Tables.INSP_TASK_RESULT;
import static com.robotmonitor.jooq.generated.Tables.ROBOT_TASK;

import com.robotmonitor.flight.domain.digitalTwin.InspectionDto;
import com.robotmonitor.flight.mapper.DigitalTwinMapper;
import java.time.LocalDate;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqDigitalTwinMapper implements DigitalTwinMapper {
    private final DSLContext dsl;

    public JooqDigitalTwinMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<InspectionDto> getInspectionList(String roomCode) {
        return dsl.select(
                INSP_TASK_RESULT.INSP_TASK_ID,
                INSP_TASK_RESULT.ROBOT_ID,
                INSP_TASK_RESULT.TYPE,
                INSP_TASK_RESULT.POINT,
                INSP_TASK_RESULT.ABNORMAL,
                INSP_TASK_RESULT.ABNORMAL_INFO,
                INSP_TASK_RESULT.IMAGE_BASE64,
                CONFIG_TASK.REGION
            )
            .from(INSP_TASK_RESULT)
            .leftJoin(ROBOT_TASK).on(INSP_TASK_RESULT.INSP_TASK_ID.eq(ROBOT_TASK.ID))
            .leftJoin(CONFIG_TASK).on(CONFIG_TASK.ID.eq(ROBOT_TASK.TASK_ID))
            .where(CONFIG_TASK.ROOM_CODE.eq(roomCode))
            .and(INSP_TASK_RESULT.ABNORMAL.ne("0"))
            .and(INSP_TASK_RESULT.CREATE_TIME.ge(LocalDate.now().atStartOfDay()))
            .fetch(this::mapInspection);
    }

    @Override
    public void handleInspection(String id) {
        dsl.update(INSP_TASK_RESULT)
            .set(INSP_TASK_RESULT.ABNORMAL, "2")
            .where(INSP_TASK_RESULT.ID.eq(Long.valueOf(id)))
            .execute();
    }

    @Override
    public String selectTaskStatusById(Long id) {
        String status = dsl.select(ROBOT_TASK.TASK_STATUS)
            .from(ROBOT_TASK)
            .where(ROBOT_TASK.ID.eq(id))
            .fetchOne(ROBOT_TASK.TASK_STATUS);
        return status == null ? "" : status;
    }

    private InspectionDto mapInspection(Record record) {
        InspectionDto dto = new InspectionDto();
        dto.setInspTaskId(record.get(INSP_TASK_RESULT.INSP_TASK_ID));
        dto.setRobotId(record.get(INSP_TASK_RESULT.ROBOT_ID));
        dto.setType(record.get(INSP_TASK_RESULT.TYPE));
        dto.setPoint(record.get(INSP_TASK_RESULT.POINT));
        dto.setAbnormal(record.get(INSP_TASK_RESULT.ABNORMAL));
        dto.setAbnormalInfo(record.get(INSP_TASK_RESULT.ABNORMAL_INFO));
        dto.setImageBase64(record.get(INSP_TASK_RESULT.IMAGE_BASE64));
        dto.setRegion(record.get(CONFIG_TASK.REGION));
        return dto;
    }
}
