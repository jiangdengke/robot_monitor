/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.kafka.common.serialization.StringDeserializer
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
 *  org.springframework.kafka.core.ConsumerFactory
 *  org.springframework.kafka.core.DefaultKafkaConsumerFactory
 */
package com.robotmonitor.flight.mq;

import java.util.HashMap;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class FaceKafkaConfig {
    @Value(value="${spring.kafka.face.bootstrap-servers}")
    private String bootstrapServers;
    @Value(value="${spring.kafka.face.group-id}")
    private String groupId;
    @Value(value="${spring.kafka.face.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public ConsumerFactory<String, String> faceConsumerFactory() {
        HashMap<String, Object> props = new HashMap<String, Object>();
        props.put("bootstrap.servers", this.bootstrapServers);
        props.put("group.id", this.groupId);
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        props.put("auto.offset.reset", this.autoOffsetReset);
        return new DefaultKafkaConsumerFactory(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> faceKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(this.faceConsumerFactory());
        return factory;
    }
}
