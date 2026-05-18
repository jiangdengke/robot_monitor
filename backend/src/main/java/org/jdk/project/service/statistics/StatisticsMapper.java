package org.jdk.project.service.statistics;

import static org.jooq.generated.project.Tables.GUIDE_LOG;
import static org.jooq.generated.project.Tables.INQUIRY_STAT;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.jdk.project.dto.statistics.GuideLogDto;
import org.jdk.project.dto.statistics.InquiryStatDto;
import org.jdk.project.dto.statistics.PassengerRecordDto;
import org.jooq.Field;
import org.jooq.Record;
import org.springframework.stereotype.Component;

@Component
class StatisticsMapper {

  private static final DateTimeFormatter DATETIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  InquiryStatDto toInquiryStatDto(Record record) {
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

  GuideLogDto toGuideLogDto(Record record) {
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

  PassengerRecordDto toPassengerRecordDto(Record record, Field<OffsetDateTime> checkOutAt) {
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
