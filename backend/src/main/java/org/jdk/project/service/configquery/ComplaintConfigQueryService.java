package org.jdk.project.service.configquery;

import static org.jooq.generated.project.Tables.COMPLAINT_RECORD;
import static org.jooq.generated.project.Tables.LOUNGE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.ComplaintDto;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintConfigQueryService {

  private final DSLContext dsl;
  private final ConfigQueryMapper mapper;

  public ListResponse<ComplaintDto> listComplaints() {
    List<ComplaintDto> rows =
        dsl.select(
                COMPLAINT_RECORD.ID,
                COMPLAINT_RECORD.PASSENGER_NAME,
                LOUNGE.CODE,
                ConfigQueryMapper.LOUNGE_NAME,
                COMPLAINT_RECORD.CARD_PROVIDER,
                COMPLAINT_RECORD.CARD_NO,
                COMPLAINT_RECORD.CONTENT,
                COMPLAINT_RECORD.FEEDBACK,
                COMPLAINT_RECORD.CREATED_AT)
            .from(COMPLAINT_RECORD)
            .leftJoin(LOUNGE)
            .on(COMPLAINT_RECORD.LOUNGE_ID.eq(LOUNGE.ID))
            .orderBy(COMPLAINT_RECORD.ID.desc())
            .fetch(mapper::toComplaintDto);
    return ListResponse.of(rows.size(), rows);
  }
}
