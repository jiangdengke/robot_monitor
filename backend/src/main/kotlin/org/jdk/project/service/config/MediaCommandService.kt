package org.jdk.project.service.config

import org.jdk.project.dto.config.AudioUpsertRequest
import org.jdk.project.dto.config.ImageUpsertRequest
import org.jdk.project.service.config.ConfigCommandSupport.defaultInt
import org.jdk.project.service.config.ConfigCommandSupport.defaultString
import org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated
import org.jooq.DSLContext
import org.jooq.generated.project.Tables.MEDIA_AUDIO
import org.jooq.generated.project.Tables.MEDIA_IMAGE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MediaCommandService(
    private val dsl: DSLContext,
) {
    @Transactional
    fun createImage(request: ImageUpsertRequest): Long? =
        dsl.insertInto(MEDIA_IMAGE)
            .set(MEDIA_IMAGE.LOUNGE_ID, request.loungeId)
            .set(MEDIA_IMAGE.NAME, request.imgName)
            .set(MEDIA_IMAGE.CATEGORY, defaultString(request.imgType, "MAP"))
            .set(MEDIA_IMAGE.CONTENT, request.img)
            .set(MEDIA_IMAGE.WIDTH, defaultInt(request.width, 0))
            .set(MEDIA_IMAGE.HEIGHT, defaultInt(request.height, 0))
            .set(MEDIA_IMAGE.ENABLED, request.enable == null || request.enable == 1)
            .set(MEDIA_IMAGE.REMARK, defaultString(request.remark, ""))
            .returningResult(MEDIA_IMAGE.ID)
            .fetchOne(MEDIA_IMAGE.ID)

    @Transactional
    fun updateImage(id: Long, request: ImageUpsertRequest) {
        val updated =
            dsl.update(MEDIA_IMAGE)
                .set(MEDIA_IMAGE.LOUNGE_ID, request.loungeId)
                .set(MEDIA_IMAGE.NAME, request.imgName)
                .set(MEDIA_IMAGE.CATEGORY, defaultString(request.imgType, "MAP"))
                .set(MEDIA_IMAGE.CONTENT, request.img)
                .set(MEDIA_IMAGE.WIDTH, defaultInt(request.width, 0))
                .set(MEDIA_IMAGE.HEIGHT, defaultInt(request.height, 0))
                .set(MEDIA_IMAGE.ENABLED, request.enable == null || request.enable == 1)
                .set(MEDIA_IMAGE.REMARK, defaultString(request.remark, ""))
                .where(MEDIA_IMAGE.ID.eq(id))
                .execute()
        ensureUpdated(updated, "图片不存在")
    }

    @Transactional
    fun deleteImage(id: Long) {
        dsl.deleteFrom(MEDIA_IMAGE).where(MEDIA_IMAGE.ID.eq(id)).execute()
    }

    @Transactional
    fun createAudio(request: AudioUpsertRequest): Long? =
        dsl.insertInto(MEDIA_AUDIO)
            .set(MEDIA_AUDIO.LOUNGE_ID, request.loungeId)
            .set(MEDIA_AUDIO.AUDIO_KEY, request.audioKey)
            .set(MEDIA_AUDIO.CATEGORY, defaultString(request.audioType, "COMMON"))
            .set(MEDIA_AUDIO.LANGUAGE_CODE, defaultString(request.languageType, "CN"))
            .set(MEDIA_AUDIO.TEXT_CONTENT, request.textInfo)
            .set(MEDIA_AUDIO.AUDIO_CONTENT, request.audioValue)
            .set(MEDIA_AUDIO.REMARK, defaultString(request.remark, ""))
            .returningResult(MEDIA_AUDIO.ID)
            .fetchOne(MEDIA_AUDIO.ID)

    @Transactional
    fun updateAudio(id: Long, request: AudioUpsertRequest) {
        val updated =
            dsl.update(MEDIA_AUDIO)
                .set(MEDIA_AUDIO.LOUNGE_ID, request.loungeId)
                .set(MEDIA_AUDIO.AUDIO_KEY, request.audioKey)
                .set(MEDIA_AUDIO.CATEGORY, defaultString(request.audioType, "COMMON"))
                .set(MEDIA_AUDIO.LANGUAGE_CODE, defaultString(request.languageType, "CN"))
                .set(MEDIA_AUDIO.TEXT_CONTENT, request.textInfo)
                .set(MEDIA_AUDIO.AUDIO_CONTENT, request.audioValue)
                .set(MEDIA_AUDIO.REMARK, defaultString(request.remark, ""))
                .where(MEDIA_AUDIO.ID.eq(id))
                .execute()
        ensureUpdated(updated, "音频不存在")
    }

    @Transactional
    fun deleteAudio(id: Long) {
        dsl.deleteFrom(MEDIA_AUDIO).where(MEDIA_AUDIO.ID.eq(id)).execute()
    }
}
