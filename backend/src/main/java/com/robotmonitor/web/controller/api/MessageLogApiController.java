/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.MessageLog
 *  com.robotmonitor.config.service.IMessageLogService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.common.core.domain.config.MessageLog;
import com.robotmonitor.config.service.IMessageLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/msg"})
public class MessageLogApiController {
    @Autowired
    private IMessageLogService messageLogService;

    @PostMapping(value={"/insertMessageLog"})
    public int insertMessageLog(@RequestBody MessageLog messageLog) {
        return this.messageLogService.insertMessageLog(messageLog);
    }
}
