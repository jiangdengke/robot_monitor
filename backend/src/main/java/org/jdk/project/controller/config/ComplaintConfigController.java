package org.jdk.project.controller.config;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.ComplaintDto;
import org.jdk.project.dto.config.ComplaintUpsertRequest;
import org.jdk.project.service.ConfigCommandService;
import org.jdk.project.service.ConfigQueryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config/complaints")
@RequiredArgsConstructor
public class ComplaintConfigController {

  private final ConfigCommandService configCommandService;
  private final ConfigQueryService configQueryService;

  @GetMapping
  public ListResponse<ComplaintDto> listComplaints() {
    return configQueryService.listComplaints();
  }

  @PostMapping
  public Long createComplaint(@RequestBody ComplaintUpsertRequest request) {
    return configCommandService.createComplaint(request);
  }

  @PutMapping("/{id}")
  public void updateComplaint(@PathVariable Long id, @RequestBody ComplaintUpsertRequest request) {
    configCommandService.updateComplaint(id, request);
  }

  @DeleteMapping("/{id}")
  public void deleteComplaint(@PathVariable Long id) {
    configCommandService.deleteComplaint(id);
  }
}
