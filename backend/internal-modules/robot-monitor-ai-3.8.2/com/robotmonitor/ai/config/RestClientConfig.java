/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ai.model.openai.autoconfigure.OpenAiConnectionProperties
 *  org.springframework.ai.openai.api.OpenAiApi
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.http.client.ClientHttpRequestFactory
 *  org.springframework.http.client.SimpleClientHttpRequestFactory
 *  org.springframework.web.client.RestClient
 *  org.springframework.web.client.RestClient$Builder
 */
package com.robotmonitor.ai.config;

import java.time.Duration;
import org.springframework.ai.model.openai.autoconfigure.OpenAiConnectionProperties;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean
    @Qualifier(value="MyRestClientBuilder")
    public RestClient.Builder myRestClientBuilder() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(Duration.ofMinutes(3L));
        requestFactory.setConnectTimeout(Duration.ofSeconds(30L));
        return RestClient.builder().requestFactory((ClientHttpRequestFactory)requestFactory);
    }

    @Bean
    public OpenAiApi openAiApi(OpenAiConnectionProperties openAiConnectionProperties, @Qualifier(value="MyRestClientBuilder") RestClient.Builder restClientBuilder) {
        return OpenAiApi.builder().baseUrl(openAiConnectionProperties.getBaseUrl()).apiKey(openAiConnectionProperties.getApiKey()).restClientBuilder(restClientBuilder).build();
    }
}
