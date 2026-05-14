package org.jdk.project.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局 CORS 配置。
 *
 * <p>从 application.yml 读取允许的来源、方法、请求头、暴露头，统一注册到所有路径。
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Value("${cors.allowedOrigins}")
  private String allowedOrigins;

  @Value("${cors.allowedMethods}")
  private String allowedMethods;

  @Value("${cors.allowedHeaders}")
  private String allowedHeaders;

  @Value("${cors.allowedExposeHeaders}")
  private String allowedExposeHeaders;

  /**
   * 注册 CORS 映射到所有接口路径。
   *
   * @param registry Spring MVC 的 CORS 注册器
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }

  /**
   * 构建 CORS 配置源。
   *
   * @return CorsConfigurationSource 对象，供 Spring Security 使用
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
    configuration.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));
    configuration.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));
    configuration.setExposedHeaders(Arrays.asList(allowedExposeHeaders.split(",")));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
