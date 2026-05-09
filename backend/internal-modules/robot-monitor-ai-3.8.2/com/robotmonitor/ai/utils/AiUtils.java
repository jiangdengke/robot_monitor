/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.constant.RobotLanguageConstants
 *  com.robotmonitor.common.core.domain.config.ConfigAudio
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 *  com.robotmonitor.config.service.IConfigAudioService
 *  org.apache.logging.log4j.util.Strings
 */
package com.robotmonitor.ai.utils;

import com.robotmonitor.common.constant.RobotLanguageConstants;
import com.robotmonitor.common.core.domain.config.ConfigAudio;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.config.service.IConfigAudioService;
import org.apache.logging.log4j.util.Strings;

public class AiUtils {
    public static String removeThinkTag(String input) {
        if (Strings.isBlank((String)input)) {
            return input;
        }
        return input.replaceAll("(?s)<think>.*?</think>\\s*", "");
    }

    public static String getPromptLanguage(String language) {
        return RobotLanguageConstants.ROBOT_CHAT_LANGUAGE_MAP.getOrDefault(language, "\u7b80\u4f53\u4e2d\u6587");
    }

    public static void setDefaultMessageAndVoice(IConfigAudioService configAudioService, String language, String location, String audioType, RobotChatResponse robotChatResponse) {
        ConfigAudio configAudio = configAudioService.getConfigAudioByKeyAndLanguageAndRoomCode(audioType, language, location);
        if (null != configAudio) {
            robotChatResponse.setMessage(configAudio.getTextInfo());
            robotChatResponse.setAudioUrl("/api/voice/config/" + configAudio.getId());
        } else {
            robotChatResponse.setMessage(AiUtils.getDefaultMessageByAudioType(audioType, language));
        }
    }

    public static String getDefaultMessageByAudioType(String audioType, String language) {
        switch (audioType) {
            case "ACCESS_START": {
                return AiUtils.getAccessMessage(language);
            }
            case "KNOWLEDGE_CHAT_UNKNOWN": {
                return AiUtils.getUnknownMessage(language);
            }
            case "PLACE_NOT_FOUND": {
                return AiUtils.getNotFindMessage(language);
            }
            case "FLIGHT_NOT_FOUND": {
                return AiUtils.getNotFindFlightMessage(language);
            }
        }
        return AiUtils.getOtherMessage(language);
    }

    private static String getAccessMessage(String language) {
        return switch (language) {
            case "EN" -> "Okay, please show me your ID. I'll help you scan it for access.";
            case "RU" -> "\u0425\u043e\u0440\u043e\u0448\u043e, \u043f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430, \u043f\u043e\u043a\u0430\u0436\u0438\u0442\u0435 \u0432\u0430\u0448 \u0434\u043e\u043a\u0443\u043c\u0435\u043d\u0442, \u044f \u043f\u043e\u043c\u043e\u0433\u0443 \u0432\u0430\u043c \u043e\u0442\u0441\u043a\u0430\u043d\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0434\u043b\u044f \u0432\u0445\u043e\u0434\u0430.";
            default -> "\u597d\u7684\uff0c\u8bf7\u51fa\u793a\u60a8\u7684\u8bc1\u4ef6\uff0c\u6211\u6765\u5e2e\u60a8\u626b\u7801\u51c6\u5165\u3002";
        };
    }

    private static String getUnknownMessage(String language) {
        return switch (language) {
            case "EN" -> "Sorry, I am still studying and unable to answer your question. Please contact the lounge staff.";
            case "RU" -> "\u0418\u0437\u0432\u0438\u043d\u0438\u0442\u0435, \u044f \u0432\u0441\u0435 \u0435\u0449\u0435 \u0443\u0447\u0443\u0441\u044c, \u043d\u0435 \u043c\u043e\u0433\u0443 \u043e\u0442\u0432\u0435\u0442\u0438\u0442\u044c \u043d\u0430 \u0432\u0430\u0448 \u0432\u043e\u043f\u0440\u043e\u0441, \u043f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430, \u0441\u0432\u044f\u0436\u0438\u0442\u0435\u0441\u044c \u0441 \u043f\u0435\u0440\u0441\u043e\u043d\u0430\u043b\u043e\u043c \u0432 \u0433\u043e\u0441\u0442\u0438\u043d\u043e\u0439.";
            default -> "\u62b1\u6b49\uff0c\u6211\u8fd8\u5728\u5b66\u4e60\u4e2d\uff0c\u65e0\u6cd5\u56de\u7b54\u60a8\u7684\u8fd9\u4e2a\u95ee\u9898\uff0c\u8bf7\u60a8\u8054\u7cfb\u4f11\u606f\u5ba4\u5de5\u4f5c\u4eba\u5458";
        };
    }

    private static String getNotFindMessage(String language) {
        return switch (language) {
            case "EN" -> "Sorry, the desired destination has not been found yet.";
            case "RU" -> "\u0418\u0437\u0432\u0438\u043d\u0438\u0442\u0435, \u0432\u0440\u0435\u043c\u0435\u043d\u043d\u043e \u043d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043d\u0430\u0439\u0442\u0438 \u043d\u0443\u0436\u043d\u044b\u0439 \u043f\u0443\u043d\u043a\u0442 \u043d\u0430\u0437\u043d\u0430\u0447\u0435\u043d\u0438\u044f.";
            default -> "\u62b1\u6b49\uff0c\u6682\u65f6\u672a\u627e\u5230\u6240\u9700\u7684\u76ee\u7684\u5730\u3002";
        };
    }

    private static String getNotFindFlightMessage(String language) {
        return switch (language) {
            case "EN" -> "Sorry, we haven't found the flight information you want to know yet";
            case "RU" -> "\u0418\u0437\u0432\u0438\u043d\u0438\u0442\u0435, \u043d\u043e \u0432\u044b \u043d\u0435 \u0437\u0430\u043f\u0440\u043e\u0441\u0438\u043b\u0438 \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044e \u043e \u0440\u0435\u0439\u0441\u0435, \u043a\u043e\u0442\u043e\u0440\u0443\u044e \u0445\u043e\u0442\u0435\u043b\u0438 \u0443\u0437\u043d\u0430\u0442\u044c.";
            default -> "\u62b1\u6b49\uff0c\u6682\u672a\u67e5\u8be2\u5230\u4f60\u60f3\u4e86\u89e3\u7684\u822a\u73ed\u4fe1\u606f";
        };
    }

    private static String getOtherMessage(String language) {
        return switch (language) {
            case "EN" -> "Sorry, I am still studying and unable to answer your question. Please contact the lounge staff.";
            case "RU" -> "\u0418\u0437\u0432\u0438\u043d\u0438\u0442\u0435, \u044f \u0432\u0441\u0435 \u0435\u0449\u0435 \u0443\u0447\u0443\u0441\u044c, \u043d\u0435 \u043c\u043e\u0433\u0443 \u043e\u0442\u0432\u0435\u0442\u0438\u0442\u044c \u043d\u0430 \u0432\u0430\u0448 \u0432\u043e\u043f\u0440\u043e\u0441, \u043f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430, \u0441\u0432\u044f\u0436\u0438\u0442\u0435\u0441\u044c \u0441 \u043f\u0435\u0440\u0441\u043e\u043d\u0430\u043b\u043e\u043c \u0432 \u0433\u043e\u0441\u0442\u0438\u043d\u043e\u0439.";
            default -> "\u62b1\u6b49\uff0c\u6211\u8fd8\u5728\u5b66\u4e60\u4e2d\uff0c\u65e0\u6cd5\u56de\u7b54\u60a8\u7684\u8fd9\u4e2a\u95ee\u9898\uff0c\u8bf7\u60a8\u8054\u7cfb\u4f11\u606f\u5ba4\u5de5\u4f5c\u4eba\u5458";
        };
    }
}
