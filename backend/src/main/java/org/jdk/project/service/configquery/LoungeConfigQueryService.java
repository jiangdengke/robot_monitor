package org.jdk.project.service.configquery;

import static org.jooq.generated.project.Tables.LOUNGE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.LoungeDto;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoungeConfigQueryService {

  private final DSLContext dsl;
  private final ConfigQueryMapper mapper;

  public ListResponse<LoungeDto> listLounges() {
    List<LoungeDto> rows =
        dsl.selectFrom(LOUNGE).orderBy(LOUNGE.ID.asc()).fetch(mapper::toLoungeDto);
    return ListResponse.of(rows.size(), rows);
  }
}
