package com.robotmonitor.food.mapper.jooq;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

final class JooqFoodMapperSupport {
    private JooqFoodMapperSupport() {
    }

    static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    static LocalDate toLocalDate(String value) {
        if (isBlank(value)) {
            return null;
        }
        return LocalDate.parse(value.substring(0, Math.min(10, value.length())));
    }

    static LocalDateTime toLocalDateTime(Date value) {
        return value == null ? null : LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }

    static Date toDate(LocalDateTime value) {
        return value == null ? null : Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

    static BigDecimal toBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    static Double toDouble(BigDecimal value) {
        return value == null ? null : value.doubleValue();
    }

    static Integer toInteger(String value) {
        if (isBlank(value)) {
            return null;
        }
        return Integer.valueOf(value);
    }

    static Integer toInteger(Long value) {
        return value == null ? null : value.intValue();
    }

    static String stringValue(Integer value) {
        return value == null ? null : String.valueOf(value);
    }
}
