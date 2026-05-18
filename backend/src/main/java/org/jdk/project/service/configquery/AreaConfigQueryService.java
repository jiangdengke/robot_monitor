package org.jdk.project.service.configquery;

import static org.jooq.generated.project.Tables.AREA;
import static org.jooq.generated.project.Tables.AREA_I18N;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.REGION;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.AreaDetailDto;
import org.jdk.project.dto.config.AreaDto;
import org.jdk.project.dto.config.RegionDto;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AreaConfigQueryService {

  private final DSLContext dsl;
  private final ConfigQueryMapper mapper;

  public ListResponse<RegionDto> listRegions() {
    List<RegionDto> rows =
        dsl.select(
                REGION.ID,
                REGION.NAME,
                REGION.AREA_ID,
                AREA.NAME.as("area_name"),
                LOUNGE.CODE,
                LOUNGE.NAME.as("lounge_name"),
                REGION.COORDINATE,
                REGION.MAX_CAPACITY,
                REGION.GUIDE_ENABLED,
                REGION.VISIBLE,
                REGION.ENABLED,
                REGION.REMARK)
            .from(REGION)
            .join(LOUNGE)
            .on(REGION.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(AREA)
            .on(REGION.AREA_ID.eq(AREA.ID))
            .orderBy(REGION.ID.asc())
            .fetch(mapper::toRegionDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<AreaDto> listAreas() {
    List<AreaDto> rows =
        dsl.select(
                AREA.ID,
                AREA.NAME,
                LOUNGE.CODE,
                LOUNGE.NAME.as("lounge_name"),
                AREA.COORDINATE,
                AREA.MAX_CAPACITY,
                AREA.GUIDE_ENABLED,
                AREA.VISIBLE,
                AREA.ENABLED,
                AREA.REMARK)
            .from(AREA)
            .join(LOUNGE)
            .on(AREA.LOUNGE_ID.eq(LOUNGE.ID))
            .orderBy(AREA.ID.asc())
            .fetch(record -> mapper.toAreaDto(record, loadAreaDetails(record.get(AREA.ID))));
    return ListResponse.of(rows.size(), rows);
  }

  private List<AreaDetailDto> loadAreaDetails(Long areaId) {
    return dsl.selectFrom(AREA_I18N)
        .where(AREA_I18N.AREA_ID.eq(areaId))
        .orderBy(AREA_I18N.ID.asc())
        .fetch(
            record ->
                AreaDetailDto.builder()
                    .id(record.get(AREA_I18N.ID))
                    .languageType(record.get(AREA_I18N.LANGUAGE_CODE))
                    .areaName(record.get(AREA_I18N.DISPLAY_NAME))
                    .label(record.get(AREA_I18N.LABEL_TEXT))
                    .arrText(record.get(AREA_I18N.ARRIVAL_TEXT))
                    .remark(record.get(AREA_I18N.SPEECH_TEXT))
                    .build());
  }
}
