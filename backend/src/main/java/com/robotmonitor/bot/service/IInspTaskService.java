/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.insp.InspTask
 */
package com.robotmonitor.bot.service;

import com.robotmonitor.common.core.domain.insp.InspTask;
import java.util.List;

public interface IInspTaskService {
    public InspTask selectInspTaskById(Long var1);

    public List<InspTask> selectInspTaskList(InspTask var1);

    public int insertInspTask(InspTask var1);

    public int updateInspTask(InspTask var1);

    public int deleteInspTaskByIds(Long[] var1);

    public int deleteInspTaskById(Long var1);
}
