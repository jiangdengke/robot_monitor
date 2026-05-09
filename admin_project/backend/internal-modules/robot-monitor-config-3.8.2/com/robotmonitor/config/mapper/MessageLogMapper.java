/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.MessageLog
 */
package com.robotmonitor.config.mapper;

import com.robotmonitor.common.core.domain.config.MessageLog;
import java.util.List;

public interface MessageLogMapper {
    public MessageLog selectMessageLogById(Long var1);

    public List<MessageLog> selectMessageLogList(MessageLog var1);

    public int insertMessageLog(MessageLog var1);

    public int updateMessageLog(MessageLog var1);

    public int deleteMessageLogById(Long var1);

    public int deleteMessageLogByIds(Long[] var1);
}
