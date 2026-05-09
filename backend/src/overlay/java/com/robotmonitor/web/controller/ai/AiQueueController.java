package com.robotmonitor.web.controller.ai;

import com.robotmonitor.common.core.domain.AjaxResult;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/queue")
public class AiQueueController {
    @PostMapping("/notice")
    public AjaxResult notice(@RequestBody(required = false) Map<String, Object> payload) {
        return AjaxResult.success("AI 队列通知已接收", Map.of("payload", payload == null ? Map.of() : payload, "mock", true));
    }
}
