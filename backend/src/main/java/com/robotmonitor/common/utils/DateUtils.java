/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.time.DateFormatUtils
 *  org.apache.commons.lang3.time.DateUtils
 */
package com.robotmonitor.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtils
extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";
    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String YYYYMMDDHH = "yyyyMMddHH";
    public static String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String HH_MM_SS = "HH:mm:ss";
    private static String[] parsePatterns = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    public static Date getNowDate() {
        return new Date();
    }

    public static String getDate() {
        return DateUtils.dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return DateUtils.dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return DateUtils.dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(String format) {
        return DateUtils.parseDateToStr(format, new Date());
    }

    public static final String dateTime(Date date) {
        return DateUtils.parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(String format, String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format((Date)now, (String)"yyyy/MM/dd");
    }

    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format((Date)now, (String)"yyyyMMdd");
    }

    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return DateUtils.parseDate((String)str.toString(), (String[])parsePatterns);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return Math.abs((int)((date2.getTime() - date1.getTime()) / 86400000L));
    }

    public static int differentMinsByMillisecond(Date date1, Date date2) {
        return Math.abs((int)((date2.getTime() - date1.getTime()) / 60000L));
    }

    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 86400000L;
        long nh = 3600000L;
        long nm = 60000L;
        long diff = endDate.getTime() - nowDate.getTime();
        long day = diff / nd;
        long hour = diff % nd / nh;
        long min = diff % nd % nh / nm;
        return day + "\u5929" + hour + "\u5c0f\u65f6" + min + "\u5206\u949f";
    }

    public static String getDatePoorSecond(Date endDate, Date nowDate) {
        long nd = 86400000L;
        long nh = 3600000L;
        long nm = 60000L;
        long ns = 1000L;
        long diff = endDate.getTime() - nowDate.getTime();
        long day = diff / nd;
        long hour = diff % nd / nh;
        long min = diff % nd % nh / nm;
        long sec = diff % nd % nh % nm / ns;
        return day + "\u5929" + hour + "\u5c0f\u65f6" + min + "\u5206\u949f" + sec + "\u79d2";
    }

    public static Date toDate(LocalDateTime temporalAccessor) {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    public static Date toDate(LocalDate temporalAccessor) {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    public static Date getDateNowYYYYMMDDHHMM() {
        return DateUtils.parseDate(DateFormatUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm"));
    }

    public static Date getTodayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }
}
