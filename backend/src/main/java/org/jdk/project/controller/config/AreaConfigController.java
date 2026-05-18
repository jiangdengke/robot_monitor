package org.jdk.project.controller.config;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.AreaDto;
import org.jdk.project.dto.config.AreaUpsertRequest;
import org.jdk.project.dto.config.RegionDto;
import org.jdk.project.dto.config.RegionUpsertRequest;
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
@RequestMapping("/config")
@RequiredArgsConstructor
public class AreaConfigController {

  private final ConfigCommandService configCommandService;
  private final ConfigQueryService configQueryService;

  @GetMapping("/regions")
  public ListResponse<RegionDto> listRegions() {
    return configQueryService.listRegions();
  }

  @PostMapping("/regions")
  public Long createRegion(@RequestBody RegionUpsertRequest request) {
    return configCommandService.createRegion(request);
  }

  @PutMapping("/regions/{id}")
  public void updateRegion(@PathVariable Long id, @RequestBody RegionUpsertRequest request) {
    configCommandService.updateRegion(id, request);
  }

  @DeleteMapping("/regions/{id}")
  public void deleteRegion(@PathVariable Long id) {
    configCommandService.deleteRegion(id);
  }

  @GetMapping("/areas")
  public ListResponse<AreaDto> listAreas() {
    return configQueryService.listAreas();
  }

  @PostMapping("/areas")
  public Long createArea(@RequestBody AreaUpsertRequest request) {
    return configCommandService.createArea(request);
  }

  @PutMapping("/areas/{id}")
  public void updateArea(@PathVariable Long id, @RequestBody AreaUpsertRequest request) {
    configCommandService.updateArea(id, request);
  }

  @DeleteMapping("/areas/{id}")
  public void deleteArea(@PathVariable Long id) {
    configCommandService.deleteArea(id);
  }
}
