package org.jdk.project.config.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 缓存配置（Redis 实现）。
 *
 * <p>定义缓存名称及其过期策略，这里示例为验证码缓存 1 分钟。
 */
@EnableCaching
@Configuration
public class CacheConfig {

  public static final String VERIFY_CODE = "verifyCode";

  /**
   * 定义 RedisCacheManager。
   *
   * @param connectionFactory Redis 连接工厂
   * @return RedisCacheManager
   */
  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

    // Cache configuration for verifyCode
    cacheConfigurations.put(
        VERIFY_CODE,
        RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(1))
            .disableCachingNullValues());

    return RedisCacheManager.builder(connectionFactory)
        .withInitialCacheConfigurations(cacheConfigurations)
        .build();
  }
}
