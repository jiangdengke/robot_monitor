package com.robotmonitor.web.controller.api;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_TABLE;

import com.robotmonitor.common.core.domain.AjaxResult;
import java.time.LocalDateTime;
import java.util.Map;
import org.jooq.DSLContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/table")
public class TableActionController {
    private final DSLContext dsl;

    public TableActionController(DSLContext dsl) {
        this.dsl = dsl;
    }

    @PostMapping
    public AjaxResult updateStatus(@RequestBody Map<String, Object> request) {
        Long id = parseLong(request.get("id"));
        String status = stringValue(request.get("status"));
        if (id == null) {
            return AjaxResult.error("桌台 ID 不能为空");
        }
        if (status == null || status.isBlank()) {
            status = "0";
        }
        return AjaxResult.success(dsl.update(CONFIG_TABLE)
            .set(CONFIG_TABLE.STATUS, status)
            .set(CONFIG_TABLE.UPDATE_TIME, LocalDateTime.now())
            .where(CONFIG_TABLE.ID.eq(id))
            .execute());
    }

    private Long parseLong(Object value) {
        try {
            return value == null ? null : Long.valueOf(String.valueOf(value));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private String stringValue(Object value) {
        return value == null ? null : String.valueOf(value);
    }
}
