/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.ai.PushMessage
 *  com.robotmonitor.config.service.IPushService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.common.core.domain.ai.PushMessage;
import com.robotmonitor.config.service.IPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/ws"})
public class PushController {
    private static final Logger log = LoggerFactory.getLogger(PushController.class);
    @Autowired
    private IPushService pushService;

    @PostMapping(value={"/push-message"})
    public String send(@RequestBody PushMessage message) {
        this.pushService.push(message);
        return "OK";
    }
}
