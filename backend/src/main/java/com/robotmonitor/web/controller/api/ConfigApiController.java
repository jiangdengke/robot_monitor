/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.controller.BaseController
 *  com.robotmonitor.common.core.domain.config.ConfigAudio
 *  com.robotmonitor.common.core.domain.config.ConfigImg
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.config.ConfigRobotAudio
 *  com.robotmonitor.common.core.domain.robot.RobotStatus
 *  com.robotmonitor.config.domain.ConfigAreaDetail
 *  com.robotmonitor.config.service.IConfigAreaService
 *  com.robotmonitor.config.service.IConfigAudioService
 *  com.robotmonitor.config.service.IConfigImgService
 *  com.robotmonitor.config.service.IConfigRobotAudioService
 *  com.robotmonitor.config.service.IConfigRobotService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.robotmonitor.web.controller.api;

import com.robotmonitor.common.core.controller.BaseController;
import com.robotmonitor.common.core.domain.config.ConfigAudio;
import com.robotmonitor.common.core.domain.config.ConfigImg;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.config.ConfigRobotAudio;
import com.robotmonitor.common.core.domain.robot.RobotStatus;
import com.robotmonitor.config.domain.ConfigAreaDetail;
import com.robotmonitor.config.service.IConfigAreaService;
import com.robotmonitor.config.service.IConfigAudioService;
import com.robotmonitor.config.service.IConfigImgService;
import com.robotmonitor.config.service.IConfigRobotAudioService;
import com.robotmonitor.config.service.IConfigRobotService;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rest/config"})
public class ConfigApiController
extends BaseController {
    @Autowired
    private IConfigRobotService configRobotService;
    @Autowired
    private IConfigAudioService configAudioService;
    @Autowired
    private IConfigRobotAudioService configRobotAudioService;
    @Autowired
    private IConfigImgService configImgService;
    @Autowired
    private IConfigAreaService configAreaService;

    @PostMapping(value={"/robot/selectConfigRobotList"})
    public List<ConfigRobot> selectConfigRobotList(@RequestBody ConfigRobot configRobot) {
        return this.configRobotService.selectConfigRobotList(configRobot);
    }

    @PostMapping(value={"/robot/updateRobotIp"})
    public int updateRobotIp(@RequestParam Long configRobotId, @RequestParam String ip) {
        return this.configRobotService.updateRobotIp(configRobotId, ip);
    }

    @PostMapping(value={"/robot/selectConfigRobotByRobotId"})
    public ConfigRobot selectConfigRobotByRobotId(@RequestParam String robotId) {
        return this.configRobotService.selectConfigRobotByRobotId(robotId);
    }

    @PostMapping(value={"/audio/selectConfigAudioById"})
    public ConfigAudio selectConfigAudioById(@RequestParam Long configAudioId) {
        return this.configAudioService.selectConfigAudioById(configAudioId);
    }

    @PostMapping(value={"/robotAudio/getNewRobotAudio"})
    public List<ConfigRobotAudio> getNewRobotAudio(@RequestParam String robotId, @RequestParam String lastUpdateTime) {
        try {
            return this.configRobotAudioService.getNewRobotAudio(robotId, lastUpdateTime);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value={"/img/selectConfigImgById"})
    public ConfigImg selectConfigImgById(@RequestParam Long id) {
        return this.configImgService.selectConfigImgById(id);
    }

    @PostMapping(value={"/audio/getConfigAreaVoiceByIdAndType"})
    public String getConfigAreaVoiceByIdAndType(@RequestParam String id, @RequestParam String type) {
        ConfigAreaDetail configAreaDetail = this.configAreaService.getConfigAreaDetailById(Long.valueOf(id));
        if (configAreaDetail == null) {
            return null;
        }
        if ("1".equals(type)) {
            return configAreaDetail.getArrAudio();
        }
        if ("0".equals(type)) {
            return configAreaDetail.getAudio();
        }
        return null;
    }

    @PostMapping(value={"/robot/updateRobotStatus"})
    public void updateRobotStatus(@RequestBody RobotStatus robotStatus) {
        this.configRobotService.updateRobotStatus(robotStatus);
    }
}
