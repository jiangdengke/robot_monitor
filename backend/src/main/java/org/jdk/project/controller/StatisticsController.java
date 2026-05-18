package org.jdk.project.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.statistics.GuideLogDto;
import org.jdk.project.dto.statistics.InquiryStatDto;
import org.jdk.project.dto.statistics.PassengerRecordDto;
import org.jdk.project.service.StatisticsQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

  private final StatisticsQueryService statisticsQueryService;

  @GetMapping("/in-lounge")
  public ListResponse<PassengerRecordDto> listInLounge(@RequestParam Map<String, String> query) {
    return statisticsQueryService.listInLoungePassengers(query);
  }

  @GetMapping("/outgoing")
  public ListResponse<PassengerRecordDto> listOutgoing(@RequestParam Map<String, String> query) {
    return statisticsQueryService.listOutgoingPassengers(query);
  }

  @GetMapping("/access-temp")
  public ListResponse<PassengerRecordDto> listAccessTemp(@RequestParam Map<String, String> query) {
    return statisticsQueryService.listAccessRecords(query);
  }

  @GetMapping("/inquiry")
  public ListResponse<InquiryStatDto> listInquiry(@RequestParam Map<String, String> query) {
    return statisticsQueryService.listInquiryStats(query);
  }

  @GetMapping("/guide")
  public ListResponse<GuideLogDto> listGuide(@RequestParam Map<String, String> query) {
    return statisticsQueryService.listGuideLogs(query);
  }
}
