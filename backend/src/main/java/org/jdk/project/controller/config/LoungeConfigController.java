package org.jdk.project.controller.config;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.LoungeDto;
import org.jdk.project.dto.config.LoungeUpsertRequest;
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
@RequestMapping("/config/lounges")
@RequiredArgsConstructor
public class LoungeConfigController {

  private final ConfigCommandService configCommandService;
  private final ConfigQueryService configQueryService;

  @GetMapping
  public ListResponse<LoungeDto> listLounges() {
    return configQueryService.listLounges();
  }

  @PostMapping
  public Long createLounge(@RequestBody LoungeUpsertRequest request) {
    return configCommandService.createLounge(request);
  }

  @PutMapping("/{id}")
  public void updateLounge(@PathVariable Long id, @RequestBody LoungeUpsertRequest request) {
    configCommandService.updateLounge(id, request);
  }

  @DeleteMapping("/{id}")
  public void deleteLounge(@PathVariable Long id) {
    configCommandService.deleteLounge(id);
  }
}
