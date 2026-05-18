package org.jdk.project.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.statistics.GuideLogDto;
import org.jdk.project.dto.statistics.InquiryStatDto;
import org.jdk.project.dto.statistics.PassengerRecordDto;
import org.jdk.project.service.statistics.GuideStatisticsService;
import org.jdk.project.service.statistics.InquiryStatisticsService;
import org.jdk.project.service.statistics.PassengerStatisticsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsQueryService {

  private final PassengerStatisticsService passengerStatisticsService;
  private final InquiryStatisticsService inquiryStatisticsService;
  private final GuideStatisticsService guideStatisticsService;

  public ListResponse<PassengerRecordDto> listInLoungePassengers(Map<String, String> query) {
    return passengerStatisticsService.listInLoungePassengers(query);
  }

  public ListResponse<PassengerRecordDto> listOutgoingPassengers(Map<String, String> query) {
    return passengerStatisticsService.listOutgoingPassengers(query);
  }

  public ListResponse<PassengerRecordDto> listAccessRecords(Map<String, String> query) {
    return passengerStatisticsService.listAccessRecords(query);
  }

  public ListResponse<InquiryStatDto> listInquiryStats(Map<String, String> query) {
    return inquiryStatisticsService.listInquiryStats(query);
  }

  public ListResponse<GuideLogDto> listGuideLogs(Map<String, String> query) {
    return guideStatisticsService.listGuideLogs(query);
  }
}
