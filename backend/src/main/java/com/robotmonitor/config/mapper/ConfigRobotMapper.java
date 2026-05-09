/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.AutoFill
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.enums.OperationType
 */
package com.robotmonitor.config.mapper;

import com.robotmonitor.common.annotation.AutoFill;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.enums.OperationType;
import java.util.List;

public interface ConfigRobotMapper {
    public ConfigRobot selectConfigRobotById(Long var1);

    public ConfigRobot selectConfigRobotByRobotId(String var1);

    public List<ConfigRobot> selectConfigRobotList(ConfigRobot var1);

    @AutoFill(value=OperationType.INSERT)
    public int insertConfigRobot(ConfigRobot var1);

    @AutoFill(value=OperationType.UPDATE)
    public int updateConfigRobot(ConfigRobot var1);

    @AutoFill(value=OperationType.UPDATE)
    public int deleteConfigRobotById(Long var1);

    @AutoFill(value=OperationType.UPDATE)
    public int deleteConfigRobotByIds(Long[] var1);

    public int updateRobotIp(Long var1, String var2);

    public int updateRobotStatus(String var1, Long var2, String var3, String var4, String var5, String var6, String var7, String var8, Long var9);

    public int updateRobotOnlineStatus(String var1, Long var2);

    public int updateRobotOfflineStatus(String var1, Long var2);
}
