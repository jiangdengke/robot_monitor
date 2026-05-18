package org.jdk.project.service;

import static org.jooq.generated.project.Tables.GUIDE_LOG;
import static org.jooq.generated.project.Tables.INQUIRY_STAT;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.PASSENGER_CHECKOUT_LOG;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.statistics.GuideLogDto;
import org.jdk.project.dto.statistics.InquiryStatDto;
import org.jdk.project.dto.statistics.PassengerRecordDto;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsQueryService {

  private static final DateTimeFormatter DATETIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final DSLContext dsl;

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

  public ListResponse<InquiryStatDto> listInquiryStats(Map<String, String> query) {
    List<InquiryStatDto> rows =
        dsl.select(
                INQUIRY_STAT.ID,
                LOUNGE.NAME.as("lounge_name"),
                ROBOT.NAME.as("robot_name"),
                PASSENGER.PASSENGER_NAME.as("passenger_name"),
                INQUIRY_STAT.TOPIC,
                INQUIRY_STAT.ROBOT_RESPONSE,
                INQUIRY_STAT.CHANNEL,
                INQUIRY_STAT.CREATED_AT)
            .from(INQUIRY_STAT)
            .leftJoin(LOUNGE)
            .on(INQUIRY_STAT.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(ROBOT)
            .on(INQUIRY_STAT.ROBOT_ID.eq(ROBOT.ID))
            .leftJoin(PASSENGER)
            .on(INQUIRY_STAT.PASSENGER_ID.eq(PASSENGER.ID))
            .where(buildInquiryCondition(query))
            .orderBy(INQUIRY_STAT.ID.desc())
            .fetch(this::toInquiryStatDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<GuideLogDto> listGuideLogs(Map<String, String> query) {
    List<GuideLogDto> rows =
        dsl.select(
                GUIDE_LOG.ID,
                LOUNGE.NAME.as("lounge_name"),
                ROBOT.NAME.as("robot_name"),
                PASSENGER.PASSENGER_NAME.as("passenger_name"),
                REGION.NAME.as("region_name"),
                GUIDE_LOG.RESULT_STATUS,
                GUIDE_LOG.COORDINATE,
                GUIDE_LOG.CREATED_AT)
            .from(GUIDE_LOG)
            .leftJoin(LOUNGE)
            .on(GUIDE_LOG.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(ROBOT)
            .on(GUIDE_LOG.ROBOT_ID.eq(ROBOT.ID))
            .leftJoin(PASSENGER)
            .on(GUIDE_LOG.PASSENGER_ID.eq(PASSENGER.ID))
            .leftJoin(REGION)
            .on(GUIDE_LOG.REGION_ID.eq(REGION.ID))
            .where(buildGuideCondition(query))
            .orderBy(GUIDE_LOG.ID.desc())
            .fetch(this::toGuideLogDto);
    return ListResponse.of(rows.size(), rows);
  }

  private InquiryStatDto toInquiryStatDto(Record record) {
    return InquiryStatDto.builder()
        .id(record.get(INQUIRY_STAT.ID))
        .deptName(record.get("lounge_name", String.class))
        .robotName(record.get("robot_name", String.class))
        .passengerName(record.get("passenger_name", String.class))
        .topic(record.get(INQUIRY_STAT.TOPIC))
        .robotResponse(record.get(INQUIRY_STAT.ROBOT_RESPONSE))
        .channel(record.get(INQUIRY_STAT.CHANNEL))
        .createdAt(formatDateTime(record.get(INQUIRY_STAT.CREATED_AT)))
        .build();
  }

  private GuideLogDto toGuideLogDto(Record record) {
    return GuideLogDto.builder()
        .id(record.get(GUIDE_LOG.ID))
        .deptName(record.get("lounge_name", String.class))
        .robotName(record.get("robot_name", String.class))
        .passengerName(record.get("passenger_name", String.class))
        .regionName(record.get("region_name", String.class))
        .resultStatus(record.get(GUIDE_LOG.RESULT_STATUS))
        .coordinate(record.get(GUIDE_LOG.COORDINATE))
        .createdAt(formatDateTime(record.get(GUIDE_LOG.CREATED_AT)))
        .build();
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
        .fetch(record -> toPassengerRecordDto(record, checkOutAt));
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

  private Condition buildInquiryCondition(Map<String, String> query) {
    Condition condition = DSL.trueCondition();
    String robotId = trimToNull(query.get("robotId"));
    if (robotId != null) {
      condition = condition.and(INQUIRY_STAT.ROBOT_ID.eq(Long.valueOf(robotId)));
    }
    return condition;
  }

  private Condition buildGuideCondition(Map<String, String> query) {
    Condition condition = DSL.trueCondition();
    String robotId = trimToNull(query.get("robotId"));
    if (robotId != null) {
      condition = condition.and(GUIDE_LOG.ROBOT_ID.eq(Long.valueOf(robotId)));
    }
    String resultStatus = trimToNull(query.get("resultStatus"));
    if (resultStatus != null) {
      condition = condition.and(GUIDE_LOG.RESULT_STATUS.eq(resultStatus));
    }
    return condition;
  }

  private String trimToNull(String value) {
    if (value == null || value.trim().isEmpty()) {
      return null;
    }
    return value.trim();
  }

  private String normalizeAccessType(String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "1" -> "ID_CARD";
      case "2" -> "QRCODE";
      case "3" -> "FACE";
      default -> value;
    };
  }

  private String normalizePassengerStatus(String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "1" -> "IN";
      case "0" -> "OUT";
      default -> value;
    };
  }

  private PassengerRecordDto toPassengerRecordDto(Record record, Field<OffsetDateTime> checkOutAt) {
    return PassengerRecordDto.builder()
        .id(record.get(PASSENGER.ID))
        .roomCode(record.get(LOUNGE.CODE))
        .deptName(record.get("lounge_name", String.class))
        .passengerName(record.get(PASSENGER.PASSENGER_NAME))
        .flightNo(record.get(PASSENGER.FLIGHT_NO))
        .flightDate(formatDate(record.get(PASSENGER.FLIGHT_DATE)))
        .cardProvider(record.get(PASSENGER.CARD_PROVIDER))
        .cardNo(record.get(PASSENGER.CARD_NO))
        .accessType(record.get(PASSENGER.ACCESS_TYPE))
        .accessStatus(record.get(PASSENGER.ACCESS_STATUS))
        .checkInAt(formatDateTime(record.get(PASSENGER.CHECK_IN_AT)))
        .checkOutAt(formatDateTime(record.get(checkOutAt)))
        .regionName(record.get(PASSENGER.REGION_NAME))
        .cabin(record.get(PASSENGER.CABIN))
        .seatNo(record.get(PASSENGER.SEAT_NO))
        .starLevel(record.get(PASSENGER.STAR_LEVEL))
        .originalImageUrl(record.get(PASSENGER.ORIGINAL_IMAGE_URL))
        .build();
  }

  private String formatDate(LocalDate value) {
    return value == null ? null : value.toString();
  }

  private String formatDateTime(OffsetDateTime value) {
    return value == null ? null : value.toLocalDateTime().format(DATETIME_FORMATTER);
  }
}
