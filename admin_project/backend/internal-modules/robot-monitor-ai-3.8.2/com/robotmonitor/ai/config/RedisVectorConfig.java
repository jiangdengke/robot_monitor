/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ai.embedding.EmbeddingModel
 *  org.springframework.ai.vectorstore.redis.RedisVectorStore
 *  org.springframework.ai.vectorstore.redis.RedisVectorStore$MetadataField
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  redis.clients.jedis.DefaultJedisClientConfig
 *  redis.clients.jedis.HostAndPort
 *  redis.clients.jedis.JedisClientConfig
 *  redis.clients.jedis.JedisPooled
 */
package com.robotmonitor.ai.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;

@Configuration
public class RedisVectorConfig {
    @Value(value="${spring.data.redis.host}")
    private String redisHost;
    @Value(value="${spring.data.redis.port}")
    private int redisPort;
    @Value(value="${spring.data.redis.password:}")
    private String redisPassword;
    @Value(value="${spring.data.redis.database:0}")
    private int redisDatabase;
    @Value(value="${spring.ai.vectorstore.redis.index-name}")
    private String indexName;
    @Value(value="${spring.ai.vectorstore.redis.initialize-schema}")
    private boolean initializeSchema;
    @Value(value="${spring.ai.vectorstore.redis.prefix}")
    private String prefix;
    private static final String REDIS_VECTOR_TAG_ENABLE = "enable";
    private static final String REDIS_VECTOR_TAG_SOURCE = "source";
    private static final String REDIS_VECTOR_TAG_TYPE = "type";

    @Bean(destroyMethod="close")
    public JedisPooled jedisPooled() {
        HostAndPort hostAndPort = new HostAndPort(this.redisHost, this.redisPort);
        DefaultJedisClientConfig clientConfig = DefaultJedisClientConfig.builder().password(this.redisPassword).database(this.redisDatabase).build();
        return new JedisPooled(hostAndPort, (JedisClientConfig)clientConfig);
    }

    @Bean
    public RedisVectorStore vectorStore(JedisPooled jedis, EmbeddingModel embeddingModel) {
        return RedisVectorStore.builder((JedisPooled)jedis, (EmbeddingModel)embeddingModel).indexName(this.indexName).prefix(this.prefix).initializeSchema(this.initializeSchema).metadataFields(new RedisVectorStore.MetadataField[]{RedisVectorStore.MetadataField.tag((String)REDIS_VECTOR_TAG_ENABLE), RedisVectorStore.MetadataField.tag((String)REDIS_VECTOR_TAG_SOURCE), RedisVectorStore.MetadataField.tag((String)REDIS_VECTOR_TAG_TYPE)}).build();
    }
}
