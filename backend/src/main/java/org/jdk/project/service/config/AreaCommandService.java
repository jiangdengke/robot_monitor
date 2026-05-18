package org.jdk.project.service.config;

import static org.jdk.project.service.config.ConfigCommandSupport.defaultInt;
import static org.jdk.project.service.config.ConfigCommandSupport.defaultString;
import static org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated;
import static org.jdk.project.service.config.ConfigCommandSupport.requiredId;
import static org.jooq.generated.project.Tables.AREA;
import static org.jooq.generated.project.Tables.AREA_I18N;
import static org.jooq.generated.project.Tables.REGION;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.config.AreaDetailUpsertRequest;
import org.jdk.project.dto.config.AreaUpsertRequest;
import org.jdk.project.dto.config.RegionUpsertRequest;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.Area;
import org.jooq.generated.project.tables.pojos.AreaI18n;
import org.jooq.generated.project.tables.pojos.Region;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AreaCommandService {

  private final DSLContext dsl;

  @Transactional
  public Long createRegion(RegionUpsertRequest request) {
    Region region = new Region();
    region.setLoungeId(requiredId(request.getLoungeId(), "贵宾室不能为空"));
    region.setAreaId(request.getAreaId());
    region.setName(request.getRegionName());
    region.setCoordinate(defaultString(request.getCoordinate(), ""));
    region.setMaxCapacity(defaultInt(request.getMaxCapacity(), 0));
    region.setGuideEnabled("1".equals(request.getIsGuide()));
    region.setVisible(!"0".equals(request.getIsShow()));
    region.setEnabled(request.getEnable() == null || request.getEnable() == 1);
    region.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(REGION)
        .set(dsl.newRecord(REGION, region))
        .returningResult(REGION.ID)
        .fetchOne(REGION.ID);
  }

  @Transactional
  public void updateRegion(Long id, RegionUpsertRequest request) {
    int updated =
        dsl.update(REGION)
            .set(REGION.LOUNGE_ID, requiredId(request.getLoungeId(), "贵宾室不能为空"))
            .set(REGION.AREA_ID, request.getAreaId())
            .set(REGION.NAME, request.getRegionName())
            .set(REGION.COORDINATE, defaultString(request.getCoordinate(), ""))
            .set(REGION.MAX_CAPACITY, defaultInt(request.getMaxCapacity(), 0))
            .set(REGION.GUIDE_ENABLED, "1".equals(request.getIsGuide()))
            .set(REGION.VISIBLE, !"0".equals(request.getIsShow()))
            .set(REGION.ENABLED, request.getEnable() == null || request.getEnable() == 1)
            .set(REGION.REMARK, defaultString(request.getRemark(), ""))
            .where(REGION.ID.eq(id))
            .execute();
    ensureUpdated(updated, "区域不存在");
  }

  @Transactional
  public void deleteRegion(Long id) {
    dsl.deleteFrom(REGION).where(REGION.ID.eq(id)).execute();
  }

  @Transactional
  public Long createArea(AreaUpsertRequest request) {
    Area area = new Area();
    area.setLoungeId(requiredId(request.getLoungeId(), "贵宾室不能为空"));
    area.setName(request.getAreaName());
    area.setCoordinate(defaultString(request.getCoordinate(), ""));
    area.setMaxCapacity(defaultInt(request.getMaxCapacity(), 0));
    area.setGuideEnabled("1".equals(request.getIsGuide()));
    area.setVisible(!"0".equals(request.getIsShow()));
    area.setEnabled(request.getEnable() == null || request.getEnable() == 1);
    area.setRemark(defaultString(request.getRemark(), ""));
    Long areaId =
        dsl.insertInto(AREA)
            .set(dsl.newRecord(AREA, area))
            .returningResult(AREA.ID)
            .fetchOne(AREA.ID);
    replaceAreaDetails(areaId, request.getConfigAreaDetailList());
    return areaId;
  }

  @Transactional
  public void updateArea(Long id, AreaUpsertRequest request) {
    int updated =
        dsl.update(AREA)
            .set(AREA.LOUNGE_ID, requiredId(request.getLoungeId(), "贵宾室不能为空"))
            .set(AREA.NAME, request.getAreaName())
            .set(AREA.COORDINATE, defaultString(request.getCoordinate(), ""))
            .set(AREA.MAX_CAPACITY, defaultInt(request.getMaxCapacity(), 0))
            .set(AREA.GUIDE_ENABLED, "1".equals(request.getIsGuide()))
            .set(AREA.VISIBLE, !"0".equals(request.getIsShow()))
            .set(AREA.ENABLED, request.getEnable() == null || request.getEnable() == 1)
            .set(AREA.REMARK, defaultString(request.getRemark(), ""))
            .where(AREA.ID.eq(id))
            .execute();
    ensureUpdated(updated, "功能区不存在");
    replaceAreaDetails(id, request.getConfigAreaDetailList());
  }

  @Transactional
  public void deleteArea(Long id) {
    dsl.deleteFrom(AREA_I18N).where(AREA_I18N.AREA_ID.eq(id)).execute();
    dsl.deleteFrom(AREA).where(AREA.ID.eq(id)).execute();
  }

  private void replaceAreaDetails(Long areaId, List<AreaDetailUpsertRequest> details) {
    dsl.deleteFrom(AREA_I18N).where(AREA_I18N.AREA_ID.eq(areaId)).execute();
    if (details == null || details.isEmpty()) {
      return;
    }
    for (AreaDetailUpsertRequest detail : details) {
      AreaI18n record = new AreaI18n();
      record.setAreaId(areaId);
      record.setLanguageCode(defaultString(detail.getLanguageType(), "CN"));
      record.setDisplayName(defaultString(detail.getAreaName(), ""));
      record.setLabelText(defaultString(detail.getLabel(), ""));
      record.setArrivalText(defaultString(detail.getArrText(), ""));
      record.setSpeechText(defaultString(detail.getRemark(), ""));
      dsl.insertInto(AREA_I18N).set(dsl.newRecord(AREA_I18N, record)).execute();
    }
  }
}
