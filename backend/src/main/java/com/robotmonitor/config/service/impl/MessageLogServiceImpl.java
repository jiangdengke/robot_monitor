/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.MessageLog
 *  com.robotmonitor.common.utils.DateUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.domain.config.MessageLog;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.mapper.MessageLogMapper;
import com.robotmonitor.config.service.IMessageLogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageLogServiceImpl
implements IMessageLogService {
    @Autowired
    private MessageLogMapper messageLogMapper;

    @Override
    public MessageLog selectMessageLogById(Long id) {
        return this.messageLogMapper.selectMessageLogById(id);
    }

    @Override
    public List<MessageLog> selectMessageLogList(MessageLog messageLog) {
        return this.messageLogMapper.selectMessageLogList(messageLog);
    }

    @Override
    public int insertMessageLog(MessageLog messageLog) {
        messageLog.setCreateTime(DateUtils.getNowDate());
        return this.messageLogMapper.insertMessageLog(messageLog);
    }

    @Override
    public int updateMessageLog(MessageLog messageLog) {
        messageLog.setUpdateTime(DateUtils.getNowDate());
        return this.messageLogMapper.updateMessageLog(messageLog);
    }

    @Override
    public int deleteMessageLogByIds(Long[] ids) {
        return this.messageLogMapper.deleteMessageLogByIds(ids);
    }

    @Override
    public int deleteMessageLogById(Long id) {
        return this.messageLogMapper.deleteMessageLogById(id);
    }
}
