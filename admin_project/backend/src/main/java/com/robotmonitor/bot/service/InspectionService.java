/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigTask
 *  com.robotmonitor.common.core.domain.insp.InspectionAlarm
 *  com.robotmonitor.common.core.domain.insp.InspectionSummary
 */
package com.robotmonitor.bot.service;

import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.core.domain.insp.InspectionAlarm;
import com.robotmonitor.common.core.domain.insp.InspectionSummary;

public interface InspectionService {
    public void run(ConfigTask var1);

    public void saveInspectionAlarm(InspectionAlarm var1);

    public void saveInspectionSummary(InspectionSummary var1);
}
