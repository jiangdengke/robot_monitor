package org.jdk.project.service.configquery

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.config.AudioDto
import org.jdk.project.dto.config.ImageDto
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.LOUNGE
import org.jooq.generated.project.Tables.MEDIA_AUDIO
import org.jooq.generated.project.Tables.MEDIA_IMAGE
import org.springframework.stereotype.Service

@Service
class MediaConfigQueryService(
    private val dsl: DSLContext,
    private val mapper: ConfigQueryMapper,
) {
    fun listImages(): ListResponse<ImageDto> {
        val rows =
            dsl.select(
                MEDIA_IMAGE.ID,
                MEDIA_IMAGE.NAME,
                MEDIA_IMAGE.CATEGORY,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                MEDIA_IMAGE.WIDTH,
                MEDIA_IMAGE.HEIGHT,
                MEDIA_IMAGE.ENABLED,
                MEDIA_IMAGE.REMARK,
            ).from(MEDIA_IMAGE)
                .leftJoin(LOUNGE).on(MEDIA_IMAGE.LOUNGE_ID.eq(LOUNGE.ID))
                .orderBy(MEDIA_IMAGE.ID.asc())
                .fetch { record -> mapper.toImageDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }

    fun listAudios(category: String?): ListResponse<AudioDto> {
        val condition = if (category == null) MEDIA_AUDIO.ID.isNotNull else MEDIA_AUDIO.CATEGORY.eq(category)
        val rows =
            dsl.select(
                MEDIA_AUDIO.ID,
                MEDIA_AUDIO.AUDIO_KEY,
                MEDIA_AUDIO.CATEGORY,
                MEDIA_AUDIO.LANGUAGE_CODE,
                MEDIA_AUDIO.TEXT_CONTENT,
                MEDIA_AUDIO.AUDIO_CONTENT,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                MEDIA_AUDIO.REMARK,
            ).from(MEDIA_AUDIO)
                .leftJoin(LOUNGE).on(MEDIA_AUDIO.LOUNGE_ID.eq(LOUNGE.ID))
                .where(condition)
                .orderBy(MEDIA_AUDIO.ID.asc())
                .fetch { record -> mapper.toAudioDto(record) }
        return ListResponse.of(rows.size.toLong(), rows)
    }
}
