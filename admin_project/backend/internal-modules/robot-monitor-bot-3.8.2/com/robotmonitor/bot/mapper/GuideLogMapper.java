/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.bot.mapper;

import com.robotmonitor.bot.domain.GuideLog;
import com.robotmonitor.bot.domain.GuideLogInfo;
import com.robotmonitor.bot.domain.GuideLogInfoRequest;
import java.util.List;

public interface GuideLogMapper {
    public GuideLog selectGuideLogById(Long var1);

    public List<GuideLog> selectGuideLogList(GuideLog var1);

    public List<GuideLogInfo> selectGuideLogInfoList(GuideLogInfoRequest var1);

    public int insertGuideLog(GuideLog var1);

    public int updateGuideLog(GuideLog var1);

    public int deleteGuideLogById(Long var1);

    public int deleteGuideLogByIds(Long[] var1);
}
