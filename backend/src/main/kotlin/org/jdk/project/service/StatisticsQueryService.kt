package org.jdk.project.service

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.statistics.GuideLogDto
import org.jdk.project.dto.statistics.GuideStatisticsQuery
import org.jdk.project.dto.statistics.InquiryStatDto
import org.jdk.project.dto.statistics.InquiryStatisticsQuery
import org.jdk.project.dto.statistics.PassengerRecordDto
import org.jdk.project.dto.statistics.PassengerStatisticsQuery
import org.jdk.project.service.statistics.GuideStatisticsService
import org.jdk.project.service.statistics.InquiryStatisticsService
import org.jdk.project.service.statistics.PassengerStatisticsService
import org.springframework.stereotype.Service

@Service
class StatisticsQueryService(
    private val passengerStatisticsService: PassengerStatisticsService,
    private val inquiryStatisticsService: InquiryStatisticsService,
    private val guideStatisticsService: GuideStatisticsService,
) {
    fun listInLoungePassengers(query: PassengerStatisticsQuery): ListResponse<PassengerRecordDto> =
        passengerStatisticsService.listInLoungePassengers(query)

    fun listOutgoingPassengers(query: PassengerStatisticsQuery): ListResponse<PassengerRecordDto> =
        passengerStatisticsService.listOutgoingPassengers(query)

    fun listAccessRecords(query: PassengerStatisticsQuery): ListResponse<PassengerRecordDto> =
        passengerStatisticsService.listAccessRecords(query)

    fun listInquiryStats(query: InquiryStatisticsQuery): ListResponse<InquiryStatDto> = inquiryStatisticsService.listInquiryStats(query)

    fun listGuideLogs(query: GuideStatisticsQuery): ListResponse<GuideLogDto> = guideStatisticsService.listGuideLogs(query)
}
