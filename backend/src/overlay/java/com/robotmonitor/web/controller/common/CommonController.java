package com.robotmonitor.web.controller.common;

import com.robotmonitor.common.config.RobotmonitorConfig;
import com.robotmonitor.common.constant.Constants;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.uuid.IdUtils;
import com.robotmonitor.framework.config.ServerConfig;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/common")
public class CommonController {
    private static final String FILE_DELIMETER = ",";

    private final ServerConfig serverConfig;

    public CommonController(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        UploadResult result = store(file, "upload");
        AjaxResult ajax = AjaxResult.success();
        ajax.put("url", serverConfig.getUrl() + result.resourcePath());
        ajax.put("fileName", result.resourcePath());
        ajax.put("newFileName", result.newFileName());
        ajax.put("originalFilename", result.originalFilename());
        return ajax;
    }

    @PostMapping("/uploads")
    public AjaxResult uploadFiles(@RequestParam("files") List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty()) {
            return AjaxResult.error("请选择上传文件");
        }
        List<String> urls = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        List<String> newFileNames = new ArrayList<>();
        List<String> originalFilenames = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadResult result = store(file, "upload");
            urls.add(serverConfig.getUrl() + result.resourcePath());
            fileNames.add(result.resourcePath());
            newFileNames.add(result.newFileName());
            originalFilenames.add(result.originalFilename());
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("urls", String.join(FILE_DELIMETER, urls));
        ajax.put("fileNames", String.join(FILE_DELIMETER, fileNames));
        ajax.put("newFileNames", String.join(FILE_DELIMETER, newFileNames));
        ajax.put("originalFilenames", String.join(FILE_DELIMETER, originalFilenames));
        return ajax;
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response, @RequestParam String fileName, @RequestParam(required = false) Boolean delete) throws IOException {
        Path root = profileRoot();
        Path target = safeResolve(root, fileName);
        writeFile(response, target, target.getFileName().toString());
        if (Boolean.TRUE.equals(delete)) {
            Files.deleteIfExists(target);
        }
    }

    @GetMapping("/download/resource")
    public void resourceDownload(HttpServletResponse response, @RequestParam String resource) throws IOException {
        if (resource == null || resource.contains("..")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid resource");
            return;
        }
        String normalized = resource.startsWith(Constants.RESOURCE_PREFIX)
            ? resource.substring(Constants.RESOURCE_PREFIX.length())
            : resource;
        Path target = safeResolve(profileRoot(), normalized);
        writeFile(response, target, target.getFileName().toString());
    }

    private UploadResult store(MultipartFile file, String category) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("上传文件不能为空");
        }
        String original = sanitize(file.getOriginalFilename());
        String extension = extension(original);
        String newFileName = DateUtils.dateTimeNow() + "_" + IdUtils.simpleUUID() + (extension.isBlank() ? "" : "." + extension);
        Path directory = profileRoot().resolve(category).resolve(DateUtils.datePath()).normalize();
        Files.createDirectories(directory);
        Path target = directory.resolve(newFileName);
        file.transferTo(target);
        String resourcePath = Constants.RESOURCE_PREFIX + "/" + category + "/" + DateUtils.datePath() + "/" + newFileName;
        return new UploadResult(resourcePath, newFileName, original);
    }

    private void writeFile(HttpServletResponse response, Path target, String downloadName) throws IOException {
        if (!Files.exists(target) || !Files.isRegularFile(target)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(downloadName, StandardCharsets.UTF_8));
        response.setContentLengthLong(Files.size(target));
        Files.copy(target, response.getOutputStream());
    }

    private Path safeResolve(Path root, String fileName) throws IOException {
        if (fileName == null || fileName.isBlank() || fileName.contains("..")) {
            throw new IOException("非法文件路径");
        }
        String clean = fileName.startsWith("/") ? fileName.substring(1) : fileName;
        if (clean.startsWith("profile/")) {
            clean = clean.substring("profile/".length());
        }
        Path target = root.resolve(clean).normalize();
        if (!target.startsWith(root)) {
            throw new IOException("非法文件路径");
        }
        return target;
    }

    private Path profileRoot() {
        String profile = RobotmonitorConfig.getProfile();
        if (profile == null || profile.isBlank()) {
            profile = "/tmp/robotmonitor-upload";
        }
        return Paths.get(profile).toAbsolutePath().normalize();
    }

    private String sanitize(String value) {
        String name = value == null || value.isBlank() ? "file" : value;
        return name.replace("\\", "_").replace("/", "_");
    }

    private String extension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index >= 0 && index < fileName.length() - 1 ? fileName.substring(index + 1) : "";
    }

    private record UploadResult(String resourcePath, String newFileName, String originalFilename) {
    }
}
