/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.entity.SysUser
 *  com.robotmonitor.common.core.domain.robot.RobotOnlineRequest
 *  com.robotmonitor.common.core.domain.robot.RobotStatus
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.DictUtils
 *  com.robotmonitor.common.utils.SecurityUtils
 *  com.robotmonitor.system.service.ISysUserService
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.util.CollectionUtils
 */
package com.robotmonitor.config.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.entity.SysUser;
import com.robotmonitor.common.core.domain.robot.RobotOnlineRequest;
import com.robotmonitor.common.core.domain.robot.RobotStatus;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.DictUtils;
import com.robotmonitor.common.utils.SecurityUtils;
import com.robotmonitor.config.mapper.ConfigRegionMapper;
import com.robotmonitor.config.mapper.ConfigRobotMapper;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.system.service.ISysUserService;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class ConfigRobotServiceImpl
implements IConfigRobotService {
    @Autowired
    private ConfigRobotMapper configRobotMapper;
    @Autowired
    private ConfigRegionMapper configRegionMapper;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ConfigRobot selectConfigRobotById(Long id) {
        return this.configRobotMapper.selectConfigRobotById(id);
    }

    @Override
    public ConfigRobot selectConfigRobotByRobotId(String id) {
        ConfigRobot robot = this.configRobotMapper.selectConfigRobotByRobotId(id);
        robot.setRegion(this.configRegionMapper.selectConfigRegionById(robot.getRegionId()));
        return robot;
    }

    @Override
    public List<ConfigRobot> selectConfigRobotList(ConfigRobot configRobot) {
        List<ConfigRobot> list = this.configRobotMapper.selectConfigRobotList(configRobot);
        list.forEach(x -> {
            x.setRegion(this.configRegionMapper.selectConfigRegionById(x.getRegionId()));
            if (ObjectUtils.isNotEmpty((Object)x.getChargingState())) {
                x.setChargingState(DictUtils.getDictLabel((String)"charging_state", (String)x.getChargingState()));
            }
            if (ObjectUtils.isNotEmpty((Object)x.getStandbyState())) {
                x.setStandbyState(DictUtils.getDictLabel((String)"standby_state", (String)x.getStandbyState()));
            }
            if (ObjectUtils.isNotEmpty((Object)x.getNetwork())) {
                x.setNetworkStr(DictUtils.getDictLabel((String)"network", (String)String.valueOf(x.getNetwork())));
            }
        });
        return list;
    }

    @Override
    @Transactional
    public int insertConfigRobot(ConfigRobot configRobot) {
        SysUser user = new SysUser();
        user.setUserName(configRobot.getRobotId());
        user.setPassword(SecurityUtils.encryptPassword((String)configRobot.getMac()));
        user.setNickName(configRobot.getRobotName());
        user.setUserType("99");
        this.userService.insertUser(user);
        configRobot.setNetwork(Long.valueOf(0L));
        return this.configRobotMapper.insertConfigRobot(configRobot);
    }

    @Override
    @Transactional
    public int updateConfigRobot(ConfigRobot configRobot) {
        SysUser user = this.userService.selectUserByUserName(configRobot.getRobotId());
        user.setPassword(SecurityUtils.encryptPassword((String)configRobot.getMac()));
        this.userService.updateUser(user);
        return this.configRobotMapper.updateConfigRobot(configRobot);
    }

    @Override
    public int deleteConfigRobotByIds(Long[] ids) {
        return this.configRobotMapper.deleteConfigRobotByIds(ids);
    }

    @Override
    @Transactional
    public int deleteConfigRobotById(Long id) {
        ConfigRobot robot = this.configRobotMapper.selectConfigRobotById(id);
        this.userService.deleteRobot(robot.getRobotId());
        return this.configRobotMapper.deleteConfigRobotById(id);
    }

    @Override
    public ConfigRobot getConfigRobotByRobotId(String robotId) {
        ConfigRobot configRobot = (ConfigRobot)this.redisCache.getCacheObject("robot_login_tokens:" + robotId);
        if (null == configRobot && null != (configRobot = this.selectConfigRobotByRobotId(robotId))) {
            this.redisCache.setCacheObject("robot_login_tokens:" + robotId, (Object)configRobot);
            return configRobot;
        }
        return configRobot;
    }

    @Override
    public List<String> getRobotIdsByRoomCode(String roomCode) {
        List<String> robotIds = (List<String>)this.redisCache.getCacheObject("robot_in_room_code:room_code_" + roomCode);
        if (CollectionUtils.isEmpty((Collection)robotIds)) {
            ConfigRobot queryRequest = new ConfigRobot();
            queryRequest.setRoomCode(roomCode);
            List<ConfigRobot> configRobots = this.selectConfigRobotList(queryRequest);
            if (!CollectionUtils.isEmpty(configRobots)) {
                robotIds = configRobots.stream().map(ConfigRobot::getRobotId).toList();
                this.redisCache.setCacheObject("robot_in_room_code:room_code_" + roomCode, robotIds, Integer.valueOf(1), TimeUnit.HOURS);
            }
        }
        return robotIds;
    }

    @Override
    public int updateRobotIp(Long id, String ip) {
        return this.configRobotMapper.updateRobotIp(id, ip);
    }

    @Override
    public void updateRobotStatus(RobotStatus robotStatus) {
        this.redisCache.setCacheObject("robot_current_status:robot_id_" + robotStatus.getRobot_id(), (Object)robotStatus);
        this.configRobotMapper.updateRobotStatus("" + robotStatus.getRobot_id(), Long.valueOf(robotStatus.getBattery_state()), robotStatus.isCharging_state() ? "1" : "0", robotStatus.isWorking_state() ? "1" : "0", robotStatus.isStandby_state() ? "1" : "0", robotStatus.getPositioning_state(), robotStatus.isRobot_error() ? "1" : "0", robotStatus.getError_messages(), robotStatus.getRobot_task_id());
    }

    @Override
    public RobotStatus getRobotStatus(String robotId) {
        return (RobotStatus)this.redisCache.getCacheObject("robot_current_status:robot_id_" + robotId);
    }

    @Override
    public void updateRobotOnlineStatus(RobotOnlineRequest robotOnlineRequest) {
        if (robotOnlineRequest.isOnline()) {
            this.configRobotMapper.updateRobotOnlineStatus(robotOnlineRequest.getRobotId(), robotOnlineRequest.getMode());
        } else {
            this.configRobotMapper.updateRobotOfflineStatus(robotOnlineRequest.getRobotId(), robotOnlineRequest.getMode());
        }
    }
}
