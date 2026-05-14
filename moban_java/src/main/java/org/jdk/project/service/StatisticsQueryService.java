package org.jdk.project.service;

import static org.jooq.generated.project.Tables.GUIDE_LOG;
import static org.jooq.generated.project.Tables.INQUIRY_STAT;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.PASSENGER_ACCESS_TEMP;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.GuideLog;
import org.jooq.generated.project.tables.pojos.InquiryStat;
import org.jooq.generated.project.tables.pojos.Passenger;
import org.jooq.generated.project.tables.pojos.PassengerAccessTemp;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsQueryService {

  private final DSLContext dsl;

  public ListResponse<Passenger> listInLoungePassengers() {
    List<Passenger> rows =
        dsl.selectFrom(PASSENGER)
            .where(PASSENGER.ACCESS_STATUS.eq("IN"))
            .orderBy(PASSENGER.ID.desc())
            .fetchInto(Passenger.class);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<Passenger> listOutgoingPassengers() {
    List<Passenger> rows =
        dsl.selectFrom(PASSENGER)
            .where(PASSENGER.ACCESS_STATUS.eq("OUT"))
            .orderBy(PASSENGER.ID.desc())
            .fetchInto(Passenger.class);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<PassengerAccessTemp> listAccessTemps() {
    List<PassengerAccessTemp> rows =
        dsl.selectFrom(PASSENGER_ACCESS_TEMP)
            .orderBy(PASSENGER_ACCESS_TEMP.ID.desc())
            .fetchInto(PassengerAccessTemp.class);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<InquiryStat> listInquiryStats() {
    List<InquiryStat> rows =
        dsl.selectFrom(INQUIRY_STAT).orderBy(INQUIRY_STAT.ID.desc()).fetchInto(InquiryStat.class);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<GuideLog> listGuideLogs() {
    List<GuideLog> rows =
        dsl.selectFrom(GUIDE_LOG).orderBy(GUIDE_LOG.ID.desc()).fetchInto(GuideLog.class);
    return ListResponse.of(rows.size(), rows);
  }
}
