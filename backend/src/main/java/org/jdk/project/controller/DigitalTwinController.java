package org.jdk.project.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ApiResponse;
import org.jdk.project.dto.digitaltwin.DigitalTwinActionRequest;
import org.jdk.project.dto.digitaltwin.DigitalTwinOverviewDto;
import org.jdk.project.dto.digitaltwin.DigitalTwinQueryRequest;
import org.jdk.project.dto.digitaltwin.DigitalTwinRegionDto;
import org.jdk.project.service.DigitalTwinService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/DigitalTwin")
@RequiredArgsConstructor
public class DigitalTwinController {

  private final DigitalTwinService digitalTwinService;

  @GetMapping("/selectRegionList")
  public ApiResponse<List<DigitalTwinRegionDto>> selectRegionList(
      @ModelAttribute DigitalTwinQueryRequest query) {
    return digitalTwinService.selectRegionList(query);
  }

  @GetMapping({"/all", "/getAll"})
  public ApiResponse<DigitalTwinOverviewDto> all(@ModelAttribute DigitalTwinQueryRequest query) {
    return digitalTwinService.all(query);
  }

  @GetMapping("/guide")
  public ApiResponse<Void> guide(@ModelAttribute DigitalTwinActionRequest request) {
    return digitalTwinService.guide(request);
  }

  @GetMapping("/interruptGuideTask")
  public ApiResponse<Void> interruptGuideTask(@ModelAttribute DigitalTwinActionRequest request) {
    return digitalTwinService.interruptGuideTask(request);
  }

  @PostMapping("/manualNotice")
  public ApiResponse<Void> manualNotice(@ModelAttribute DigitalTwinActionRequest request) {
    return digitalTwinService.manualNotice(request);
  }

  @GetMapping("/notifyCustomer")
  public ApiResponse<Void> notifyCustomer(@ModelAttribute DigitalTwinActionRequest request) {
    return digitalTwinService.notifyCustomer(request);
  }

  @PostMapping("/handleInspection")
  public ApiResponse<Void> handleInspection() {
    return digitalTwinService.handleInspection();
  }
}
