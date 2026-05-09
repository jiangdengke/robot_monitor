package com.robotmonitor.flight.mapper.jooq;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

final class JooqFlightMapperSupport {
    private static final DateTimeFormatter COMPACT_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private JooqFlightMapperSupport() {
    }

    static LocalDate toLocalDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String text = value.trim();
        if (text.length() == 8 && text.chars().allMatch(Character::isDigit)) {
            return LocalDate.parse(text, DateTimeFormatter.BASIC_ISO_DATE);
        }
        return LocalDate.parse(text.substring(0, Math.min(10, text.length())));
    }

    static LocalDate toLocalDate(Date value) {
        return value == null ? null : value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    static LocalDateTime toLocalDateTime(Date value) {
        return value == null ? null : LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }

    static LocalDateTime toLocalDateTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String text = value.trim();
        if (text.length() == 14 && text.chars().allMatch(Character::isDigit)) {
            return LocalDateTime.parse(text, COMPACT_DATE_TIME);
        }
        if (text.length() == 8 && text.chars().allMatch(Character::isDigit)) {
            return LocalDate.parse(text, DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay();
        }
        if (text.length() == 10) {
            return LocalDate.parse(text).atStartOfDay();
        }
        return LocalDateTime.parse(text.replace(' ', 'T'));
    }

    static Date toDate(LocalDate value) {
        return value == null ? null : Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    static Date toDate(LocalDateTime value) {
        return value == null ? null : Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

    static String toCompactDateTime(LocalDateTime value) {
        return value == null ? null : value.format(COMPACT_DATE_TIME);
    }

    static Long toLong(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Long.valueOf(value);
    }

    static Integer toInteger(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Integer.valueOf(value);
    }

    static String stringValue(Long value) {
        return value == null ? null : String.valueOf(value);
    }

    static String stringValue(Integer value) {
        return value == null ? null : String.valueOf(value);
    }

    static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
