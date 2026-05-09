/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.constant;

import java.util.HashMap;
import java.util.Map;

public class RobotLanguageConstants {
    public static final String ROBOT_LANGUAGE_CN = "CN";
    public static final String WHISPER_LANGUAGE_CN = "zh";
    public static final String AI_CHAT_LANGUAGE_CN = "\u7b80\u4f53\u4e2d\u6587";
    public static final String QWEN3_LANGUAGE_CN = "Chinese";
    public static final String ROBOT_LANGUAGE_EN = "EN";
    public static final String WHISPER_LANGUAGE_EN = "en";
    public static final String AI_CHAT_LANGUAGE_EN = "\u82f1\u8bed";
    public static final String QWEN3_LANGUAGE_EN = "English";
    public static final String ROBOT_LANGUAGE_RU = "RU";
    public static final String WHISPER_LANGUAGE_RU = "ru";
    public static final String AI_CHAT_LANGUAGE_RU = "\u4fc4\u8bed";
    public static final String QWEN3_LANGUAGE_RU = "Russian";
    public static final String ROBOT_LANGUAGE_JP = "JP";
    public static final String WHISPER_LANGUAGE_JP = "ja";
    public static final String AI_CHAT_LANGUAGE_JP = "\u65e5\u8bed";
    public static final String QWEN3_LANGUAGE_JP = "Japanese";
    public static final Map<String, String> WHISPER_ROBOT_LANGUAGE_MAP = new HashMap<String, String>();
    public static final Map<String, String> ROBOT_CHAT_LANGUAGE_MAP = new HashMap<String, String>();
    public static final Map<String, String> QWEN3_LANGUAGE_MAP = new HashMap<String, String>();

    static {
        WHISPER_ROBOT_LANGUAGE_MAP.put(WHISPER_LANGUAGE_CN, ROBOT_LANGUAGE_CN);
        WHISPER_ROBOT_LANGUAGE_MAP.put(WHISPER_LANGUAGE_EN, ROBOT_LANGUAGE_EN);
        WHISPER_ROBOT_LANGUAGE_MAP.put(WHISPER_LANGUAGE_RU, ROBOT_LANGUAGE_RU);
        WHISPER_ROBOT_LANGUAGE_MAP.put(WHISPER_LANGUAGE_JP, ROBOT_LANGUAGE_JP);
        ROBOT_CHAT_LANGUAGE_MAP.put(ROBOT_LANGUAGE_CN, AI_CHAT_LANGUAGE_CN);
        ROBOT_CHAT_LANGUAGE_MAP.put(ROBOT_LANGUAGE_EN, AI_CHAT_LANGUAGE_EN);
        ROBOT_CHAT_LANGUAGE_MAP.put(ROBOT_LANGUAGE_RU, AI_CHAT_LANGUAGE_RU);
        ROBOT_CHAT_LANGUAGE_MAP.put(ROBOT_LANGUAGE_JP, AI_CHAT_LANGUAGE_JP);
        QWEN3_LANGUAGE_MAP.put(ROBOT_LANGUAGE_CN, QWEN3_LANGUAGE_CN);
        QWEN3_LANGUAGE_MAP.put(ROBOT_LANGUAGE_EN, QWEN3_LANGUAGE_EN);
        QWEN3_LANGUAGE_MAP.put(ROBOT_LANGUAGE_RU, QWEN3_LANGUAGE_RU);
        QWEN3_LANGUAGE_MAP.put(ROBOT_LANGUAGE_JP, QWEN3_LANGUAGE_JP);
    }
}
