package com.robotmonitor.web.controller.api;

import static com.robotmonitor.jooq.generated.Tables.AI_CHAT_LOG;
import static com.robotmonitor.jooq.generated.Tables.CONFIG_ROBOT;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.robot.Admittance;
import com.robotmonitor.common.core.domain.robot.RobotChatRequest;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.common.core.domain.robot.RobotListenQwenRequest;
import com.robotmonitor.common.core.page.TableDataInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/ai")
public class AiApiController {
    private final DSLContext dsl;

    public AiApiController(DSLContext dsl) {
        this.dsl = dsl;
    }

    @PostMapping({"/robot-chat", "/robot-qwen-chat", "/normal-chat", "/unitree-robot-chat", "/robot-chat-only"})
    public RobotChatResponse chat(@RequestBody(required = false) RobotChatRequest request) {
        String robotId = request == null || isBlank(request.getRobotId()) ? firstRobotId() : request.getRobotId();
        String question = request == null ? "" : request.getMessage();
        String answer = "本地 AI mock 已接收：" + (isBlank(question) ? "空问题" : question);
        saveChatLog(robotId, question, answer, "NORMAL", request == null ? "CN" : request.getLanguage());
        RobotChatResponse response = new RobotChatResponse("TEXT", answer);
        response.setRobotId(robotId);
        response.setLanguage(request == null ? "CN" : request.getLanguage());
        response.setExtraInfo("mock");
        response.setNeedVoice(request != null && request.isNeedVoice());
        return response;
    }

    @PostMapping("/robot-qwen-chat-intent-detection")
    public RobotChatResponse intentDetection(@RequestBody(required = false) RobotListenQwenRequest request) {
        RobotChatResponse response = new RobotChatResponse("INTENT", "本地 mock 意图识别完成");
        response.setExtraInfo("FAQ");
        response.setNeedVoice(false);
        return response;
    }

    @GetMapping("/robot-reset-memory")
    public boolean resetMemory(@RequestParam(value = "chatId", required = false) String chatId) {
        return true;
    }

    @PostMapping("/validate-admittance")
    public RobotChatResponse validateAdmittance(@RequestBody(required = false) Admittance admittance) {
        RobotChatResponse response = new RobotChatResponse("ADMITTANCE", "本地 mock 准入通过");
        response.setRobotId(admittance == null ? firstRobotId() : admittance.getRobotId());
        response.setExtraInfo("mock");
        return response;
    }

    @GetMapping("/run-ai-auto-classification")
    public AjaxResult runAiAutoClassification() {
        int rows = dsl.update(AI_CHAT_LOG)
            .set(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION, "1")
            .where(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION.isNull()
                .or(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION.eq(""))
                .or(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION.eq("0")))
            .execute();
        return AjaxResult.success(Map.of("updated", rows, "mock", true));
    }

    @GetMapping("/ai-question-stat-list")
    public TableDataInfo selectAiQuestionStatList(
        @RequestParam(value = "robotId", required = false) String robotId,
        @RequestParam(value = "question", required = false) String question,
        @RequestParam(value = "chatType", required = false) String chatType,
        @RequestParam(value = "startTime", required = false) String startTime,
        @RequestParam(value = "endTime", required = false) String endTime,
        @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
        @RequestParam(value = "pageSize", required = false, defaultValue = "50") Integer pageSize
    ) {
        Condition condition = DSL.and(
            eqIfPresent(AI_CHAT_LOG.ROBOT_ID, robotId),
            isBlank(question) ? DSL.noCondition() : AI_CHAT_LOG.QUESTION.like("%" + question + "%"),
            eqIfPresent(AI_CHAT_LOG.CHAT_TYPE, chatType),
            isBlank(startTime) ? DSL.noCondition() : AI_CHAT_LOG.CREATE_TIME.ge(parseDateTime(startTime)),
            isBlank(endTime) ? DSL.noCondition() : AI_CHAT_LOG.CREATE_TIME.le(parseDateTime(endTime))
        );
        long total = dsl.fetchCount(AI_CHAT_LOG, condition);
        int safePageNum = pageNum == null || pageNum <= 0 ? 1 : pageNum;
        int safePageSize = pageSize == null || pageSize <= 0 ? 50 : pageSize;
        List<Map<String, Object>> rows = dsl.select(
                AI_CHAT_LOG.ID,
                AI_CHAT_LOG.ROBOT_ID,
                CONFIG_ROBOT.ROBOT_NAME,
                SYS_DEPT.DEPT_NAME,
                AI_CHAT_LOG.AI_AUTO_CLASSIFICATION,
                AI_CHAT_LOG.QUESTION,
                AI_CHAT_LOG.ANSWER,
                AI_CHAT_LOG.CHAT_TYPE,
                AI_CHAT_LOG.CREATE_TIME
            )
            .from(AI_CHAT_LOG)
            .leftJoin(CONFIG_ROBOT).on(AI_CHAT_LOG.ROBOT_ID.eq(CONFIG_ROBOT.ROBOT_ID))
            .leftJoin(SYS_DEPT).on(CONFIG_ROBOT.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE))
            .where(condition)
            .orderBy(AI_CHAT_LOG.CREATE_TIME.desc(), AI_CHAT_LOG.ID.desc())
            .limit(safePageSize)
            .offset((safePageNum - 1) * safePageSize)
            .fetch(this::mapQuestionStat);
        return table(rows, total);
    }

    @PostMapping("/prepare-host-admittance")
    public AjaxResult prepareHostAdmittance(@RequestParam(value = "robotId") String robotId) {
        return AjaxResult.success("准备成功", Map.of("robotId", robotId, "type", "HOST", "mock", true));
    }

    @PostMapping("/prepare-follower-admittance")
    public AjaxResult prepareFollowerAdmittance(
        @RequestParam(value = "robotId") String robotId,
        @RequestParam(value = "hostCollectId") String hostCollectId
    ) {
        return AjaxResult.success("准备成功", Map.of("robotId", robotId, "hostCollectId", hostCollectId, "type", "FOLLOWER", "mock", true));
    }

    @PostMapping("/queue/notice")
    public AjaxResult queueNotice(@RequestBody(required = false) Map<String, Object> payload) {
        return AjaxResult.success("AI 队列通知已接收", Map.of("payload", payload == null ? Map.of() : payload, "mock", true));
    }

    private void saveChatLog(String robotId, String question, String answer, String chatType, String language) {
        dsl.insertInto(AI_CHAT_LOG)
            .set(AI_CHAT_LOG.ROBOT_ID, robotId)
            .set(AI_CHAT_LOG.QUESTION, question)
            .set(AI_CHAT_LOG.ANSWER, answer)
            .set(AI_CHAT_LOG.CHAT_TYPE, chatType)
            .set(AI_CHAT_LOG.LANGUAGE, isBlank(language) ? "CN" : language)
            .set(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION, "0")
            .set(AI_CHAT_LOG.CREATE_TIME, LocalDateTime.now())
            .execute();
    }

    private Map<String, Object> mapQuestionStat(Record record) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("id", record.get(AI_CHAT_LOG.ID));
        row.put("robotId", record.get(AI_CHAT_LOG.ROBOT_ID));
        row.put("robotName", record.get(CONFIG_ROBOT.ROBOT_NAME));
        row.put("deptName", record.get(SYS_DEPT.DEPT_NAME));
        row.put("aiAutoClassification", record.get(AI_CHAT_LOG.AI_AUTO_CLASSIFICATION));
        row.put("question", record.get(AI_CHAT_LOG.QUESTION));
        row.put("answer", record.get(AI_CHAT_LOG.ANSWER));
        row.put("chatType", record.get(AI_CHAT_LOG.CHAT_TYPE));
        row.put("createTime", record.get(AI_CHAT_LOG.CREATE_TIME));
        row.put("count", 1);
        return row;
    }

    private TableDataInfo table(List<?> rows, long total) {
        TableDataInfo table = new TableDataInfo();
        table.setCode(200);
        table.setMsg("查询成功");
        table.setRows(rows);
        table.setTotal(total);
        return table;
    }

    private String firstRobotId() {
        String robotId = dsl.select(CONFIG_ROBOT.ROBOT_ID)
            .from(CONFIG_ROBOT)
            .where(CONFIG_ROBOT.IS_DELETE.ne("1"))
            .limit(1)
            .fetchOne(CONFIG_ROBOT.ROBOT_ID);
        return isBlank(robotId) ? "mock-robot" : robotId;
    }

    private org.jooq.Condition eqIfPresent(org.jooq.Field<String> field, String value) {
        return isBlank(value) ? DSL.noCondition() : field.eq(value);
    }

    private LocalDateTime parseDateTime(String value) {
        String text = value.trim();
        if (text.length() == 10) {
            return LocalDate.parse(text).atStartOfDay();
        }
        if (text.length() == 14 && text.chars().allMatch(Character::isDigit)) {
            return LocalDateTime.parse(text, java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }
        return LocalDateTime.parse(text.replace(' ', 'T'));
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
