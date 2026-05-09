/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.insp.InspTaskResult
 */
package com.robotmonitor.bot.service;

import com.robotmonitor.common.core.domain.insp.InspTaskResult;
import java.util.List;

public interface IInspTaskResultService {
    public InspTaskResult selectInspTaskResultById(Long var1);

    public List<InspTaskResult> selectInspTaskResultList(InspTaskResult var1);

    public int insertInspTaskResult(InspTaskResult var1);

    public int updateInspTaskResult(InspTaskResult var1);

    public int deleteInspTaskResultByIds(Long[] var1);

    public int deleteInspTaskResultById(Long var1);
}
