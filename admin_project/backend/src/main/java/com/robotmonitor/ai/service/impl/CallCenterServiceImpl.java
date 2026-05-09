/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.JsonUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.stereotype.Service
 *  org.springframework.util.MultiValueMap
 *  org.springframework.web.client.HttpClientErrorException
 *  org.springframework.web.client.RestTemplate
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.domain.EsChatRequest;
import com.robotmonitor.ai.domain.EsChatResponse;
import com.robotmonitor.ai.service.CallCenterService;
import com.robotmonitor.ai.utils.AiUtils;
import com.robotmonitor.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CallCenterServiceImpl
implements CallCenterService {
    private static final Logger log = LoggerFactory.getLogger(CallCenterServiceImpl.class);
    @Value(value="${call-center.es-chat-url}")
    private String ES_CHAT_URL;
    @Value(value="${call-center.problem-source}")
    private String PROBLEM_SOURCE;
    @Value(value="${call-center.agent}")
    private String AGENT;
    @Value(value="${call-center.default-long-sentence}")
    private String DEFAULT_LONG_SENTENCE;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String esChat(String content, String language, String robotId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            EsChatRequest esChatRequest = new EsChatRequest();
            esChatRequest.setProblemSource(this.PROBLEM_SOURCE);
            esChatRequest.setAgent(this.AGENT + robotId);
            esChatRequest.setLongSentence(this.DEFAULT_LONG_SENTENCE.formatted(AiUtils.getPromptLanguage(language), content));
            HttpEntity httpEntity = new HttpEntity((Object)esChatRequest, (MultiValueMap)headers);
            log.info("\u8c03\u7528call center \u5bf9\u8bdd\u63a5\u53e3: {}, \u53c2\u6570: {}", (Object)this.ES_CHAT_URL, (Object)JsonUtils.obj2String((Object)esChatRequest));
            ResponseEntity response = this.restTemplate.exchange(this.ES_CHAT_URL, HttpMethod.POST, httpEntity, EsChatResponse.class, new Object[0]);
            EsChatResponse result = (EsChatResponse)response.getBody();
            log.info("call center \u5bf9\u8bdd\u63a5\u53e3\u8fd4\u56de: result={},", (Object)JsonUtils.obj2String((Object)result));
            if (null != result) {
                return result.getResult();
            }
            return "";
        }
        catch (HttpClientErrorException e) {
            return "";
        }
    }
}
