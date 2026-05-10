package com.robotmonitor.web.controller.api;

import com.robotmonitor.common.core.domain.config.ConfigImg;
import com.robotmonitor.config.service.IConfigImgService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/rest"})
public class AudioResourceController {
    private static final byte[] PLACEHOLDER_PNG = Base64.getDecoder().decode(
        "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAO+/p9sAAAAASUVORK5CYII="
    );

    @Autowired
    private IConfigImgService configImgService;

    @GetMapping({"/image/config/{id}"})
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) throws IOException {
        ConfigImg configImg = configImgService.selectConfigImgById(id);
        if (configImg == null || isBlank(configImg.getImg())) {
            return ResponseEntity.notFound().build();
        }

        ImagePayload payload = resolveImage(configImg.getImg());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(payload.mediaType);
        headers.setContentDisposition(ContentDisposition.inline().filename(safeFileName(configImg)).build());
        return new ResponseEntity<>(payload.data, headers, HttpStatus.OK);
    }

    private ImagePayload resolveImage(String imageValue) throws IOException {
        String value = imageValue.trim();
        if (value.regionMatches(true, 0, "data:image/", 0, "data:image/".length())) {
            int commaIndex = value.indexOf(',');
            if (commaIndex > -1) {
                String header = value.substring(0, commaIndex).toLowerCase();
                MediaType mediaType = header.contains("jpeg") || header.contains("jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
                return new ImagePayload(Base64.getDecoder().decode(value.substring(commaIndex + 1)), mediaType);
            }
        }

        Path path = Path.of(value);
        if (Files.isRegularFile(path)) {
            MediaType mediaType = mediaTypeForPath(path);
            return new ImagePayload(Files.readAllBytes(path), mediaType);
        }

        try {
            return new ImagePayload(Base64.getDecoder().decode(value), MediaType.IMAGE_PNG);
        } catch (IllegalArgumentException ignored) {
            return new ImagePayload(PLACEHOLDER_PNG, MediaType.IMAGE_PNG);
        }
    }

    private MediaType mediaTypeForPath(Path path) throws IOException {
        String contentType = Files.probeContentType(path);
        if (contentType == null || contentType.isBlank()) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
        return MediaType.parseMediaType(contentType);
    }

    private String safeFileName(ConfigImg configImg) {
        return isBlank(configImg.getImgName()) ? "image.png" : configImg.getImgName();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private record ImagePayload(byte[] data, MediaType mediaType) {
    }
}
