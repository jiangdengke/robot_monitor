package org.jdk.project.controller;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.statistics.GuideLogDto;
import org.jdk.project.dto.statistics.GuideStatisticsQuery;
import org.jdk.project.dto.statistics.InquiryStatDto;
import org.jdk.project.dto.statistics.InquiryStatisticsQuery;
import org.jdk.project.dto.statistics.PassengerRecordDto;
import org.jdk.project.dto.statistics.PassengerStatisticsQuery;
import org.jdk.project.service.StatisticsQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

  private final StatisticsQueryService statisticsQueryService;

  @GetMapping("/in-lounge")
  public ListResponse<PassengerRecordDto> listInLounge(
      @ModelAttribute PassengerStatisticsQuery query) {
    return statisticsQueryService.listInLoungePassengers(query);
  }

  @GetMapping("/outgoing")
  public ListResponse<PassengerRecordDto> listOutgoing(
      @ModelAttribute PassengerStatisticsQuery query) {
    return statisticsQueryService.listOutgoingPassengers(query);
  }

  @GetMapping("/access-temp")
  public ListResponse<PassengerRecordDto> listAccessTemp(
      @ModelAttribute PassengerStatisticsQuery query) {
    return statisticsQueryService.listAccessRecords(query);
  }

  @GetMapping("/inquiry")
  public ListResponse<InquiryStatDto> listInquiry(@ModelAttribute InquiryStatisticsQuery query) {
    return statisticsQueryService.listInquiryStats(query);
  }

  @GetMapping("/guide")
  public ListResponse<GuideLogDto> listGuide(@ModelAttribute GuideStatisticsQuery query) {
    return statisticsQueryService.listGuideLogs(query);
  }
}
