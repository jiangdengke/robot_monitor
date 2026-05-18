package org.jdk.project.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jdk.project.service.DigitalTwinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/DigitalTwin")
@RequiredArgsConstructor
public class DigitalTwinController {

  private final DigitalTwinService digitalTwinService;

  @GetMapping("/selectRegionList")
  public Map<String, Object> selectRegionList(@RequestParam Map<String, String> query) {
    return digitalTwinService.selectRegionList(query);
  }

  @GetMapping({"/all", "/getAll"})
  public Map<String, Object> all(@RequestParam Map<String, String> query) {
    return digitalTwinService.all(query);
  }

  @GetMapping("/guide")
  public Map<String, Object> guide(@RequestParam Map<String, String> query) {
    return digitalTwinService.guide(query);
  }

  @GetMapping("/interruptGuideTask")
  public Map<String, Object> interruptGuideTask(@RequestParam Map<String, String> query) {
    return digitalTwinService.interruptGuideTask(query);
  }

  @PostMapping("/manualNotice")
  public Map<String, Object> manualNotice(@RequestParam Map<String, String> query) {
    return digitalTwinService.manualNotice(query);
  }

  @GetMapping("/notifyCustomer")
  public Map<String, Object> notifyCustomer(@RequestParam Map<String, String> query) {
    return digitalTwinService.notifyCustomer(query);
  }

  @PostMapping("/handleInspection")
  public Map<String, Object> handleInspection() {
    return digitalTwinService.handleInspection();
  }
}
