/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.utils;

public class ChatTypeConvertUtil {
    public static String convertToChinese(String chatType) {
        if (chatType == null || chatType.trim().isEmpty()) {
            return "\u5176\u4ed6";
        }
        switch (chatType.toUpperCase()) {
            case "FAQ": {
                return "\u95ee\u7b54";
            }
            case "FINDING_PLACES": {
                return "\u5bfb\u8def";
            }
            case "FLIGHT": {
                return "\u822a\u73ed\u52a8\u6001\u76f8\u5173";
            }
            case "ACCESS": {
                return "\u8d35\u5bbe\u5ba4\u51c6\u5165";
            }
            case "OTHER": {
                return "\u5176\u4ed6";
            }
        }
        return "\u5176\u4ed6";
    }
}
