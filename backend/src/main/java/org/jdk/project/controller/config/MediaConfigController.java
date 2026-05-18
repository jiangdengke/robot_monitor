package org.jdk.project.controller.config;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.AudioDto;
import org.jdk.project.dto.config.AudioUpsertRequest;
import org.jdk.project.dto.config.ImageDto;
import org.jdk.project.dto.config.ImageUpsertRequest;
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
public class MediaConfigController {

  private final ConfigCommandService configCommandService;
  private final ConfigQueryService configQueryService;

  @GetMapping("/images")
  public ListResponse<ImageDto> listImages() {
    return configQueryService.listImages();
  }

  @PostMapping("/images")
  public Long createImage(@RequestBody ImageUpsertRequest request) {
    return configCommandService.createImage(request);
  }

  @PutMapping("/images/{id}")
  public void updateImage(@PathVariable Long id, @RequestBody ImageUpsertRequest request) {
    configCommandService.updateImage(id, request);
  }

  @DeleteMapping("/images/{id}")
  public void deleteImage(@PathVariable Long id) {
    configCommandService.deleteImage(id);
  }

  @GetMapping("/audios")
  public ListResponse<AudioDto> listAudios() {
    return configQueryService.listAudios("COMMON");
  }

  @PostMapping("/audios")
  public Long createAudio(@RequestBody AudioUpsertRequest request) {
    return configCommandService.createAudio(request);
  }

  @PutMapping("/audios/{id}")
  public void updateAudio(@PathVariable Long id, @RequestBody AudioUpsertRequest request) {
    configCommandService.updateAudio(id, request);
  }

  @DeleteMapping("/audios/{id}")
  public void deleteAudio(@PathVariable Long id) {
    configCommandService.deleteAudio(id);
  }

  @GetMapping("/robot-audios")
  public ListResponse<AudioDto> listRobotAudios() {
    return configQueryService.listAudios("ROBOT");
  }
}
