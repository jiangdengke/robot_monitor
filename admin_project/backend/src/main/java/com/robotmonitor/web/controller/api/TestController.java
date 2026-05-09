/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.bot.domain.CustomerNotificationRequest
 *  com.robotmonitor.bot.service.RobotService
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoRequest
 *  com.robotmonitor.config.service.IDeepGlintService
 *  com.robotmonitor.flight.service.IPassengerService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.bot.domain.CustomerNotificationRequest;
import com.robotmonitor.bot.service.RobotService;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoRequest;
import com.robotmonitor.config.service.IDeepGlintService;
import com.robotmonitor.flight.service.IPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/test"})
public class TestController {
    @Autowired
    private IPassengerService tPassengerService;
    @Autowired
    private IDeepGlintService deepGlintService;
    @Autowired
    private RobotService robotService;

    @GetMapping(value={"/statistics"})
    public AjaxResult getPassengerStatistics() {
        return AjaxResult.success((Object)this.tPassengerService.getPassengerStatistics());
    }

    @PostMapping(value={"/notifyCustomer"})
    public AjaxResult notifyCustomer(@RequestBody CustomerNotificationRequest customerNotificationRequest) {
        return AjaxResult.success((Object)this.robotService.notifyCustomer(customerNotificationRequest));
    }

    @PostMapping(value={"/registerPersonToCompareRepo"})
    public AjaxResult registerPersonToCompareRepo(@RequestBody RegisterPersonToCompareRepoRequest request) {
        return AjaxResult.success((Object)this.deepGlintService.registerPersonToCompareRepo(request));
    }
}
