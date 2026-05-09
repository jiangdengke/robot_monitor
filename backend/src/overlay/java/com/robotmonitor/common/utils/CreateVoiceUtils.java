package com.robotmonitor.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.data.redis.core.StringRedisTemplate;

public class CreateVoiceUtils {
    public static String createVoiceUrl(String robotUrl, String text, String language) {
        return "mock-voice://" + normalize(language) + "/" + encode(text);
    }

    public static String createVoice(String robotUrl, String text, String language) {
        return createMockVoice(text, language);
    }

    public static String createVoice(StringRedisTemplate stringRedisTemplate, String text, String language) {
        return createMockVoice(text, language);
    }

    private static String createMockVoice(String text, String language) {
        return "mock-audio:" + normalize(language) + ":" + encode(text);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? "CN" : value.trim();
    }

    private static String encode(String value) {
        String source = value == null ? "" : value;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(source.getBytes(StandardCharsets.UTF_8));
    }
}
