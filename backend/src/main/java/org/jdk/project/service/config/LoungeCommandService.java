package org.jdk.project.service.config;

import static org.jdk.project.service.config.ConfigCommandSupport.defaultString;
import static org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated;
import static org.jooq.generated.project.Tables.LOUNGE;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.config.LoungeUpsertRequest;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.Lounge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoungeCommandService {

  private final DSLContext dsl;

  @Transactional
  public Long create(LoungeUpsertRequest request) {
    Lounge lounge = new Lounge();
    lounge.setCode(request.getRoomCode());
    lounge.setName(request.getDeptName());
    lounge.setTerminal(defaultString(request.getTerminal(), ""));
    lounge.setLocationDesc(defaultString(request.getLocationDesc(), ""));
    lounge.setEnabled(request.getEnabled() == null || request.getEnabled());
    lounge.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(LOUNGE)
        .set(dsl.newRecord(LOUNGE, lounge))
        .returningResult(LOUNGE.ID)
        .fetchOne(LOUNGE.ID);
  }

  @Transactional
  public void update(Long id, LoungeUpsertRequest request) {
    int updated =
        dsl.update(LOUNGE)
            .set(LOUNGE.CODE, request.getRoomCode())
            .set(LOUNGE.NAME, request.getDeptName())
            .set(LOUNGE.TERMINAL, defaultString(request.getTerminal(), ""))
            .set(LOUNGE.LOCATION_DESC, defaultString(request.getLocationDesc(), ""))
            .set(LOUNGE.ENABLED, request.getEnabled() == null || request.getEnabled())
            .set(LOUNGE.REMARK, defaultString(request.getRemark(), ""))
            .where(LOUNGE.ID.eq(id))
            .execute();
    ensureUpdated(updated, "贵宾室不存在");
  }

  @Transactional
  public void delete(Long id) {
    dsl.deleteFrom(LOUNGE).where(LOUNGE.ID.eq(id)).execute();
  }
}
