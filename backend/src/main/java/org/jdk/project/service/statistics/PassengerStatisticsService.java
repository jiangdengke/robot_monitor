package org.jdk.project.service.statistics;

import static org.jdk.project.service.statistics.StatisticsQuerySupport.normalizeAccessType;
import static org.jdk.project.service.statistics.StatisticsQuerySupport.normalizePassengerStatus;
import static org.jdk.project.service.statistics.StatisticsQuerySupport.trimToNull;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.PASSENGER_CHECKOUT_LOG;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.statistics.PassengerRecordDto;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerStatisticsService {

  private final DSLContext dsl;
  private final StatisticsMapper mapper;

  public ListResponse<PassengerRecordDto> listInLoungePassengers(Map<String, String> query) {
    List<PassengerRecordDto> rows =
        listPassengerRecords(buildPassengerCondition(query).and(PASSENGER.ACCESS_STATUS.eq("IN")));
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<PassengerRecordDto> listOutgoingPassengers(Map<String, String> query) {
    List<PassengerRecordDto> rows =
        listPassengerRecords(buildPassengerCondition(query).and(PASSENGER.ACCESS_STATUS.eq("OUT")));
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<PassengerRecordDto> listAccessRecords(Map<String, String> query) {
    List<PassengerRecordDto> rows = listPassengerRecords(buildPassengerCondition(query));
    return ListResponse.of(rows.size(), rows);
  }

  private List<PassengerRecordDto> listPassengerRecords(Condition condition) {
    Field<OffsetDateTime> checkOutAt =
        DSL.coalesce(PASSENGER.CHECK_OUT_AT, DSL.max(PASSENGER_CHECKOUT_LOG.CHECKOUT_AT))
            .as("check_out_at");
    return dsl.select(
            PASSENGER.ID,
            LOUNGE.CODE,
            LOUNGE.NAME.as("lounge_name"),
            PASSENGER.PASSENGER_NAME,
            PASSENGER.FLIGHT_NO,
            PASSENGER.FLIGHT_DATE,
            PASSENGER.CARD_PROVIDER,
            PASSENGER.CARD_NO,
            PASSENGER.ACCESS_TYPE,
            PASSENGER.ACCESS_STATUS,
            PASSENGER.CHECK_IN_AT,
            checkOutAt,
            PASSENGER.REGION_NAME,
            PASSENGER.CABIN,
            PASSENGER.SEAT_NO,
            PASSENGER.STAR_LEVEL,
            PASSENGER.ORIGINAL_IMAGE_URL)
        .from(PASSENGER)
        .leftJoin(LOUNGE)
        .on(PASSENGER.LOUNGE_ID.eq(LOUNGE.ID))
        .leftJoin(PASSENGER_CHECKOUT_LOG)
        .on(PASSENGER.ID.eq(PASSENGER_CHECKOUT_LOG.PASSENGER_ID))
        .where(condition)
        .groupBy(
            PASSENGER.ID,
            LOUNGE.CODE,
            LOUNGE.NAME,
            PASSENGER.PASSENGER_NAME,
            PASSENGER.FLIGHT_NO,
            PASSENGER.FLIGHT_DATE,
            PASSENGER.CARD_PROVIDER,
            PASSENGER.CARD_NO,
            PASSENGER.ACCESS_TYPE,
            PASSENGER.ACCESS_STATUS,
            PASSENGER.CHECK_IN_AT,
            PASSENGER.CHECK_OUT_AT,
            PASSENGER.REGION_NAME,
            PASSENGER.CABIN,
            PASSENGER.SEAT_NO,
            PASSENGER.STAR_LEVEL,
            PASSENGER.ORIGINAL_IMAGE_URL)
        .orderBy(PASSENGER.ID.desc())
        .fetch(record -> mapper.toPassengerRecordDto(record, checkOutAt));
  }

  private Condition buildPassengerCondition(Map<String, String> query) {
    Condition condition = DSL.trueCondition();
    String roomCode = trimToNull(query.get("roomCode"));
    if (roomCode != null) {
      condition = condition.and(LOUNGE.CODE.eq(roomCode));
    }
    String flightDate = trimToNull(query.get("flightDate"));
    if (flightDate != null) {
      condition = condition.and(PASSENGER.FLIGHT_DATE.eq(LocalDate.parse(flightDate)));
    }
    String cardNo = trimToNull(query.get("cardNo"));
    if (cardNo != null) {
      condition = condition.and(PASSENGER.CARD_NO.containsIgnoreCase(cardNo));
    }
    String accessType = normalizeAccessType(trimToNull(query.get("accessType")));
    if (accessType != null) {
      condition = condition.and(PASSENGER.ACCESS_TYPE.eq(accessType));
    }
    String status = normalizePassengerStatus(trimToNull(query.get("status")));
    if (status != null) {
      condition = condition.and(PASSENGER.ACCESS_STATUS.eq(status));
    }
    return condition;
  }
}
