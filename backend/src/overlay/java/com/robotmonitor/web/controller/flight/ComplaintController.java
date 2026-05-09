package com.robotmonitor.web.controller.flight;

import static com.robotmonitor.jooq.generated.Tables.FLIGHT_COMPLAINT;

import com.robotmonitor.common.annotation.Log;
import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import com.robotmonitor.common.enums.BusinessType;
import com.robotmonitor.flight.domain.Complaint;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight/complaint")
public class ComplaintController extends BaseController {
    private final DSLContext dsl;

    public ComplaintController(DSLContext dsl) {
        this.dsl = dsl;
    }

    @GetMapping("/list")
    public TableDataInfo list(Complaint query) {
        startPage();
        return getDataTable(dsl.select(FLIGHT_COMPLAINT.fields())
            .from(FLIGHT_COMPLAINT)
            .where(conditions(query))
            .orderBy(FLIGHT_COMPLAINT.CREATE_TIME.desc())
            .fetch(this::mapComplaint));
    }

    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        Complaint complaint = dsl.select(FLIGHT_COMPLAINT.fields())
            .from(FLIGHT_COMPLAINT)
            .where(FLIGHT_COMPLAINT.ID.eq(id))
            .fetchOne(this::mapComplaint);
        return AjaxResult.success(complaint);
    }

    @Log(title = "投诉记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Complaint complaint) {
        LocalDateTime now = LocalDateTime.now();
        Long id = dsl.insertInto(FLIGHT_COMPLAINT)
            .set(FLIGHT_COMPLAINT.USER_NAME, complaint.getUserName())
            .set(FLIGHT_COMPLAINT.ROOM_CODE, complaint.getRoomCode())
            .set(FLIGHT_COMPLAINT.CARD_SERVICE, complaint.getCardService())
            .set(FLIGHT_COMPLAINT.CARD_NO, complaint.getCardNo())
            .set(FLIGHT_COMPLAINT.COMPLAINT_CONTENT, complaint.getComplaintContent())
            .set(FLIGHT_COMPLAINT.COMPLAINT_FEEDBACK, complaint.getComplaintFeedback())
            .set(FLIGHT_COMPLAINT.CREATE_TIME, now)
            .set(FLIGHT_COMPLAINT.UPDATE_TIME, now)
            .returningResult(FLIGHT_COMPLAINT.ID)
            .fetchOne(FLIGHT_COMPLAINT.ID);
        return toAjax(id == null ? 0 : 1);
    }

    @Log(title = "投诉记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Complaint complaint) {
        if (complaint.getId() == null) {
            return AjaxResult.error("投诉记录 ID 不能为空");
        }
        Map<Field<?>, Object> values = new LinkedHashMap<>();
        put(values, FLIGHT_COMPLAINT.USER_NAME, complaint.getUserName());
        put(values, FLIGHT_COMPLAINT.ROOM_CODE, complaint.getRoomCode());
        put(values, FLIGHT_COMPLAINT.CARD_SERVICE, complaint.getCardService());
        put(values, FLIGHT_COMPLAINT.CARD_NO, complaint.getCardNo());
        put(values, FLIGHT_COMPLAINT.COMPLAINT_CONTENT, complaint.getComplaintContent());
        put(values, FLIGHT_COMPLAINT.COMPLAINT_FEEDBACK, complaint.getComplaintFeedback());
        values.put(FLIGHT_COMPLAINT.UPDATE_TIME, LocalDateTime.now());
        return toAjax(dsl.update(FLIGHT_COMPLAINT)
            .set(values)
            .where(FLIGHT_COMPLAINT.ID.eq(complaint.getId()))
            .execute());
    }

    @Log(title = "投诉记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dsl.deleteFrom(FLIGHT_COMPLAINT)
            .where(FLIGHT_COMPLAINT.ID.in(Arrays.asList(ids)))
            .execute());
    }

    private Condition conditions(Complaint query) {
        if (query == null) {
            return DSL.noCondition();
        }
        return DSL.and(
            likeIfPresent(FLIGHT_COMPLAINT.USER_NAME, query.getUserName()),
            eqIfPresent(FLIGHT_COMPLAINT.ROOM_CODE, query.getRoomCode()),
            eqIfPresent(FLIGHT_COMPLAINT.CARD_SERVICE, query.getCardService()),
            likeIfPresent(FLIGHT_COMPLAINT.CARD_NO, query.getCardNo())
        );
    }

    private Complaint mapComplaint(Record record) {
        Complaint complaint = new Complaint();
        complaint.setId(record.get(FLIGHT_COMPLAINT.ID));
        complaint.setUserName(record.get(FLIGHT_COMPLAINT.USER_NAME));
        complaint.setRoomCode(record.get(FLIGHT_COMPLAINT.ROOM_CODE));
        complaint.setCardService(record.get(FLIGHT_COMPLAINT.CARD_SERVICE));
        complaint.setCardNo(record.get(FLIGHT_COMPLAINT.CARD_NO));
        complaint.setComplaintContent(record.get(FLIGHT_COMPLAINT.COMPLAINT_CONTENT));
        complaint.setComplaintFeedback(record.get(FLIGHT_COMPLAINT.COMPLAINT_FEEDBACK));
        LocalDateTime createTime = record.get(FLIGHT_COMPLAINT.CREATE_TIME);
        LocalDateTime updateTime = record.get(FLIGHT_COMPLAINT.UPDATE_TIME);
        complaint.setCreateTime(createTime == null ? null : java.sql.Timestamp.valueOf(createTime));
        complaint.setUpdateTime(updateTime == null ? null : java.sql.Timestamp.valueOf(updateTime));
        return complaint;
    }

    private Condition eqIfPresent(Field<String> field, String value) {
        if (value == null || value.isBlank()) {
            return DSL.noCondition();
        }
        return field.eq(value);
    }

    private Condition likeIfPresent(Field<String> field, String value) {
        if (value == null || value.isBlank()) {
            return DSL.noCondition();
        }
        return field.like("%" + value + "%");
    }

    private void put(Map<Field<?>, Object> values, Field<?> field, Object value) {
        if (value != null) {
            values.put(field, value);
        }
    }
}
