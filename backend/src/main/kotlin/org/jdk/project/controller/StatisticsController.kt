package org.jdk.project.controller

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.statistics.GuideLogDto
import org.jdk.project.dto.statistics.GuideStatisticsQuery
import org.jdk.project.dto.statistics.InquiryStatDto
import org.jdk.project.dto.statistics.InquiryStatisticsQuery
import org.jdk.project.dto.statistics.PassengerRecordDto
import org.jdk.project.dto.statistics.PassengerStatisticsQuery
import org.jdk.project.service.StatisticsQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/statistics")
class StatisticsController(
    private val statisticsQueryService: StatisticsQueryService,
) {
    @GetMapping("/in-lounge")
    fun listInLounge(@ModelAttribute query: PassengerStatisticsQuery): ListResponse<PassengerRecordDto> =
        statisticsQueryService.listInLoungePassengers(query)

    @GetMapping("/outgoing")
    fun listOutgoing(@ModelAttribute query: PassengerStatisticsQuery): ListResponse<PassengerRecordDto> =
        statisticsQueryService.listOutgoingPassengers(query)

    @GetMapping("/access-temp")
    fun listAccessTemp(@ModelAttribute query: PassengerStatisticsQuery): ListResponse<PassengerRecordDto> =
        statisticsQueryService.listAccessRecords(query)

    @GetMapping("/inquiry")
    fun listInquiry(@ModelAttribute query: InquiryStatisticsQuery): ListResponse<InquiryStatDto> =
        statisticsQueryService.listInquiryStats(query)

    @GetMapping("/guide")
    fun listGuide(@ModelAttribute query: GuideStatisticsQuery): ListResponse<GuideLogDto> =
        statisticsQueryService.listGuideLogs(query)
}
