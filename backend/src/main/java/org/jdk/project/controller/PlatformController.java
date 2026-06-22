package org.jdk.project.controller;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.platform.PlatformBootstrapDto;
import org.jdk.project.service.platform.PlatformConfigService;
import org.springframework.web.bind.annotation.GetMapping;
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
}
