package com.robotmonitor.web.controller.api;

import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.ai.PushMessage;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/ws")
public class PushController {
    @PostMapping("/push-message")
    public String send(@RequestBody(required = false) PushMessage message) {
        return "OK";
    }

    @PostMapping("/ai/queue/notice")
    public AjaxResult aiQueueNotice(@RequestBody(required = false) Map<String, Object> payload) {
        return AjaxResult.success("AI 队列通知已接收", Map.of("payload", payload == null ? Map.of() : payload, "mock", true));
    }
}
