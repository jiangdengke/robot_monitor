package com.robotmonitor.system.mapper.jooq;

import com.robotmonitor.common.core.domain.BaseEntity;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

final class JooqSystemMapperSupport {
    private JooqSystemMapperSupport() {
    }

    static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    static Date toDate(LocalDateTime value) {
        return value == null ? null : java.sql.Timestamp.valueOf(value);
    }

    static LocalDateTime toLocalDateTime(Date value) {
        if (value == null) {
            return null;
        }
        return new java.sql.Timestamp(value.getTime()).toLocalDateTime();
    }

    static Condition contains(Field<String> field, String value) {
        return hasText(value) ? field.like("%" + value + "%") : DSL.noCondition();
    }

    static Condition equalsIfPresent(Field<String> field, String value) {
        return hasText(value) ? field.eq(value) : DSL.noCondition();
    }

    static <T> Condition equalsIfPresent(Field<T> field, T value) {
        return value == null ? DSL.noCondition() : field.eq(value);
    }

    static <T> Condition inIfPresent(Field<T> field, Collection<T> values) {
        return values == null || values.isEmpty() ? DSL.noCondition() : field.in(values);
    }

    static Condition betweenParams(Field<LocalDateTime> field, BaseEntity entity) {
        if (entity == null) {
            return DSL.noCondition();
        }
        Map<String, Object> params = entity.getParams();
        Object begin = params.get("beginTime");
        Object end = params.get("endTime");
        Condition condition = DSL.noCondition();
        if (begin instanceof String beginText && hasText(beginText)) {
            condition = condition.and(field.ge(LocalDateTime.parse(beginText.replace(' ', 'T'))));
        }
        if (end instanceof String endText && hasText(endText)) {
            condition = condition.and(field.le(LocalDateTime.parse(endText.replace(' ', 'T'))));
        }
        return condition;
    }
}
