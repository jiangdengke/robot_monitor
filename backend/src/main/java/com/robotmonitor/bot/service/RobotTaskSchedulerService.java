/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.robot.RobotTask
 */
package com.robotmonitor.bot.service;

import com.robotmonitor.common.core.domain.robot.RobotTask;

public interface RobotTaskSchedulerService {
    public void stop(String var1, Long var2, String var3);

    public void addQueue(RobotTask var1);

    public void clearQueue(String var1, String var2);

    public void startNow(String var1);

    public void setRobotInUseFlag(String var1, String var2);

    public boolean getRobotInUseFlag(String var1);

    public void geHome(String var1);

    public void scheduleGoHomeIfIdle(String var1);

    public void setHomeStatus(String var1, boolean var2);

    public void checkCurrentTask(String var1);
}
