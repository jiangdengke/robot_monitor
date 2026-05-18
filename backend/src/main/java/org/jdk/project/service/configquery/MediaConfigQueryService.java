package org.jdk.project.service.configquery;

import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.MEDIA_AUDIO;
import static org.jooq.generated.project.Tables.MEDIA_IMAGE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.AudioDto;
import org.jdk.project.dto.config.ImageDto;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaConfigQueryService {

  private final DSLContext dsl;
  private final ConfigQueryMapper mapper;

  public ListResponse<ImageDto> listImages() {
    List<ImageDto> rows =
        dsl.select(
                MEDIA_IMAGE.ID,
                MEDIA_IMAGE.NAME,
                MEDIA_IMAGE.CATEGORY,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                MEDIA_IMAGE.WIDTH,
                MEDIA_IMAGE.HEIGHT,
                MEDIA_IMAGE.ENABLED,
                MEDIA_IMAGE.REMARK)
            .from(MEDIA_IMAGE)
            .leftJoin(LOUNGE)
            .on(MEDIA_IMAGE.LOUNGE_ID.eq(LOUNGE.ID))
            .orderBy(MEDIA_IMAGE.ID.asc())
            .fetch(mapper::toImageDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<AudioDto> listAudios(String category) {
    var condition = category == null ? MEDIA_AUDIO.ID.isNotNull() : MEDIA_AUDIO.CATEGORY.eq(category);
    List<AudioDto> rows =
        dsl.select(
                MEDIA_AUDIO.ID,
                MEDIA_AUDIO.AUDIO_KEY,
                MEDIA_AUDIO.CATEGORY,
                MEDIA_AUDIO.LANGUAGE_CODE,
                MEDIA_AUDIO.TEXT_CONTENT,
                MEDIA_AUDIO.AUDIO_CONTENT,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                MEDIA_AUDIO.REMARK)
            .from(MEDIA_AUDIO)
            .leftJoin(LOUNGE)
            .on(MEDIA_AUDIO.LOUNGE_ID.eq(LOUNGE.ID))
            .where(condition)
            .orderBy(MEDIA_AUDIO.ID.asc())
            .fetch(mapper::toAudioDto);
    return ListResponse.of(rows.size(), rows);
  }
}
