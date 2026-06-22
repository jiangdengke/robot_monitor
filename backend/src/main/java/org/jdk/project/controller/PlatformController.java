package org.jdk.project.controller;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.platform.PlatformBootstrapConfigDto;
import org.jdk.project.dto.platform.PlatformBootstrapConfigUpsertRequest;
import org.jdk.project.dto.platform.PlatformBootstrapDto;
import org.jdk.project.service.platform.PlatformConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platform")
@RequiredArgsConstructor
public class PlatformController {

  private final PlatformConfigService platformConfigService;

  @GetMapping("/bootstrap")
  public PlatformBootstrapDto bootstrap() {
    return platformConfigService.getBootstrap();
  }

  @GetMapping("/bootstrap-configs")
  public ListResponse<PlatformBootstrapConfigDto> listBootstrapConfigs() {
    return platformConfigService.listBootstrapConfigs();
  }

  @PostMapping("/bootstrap-configs")
  public Long saveBootstrapConfig(@RequestBody PlatformBootstrapConfigUpsertRequest request) {
    return platformConfigService.saveBootstrapConfig(request);
  }
}
