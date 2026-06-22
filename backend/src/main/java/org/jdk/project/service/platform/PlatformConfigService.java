package org.jdk.project.service.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.platform.PlatformBootstrapConfigDto;
import org.jdk.project.dto.platform.PlatformBootstrapConfigUpsertRequest;
import org.jdk.project.dto.platform.PlatformBootstrapDto;
import org.jdk.project.exception.BusinessException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.generated.project.tables.PlatformBootstrapConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlatformConfigService {

  private static final PlatformBootstrapConfig PLATFORM_BOOTSTRAP =
      PlatformBootstrapConfig.PLATFORM_BOOTSTRAP_CONFIG;

  private final ObjectMapper objectMapper;
  private final ResourceLoader resourceLoader;
  private final DSLContext dsl;

  @Value("${platform.bootstrap-location:classpath:platform/templates/lounge-greeting/bootstrap.json}")
  private String bootstrapLocation;

  public PlatformBootstrapDto getBootstrap() {
    return getBootstrapFromDatabase().orElseGet(this::getBootstrapFromTemplate);
  }

  public ListResponse<PlatformBootstrapConfigDto> listBootstrapConfigs() {
    List<PlatformBootstrapConfigDto> rows =
        dsl.selectFrom(PLATFORM_BOOTSTRAP)
            .orderBy(PLATFORM_BOOTSTRAP.UPDATED_AT.desc(), PLATFORM_BOOTSTRAP.ID.desc())
            .fetch(this::toConfigDto);
    return ListResponse.of(rows.size(), rows);
  }

  public Long saveBootstrapConfig(PlatformBootstrapConfigUpsertRequest request) {
    String configKey = defaultString(request.getConfigKey(), "default");
    String configJson = defaultString(request.getConfigJson(), null);
    if (configJson == null) {
      throw new BusinessException("平台启动配置 JSON 不能为空");
    }
    validateBootstrapConfig(configJson);

    Long existingId =
        dsl.select(PLATFORM_BOOTSTRAP.ID)
            .from(PLATFORM_BOOTSTRAP)
            .where(PLATFORM_BOOTSTRAP.CONFIG_KEY.eq(configKey))
            .fetchOptional(PLATFORM_BOOTSTRAP.ID)
            .orElse(null);
    if (existingId != null) {
      dsl.update(PLATFORM_BOOTSTRAP)
          .set(PLATFORM_BOOTSTRAP.CONFIG_JSON, configJson)
          .set(PLATFORM_BOOTSTRAP.ENABLED, request.getEnabled() == null || request.getEnabled())
          .set(PLATFORM_BOOTSTRAP.REMARK, defaultString(request.getRemark(), ""))
          .where(PLATFORM_BOOTSTRAP.ID.eq(existingId))
          .execute();
      return existingId;
    }

    dsl.insertInto(PLATFORM_BOOTSTRAP)
        .set(PLATFORM_BOOTSTRAP.CONFIG_KEY, configKey)
        .set(PLATFORM_BOOTSTRAP.CONFIG_JSON, configJson)
        .set(PLATFORM_BOOTSTRAP.ENABLED, request.getEnabled() == null || request.getEnabled())
        .set(PLATFORM_BOOTSTRAP.REMARK, defaultString(request.getRemark(), ""))
        .execute();
    return dsl.select(PLATFORM_BOOTSTRAP.ID)
        .from(PLATFORM_BOOTSTRAP)
        .where(PLATFORM_BOOTSTRAP.CONFIG_KEY.eq(configKey))
        .fetchOne(PLATFORM_BOOTSTRAP.ID);
  }

  private Optional<PlatformBootstrapDto> getBootstrapFromDatabase() {
    try {
      String configJson =
          dsl.select(PLATFORM_BOOTSTRAP.CONFIG_JSON)
              .from(PLATFORM_BOOTSTRAP)
              .where(PLATFORM_BOOTSTRAP.ENABLED.isTrue())
              .orderBy(PLATFORM_BOOTSTRAP.UPDATED_AT.desc(), PLATFORM_BOOTSTRAP.ID.desc())
              .limit(1)
              .fetchOptional(PLATFORM_BOOTSTRAP.CONFIG_JSON)
              .orElse(null);
      if (configJson == null || configJson.isBlank()) {
        return Optional.empty();
      }
      return Optional.of(objectMapper.readValue(configJson, PlatformBootstrapDto.class));
    } catch (Exception exception) {
      log.warn("Failed to load platform bootstrap config from database, fallback to template", exception);
      return Optional.empty();
    }
  }

  private PlatformBootstrapDto getBootstrapFromTemplate() {
    Resource resource = resourceLoader.getResource(bootstrapLocation);
    try (InputStream inputStream = resource.getInputStream()) {
      return objectMapper.readValue(inputStream, PlatformBootstrapDto.class);
    } catch (IOException exception) {
      throw new IllegalStateException(
          "Failed to load platform bootstrap config: " + bootstrapLocation, exception);
    }
  }

  private PlatformBootstrapConfigDto toConfigDto(Record record) {
    return PlatformBootstrapConfigDto.builder()
        .id(record.get(PLATFORM_BOOTSTRAP.ID))
        .configKey(record.get(PLATFORM_BOOTSTRAP.CONFIG_KEY))
        .configJson(record.get(PLATFORM_BOOTSTRAP.CONFIG_JSON))
        .enabled(record.get(PLATFORM_BOOTSTRAP.ENABLED))
        .remark(record.get(PLATFORM_BOOTSTRAP.REMARK))
        .build();
  }

  private void validateBootstrapConfig(String configJson) {
    try {
      objectMapper.readValue(configJson, PlatformBootstrapDto.class);
    } catch (IOException exception) {
      throw new BusinessException("平台启动配置 JSON 格式不正确", exception);
    }
  }

  private String defaultString(String value, String fallback) {
    if (value == null || value.isBlank()) {
      return fallback;
    }
    return value.trim();
  }
}
