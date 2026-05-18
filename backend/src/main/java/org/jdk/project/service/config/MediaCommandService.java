package org.jdk.project.service.config;

import static org.jdk.project.service.config.ConfigCommandSupport.defaultInt;
import static org.jdk.project.service.config.ConfigCommandSupport.defaultString;
import static org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated;
import static org.jooq.generated.project.Tables.MEDIA_AUDIO;
import static org.jooq.generated.project.Tables.MEDIA_IMAGE;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.config.AudioUpsertRequest;
import org.jdk.project.dto.config.ImageUpsertRequest;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.MediaAudio;
import org.jooq.generated.project.tables.pojos.MediaImage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MediaCommandService {

  private final DSLContext dsl;

  @Transactional
  public Long createImage(ImageUpsertRequest request) {
    MediaImage image = new MediaImage();
    image.setLoungeId(request.getLoungeId());
    image.setName(request.getImgName());
    image.setCategory(defaultString(request.getImgType(), "MAP"));
    image.setContent(request.getImg());
    image.setWidth(defaultInt(request.getWidth(), 0));
    image.setHeight(defaultInt(request.getHeight(), 0));
    image.setEnabled(request.getEnable() == null || request.getEnable() == 1);
    image.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(MEDIA_IMAGE)
        .set(dsl.newRecord(MEDIA_IMAGE, image))
        .returningResult(MEDIA_IMAGE.ID)
        .fetchOne(MEDIA_IMAGE.ID);
  }

  @Transactional
  public void updateImage(Long id, ImageUpsertRequest request) {
    int updated =
        dsl.update(MEDIA_IMAGE)
            .set(MEDIA_IMAGE.LOUNGE_ID, request.getLoungeId())
            .set(MEDIA_IMAGE.NAME, request.getImgName())
            .set(MEDIA_IMAGE.CATEGORY, defaultString(request.getImgType(), "MAP"))
            .set(MEDIA_IMAGE.CONTENT, request.getImg())
            .set(MEDIA_IMAGE.WIDTH, defaultInt(request.getWidth(), 0))
            .set(MEDIA_IMAGE.HEIGHT, defaultInt(request.getHeight(), 0))
            .set(MEDIA_IMAGE.ENABLED, request.getEnable() == null || request.getEnable() == 1)
            .set(MEDIA_IMAGE.REMARK, defaultString(request.getRemark(), ""))
            .where(MEDIA_IMAGE.ID.eq(id))
            .execute();
    ensureUpdated(updated, "图片不存在");
  }

  @Transactional
  public void deleteImage(Long id) {
    dsl.deleteFrom(MEDIA_IMAGE).where(MEDIA_IMAGE.ID.eq(id)).execute();
  }

  @Transactional
  public Long createAudio(AudioUpsertRequest request) {
    MediaAudio audio = new MediaAudio();
    audio.setLoungeId(request.getLoungeId());
    audio.setAudioKey(request.getAudioKey());
    audio.setCategory(defaultString(request.getAudioType(), "COMMON"));
    audio.setLanguageCode(defaultString(request.getLanguageType(), "CN"));
    audio.setTextContent(request.getTextInfo());
    audio.setAudioContent(request.getAudioValue());
    audio.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(MEDIA_AUDIO)
        .set(dsl.newRecord(MEDIA_AUDIO, audio))
        .returningResult(MEDIA_AUDIO.ID)
        .fetchOne(MEDIA_AUDIO.ID);
  }

  @Transactional
  public void updateAudio(Long id, AudioUpsertRequest request) {
    int updated =
        dsl.update(MEDIA_AUDIO)
            .set(MEDIA_AUDIO.LOUNGE_ID, request.getLoungeId())
            .set(MEDIA_AUDIO.AUDIO_KEY, request.getAudioKey())
            .set(MEDIA_AUDIO.CATEGORY, defaultString(request.getAudioType(), "COMMON"))
            .set(MEDIA_AUDIO.LANGUAGE_CODE, defaultString(request.getLanguageType(), "CN"))
            .set(MEDIA_AUDIO.TEXT_CONTENT, request.getTextInfo())
            .set(MEDIA_AUDIO.AUDIO_CONTENT, request.getAudioValue())
            .set(MEDIA_AUDIO.REMARK, defaultString(request.getRemark(), ""))
            .where(MEDIA_AUDIO.ID.eq(id))
            .execute();
    ensureUpdated(updated, "音频不存在");
  }

  @Transactional
  public void deleteAudio(Long id) {
    dsl.deleteFrom(MEDIA_AUDIO).where(MEDIA_AUDIO.ID.eq(id)).execute();
  }
}
