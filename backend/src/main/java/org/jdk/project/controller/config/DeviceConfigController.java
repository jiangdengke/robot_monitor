package org.jdk.project.controller.config;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.DeviceDto;
import org.jdk.project.dto.config.DeviceRegionBindingUpsertRequest;
import org.jdk.project.dto.config.DeviceUpsertRequest;
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
public class DeviceConfigController {

  private final ConfigCommandService configCommandService;
  private final ConfigQueryService configQueryService;

  @GetMapping("/devices")
  public ListResponse<DeviceDto> listDevices() {
    return configQueryService.listDevices();
  }

  @PostMapping("/devices")
  public Long createDevice(@RequestBody DeviceUpsertRequest request) {
    return configCommandService.createDevice(request);
  }

  @PutMapping("/devices/{id}")
  public void updateDevice(@PathVariable Long id, @RequestBody DeviceUpsertRequest request) {
    configCommandService.updateDevice(id, request);
  }

  @DeleteMapping("/devices/{id}")
  public void deleteDevice(@PathVariable Long id) {
    configCommandService.deleteDevice(id);
  }

  @PostMapping("/device-region-bindings")
  public void saveDeviceRegionBinding(@RequestBody DeviceRegionBindingUpsertRequest request) {
    configCommandService.saveDeviceRegionBinding(request);
  }

  @DeleteMapping("/device-region-bindings/{deviceId}/{regionId}")
  public void deleteDeviceRegionBinding(@PathVariable Long deviceId, @PathVariable Long regionId) {
    configCommandService.deleteDeviceRegionBinding(deviceId, regionId);
  }
}
