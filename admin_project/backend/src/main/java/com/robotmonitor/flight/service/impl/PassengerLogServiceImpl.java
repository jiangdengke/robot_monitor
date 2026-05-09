/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.DictUtils
 *  com.robotmonitor.config.service.IConfigRobotService
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.flight.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.DictUtils;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.flight.domain.CollectInResponse2;
import com.robotmonitor.flight.domain.PassengerLog;
import com.robotmonitor.flight.mapper.PassengerLogMapper;
import com.robotmonitor.flight.service.IPassengerLogService;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerLogServiceImpl
implements IPassengerLogService {
    @Autowired
    private PassengerLogMapper passengerLogMapper;
    @Autowired
    private IConfigRobotService robotService;

    @Override
    public PassengerLog selectPassengerLogById(Long id) {
        return this.passengerLogMapper.selectPassengerLogById(id);
    }

    @Override
    public List<PassengerLog> selectPassengerLogList(PassengerLog passengerLog) {
        return this.passengerLogMapper.selectPassengerLogList(passengerLog);
    }

    @Override
    public int insertPassengerLog(PassengerLog passengerLog) {
        passengerLog.setCreateTime(DateUtils.getNowDate());
        return this.passengerLogMapper.insertPassengerLog(passengerLog);
    }

    @Override
    public int insertGetInLog(ConfigRobot robot, CollectInResponse2 param) {
        PassengerLog log = new PassengerLog();
        log.setPId(param.getParam().getPId());
        log.setCollectData(param.getParam().getCollectData());
        log.setGetType(DictUtils.getDictValue((String)"collect_type", (String)param.getParam().getCollectType()));
        if (ObjectUtils.isNotEmpty((Object)robot)) {
            log.setRobotId(robot.getRobotId());
            log.setRoomCode(robot.getRoomCode());
        }
        if (param.getCode().equals("1")) {
            log.setIsSuccess("1");
            log.setBackInfo(param.getData().getMsg());
        } else {
            log.setIsSuccess("0");
            log.setBackInfo(param.getData().getErr_msg());
        }
        log.setCreateTime(DateUtils.getNowDate());
        return this.passengerLogMapper.insertPassengerLog(log);
    }

    @Override
    public int insertGetOut(PassengerLog log) {
        log.setGetType("9");
        log.setCreateTime(DateUtils.getNowDate());
        return this.passengerLogMapper.insertPassengerLog(log);
    }

    @Override
    public int updatePassengerLog(PassengerLog passengerLog) {
        return this.passengerLogMapper.updatePassengerLog(passengerLog);
    }

    @Override
    public int deletePassengerLogByIds(Long[] ids) {
        return this.passengerLogMapper.deletePassengerLogByIds(ids);
    }

    @Override
    public int deletePassengerLogById(Long id) {
        return this.passengerLogMapper.deletePassengerLogById(id);
    }
}
