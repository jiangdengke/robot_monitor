package org.jdk.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

  @Value("${app.upload-dir:/tmp/robot-monitor/uploads}")
  private String uploadDir;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public List<String> upload(@RequestPart("files") List<MultipartFile> files) throws IOException {
    Path root = Path.of(uploadDir, LocalDate.now().toString());
    Files.createDirectories(root);
    return files.stream().map(file -> saveFile(root, file)).toList();
  }

  private String saveFile(Path root, MultipartFile file) {
    try {
      String originalName = file.getOriginalFilename() == null ? "file.bin" : file.getOriginalFilename();
      String targetName = UUID.randomUUID() + "-" + originalName;
      Path target = root.resolve(targetName);
      Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
      return target.toString();
    } catch (IOException e) {
      throw new RuntimeException("保存文件失败", e);
    }
  }
}
