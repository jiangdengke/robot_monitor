package org.jdk.project.service;

import static org.jooq.generated.project.Tables.GUIDE_LOG;
import static org.jooq.generated.project.Tables.INQUIRY_STAT;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.PASSENGER;
import static org.jooq.generated.project.Tables.PASSENGER_ACCESS_TEMP;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;

import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.statistics.GuideLogDto;
import org.jdk.project.dto.statistics.InquiryStatDto;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.generated.project.tables.pojos.Passenger;
import org.jooq.generated.project.tables.pojos.PassengerAccessTemp;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsQueryService {

  private static final DateTimeFormatter DATETIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

  public ListResponse<InquiryStatDto> listInquiryStats() {
    List<InquiryStatDto> rows =
        dsl.select(
                INQUIRY_STAT.ID,
                LOUNGE.NAME.as("lounge_name"),
                ROBOT.NAME.as("robot_name"),
                PASSENGER.PASSENGER_NAME.as("passenger_name"),
                INQUIRY_STAT.TOPIC,
                INQUIRY_STAT.CHANNEL,
                INQUIRY_STAT.CREATED_AT)
            .from(INQUIRY_STAT)
            .leftJoin(LOUNGE)
            .on(INQUIRY_STAT.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(ROBOT)
            .on(INQUIRY_STAT.ROBOT_ID.eq(ROBOT.ID))
            .leftJoin(PASSENGER)
            .on(INQUIRY_STAT.PASSENGER_ID.eq(PASSENGER.ID))
            .orderBy(INQUIRY_STAT.ID.desc())
            .fetch(this::toInquiryStatDto);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<GuideLogDto> listGuideLogs() {
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

  private String formatDateTime(java.time.OffsetDateTime value) {
    return value == null ? null : value.toLocalDateTime().format(DATETIME_FORMATTER);
  }
}
