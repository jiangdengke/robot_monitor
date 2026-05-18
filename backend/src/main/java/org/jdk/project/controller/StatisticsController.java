package org.jdk.project.controller;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.statistics.GuideLogDto;
import org.jdk.project.dto.statistics.InquiryStatDto;
import org.jooq.generated.project.tables.pojos.Passenger;
import org.jooq.generated.project.tables.pojos.PassengerAccessTemp;
import org.jdk.project.service.StatisticsQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

  private final StatisticsQueryService statisticsQueryService;

  @GetMapping("/in-lounge")
  public ListResponse<Passenger> listInLounge() {
    return statisticsQueryService.listInLoungePassengers();
  }

  @GetMapping("/outgoing")
  public ListResponse<Passenger> listOutgoing() {
    return statisticsQueryService.listOutgoingPassengers();
  }

  @GetMapping("/access-temp")
  public ListResponse<PassengerAccessTemp> listAccessTemp() {
    return statisticsQueryService.listAccessTemps();
  }

  @GetMapping("/inquiry")
  public ListResponse<InquiryStatDto> listInquiry() {
    return statisticsQueryService.listInquiryStats();
  }

  @GetMapping("/guide")
  public ListResponse<GuideLogDto> listGuide() {
    return statisticsQueryService.listGuideLogs();
  }
}
