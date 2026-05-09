/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.constant;

import java.util.HashMap;
import java.util.Map;

public class PushMessageConstants {
    public static final String PUSH_MESSAGE_TYPE_LISTEN_STATUS = "listen";
    public static final String PUSH_MESSAGE_TYPE_WELCOME = "welcome";
    public static final String PUSH_MESSAGE_TYPE_AI = "ai";
    public static final String PUSH_MESSAGE_TYPE_GUIDE = "guide";
    public static final String PUSH_MESSAGE_TYPE_DIGITAL_TWIN = "digitalTwin";
    public static final String PUSH_MESSAGE_TYPE_NOTICE = "notice";
    public static final String WEB_SOCKET_DESTINATION_TYPE_WELCOME = "/queue/welcome";
    public static final String WEB_SOCKET_DESTINATION_TYPE_AI = "/queue/aiResponse";
    public static final String WEB_SOCKET_DESTINATION_TYPE_DIGITAL_TWIN = "/queue/digitalTwin";
    public static final String WEB_SOCKET_DESTINATION_TYPE_LISTEN_STATUS = "/queue/listen";
    public static final String WEB_SOCKET_DESTINATION_TYPE_GUIDE = "/queue/guide";
    public static final String WEB_SOCKET_DESTINATION_TYPE_NOTICE = "/queue/notice";
    public static final Map<String, String> TYPE_WEB_SOCKET_DESTINATION_MAP = new HashMap<String, String>();

    static {
        TYPE_WEB_SOCKET_DESTINATION_MAP.put(PUSH_MESSAGE_TYPE_WELCOME, WEB_SOCKET_DESTINATION_TYPE_WELCOME);
        TYPE_WEB_SOCKET_DESTINATION_MAP.put(PUSH_MESSAGE_TYPE_AI, WEB_SOCKET_DESTINATION_TYPE_AI);
        TYPE_WEB_SOCKET_DESTINATION_MAP.put(PUSH_MESSAGE_TYPE_DIGITAL_TWIN, WEB_SOCKET_DESTINATION_TYPE_DIGITAL_TWIN);
        TYPE_WEB_SOCKET_DESTINATION_MAP.put(PUSH_MESSAGE_TYPE_LISTEN_STATUS, WEB_SOCKET_DESTINATION_TYPE_LISTEN_STATUS);
        TYPE_WEB_SOCKET_DESTINATION_MAP.put(PUSH_MESSAGE_TYPE_GUIDE, WEB_SOCKET_DESTINATION_TYPE_GUIDE);
        TYPE_WEB_SOCKET_DESTINATION_MAP.put(PUSH_MESSAGE_TYPE_NOTICE, WEB_SOCKET_DESTINATION_TYPE_NOTICE);
    }
}
