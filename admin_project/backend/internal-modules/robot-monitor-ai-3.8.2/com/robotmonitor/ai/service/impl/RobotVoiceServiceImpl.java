/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.constant.RobotLanguageConstants
 *  com.robotmonitor.common.core.domain.robot.RobotVoice
 *  com.robotmonitor.common.core.domain.robot.RobotVoiceResponse
 *  com.robotmonitor.common.core.domain.robot.SenseVoiceRequest
 *  com.robotmonitor.common.core.domain.robot.VoiceResponse
 *  com.robotmonitor.common.core.domain.robot.WhisperVoiceRequest
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.http.MediaType
 *  org.springframework.http.client.reactive.ClientHttpConnector
 *  org.springframework.http.client.reactive.ReactorClientHttpConnector
 *  org.springframework.stereotype.Service
 *  org.springframework.web.reactive.function.client.WebClient
 *  org.springframework.web.reactive.function.client.WebClient$RequestBodySpec
 *  reactor.core.publisher.Mono
 *  reactor.netty.http.HttpProtocol
 *  reactor.netty.http.client.HttpClient
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.service.RobotVoiceService;
import com.robotmonitor.common.constant.RobotLanguageConstants;
import com.robotmonitor.common.core.domain.robot.RobotVoice;
import com.robotmonitor.common.core.domain.robot.RobotVoiceResponse;
import com.robotmonitor.common.core.domain.robot.SenseVoiceRequest;
import com.robotmonitor.common.core.domain.robot.VoiceResponse;
import com.robotmonitor.common.core.domain.robot.WhisperVoiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;

@Service
public class RobotVoiceServiceImpl
implements RobotVoiceService {
    private static final Logger log = LoggerFactory.getLogger(RobotVoiceServiceImpl.class);
    @Value(value="${robot.voice-url.sense-voice}")
    private String senseVoiceUrl;
    @Value(value="${robot.voice-url.whisper-voice}")
    private String whisperVoiceUrl;

    @Override
    public Mono<RobotVoiceResponse> listen(RobotVoice robotVoice) {
        HttpClient httpClient = HttpClient.create().protocol(new HttpProtocol[]{HttpProtocol.HTTP11});
        WebClient webClient = WebClient.builder().clientConnector((ClientHttpConnector)new ReactorClientHttpConnector(httpClient)).build();
        Mono voiceResponseMono = ((WebClient.RequestBodySpec)webClient.post().uri(this.senseVoiceUrl, new Object[0])).contentType(MediaType.APPLICATION_JSON).bodyValue((Object)new SenseVoiceRequest(robotVoice.getVoice())).retrieve().bodyToMono(VoiceResponse.class).doOnError(e -> log.warn("senseVoiceUrl request failed", e));
        Mono whisperResponseMono = ((WebClient.RequestBodySpec)webClient.post().uri(this.whisperVoiceUrl, new Object[0])).contentType(MediaType.APPLICATION_JSON).bodyValue((Object)new WhisperVoiceRequest(robotVoice.getVoice())).retrieve().bodyToMono(VoiceResponse.class).doOnError(e -> log.warn("whisperVoiceUrl request failed", e));
        return Mono.zipDelayError((Mono)voiceResponseMono, (Mono)whisperResponseMono).map(tuple -> {
            VoiceResponse voiceResponse = (VoiceResponse)tuple.getT1();
            VoiceResponse whisperVoiceResponse = (VoiceResponse)tuple.getT2();
            log.info("voiceResponse : {}", (Object)voiceResponse);
            log.info("text : {}", (Object)voiceResponse.getText());
            log.info("whisperVoiceResponse : {}", (Object)whisperVoiceResponse);
            RobotVoiceResponse response = new RobotVoiceResponse();
            response.setText(".".equals(voiceResponse.getText()) ? whisperVoiceResponse.getText() : voiceResponse.getText());
            response.setLanguage(this.getLanguage(whisperVoiceResponse.getLanguage()));
            return response;
        }).onErrorResume(throwable -> voiceResponseMono.flatMap(voiceResponse -> {
            RobotVoiceResponse response = new RobotVoiceResponse();
            response.setText(voiceResponse.getText());
            response.setLanguage("CN");
            return Mono.just((Object)response);
        }).switchIfEmpty(whisperResponseMono.flatMap(whisperVoiceResponse -> {
            RobotVoiceResponse response = new RobotVoiceResponse();
            response.setText(whisperVoiceResponse.getText());
            response.setLanguage(this.getLanguage(whisperVoiceResponse.getLanguage()));
            return Mono.just((Object)response);
        }))).doOnTerminate(() -> log.info("POST {} and {} completed", (Object)this.senseVoiceUrl, (Object)this.whisperVoiceUrl));
    }

    private String getLanguage(String language) {
        return RobotLanguageConstants.WHISPER_ROBOT_LANGUAGE_MAP.getOrDefault(language, "CN");
    }
}
