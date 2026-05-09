/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.AjaxResult
 *  com.robotmonitor.common.core.domain.ai.PushMessage
 *  com.robotmonitor.common.core.domain.config.ConfigAudio
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.config.MessageLog
 *  com.robotmonitor.common.core.domain.robot.Admittance
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.config.domain.deepglint.compare.RegisterImage
 *  com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoRequest
 *  com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoResponse
 *  com.robotmonitor.config.service.IConfigAudioService
 *  com.robotmonitor.config.service.IConfigRobotService
 *  com.robotmonitor.config.service.IDeepGlintService
 *  com.robotmonitor.config.service.IMessageLogService
 *  com.robotmonitor.config.service.IPushService
 *  com.robotmonitor.flight.domain.BarCodeRespons
 *  com.robotmonitor.flight.domain.CollectInParam
 *  com.robotmonitor.flight.domain.CollectInParam2
 *  com.robotmonitor.flight.domain.CollectInResponse2
 *  com.robotmonitor.flight.domain.Passenger
 *  com.robotmonitor.flight.service.IPassengerService
 *  com.rometools.utils.Strings
 *  io.micrometer.common.util.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.ai.service.impl;

import com.robotmonitor.ai.domain.AccessIdInfo;
import com.robotmonitor.ai.service.AccessService;
import com.robotmonitor.common.core.domain.AjaxResult;
import com.robotmonitor.common.core.domain.ai.PushMessage;
import com.robotmonitor.common.core.domain.config.ConfigAudio;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.config.MessageLog;
import com.robotmonitor.common.core.domain.robot.Admittance;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.config.domain.deepglint.compare.RegisterImage;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoRequest;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoResponse;
import com.robotmonitor.config.service.IConfigAudioService;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.config.service.IDeepGlintService;
import com.robotmonitor.config.service.IMessageLogService;
import com.robotmonitor.config.service.IPushService;
import com.robotmonitor.flight.domain.BarCodeRespons;
import com.robotmonitor.flight.domain.CollectInParam;
import com.robotmonitor.flight.domain.CollectInParam2;
import com.robotmonitor.flight.domain.CollectInResponse2;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.service.IPassengerService;
import com.rometools.utils.Strings;
import io.micrometer.common.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl
implements AccessService {
    private static final Logger log = LoggerFactory.getLogger(AccessServiceImpl.class);
    @Autowired
    private IDeepGlintService deepGlintService;
    @Autowired
    private IPassengerService passengerService;
    @Autowired
    private IConfigRobotService configRobotService;
    @Autowired
    private IConfigAudioService configAudioService;
    @Autowired
    private IPushService pushService;
    @Autowired
    private IMessageLogService messageLogService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public RobotChatResponse validateAdmittance(Admittance admittance) {
        log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u5165\u53c2:{}", (Object)admittance.getRobotId(), (Object)admittance);
        String base64Code = admittance.getBase64Code();
        ConfigRobot configRobot = this.configRobotService.getConfigRobotByRobotId(admittance.getRobotId());
        try {
            String pId;
            String reId;
            String collectId;
            CollectInResponse2 barCode2Result;
            CollectInParam2 collectInParam2;
            String admittanceType;
            String typeKey;
            block23: {
                ConfigAudio configAudio;
                typeKey = "admittance:" + admittance.getRobotId() + ":type";
                admittanceType = (String)this.redisCache.getCacheObject(typeKey);
                if (admittanceType == null) {
                    admittanceType = "HOST";
                    log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u672a\u627e\u5230\u51c6\u5165\u7c7b\u578b\uff0c\u9ed8\u8ba4\u4e3a\u4e3b\u65c5\u5ba2", (Object)admittance.getRobotId());
                }
                log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u51c6\u5165\u7c7b\u578b:{}", (Object)admittance.getRobotId(), (Object)admittanceType);
                String code = admittance.getCode();
                String collectType = "EBP";
                if ("1".equals(admittance.getAdmitType())) {
                    collectType = "IDCARD";
                    try {
                        AccessIdInfo accessIdInfo = (AccessIdInfo)JsonUtils.string2Obj((String)admittance.getCode().replaceAll("'", "\""), AccessIdInfo.class);
                        code = accessIdInfo.getIdCardNumber();
                    }
                    catch (Exception e) {
                        log.error("\u89e3\u6790json\u5931\u8d25\uff0c\u4f7f\u7528\u539f\u59cb\u5bf9\u8c61");
                        code = admittance.getCode();
                    }
                } else if ("3".equals(admittance.getAdmitType())) {
                    collectType = "FACE";
                    code = admittance.getBase64Code();
                }
                collectInParam2 = new CollectInParam2();
                collectInParam2.setCollectType(collectType);
                collectInParam2.setDataResource("ROBOT");
                collectInParam2.setCollectData(code);
                collectInParam2.setRobotId(admittance.getRobotId());
                if ("FOLLOWER".equals(admittanceType)) {
                    collectInParam2.setIdentityType("GUEST");
                    String hostCollectIdKey = "admittance:" + admittance.getRobotId() + ":hostCollectId";
                    String hostCollectId = (String)this.redisCache.getCacheObject(hostCollectIdKey);
                    if (hostCollectId == null) {
                        log.error("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u968f\u5458\u51c6\u5165\u4f46\u672a\u627e\u5230\u4e3b\u65c5\u5ba2collectId", (Object)admittance.getRobotId());
                        ConfigAudio configAudio2 = this.getConfigAudio("ACCESS_FAIL", admittance.getLanguage(), configRobot.getRoomCode());
                        return this.createAccessResponse(admittance.getRobotId(), "\u968f\u5458\u51c6\u5165\u5931\u8d25\uff1a\u672a\u627e\u5230\u4e3b\u65c5\u5ba2\u4fe1\u606f", AjaxResult.error((String)"\u968f\u5458\u51c6\u5165\u5931\u8d25\uff1a\u672a\u627e\u5230\u4e3b\u65c5\u5ba2\u4fe1\u606f"), this.getAudioUrl(configAudio2.getId()), admittance.getLanguage(), false, configRobot.getRobotName(), configRobot.getRoomCode());
                    }
                    collectInParam2.setHostCollectId(hostCollectId);
                    log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u968f\u5458\u51c6\u5165\uff0chostCollectId:{}", (Object)admittance.getRobotId(), (Object)hostCollectId);
                } else {
                    collectInParam2.setIdentityType("HOST");
                    collectInParam2.setHostCollectId(null);
                    log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u4e3b\u65c5\u5ba2\u51c6\u5165", (Object)admittance.getRobotId());
                }
                barCode2Result = this.passengerService.barCode2(collectInParam2);
                log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0cbarCode2\u63a5\u53e3\u6267\u884c\u540e\uff1a{}", (Object)admittance.getRobotId(), (Object)JsonUtils.obj2String((Object)barCode2Result));
                if (barCode2Result == null || !"1".equals(barCode2Result.getCode())) {
                    log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u672a\u67e5\u8be2\u5230\u5bf9\u5e94\u65c5\u5ba2\u6570\u636e", (Object)admittance.getRobotId());
                    configAudio = this.getConfigAudio("ACCESS_FAIL", admittance.getLanguage(), configRobot.getRoomCode());
                    return this.createAccessResponse(admittance.getRobotId(), configAudio.getTextInfo(), AjaxResult.error((String)configAudio.getTextInfo()), this.getAudioUrl(configAudio.getId()), admittance.getLanguage(), false, configRobot.getRobotName(), configRobot.getRoomCode());
                }
                if (barCode2Result == null || barCode2Result.getData() == null) {
                    log.error("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u8fd4\u56de\u6570\u636e\u4e3a\u7a7a", (Object)admittance.getRobotId());
                    configAudio = this.getConfigAudio("ACCESS_FAIL", admittance.getLanguage(), configRobot.getRoomCode());
                    return this.createAccessResponse(admittance.getRobotId(), configAudio.getTextInfo(), AjaxResult.error((String)configAudio.getTextInfo()), this.getAudioUrl(configAudio.getId()), admittance.getLanguage(), false, configRobot.getRobotName(), configRobot.getRoomCode());
                }
                collectId = barCode2Result.getCollectId();
                log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u83b7\u53d6\u5230collectId:{}", (Object)admittance.getRobotId(), (Object)collectId);
                reId = null;
                pId = null;
                if (StringUtils.isNotBlank((String)base64Code)) {
                    Passenger passenger = new Passenger();
                    passenger.setUserName(barCode2Result.getPassengerResponse() != null ? barCode2Result.getPassengerResponse().getUsername() : "");
                    RegisterPersonToCompareRepoResponse registeredPerson = null;
                    try {
                        registeredPerson = this.registerPerson(passenger, base64Code, admittance.getRobotId());
                        log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u683c\u6797\u6ce8\u518c\u63a5\u53e3\u8fd4\u56de:{}", (Object)admittance.getRobotId(), (Object)JsonUtils.obj2String((Object)registeredPerson));
                        if (registeredPerson != null && registeredPerson.getData() != null) {
                            reId = registeredPerson.getData().getRegisterId();
                            pId = registeredPerson.getData().getPersonId();
                        }
                    }
                    catch (Exception e) {
                        log.error("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u8c03\u7528\u683c\u7075\u6df1\u77b3\u6bd4\u5bf9\u5e93\u4eba\u5458\u6ce8\u518c\u63a5\u53e3\u5931\u8d25: {}", new Object[]{admittance.getRobotId(), e.getMessage(), e});
                        if (!StringUtils.isNotBlank((String)admittance.getBase64Code2())) break block23;
                        log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u4f7f\u7528\u5907\u7528\u56fe\u7247", (Object)admittance.getRobotId());
                        try {
                            registeredPerson = this.registerPerson(passenger, admittance.getBase64Code2(), admittance.getRobotId());
                            log.info("\u518d\u6b21\u5c1d\u8bd5\uff0c\u4f7f\u7528\u5907\u7528\u56fe\u7247\uff0cvalidateAdmittance\u51c6\u5165robotId:{}\uff0c\u683c\u6797\u6ce8\u518c\u63a5\u53e3\u8fd4\u56de:{}", (Object)admittance.getRobotId(), (Object)JsonUtils.obj2String((Object)registeredPerson));
                            if (registeredPerson != null && registeredPerson.getData() != null) {
                                reId = registeredPerson.getData().getRegisterId();
                                pId = registeredPerson.getData().getPersonId();
                            }
                        }
                        catch (Exception e2) {
                            log.error("\u518d\u6b21\u5c1d\u8bd5\uff0c\u4f7f\u7528\u5907\u7528\u56fe\u7247\uff0cvalidateAdmittance\u51c6\u5165robotId:{}\uff0c\u8c03\u7528\u683c\u7075\u6df1\u77b3\u6bd4\u5bf9\u5e93\u4eba\u5458\u6ce8\u518c\u63a5\u53e3\u5931\u8d25: {}", new Object[]{admittance.getRobotId(), e2.getMessage(), e2});
                        }
                    }
                }
            }
            if (reId != null && pId != null) {
                collectInParam2.setReId(reId);
                collectInParam2.setPId(pId);
                barCode2Result.setParam(collectInParam2);
            }
            this.passengerService.passengerGetIn2(admittance.getRobotId(), barCode2Result);
            log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0cpassengerGetIn2\u63a5\u53e3\u8c03\u7528\u5b8c\u6210", (Object)admittance.getRobotId());
            if ("HOST".equals(admittanceType)) {
                String redisKey = "follower:host:" + collectId;
                this.redisCache.setCacheObject(redisKey, (Object)barCode2Result, Integer.valueOf(30), TimeUnit.MINUTES);
                log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u4e3b\u65c5\u5ba2\u6709{}\u4f4d\u968f\u5458\uff0ccollectId:{}\u5df2\u5b58\u5165Redis", new Object[]{admittance.getRobotId(), barCode2Result.getData().getFollowerNum(), collectId});
            }
            this.redisCache.deleteObject(typeKey);
            if ("FOLLOWER".equals(admittanceType)) {
                String hostCollectIdKey = "admittance:" + admittance.getRobotId() + ":hostCollectId";
                this.redisCache.deleteObject(hostCollectIdKey);
            }
            log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u51c6\u5165\u6210\u529f", (Object)admittance.getRobotId());
            ConfigAudio configAudio = this.getConfigAudio("ACCESS_SUCCESS", admittance.getLanguage(), configRobot.getRoomCode());
            return this.createAccessResponse(admittance.getRobotId(), configAudio.getTextInfo(), AjaxResult.success((Object)barCode2Result), this.getAudioUrl(configAudio.getId()), admittance.getLanguage(), true, configRobot.getRobotName(), configRobot.getRoomCode());
        }
        catch (Exception e) {
            log.error("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u4eba\u5458\u51c6\u5165\u5931\u8d25\uff1a{}", new Object[]{admittance.getRobotId(), e.getMessage(), e});
            ConfigAudio configAudio = this.getConfigAudio("ACCESS_FAIL", admittance.getLanguage(), configRobot.getRoomCode());
            return this.createAccessResponse(admittance.getRobotId(), configAudio.getTextInfo(), AjaxResult.error((String)configAudio.getTextInfo()), this.getAudioUrl(configAudio.getId()), admittance.getLanguage(), false, configRobot.getRobotName(), configRobot.getRoomCode());
        }
    }

    private CollectInParam createCollectInParam(RegisterPersonToCompareRepoResponse registeredPerson, BarCodeRespons barCodeRespons, ConfigRobot configRobot) {
        CollectInParam cin = new CollectInParam();
        if (null != registeredPerson && null != registeredPerson.getData()) {
            cin.setReId(registeredPerson.getData().getRegisterId());
            cin.setPId(registeredPerson.getData().getPersonId());
        }
        cin.setInType(barCodeRespons.getInType());
        cin.setUserName(barCodeRespons.getUsername());
        cin.setFlightno(barCodeRespons.getFlightNo());
        cin.setOrig(barCodeRespons.getOrig());
        cin.setDest(barCodeRespons.getDest());
        cin.setCabin(barCodeRespons.getCabin());
        cin.setSeat(barCodeRespons.getSeat());
        cin.setSeq(barCodeRespons.getSeg());
        cin.setCardService(barCodeRespons.getCardService());
        cin.setCardNo(barCodeRespons.getCardNo());
        cin.setMemLevel(barCodeRespons.getMemLevel());
        cin.setStarLevel(barCodeRespons.getStarLevel());
        cin.setInType(barCodeRespons.getInType());
        cin.setRoomCode(configRobot.getRoomCode());
        cin.setList(new ArrayList());
        return cin;
    }

    private String getAudioUrl(Long audioId) {
        return "/api/voice/config/" + audioId;
    }

    private ConfigAudio getConfigAudio(String key, String language, String roomCode) {
        if (Strings.isBlank((String)language)) {
            language = "CN";
        }
        return this.configAudioService.getConfigAudioByKeyAndLanguageAndRoomCode(key, language, roomCode);
    }

    private RegisterPersonToCompareRepoResponse registerPerson(Passenger passenger, String image, String robotId) {
        RegisterPersonToCompareRepoRequest registerPersonToCompareRepoRequest = new RegisterPersonToCompareRepoRequest();
        registerPersonToCompareRepoRequest.setName(passenger.getUserName());
        RegisterImage registerImage = new RegisterImage();
        registerPersonToCompareRepoRequest.setImages(List.of(registerImage));
        registerImage.setBinData(image);
        log.info("validateAdmittance\u51c6\u5165robotId:{}\uff0c\u683c\u6797\u6ce8\u518c\u63a5\u53e3\u5165\u53c2\uff1a{}", (Object)robotId, (Object)registerPersonToCompareRepoRequest);
        return this.deepGlintService.registerPersonToCompareRepo(registerPersonToCompareRepoRequest);
    }

    private RobotChatResponse createAccessResponse(String robotId, String message, Object pushObj, String audioUrl, String language, boolean isSuccess, String robotName, String roomCode) {
        RobotChatResponse robotChatResponse = new RobotChatResponse();
        robotChatResponse.setEventType(isSuccess ? "ACCESS_SUCCESS" : "ACCESS_FAIL");
        robotChatResponse.setRobotId(robotId);
        robotChatResponse.setMessage(message);
        robotChatResponse.setLanguage(language);
        robotChatResponse.setNeedVoice(true);
        if (!Strings.isBlank((String)audioUrl)) {
            robotChatResponse.setAudioUrl(audioUrl);
        }
        this.saveMessageLogAndPushMessage(isSuccess, robotId, robotName, pushObj, roomCode);
        return robotChatResponse;
    }

    private void saveMessageLogAndPushMessage(boolean isSuccess, String robotId, String robotName, Object pushObj, String roomCode) {
        MessageLog messageLog = new MessageLog();
        messageLog.setTitle("\u51c6\u5165" + (isSuccess ? "\u6210\u529f" : "\u5931\u8d25"));
        messageLog.setContent("\u65c5\u5ba2\u81ea\u52a9\u51c6\u5165" + (isSuccess ? "\u6210\u529f" : "\u5931\u8d25"));
        messageLog.setStatus("0");
        messageLog.setSource(robotName);
        messageLog.setRoomCode(roomCode);
        this.pushService.push(new PushMessage(robotId, "welcome", JsonUtils.obj2String((Object)pushObj)));
        this.pushService.push(new PushMessage(roomCode, "notice", JsonUtils.obj2String((Object)messageLog)));
        this.messageLogService.insertMessageLog(messageLog);
    }
}
