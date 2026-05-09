/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.utils.sql;

import com.robotmonitor.common.exception.UtilException;
import com.robotmonitor.common.utils.StringUtils;

public class SqlUtil {
    public static String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !SqlUtil.isValidOrderBySql(value)) {
            throw new UtilException("\u53c2\u6570\u4e0d\u7b26\u5408\u89c4\u8303\uff0c\u4e0d\u80fd\u8fdb\u884c\u67e5\u8be2");
        }
        return value;
    }

    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }

    public static void filterKeyword(String value) {
        String[] sqlKeywords;
        if (StringUtils.isEmpty(value)) {
            return;
        }
        for (String sqlKeyword : sqlKeywords = StringUtils.split((String)SQL_REGEX, (String)"\\|")) {
            if (StringUtils.indexOfIgnoreCase((CharSequence)value, (CharSequence)sqlKeyword) <= -1) continue;
            throw new UtilException("\u53c2\u6570\u5b58\u5728SQL\u6ce8\u5165\u98ce\u9669");
        }
    }
}
