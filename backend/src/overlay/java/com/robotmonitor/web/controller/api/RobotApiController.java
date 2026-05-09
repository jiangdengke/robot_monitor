package com.robotmonitor.web.controller.api;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_ROBOT;
import static com.robotmonitor.jooq.generated.Tables.ROBOT_CMD_LOG;
import static com.robotmonitor.jooq.generated.Tables.ROBOT_TASK;

import com.robotmonitor.common.core.domain.AjaxResult;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jooq.DSLContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/robot")
public class RobotApiController {
    private final DSLContext dsl;

    public RobotApiController(DSLContext dsl) {
        this.dsl = dsl;
    }

    @PostMapping("/cmd")
    public AjaxResult sendCmd(@RequestBody(required = false) Map<String, Object> request) {
        return AjaxResult.success(createTask(resolveRobotId(request), "本地指令任务", "cmd", request));
    }

    @PostMapping("/move")
    public AjaxResult sendMove(@RequestBody(required = false) Map<String, Object> request) {
        return AjaxResult.success(createTask(resolveRobotId(request), "本地引导任务", "move", request));
    }

    @GetMapping("/get-position")
    public AjaxResult getPosition(@RequestParam String robotId) {
        if (robotId != null && robotId.contains(",")) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (String id : robotId.split(",")) {
                list.add(position(id.trim()));
            }
            return AjaxResult.success(list);
        }
        return AjaxResult.success(position(robotId));
    }

    @GetMapping("/get-position-by-roomcode")
    public AjaxResult getPositionByRoomCode(@RequestParam String roomCode) {
        List<Map<String, Object>> list = dsl.select(CONFIG_ROBOT.ROBOT_ID, CONFIG_ROBOT.REGION_ID, CONFIG_ROBOT.ORI_COORDINATE)
            .from(CONFIG_ROBOT)
            .where(CONFIG_ROBOT.ROOM_CODE.eq(roomCode).and(CONFIG_ROBOT.IS_DELETE.ne("1")))
            .fetch(record -> {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("robotId", record.get(CONFIG_ROBOT.ROBOT_ID));
                row.put("regionId", record.get(CONFIG_ROBOT.REGION_ID));
                row.put("location", record.get(CONFIG_ROBOT.ORI_COORDINATE));
                return row;
            });
        return AjaxResult.success(list);
    }

    @PostMapping("/set-robot-state")
    public AjaxResult setRobotState(@RequestBody(required = false) Map<String, Object> request) {
        String robotId = resolveRobotId(request);
        String state = stringValue(request, "state", "workingState", "working_state");
        if (state == null || state.isBlank()) {
            state = "idle";
        }
        dsl.update(CONFIG_ROBOT)
            .set(CONFIG_ROBOT.WORKING_STATE, state)
            .set(CONFIG_ROBOT.UPDATE_TIME, LocalDateTime.now())
            .where(CONFIG_ROBOT.ROBOT_ID.eq(robotId).or(CONFIG_ROBOT.ID.eq(parseLong(robotId))))
            .execute();
        return AjaxResult.success();
    }

    @PostMapping("/listen")
    public AjaxResult listen(@RequestBody(required = false) Map<String, Object> request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("text", stringValue(request, "text", "question", "content"));
        response.put("answer", "本地 mock 已接收语音请求");
        response.put("mock", true);
        return AjaxResult.success(response);
    }

    @PostMapping("/insertRobotCmdLog")
    public AjaxResult insertRobotCmdLog(@RequestBody(required = false) Map<String, Object> request) {
        dsl.insertInto(ROBOT_CMD_LOG)
            .set(ROBOT_CMD_LOG.ROBOT_ID, resolveRobotId(request))
            .set(ROBOT_CMD_LOG.CMD, request == null ? "{}" : request.toString())
            .set(ROBOT_CMD_LOG.CMD_TYPE, "mocked")
            .set(ROBOT_CMD_LOG.CREATE_TIME, LocalDateTime.now())
            .execute();
        return AjaxResult.success();
    }

    @PostMapping("/endTask")
    public AjaxResult endTask(@RequestBody(required = false) Map<String, Object> request) {
        Long taskId = parseLong(stringValue(request, "robotTaskId", "robot_task_id", "id"));
        if (taskId != null) {
            dsl.update(ROBOT_TASK)
                .set(ROBOT_TASK.TASK_STATUS, "9")
                .set(ROBOT_TASK.END_TIME, LocalDateTime.now())
                .where(ROBOT_TASK.ID.eq(taskId))
                .execute();
        }
        return AjaxResult.success();
    }

    @PostMapping("/robotOnlineEvent")
    public AjaxResult robotOnlineEvent(@RequestBody(required = false) Map<String, Object> request) {
        String robotId = resolveRobotId(request);
        String online = String.valueOf(request == null ? true : request.getOrDefault("online", true));
        dsl.update(CONFIG_ROBOT)
            .set(CONFIG_ROBOT.NETWORK, "true".equalsIgnoreCase(online) ? 1 : 0)
            .set(CONFIG_ROBOT.UPDATE_TIME, LocalDateTime.now())
            .where(CONFIG_ROBOT.ROBOT_ID.eq(robotId).or(CONFIG_ROBOT.ID.eq(parseLong(robotId))))
            .execute();
        return AjaxResult.success();
    }

    @GetMapping("/reset-robot-task-status")
    public AjaxResult resetRobotTaskStatus(@RequestParam String robotId) {
        dsl.update(CONFIG_ROBOT)
            .set(CONFIG_ROBOT.TASK_STATUS, "0")
            .set(CONFIG_ROBOT.WORKING_STATE, "idle")
            .set(CONFIG_ROBOT.UPDATE_TIME, LocalDateTime.now())
            .where(CONFIG_ROBOT.ROBOT_ID.eq(robotId).or(CONFIG_ROBOT.ID.eq(parseLong(robotId))))
            .execute();
        return AjaxResult.success();
    }

    @GetMapping("/reset-robot-home-status")
    public AjaxResult resetRobotHomeStatus(@RequestParam String robotId) {
        return resetRobotTaskStatus(robotId);
    }

    @PostMapping("/robotTaskCheckEvent")
    public AjaxResult robotTaskCheckEvent(@RequestBody(required = false) Map<String, Object> request) {
        return AjaxResult.success(createTask(resolveRobotId(request), "本地任务检查", "check", request));
    }

    @GetMapping("/task/{id}")
    public AjaxResult task(@PathVariable("id") Long id) {
        Map<String, Object> task = dsl.select(ROBOT_TASK.fields())
            .from(ROBOT_TASK)
            .where(ROBOT_TASK.ID.eq(id))
            .fetchOneMap();
        return AjaxResult.success(task);
    }

    private Map<String, Object> createTask(String robotId, String taskName, String action, Map<String, Object> request) {
        LocalDateTime now = LocalDateTime.now();
        String cmd = request == null ? "{}" : request.toString();
        Long taskId = dsl.insertInto(ROBOT_TASK)
            .set(ROBOT_TASK.ROBOT_ID, robotId)
            .set(ROBOT_TASK.TASK_NAME, taskName)
            .set(ROBOT_TASK.TASK_TYPE, "0")
            .set(ROBOT_TASK.TASK_SUBTYPE, action)
            .set(ROBOT_TASK.TASK_MODE, "mock")
            .set(ROBOT_TASK.TASK_STATUS, "2")
            .set(ROBOT_TASK.DIRECT_EXECUTION, "1")
            .set(ROBOT_TASK.CREATE_TIME, now)
            .set(ROBOT_TASK.START_TIME, now)
            .set(ROBOT_TASK.RETURN_INFO, "本地 mock 任务已提交")
            .set(ROBOT_TASK.CMD, cmd)
            .returningResult(ROBOT_TASK.ID)
            .fetchOne(ROBOT_TASK.ID);
        dsl.update(CONFIG_ROBOT)
            .set(CONFIG_ROBOT.TASK_ID, taskId)
            .set(CONFIG_ROBOT.TASK_STATUS, "2")
            .set(CONFIG_ROBOT.WORKING_STATE, action)
            .set(CONFIG_ROBOT.UPDATE_TIME, now)
            .where(CONFIG_ROBOT.ROBOT_ID.eq(robotId).or(CONFIG_ROBOT.ID.eq(parseLong(robotId))))
            .execute();
        Map<String, Object> task = new LinkedHashMap<>();
        task.put("id", taskId);
        task.put("robotId", robotId);
        task.put("taskName", taskName);
        task.put("taskStatus", "2");
        task.put("action", action);
        task.put("mock", true);
        return task;
    }

    private Map<String, Object> position(String robotId) {
        var record = dsl.select(CONFIG_ROBOT.ROBOT_ID, CONFIG_ROBOT.REGION_ID, CONFIG_ROBOT.ORI_COORDINATE)
            .from(CONFIG_ROBOT)
            .where(CONFIG_ROBOT.ROBOT_ID.eq(robotId).or(CONFIG_ROBOT.ID.eq(parseLong(robotId))))
            .fetchOne();
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("robotId", robotId);
        row.put("regionId", record == null ? null : record.get(CONFIG_ROBOT.REGION_ID));
        row.put("location", record == null ? null : record.get(CONFIG_ROBOT.ORI_COORDINATE));
        return row;
    }

    private String resolveRobotId(Map<String, Object> request) {
        String robotId = stringValue(request, "robotId", "robot_id", "id");
        if (robotId != null && !robotId.isBlank()) {
            return robotId;
        }
        String first = dsl.select(CONFIG_ROBOT.ROBOT_ID)
            .from(CONFIG_ROBOT)
            .where(CONFIG_ROBOT.IS_DELETE.ne("1"))
            .limit(1)
            .fetchOne(CONFIG_ROBOT.ROBOT_ID);
        return first == null ? "mock-robot" : first;
    }

    private String stringValue(Map<String, Object> request, String... keys) {
        if (request == null) {
            return null;
        }
        for (String key : keys) {
            Object value = request.get(key);
            if (value != null) {
                return String.valueOf(value);
            }
        }
        return null;
    }

    private Long parseLong(String value) {
        try {
            return value == null || value.isBlank() ? null : Long.valueOf(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
