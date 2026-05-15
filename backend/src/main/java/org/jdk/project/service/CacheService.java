package org.jdk.project.service;

import lombok.extern.slf4j.Slf4j;
import org.jdk.project.config.cache.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheService {
  /**
   * 根据标识获取验证码（缓存命中即返回）。
   *
   * @param identify 唯一标识（如手机号/邮箱等）
   * @return 验证码，未命中返回 null
   */
  @Cacheable(value = CacheConfig.VERIFY_CODE, key = "#identify", unless = "#result == null")
  public String getVerifyCodeBy(String identify) {
    return null;
  }

  /**
   * 写入或更新指定标识的验证码。
   *
   * @param identify 唯一标识
   * @param value 验证码值
   * @return 返回写入后的验证码值
   */
  @CachePut(value = CacheConfig.VERIFY_CODE, key = "#identify")
  public String upsertVerifyCodeBy(String identify, String value) {
    return value;
  }

  /**
   * 删除指定标识的验证码缓存。
   *
   * @param identify 唯一标识
   */
  @CacheEvict(value = CacheConfig.VERIFY_CODE, key = "#identify")
  public void removeVerifyCodeBy(String identify) {}

  /** 清空所有验证码缓存。 */
  @CacheEvict(value = CacheConfig.VERIFY_CODE, allEntries = true)
  public void clearAllVerifyCode() {}
}
