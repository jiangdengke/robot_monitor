/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.robotmonitor.common.utils;

import com.robotmonitor.common.core.domain.robot.GenerateVoiceRequest;
import com.robotmonitor.common.utils.RedisRequestClient;
import com.robotmonitor.common.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

public class CreateVoiceUtils {
    private static final Logger log = LoggerFactory.getLogger(CreateVoiceUtils.class);

    public static String createVoiceUrl(String robotUrl, String text, String language) {
        log.info("\u5f00\u59cb\u521b\u5efa\u8bed\u97f3\uff0c\u6587\u672c\u957f\u5ea6: {}", (Object)text.length());
        String url = robotUrl + "/api/generate";
        Object voiceUrl = "";
        try {
            voiceUrl = robotUrl + HttpUtils.sendPost(url, "text=" + text + "&language=" + language, false);
        }
        catch (Exception e) {
            log.error("\u8bed\u97f3\u751f\u6210\u5931\u8d25\uff0c\u9519\u8bef\u4fe1\u606f: {}", (Object)e.getMessage(), (Object)e);
        }
        return voiceUrl;
    }

    public static String createVoice(String robotUrl, String text, String language) {
        log.info("\u5f00\u59cb\u521b\u5efa\u8bed\u97f3\uff0c\u6587\u672c\u957f\u5ea6: {}", (Object)text.length());
        String url = robotUrl + "/api/generateString";
        String voice = "";
        try {
            voice = HttpUtils.sendPost(url, "text=" + text + "&language=" + language, false);
        }
        catch (Exception e) {
            log.error("\u8bed\u97f3\u751f\u6210\u5931\u8d25\uff0c\u9519\u8bef\u4fe1\u606f: {}", (Object)e.getMessage(), (Object)e);
        }
        return voice;
    }

    public static String createVoice(StringRedisTemplate stringRedisTemplate, String text, String language) {
        RedisRequestClient redisRequestClient = new RedisRequestClient(stringRedisTemplate);
        return redisRequestClient.sendAndReceive("generateStringVoiceQueue", new GenerateVoiceRequest(text, language), String.class, 30);
    }
}
