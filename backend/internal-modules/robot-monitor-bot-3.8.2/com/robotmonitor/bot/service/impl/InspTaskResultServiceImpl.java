/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.insp.InspTaskResult
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.bot.service.impl;

import com.robotmonitor.bot.mapper.InspTaskResultMapper;
import com.robotmonitor.bot.service.IInspTaskResultService;
import com.robotmonitor.common.core.domain.insp.InspTaskResult;
import com.robotmonitor.common.utils.DateUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspTaskResultServiceImpl
implements IInspTaskResultService {
    @Autowired
    private InspTaskResultMapper inspTaskResultMapper;

    @Override
    public InspTaskResult selectInspTaskResultById(Long id) {
        return this.inspTaskResultMapper.selectInspTaskResultById(id);
    }

    @Override
    public List<InspTaskResult> selectInspTaskResultList(InspTaskResult inspTaskResult) {
        return this.inspTaskResultMapper.selectInspTaskResultList(inspTaskResult);
    }

    @Override
    public int insertInspTaskResult(InspTaskResult inspTaskResult) {
        inspTaskResult.setCreateTime(DateUtils.getNowDate());
        return this.inspTaskResultMapper.insertInspTaskResult(inspTaskResult);
    }

    @Override
    public int updateInspTaskResult(InspTaskResult inspTaskResult) {
        return this.inspTaskResultMapper.updateInspTaskResult(inspTaskResult);
    }

    @Override
    public int deleteInspTaskResultByIds(Long[] ids) {
        return this.inspTaskResultMapper.deleteInspTaskResultByIds(ids);
    }

    @Override
    public int deleteInspTaskResultById(Long id) {
        return this.inspTaskResultMapper.deleteInspTaskResultById(id);
    }
}
