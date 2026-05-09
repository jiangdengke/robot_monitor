/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.constant;

import java.util.HashMap;
import java.util.Map;

public class RobotChatConstants {
    public static final String ROBOT_CHAT_ROLE_ASSISTANT = "assistant";
    public static final String ROBOT_CHAT_ROLE_USER = "user";
    public static final String[] BOARDING_KEYWORDS = new String[]{"\u626b\u7801", "\u51c6\u5165", "\u8f6c\u5165", "\u626b\u9a6c"};
    public static final String[] GUIDE_KEYWORDS = new String[]{"\u5f15\u5bfc", "\u5e94\u5230", "\u5e26\u9886", "\u5e26\u5230", "\u5e26\u6211\u53bb"};
    public static final Map<String, String> DESTINATION_MAPPING = new HashMap<String, String>();

    static {
        DESTINATION_MAPPING.put("\u524d\u53f0", "\u524d\u53f0");
        DESTINATION_MAPPING.put("\u5927\u5385", "\u5927\u5385");
        DESTINATION_MAPPING.put("\u53a8\u623f", "\u53a8\u623f");
    }
}
