package com.robotmonitor.web.controller.ai;

import static com.robotmonitor.jooq.generated.Tables.AI_KNOWLEDGE_BASE;

import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.page.TableDataInfo;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/knowledge")
public class AiKnowledgeBaseController {
    private final DSLContext dsl;

    public AiKnowledgeBaseController(DSLContext dsl) {
        this.dsl = dsl;
    }

    @GetMapping("/list")
    public TableDataInfo list(@RequestParam Map<String, String> query) {
        int pageNum = parseInt(query.get("pageNum"), 1);
        int pageSize = parseInt(query.get("pageSize"), 20);
        Condition condition = condition(query);
        long total = dsl.fetchCount(AI_KNOWLEDGE_BASE, condition);
        List<Map<String, Object>> rows = dsl.select(AI_KNOWLEDGE_BASE.fields())
            .from(AI_KNOWLEDGE_BASE)
            .where(condition)
            .orderBy(AI_KNOWLEDGE_BASE.UPDATE_TIME.desc(), AI_KNOWLEDGE_BASE.ID.desc())
            .limit(pageSize)
            .offset(Math.max(0, pageNum - 1) * pageSize)
            .fetch(this::mapKnowledge);
        return table(rows, total);
    }

    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        Map<String, Object> row = dsl.select(AI_KNOWLEDGE_BASE.fields())
            .from(AI_KNOWLEDGE_BASE)
            .where(AI_KNOWLEDGE_BASE.ID.eq(id))
            .fetchOne(this::mapKnowledge);
        return AjaxResult.success(row);
    }

    @PostMapping
    public AjaxResult add(@RequestBody Map<String, Object> payload) {
        LocalDateTime now = LocalDateTime.now();
        Long id = dsl.insertInto(AI_KNOWLEDGE_BASE)
            .set(AI_KNOWLEDGE_BASE.CONTENT, stringValue(payload, "content"))
            .set(AI_KNOWLEDGE_BASE.SOURCE, defaultText(stringValue(payload, "source"), "manual"))
            .set(AI_KNOWLEDGE_BASE.TYPE, defaultText(stringValue(payload, "type"), "faq"))
            .set(AI_KNOWLEDGE_BASE.STATUS, defaultText(stringValue(payload, "status"), "1"))
            .set(AI_KNOWLEDGE_BASE.ENABLE, defaultText(stringValue(payload, "enable"), "1"))
            .set(AI_KNOWLEDGE_BASE.F_ID, parseLong(value(payload, "fId", "f_id")))
            .set(AI_KNOWLEDGE_BASE.VECTOR_ID, defaultText(stringValue(payload, "vectorId", "vector_id"), ""))
            .set(AI_KNOWLEDGE_BASE.CREATE_BY, defaultText(stringValue(payload, "createBy", "create_by"), "system"))
            .set(AI_KNOWLEDGE_BASE.CREATE_TIME, now)
            .set(AI_KNOWLEDGE_BASE.UPDATE_BY, defaultText(stringValue(payload, "updateBy", "update_by"), "system"))
            .set(AI_KNOWLEDGE_BASE.UPDATE_TIME, now)
            .set(AI_KNOWLEDGE_BASE.REMARK, defaultText(stringValue(payload, "remark"), ""))
            .returningResult(AI_KNOWLEDGE_BASE.ID)
            .fetchOne(AI_KNOWLEDGE_BASE.ID);
        return AjaxResult.success(Map.of("id", id));
    }

    @PutMapping
    public AjaxResult edit(@RequestBody Map<String, Object> payload) {
        Long id = parseLong(value(payload, "id"));
        if (id == null) {
            return AjaxResult.error("知识 ID 不能为空");
        }
        int rows = dsl.update(AI_KNOWLEDGE_BASE)
            .set(AI_KNOWLEDGE_BASE.CONTENT, stringValue(payload, "content"))
            .set(AI_KNOWLEDGE_BASE.SOURCE, defaultText(stringValue(payload, "source"), "manual"))
            .set(AI_KNOWLEDGE_BASE.TYPE, defaultText(stringValue(payload, "type"), "faq"))
            .set(AI_KNOWLEDGE_BASE.STATUS, defaultText(stringValue(payload, "status"), "1"))
            .set(AI_KNOWLEDGE_BASE.ENABLE, defaultText(stringValue(payload, "enable"), "1"))
            .set(AI_KNOWLEDGE_BASE.F_ID, parseLong(value(payload, "fId", "f_id")))
            .set(AI_KNOWLEDGE_BASE.VECTOR_ID, defaultText(stringValue(payload, "vectorId", "vector_id"), ""))
            .set(AI_KNOWLEDGE_BASE.UPDATE_BY, defaultText(stringValue(payload, "updateBy", "update_by"), "system"))
            .set(AI_KNOWLEDGE_BASE.UPDATE_TIME, LocalDateTime.now())
            .set(AI_KNOWLEDGE_BASE.REMARK, defaultText(stringValue(payload, "remark"), ""))
            .where(AI_KNOWLEDGE_BASE.ID.eq(id))
            .execute();
        return rows > 0 ? AjaxResult.success() : AjaxResult.error("知识不存在");
    }

    @PostMapping("/embedding")
    public AjaxResult embedding(@RequestBody Long[] ids) {
        List<Long> idList = idList(ids);
        if (idList.isEmpty()) {
            return AjaxResult.error("没有需要向量化的知识");
        }
        for (Long id : idList) {
            dsl.update(AI_KNOWLEDGE_BASE)
                .set(AI_KNOWLEDGE_BASE.STATUS, "1")
                .set(AI_KNOWLEDGE_BASE.VECTOR_ID, "vec-" + id)
                .set(AI_KNOWLEDGE_BASE.UPDATE_BY, "system")
                .set(AI_KNOWLEDGE_BASE.UPDATE_TIME, LocalDateTime.now())
                .where(AI_KNOWLEDGE_BASE.ID.eq(id))
                .execute();
        }
        return AjaxResult.success(Map.of("ids", idList, "mock", true));
    }

    @PostMapping("/enable")
    public AjaxResult enable(@RequestBody Long[] ids) {
        return updateEnable(ids, "1");
    }

    @PostMapping("/disable")
    public AjaxResult disable(@RequestBody Long[] ids) {
        return updateEnable(ids, "0");
    }

    @PostMapping("/export")
    public AjaxResult export(@RequestParam Map<String, String> query) {
        return AjaxResult.success("本地 mock 导出已完成", Map.of("total", dsl.fetchCount(AI_KNOWLEDGE_BASE, condition(query))));
    }

    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
            .map(this::parseLong)
            .filter(Objects::nonNull)
            .toList();
        if (idList.isEmpty()) {
            return AjaxResult.error("没有需要删除的知识");
        }
        return AjaxResult.success(dsl.deleteFrom(AI_KNOWLEDGE_BASE)
            .where(AI_KNOWLEDGE_BASE.ID.in(idList))
            .execute());
    }

    private AjaxResult updateEnable(Long[] ids, String enable) {
        List<Long> idList = idList(ids);
        if (idList.isEmpty()) {
            return AjaxResult.error("没有需要处理的知识");
        }
        int rows = dsl.update(AI_KNOWLEDGE_BASE)
            .set(AI_KNOWLEDGE_BASE.ENABLE, enable)
            .set(AI_KNOWLEDGE_BASE.UPDATE_BY, "system")
            .set(AI_KNOWLEDGE_BASE.UPDATE_TIME, LocalDateTime.now())
            .where(AI_KNOWLEDGE_BASE.ID.in(idList))
            .execute();
        return AjaxResult.success(rows);
    }

    private Condition condition(Map<String, String> query) {
        return DSL.and(
            likeIfPresent(AI_KNOWLEDGE_BASE.CONTENT, query.get("content")),
            likeIfPresent(AI_KNOWLEDGE_BASE.SOURCE, query.get("source")),
            eqIfPresent(AI_KNOWLEDGE_BASE.TYPE, query.get("type")),
            eqIfPresent(AI_KNOWLEDGE_BASE.STATUS, query.get("status")),
            eqIfPresent(AI_KNOWLEDGE_BASE.ENABLE, query.get("enable"))
        );
    }

    private Map<String, Object> mapKnowledge(Record record) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("id", record.get(AI_KNOWLEDGE_BASE.ID));
        row.put("content", record.get(AI_KNOWLEDGE_BASE.CONTENT));
        row.put("source", record.get(AI_KNOWLEDGE_BASE.SOURCE));
        row.put("type", record.get(AI_KNOWLEDGE_BASE.TYPE));
        row.put("status", record.get(AI_KNOWLEDGE_BASE.STATUS));
        row.put("enable", record.get(AI_KNOWLEDGE_BASE.ENABLE));
        row.put("fId", record.get(AI_KNOWLEDGE_BASE.F_ID));
        row.put("vectorId", record.get(AI_KNOWLEDGE_BASE.VECTOR_ID));
        row.put("createBy", record.get(AI_KNOWLEDGE_BASE.CREATE_BY));
        row.put("createTime", record.get(AI_KNOWLEDGE_BASE.CREATE_TIME));
        row.put("updateBy", record.get(AI_KNOWLEDGE_BASE.UPDATE_BY));
        row.put("updateTime", record.get(AI_KNOWLEDGE_BASE.UPDATE_TIME));
        row.put("remark", record.get(AI_KNOWLEDGE_BASE.REMARK));
        return row;
    }

    private TableDataInfo table(List<?> rows, long total) {
        TableDataInfo table = new TableDataInfo();
        table.setCode(200);
        table.setMsg("查询成功");
        table.setRows(rows);
        table.setTotal(total);
        return table;
    }

    private List<Long> idList(Long[] ids) {
        return ids == null ? List.of() : Arrays.stream(ids).filter(Objects::nonNull).toList();
    }

    private org.jooq.Condition eqIfPresent(org.jooq.Field<String> field, String value) {
        return isBlank(value) ? DSL.noCondition() : field.eq(value);
    }

    private org.jooq.Condition likeIfPresent(org.jooq.Field<String> field, String value) {
        return isBlank(value) ? DSL.noCondition() : field.like("%" + value + "%");
    }

    private Object value(Map<String, Object> payload, String... keys) {
        if (payload == null) {
            return null;
        }
        for (String key : keys) {
            Object value = payload.get(key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    private String stringValue(Map<String, Object> payload, String... keys) {
        Object value = value(payload, keys);
        return value == null ? null : String.valueOf(value);
    }

    private String defaultText(String value, String fallback) {
        return isBlank(value) ? fallback : value;
    }

    private Long parseLong(Object value) {
        try {
            return value == null || String.valueOf(value).isBlank() ? null : Long.valueOf(String.valueOf(value));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private int parseInt(String value, int fallback) {
        try {
            return isBlank(value) ? fallback : Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
