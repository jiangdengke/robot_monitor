package org.jdk.project.service.statistics

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import org.jdk.project.dto.statistics.GuideLogDto
import org.jdk.project.dto.statistics.InquiryStatDto
import org.jdk.project.dto.statistics.PassengerRecordDto
import org.jooq.Field
import org.jooq.Record
import org.jooq.generated.project.Tables.GUIDE_LOG
import org.jooq.generated.project.Tables.INQUIRY_STAT
import org.jooq.generated.project.Tables.LOUNGE
import org.jooq.generated.project.Tables.PASSENGER
import org.jooq.generated.project.Tables.REGION
import org.jooq.generated.project.Tables.ROBOT
import org.springframework.stereotype.Component

@Component
class StatisticsMapper {
    fun toInquiryStatDto(record: Record): InquiryStatDto =
        InquiryStatDto(
            id = record.get(INQUIRY_STAT.ID),
            deptName = record.get(LOUNGE_NAME),
            robotName = record.get(ROBOT_NAME),
            passengerName = record.get(PASSENGER_NAME),
            topic = record.get(INQUIRY_STAT.TOPIC),
            robotResponse = record.get(INQUIRY_STAT.ROBOT_RESPONSE),
            channel = record.get(INQUIRY_STAT.CHANNEL),
            createdAt = formatDateTime(record.get(INQUIRY_STAT.CREATED_AT)),
        )

    fun toGuideLogDto(record: Record): GuideLogDto =
        GuideLogDto(
            id = record.get(GUIDE_LOG.ID),
            deptName = record.get(LOUNGE_NAME),
            robotName = record.get(ROBOT_NAME),
            passengerName = record.get(PASSENGER_NAME),
            regionName = record.get(REGION_NAME),
            resultStatus = record.get(GUIDE_LOG.RESULT_STATUS),
            coordinate = record.get(GUIDE_LOG.COORDINATE),
            createdAt = formatDateTime(record.get(GUIDE_LOG.CREATED_AT)),
        )

    fun toPassengerRecordDto(
        record: Record,
        checkOutAt: Field<OffsetDateTime>,
    ): PassengerRecordDto =
        PassengerRecordDto(
            id = record.get(PASSENGER.ID),
            roomCode = record.get(LOUNGE.CODE),
            deptName = record.get(LOUNGE_NAME),
            passengerName = record.get(PASSENGER.PASSENGER_NAME),
            flightNo = record.get(PASSENGER.FLIGHT_NO),
            flightDate = formatDate(record.get(PASSENGER.FLIGHT_DATE)),
            cardProvider = record.get(PASSENGER.CARD_PROVIDER),
            cardNo = record.get(PASSENGER.CARD_NO),
            accessType = record.get(PASSENGER.ACCESS_TYPE),
            accessStatus = record.get(PASSENGER.ACCESS_STATUS),
            checkInAt = formatDateTime(record.get(PASSENGER.CHECK_IN_AT)),
            checkOutAt = formatDateTime(record.get(checkOutAt)),
            regionName = record.get(PASSENGER.REGION_NAME),
            cabin = record.get(PASSENGER.CABIN),
            seatNo = record.get(PASSENGER.SEAT_NO),
            starLevel = record.get(PASSENGER.STAR_LEVEL),
            originalImageUrl = record.get(PASSENGER.ORIGINAL_IMAGE_URL),
        )

    private fun formatDateTime(value: OffsetDateTime?): String? = value?.toLocalDateTime()?.format(DATETIME_FORMATTER)

    private fun formatDate(value: LocalDate?): String? = value?.toString()

    companion object {
        private val DATETIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        @JvmField
        val LOUNGE_NAME: Field<String> = LOUNGE.NAME.`as`("lounge_name")

        @JvmField
        val ROBOT_NAME: Field<String> = ROBOT.NAME.`as`("robot_name")

        @JvmField
        val PASSENGER_NAME: Field<String> = PASSENGER.PASSENGER_NAME.`as`("passenger_name")

        @JvmField
        val REGION_NAME: Field<String> = REGION.NAME.`as`("region_name")
    }
}
