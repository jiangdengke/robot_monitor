package org.jdk.project.service.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.platform.PlatformBootstrapDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformConfigService {

  private final ObjectMapper objectMapper;
  private final ResourceLoader resourceLoader;

  @Value("${platform.bootstrap-location:classpath:platform/templates/lounge-greeting/bootstrap.json}")
  private String bootstrapLocation;

  public PlatformBootstrapDto getBootstrap() {
    Resource resource = resourceLoader.getResource(bootstrapLocation);
    try (InputStream inputStream = resource.getInputStream()) {
      return objectMapper.readValue(inputStream, PlatformBootstrapDto.class);
    } catch (IOException exception) {
      throw new IllegalStateException(
          "Failed to load platform bootstrap config: " + bootstrapLocation, exception);
    }
  }
}
