package com.robotmonitor.web.controller.common;

import static com.robotmonitor.jooq.generated.Tables.GUIDE_LOG;
import static com.robotmonitor.jooq.generated.Tables.FLIGHT_INFO;
import static com.robotmonitor.jooq.generated.Tables.SYS_CONFIG;
import static com.robotmonitor.jooq.generated.Tables.SYS_DICT_DATA;
import static com.robotmonitor.jooq.generated.Tables.SYS_DICT_TYPE;
import static com.robotmonitor.jooq.generated.Tables.SYS_JOB;
import static com.robotmonitor.jooq.generated.Tables.SYS_JOB_LOG;
import static com.robotmonitor.jooq.generated.Tables.SYS_LOGININFOR;
import static com.robotmonitor.jooq.generated.Tables.SYS_OPER_LOG;
import static com.robotmonitor.jooq.generated.Tables.SYS_POST;
import static com.robotmonitor.jooq.generated.Tables.SYS_ROLE;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;
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
    private final DSLContext dsl;

    public CommonController(ServerConfig serverConfig, DSLContext dsl) {
        this.serverConfig = serverConfig;
        this.dsl = dsl;
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

    @RequestMapping("/export")
    public void export(HttpServletResponse response, @RequestParam Map<String, String> query) throws IOException {
        String sourcePath = query.get("sourcePath");
        ExportTarget target = exportTargets().get(sourcePath);
        if (target == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported export source: " + sourcePath);
            return;
        }
        Condition condition = buildExportCondition(target.table(), query);
        Result<Record> rows = dsl.select(target.fields())
            .from(target.table())
            .where(condition)
            .limit(5000)
            .fetch();
        byte[] data = toCsv(target, rows).getBytes(StandardCharsets.UTF_8);
        response.reset();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(target.fileName(), StandardCharsets.UTF_8));
        response.setContentLength(data.length);
        response.getOutputStream().write(data);
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

    private Map<String, ExportTarget> exportTargets() {
        Map<String, ExportTarget> targets = new LinkedHashMap<>();
        targets.put("/system/user/export", new ExportTarget("用户数据.csv", SYS_USER, SYS_USER.USER_ID, SYS_USER.USER_NAME, SYS_USER.NICK_NAME, SYS_USER.EMAIL, SYS_USER.PHONENUMBER, SYS_USER.STATUS, SYS_USER.CREATE_TIME));
        targets.put("/system/role/export", new ExportTarget("角色数据.csv", SYS_ROLE, SYS_ROLE.ROLE_ID, SYS_ROLE.ROLE_NAME, SYS_ROLE.ROLE_KEY, SYS_ROLE.ROLE_SORT, SYS_ROLE.STATUS, SYS_ROLE.CREATE_TIME));
        targets.put("/system/post/export", new ExportTarget("岗位数据.csv", SYS_POST, SYS_POST.POST_ID, SYS_POST.POST_CODE, SYS_POST.POST_NAME, SYS_POST.POST_SORT, SYS_POST.STATUS, SYS_POST.CREATE_TIME));
        targets.put("/system/config/export", new ExportTarget("参数数据.csv", SYS_CONFIG, SYS_CONFIG.CONFIG_ID, SYS_CONFIG.CONFIG_NAME, SYS_CONFIG.CONFIG_KEY, SYS_CONFIG.CONFIG_VALUE, SYS_CONFIG.CONFIG_TYPE, SYS_CONFIG.REMARK));
        targets.put("/system/dict/type/export", new ExportTarget("字典类型.csv", SYS_DICT_TYPE, SYS_DICT_TYPE.DICT_ID, SYS_DICT_TYPE.DICT_NAME, SYS_DICT_TYPE.DICT_TYPE, SYS_DICT_TYPE.STATUS, SYS_DICT_TYPE.CREATE_TIME));
        targets.put("/system/dict/data/export", new ExportTarget("字典数据.csv", SYS_DICT_DATA, SYS_DICT_DATA.DICT_CODE, SYS_DICT_DATA.DICT_LABEL, SYS_DICT_DATA.DICT_VALUE, SYS_DICT_DATA.DICT_TYPE, SYS_DICT_DATA.LIST_CLASS, SYS_DICT_DATA.STATUS));
        targets.put("/flight/flightinfo/export", new ExportTarget("航班信息.csv", FLIGHT_INFO, FLIGHT_INFO.FLIGHT_ID, FLIGHT_INFO.FLIGHT_NO, FLIGHT_INFO.AIRLINE_CD, FLIGHT_INFO.SCHE_EXEC_DATE, FLIGHT_INFO.LATEST_OFF_STATUS, FLIGHT_INFO.LATEST_ON_STATUS, FLIGHT_INFO.GATE_CD, FLIGHT_INFO.ESTM_TAKE_OFF_TIME));
        targets.put("/monitor/logininfor/export", new ExportTarget("登录日志.csv", SYS_LOGININFOR, SYS_LOGININFOR.INFO_ID, SYS_LOGININFOR.USER_NAME, SYS_LOGININFOR.IPADDR, SYS_LOGININFOR.LOGIN_LOCATION, SYS_LOGININFOR.BROWSER, SYS_LOGININFOR.OS, SYS_LOGININFOR.STATUS, SYS_LOGININFOR.LOGIN_TIME));
        targets.put("/monitor/operlog/export", new ExportTarget("操作日志.csv", SYS_OPER_LOG, SYS_OPER_LOG.OPER_ID, SYS_OPER_LOG.TITLE, SYS_OPER_LOG.BUSINESS_TYPE, SYS_OPER_LOG.METHOD, SYS_OPER_LOG.REQUEST_METHOD, SYS_OPER_LOG.OPER_NAME, SYS_OPER_LOG.OPER_IP, SYS_OPER_LOG.STATUS, SYS_OPER_LOG.OPER_TIME));
        targets.put("/monitor/job/export", new ExportTarget("定时任务.csv", SYS_JOB, SYS_JOB.JOB_ID, SYS_JOB.JOB_NAME, SYS_JOB.JOB_GROUP, SYS_JOB.INVOKE_TARGET, SYS_JOB.CRON_EXPRESSION, SYS_JOB.STATUS, SYS_JOB.CREATE_TIME));
        targets.put("/monitor/jobLog/export", new ExportTarget("调度日志.csv", SYS_JOB_LOG, SYS_JOB_LOG.JOB_LOG_ID, SYS_JOB_LOG.JOB_NAME, SYS_JOB_LOG.JOB_GROUP, SYS_JOB_LOG.INVOKE_TARGET, SYS_JOB_LOG.JOB_MESSAGE, SYS_JOB_LOG.STATUS, SYS_JOB_LOG.CREATE_TIME));
        targets.put("/ai/log/export", new ExportTarget("引导日志.csv", GUIDE_LOG, GUIDE_LOG.ID, GUIDE_LOG.ROBOT_ID, GUIDE_LOG.REGION_ID, GUIDE_LOG.COORDINATE, GUIDE_LOG.CREATE_TIME));
        return targets;
    }

    private Condition buildExportCondition(Table<?> table, Map<String, String> query) {
        Condition condition = DSL.noCondition();
        for (Field<?> field : table.fields()) {
            String value = query.get(camel(field.getName()));
            if (value == null || value.isBlank()) {
                value = query.get(field.getName());
            }
            if (value == null || value.isBlank() || "sourcePath".equals(field.getName())) {
                continue;
            }
            if (field.getType() == String.class) {
                condition = condition.and(field.cast(String.class).like("%" + value + "%"));
            } else {
                condition = condition.and(field.cast(String.class).eq(value));
            }
        }
        return condition;
    }

    private String toCsv(ExportTarget target, Result<Record> rows) {
        StringBuilder builder = new StringBuilder("\uFEFF");
        builder.append(target.fields().stream().map(Field::getName).map(this::escapeCsv).collect(java.util.stream.Collectors.joining(","))).append("\n");
        for (Record row : rows) {
            builder.append(target.fields().stream()
                .map(field -> row.get(field))
                .map(value -> value == null ? "" : String.valueOf(value))
                .map(this::escapeCsv)
                .collect(java.util.stream.Collectors.joining(",")))
                .append("\n");
        }
        return builder.toString();
    }

    private String escapeCsv(String value) {
        String text = value == null ? "" : value;
        if (text.contains(",") || text.contains("\"") || text.contains("\n") || text.contains("\r")) {
            return "\"" + text.replace("\"", "\"\"") + "\"";
        }
        return text;
    }

    private String camel(String value) {
        StringBuilder builder = new StringBuilder();
        boolean upperNext = false;
        for (char ch : value.toCharArray()) {
            if (ch == '_') {
                upperNext = true;
                continue;
            }
            builder.append(upperNext ? Character.toUpperCase(ch) : Character.toLowerCase(ch));
            upperNext = false;
        }
        return builder.toString();
    }

    private record UploadResult(String resourcePath, String newFileName, String originalFilename) {
    }

    private record ExportTarget(String fileName, Table<?> table, List<Field<?>> fields) {
        ExportTarget(String fileName, Table<?> table, Field<?>... fields) {
            this(fileName, table, List.of(fields));
        }
    }
}
