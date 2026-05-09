/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.ai.service.IAiChatLogService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.quartz.task;

import com.robotmonitor.ai.service.IAiChatLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="aiAutoClassificationTask")
public class AiAutoClassificationTask {
    private static final Logger log = LoggerFactory.getLogger(AiAutoClassificationTask.class);
    @Autowired
    private IAiChatLogService aiChatLogService;

    public void autoClassification() {
        log.info("\u81ea\u52a8\u6267\u884cAI\u804a\u5929\u8bb0\u5f55\u5f52\u7c7b");
        this.aiChatLogService.runAiAutoClassification();
    }
}
