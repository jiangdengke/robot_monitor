/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.flight.service.IUpdatePassengerService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.quartz.task;

import com.robotmonitor.flight.service.IUpdatePassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MergePassengerTask {
    @Autowired
    private IUpdatePassengerService updatePassengerService;

    public void mergePassenger() {
        this.updatePassengerService.mergePassenger();
    }
}
