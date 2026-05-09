/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.insp.InspTask
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.bot.service.impl;

import com.robotmonitor.bot.mapper.InspTaskMapper;
import com.robotmonitor.bot.service.IInspTaskService;
import com.robotmonitor.common.core.domain.insp.InspTask;
import com.robotmonitor.common.utils.DateUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspTaskServiceImpl
implements IInspTaskService {
    @Autowired
    private InspTaskMapper inspTaskMapper;

    @Override
    public InspTask selectInspTaskById(Long id) {
        return this.inspTaskMapper.selectInspTaskById(id);
    }

    @Override
    public List<InspTask> selectInspTaskList(InspTask inspTask) {
        return this.inspTaskMapper.selectInspTaskList(inspTask);
    }

    @Override
    public int insertInspTask(InspTask inspTask) {
        inspTask.setCreateTime(DateUtils.getNowDate());
        return this.inspTaskMapper.insertInspTask(inspTask);
    }

    @Override
    public int updateInspTask(InspTask inspTask) {
        return this.inspTaskMapper.updateInspTask(inspTask);
    }

    @Override
    public int deleteInspTaskByIds(Long[] ids) {
        return this.inspTaskMapper.deleteInspTaskByIds(ids);
    }

    @Override
    public int deleteInspTaskById(Long id) {
        return this.inspTaskMapper.deleteInspTaskById(id);
    }
}
