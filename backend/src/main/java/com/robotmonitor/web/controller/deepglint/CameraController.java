/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.config.service.IConfigDeviceRegionService
 *  com.robotmonitor.config.service.IConfigDeviceService
 *  com.robotmonitor.config.service.IDeepGlintService
 *  com.robotmonitor.flight.service.IPassengerService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.deepglint;

import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.config.service.IConfigDeviceRegionService;
import com.robotmonitor.config.service.IConfigDeviceService;
import com.robotmonitor.config.service.IDeepGlintService;
import com.robotmonitor.flight.service.IPassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/camera"})
public class CameraController
extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CameraController.class);
    @Autowired
    private IDeepGlintService greenService;
    @Autowired
    private IPassengerService passengerService;
    @Autowired
    private IConfigDeviceService cofigDeviceService;
    @Autowired
    private IConfigDeviceRegionService configDeviceRegionService;

    public void init() {
    }
}
