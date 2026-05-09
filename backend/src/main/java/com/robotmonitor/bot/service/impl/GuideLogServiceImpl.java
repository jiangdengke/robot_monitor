/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.bot.service.impl;

import com.robotmonitor.bot.domain.GuideLog;
import com.robotmonitor.bot.domain.GuideLogInfo;
import com.robotmonitor.bot.domain.GuideLogInfoRequest;
import com.robotmonitor.bot.mapper.GuideLogMapper;
import com.robotmonitor.bot.service.IGuideLogService;
import com.robotmonitor.common.utils.DateUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuideLogServiceImpl
implements IGuideLogService {
    @Autowired
    private GuideLogMapper guideLogMapper;

    @Override
    public GuideLog selectGuideLogById(Long id) {
        return this.guideLogMapper.selectGuideLogById(id);
    }

    @Override
    public List<GuideLog> selectGuideLogList(GuideLog guideLog) {
        return this.guideLogMapper.selectGuideLogList(guideLog);
    }

    @Override
    public List<GuideLogInfo> selectGuideLogInfoList(GuideLogInfoRequest guideLogInfoRequest) {
        return this.guideLogMapper.selectGuideLogInfoList(guideLogInfoRequest);
    }

    @Override
    public int insertGuideLog(GuideLog guideLog) {
        guideLog.setCreateTime(DateUtils.getNowDate());
        return this.guideLogMapper.insertGuideLog(guideLog);
    }

    @Override
    public int updateGuideLog(GuideLog guideLog) {
        return this.guideLogMapper.updateGuideLog(guideLog);
    }

    @Override
    public int deleteGuideLogByIds(Long[] ids) {
        return this.guideLogMapper.deleteGuideLogByIds(ids);
    }

    @Override
    public int deleteGuideLogById(Long id) {
        return this.guideLogMapper.deleteGuideLogById(id);
    }
}
