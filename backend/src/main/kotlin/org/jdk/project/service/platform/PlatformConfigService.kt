package org.jdk.project.service.platform

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.platform.PlatformBootstrapConfigDto
import org.jdk.project.dto.platform.PlatformBootstrapConfigUpsertRequest
import org.jdk.project.dto.platform.PlatformBootstrapDto
import org.jdk.project.exception.BusinessException
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.generated.project.tables.PlatformBootstrapConfig as PlatformBootstrapConfigTable
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class PlatformConfigService(
    private val objectMapper: ObjectMapper,
    private val resourceLoader: ResourceLoader,
    private val dsl: DSLContext,
    @Value("\${platform.bootstrap-location:classpath:platform/templates/lounge-greeting/bootstrap.json}")
    private val bootstrapLocation: String,
) {
    fun getBootstrap(): PlatformBootstrapDto = getBootstrapFromDatabase() ?: getBootstrapFromTemplate()

    fun listBootstrapConfigs(): ListResponse<PlatformBootstrapConfigDto> {
        val rows =
            dsl.selectFrom(PLATFORM_BOOTSTRAP)
                .orderBy(PLATFORM_BOOTSTRAP.UPDATED_AT.desc(), PLATFORM_BOOTSTRAP.ID.desc())
                .fetch { record -> toConfigDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }

    fun saveBootstrapConfig(request: PlatformBootstrapConfigUpsertRequest): Long {
        val configKey = defaultString(request.configKey, "default") ?: "default"
        val configJson =
            defaultString(request.configJson, null)
                ?: throw BusinessException("平台启动配置 JSON 不能为空")
        validateBootstrapConfig(configJson)

        val existingId =
            dsl.select(PLATFORM_BOOTSTRAP.ID)
                .from(PLATFORM_BOOTSTRAP)
                .where(PLATFORM_BOOTSTRAP.CONFIG_KEY.eq(configKey))
                .fetchOptional(PLATFORM_BOOTSTRAP.ID)
                .orElse(null)
        if (existingId != null) {
            dsl.update(PLATFORM_BOOTSTRAP)
                .set(PLATFORM_BOOTSTRAP.CONFIG_JSON, configJson)
                .set(PLATFORM_BOOTSTRAP.ENABLED, request.enabled != false)
                .set(PLATFORM_BOOTSTRAP.REMARK, defaultString(request.remark, ""))
                .where(PLATFORM_BOOTSTRAP.ID.eq(existingId))
                .execute()
            return existingId
        }

        dsl.insertInto(PLATFORM_BOOTSTRAP)
            .set(PLATFORM_BOOTSTRAP.CONFIG_KEY, configKey)
            .set(PLATFORM_BOOTSTRAP.CONFIG_JSON, configJson)
            .set(PLATFORM_BOOTSTRAP.ENABLED, request.enabled != false)
            .set(PLATFORM_BOOTSTRAP.REMARK, defaultString(request.remark, ""))
            .execute()
        return dsl.select(PLATFORM_BOOTSTRAP.ID)
            .from(PLATFORM_BOOTSTRAP)
            .where(PLATFORM_BOOTSTRAP.CONFIG_KEY.eq(configKey))
            .fetchOne(PLATFORM_BOOTSTRAP.ID)
            ?: throw IllegalStateException("Failed to read saved platform bootstrap config id")
    }

    private fun getBootstrapFromDatabase(): PlatformBootstrapDto? =
        try {
            val configJson =
                dsl.select(PLATFORM_BOOTSTRAP.CONFIG_JSON)
                    .from(PLATFORM_BOOTSTRAP)
                    .where(PLATFORM_BOOTSTRAP.ENABLED.isTrue())
                    .orderBy(PLATFORM_BOOTSTRAP.UPDATED_AT.desc(), PLATFORM_BOOTSTRAP.ID.desc())
                    .limit(1)
                    .fetchOptional(PLATFORM_BOOTSTRAP.CONFIG_JSON)
                    .orElse(null)
            if (configJson.isNullOrBlank()) {
                null
            } else {
                objectMapper.readValue(configJson, PlatformBootstrapDto::class.java)
            }
        } catch (exception: Exception) {
            log.warn("Failed to load platform bootstrap config from database, fallback to template", exception)
            null
        }

    private fun getBootstrapFromTemplate(): PlatformBootstrapDto {
        val resource = resourceLoader.getResource(bootstrapLocation)
        try {
            resource.inputStream.use { inputStream ->
                return objectMapper.readValue(inputStream, PlatformBootstrapDto::class.java)
            }
        } catch (exception: IOException) {
            throw IllegalStateException(
                "Failed to load platform bootstrap config: $bootstrapLocation",
                exception,
            )
        }
    }

    private fun toConfigDto(record: Record): PlatformBootstrapConfigDto =
        PlatformBootstrapConfigDto(
            id = record.get(PLATFORM_BOOTSTRAP.ID),
            configKey = record.get(PLATFORM_BOOTSTRAP.CONFIG_KEY),
            configJson = record.get(PLATFORM_BOOTSTRAP.CONFIG_JSON),
            enabled = record.get(PLATFORM_BOOTSTRAP.ENABLED),
            remark = record.get(PLATFORM_BOOTSTRAP.REMARK),
        )

    private fun validateBootstrapConfig(configJson: String) {
        try {
            objectMapper.readValue(configJson, PlatformBootstrapDto::class.java)
        } catch (exception: IOException) {
            throw BusinessException("平台启动配置 JSON 格式不正确", exception)
        }
    }

    private fun defaultString(
        value: String?,
        fallback: String?,
    ): String? = if (value.isNullOrBlank()) fallback else value.trim()

    private companion object {
        private val log = LoggerFactory.getLogger(PlatformConfigService::class.java)
        private val PLATFORM_BOOTSTRAP = PlatformBootstrapConfigTable.PLATFORM_BOOTSTRAP_CONFIG
    }
}
