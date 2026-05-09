/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.DictUtils
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.sign.Md5Utils
 *  com.robotmonitor.config.deepglint.DeepGlintApiConfig
 *  com.robotmonitor.config.domain.ConfigDevice
 *  com.robotmonitor.config.domain.RecognitionResult
 *  com.robotmonitor.config.domain.deepglint.face.CaptureFace
 *  com.robotmonitor.config.domain.deepglint.face.HitTag
 *  com.robotmonitor.config.mapper.ConfigDeviceMapper
 *  com.robotmonitor.config.service.IConfigRobotService
 *  com.robotmonitor.config.service.IDeepGlintService
 *  com.robotmonitor.config.service.IRegionMatchService
 *  jakarta.annotation.Resource
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apache.ibatis.session.SqlSessionFactory
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Profile
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.robotmonitor.flight.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.DictUtils;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.sign.Md5Utils;
import com.robotmonitor.config.deepglint.DeepGlintApiConfig;
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.domain.RecognitionResult;
import com.robotmonitor.config.domain.deepglint.face.CaptureFace;
import com.robotmonitor.config.domain.deepglint.face.HitTag;
import com.robotmonitor.config.mapper.ConfigDeviceMapper;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.config.service.IDeepGlintService;
import com.robotmonitor.config.service.IRegionMatchService;
import com.robotmonitor.flight.domain.AccessInfoParam;
import com.robotmonitor.flight.domain.AccessInfoResponse;
import com.robotmonitor.flight.domain.AuthResponse;
import com.robotmonitor.flight.domain.BarCodeParam;
import com.robotmonitor.flight.domain.BarCodeRespons;
import com.robotmonitor.flight.domain.CollectInParam;
import com.robotmonitor.flight.domain.CollectInParam2;
import com.robotmonitor.flight.domain.CollectInResponse;
import com.robotmonitor.flight.domain.CollectInResponse2;
import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.GetInTmp;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerLocationLog;
import com.robotmonitor.flight.domain.PassengerParam;
import com.robotmonitor.flight.domain.PassengerStatistics;
import com.robotmonitor.flight.domain.Result;
import com.robotmonitor.flight.domain.dto.FlightChangePassengerDTO;
import com.robotmonitor.flight.mapper.PassengerLocationLogMapper;
import com.robotmonitor.flight.mapper.PassengerMapper;
import com.robotmonitor.flight.service.IFlightInfoService;
import com.robotmonitor.flight.service.IGetInTmpService;
import com.robotmonitor.flight.service.ILtsFeignClient;
import com.robotmonitor.flight.service.IPassengerLogService;
import com.robotmonitor.flight.service.IPassengerService;
import jakarta.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile(value={"dev"})
public class PassengerServiceImpl
implements IPassengerService {
    private static final Logger log = LoggerFactory.getLogger(PassengerServiceImpl.class);
    @Autowired
    private PassengerMapper passengerMapper;
    @Resource
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private ILtsFeignClient itILtsFeignClient;
    @Value(value="${robotmonitor.ltsAppId}")
    private String appId;
    @Value(value="${robotmonitor.ltsAppKey}")
    private String apKey;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IDeepGlintService deepGlintService;
    @Autowired
    private DeepGlintApiConfig deepGlintApiConfig;
    @Autowired
    private IRegionMatchService regionMatchService;
    @Autowired
    private IFlightInfoService flightInfoService;
    @Autowired
    private PassengerLocationLogMapper passengerLocationLogMapper;
    @Autowired
    private ConfigDeviceMapper configDeviceMapper;
    @Autowired
    private IGetInTmpService getInTmpService;
    @Autowired
    private IConfigRobotService configRobotService;
    @Autowired
    private IPassengerLogService passengerLogService;
    final String FACE = "3";

    @Override
    public AuthResponse getAuth(String employeeNo) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String sign = this.getSign("", this.appId, this.apKey, timestamp);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("appId", this.appId);
        headerMap.put("timestamp", timestamp);
        headerMap.put("sign", sign);
        Result<AuthResponse> authResponse = this.itILtsFeignClient.GetAuth(headerMap, employeeNo);
        return authResponse.getData();
    }

    @Override
    public BarCodeRespons barCode(String robotId, String barCode, String inType) {
        log.info("\u8fd9\u91cc\u662f\u751f\u4ea7\u73af\u5883\u83b7\u53d6barcode\uff0crobotId\uff1a" + robotId);
        ConfigRobot robot = (ConfigRobot)this.redisCache.getCacheObject("robot_login_tokens:" + robotId);
        BarCodeParam param = new BarCodeParam();
        param.setBarCode(barCode);
        param.setMode("0");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String sign = this.getSign(robot.getAccountId(), this.appId, this.apKey, timestamp);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("appId", this.appId);
        headerMap.put("timestamp", timestamp);
        headerMap.put("sign", sign);
        headerMap.put("accountId", robot.getAccountId());
        Result<BarCodeRespons> barCodeRespons = this.itILtsFeignClient.barCode(headerMap, param);
        AccessInfoParam accessInfoParam = new AccessInfoParam();
        accessInfoParam.setServiceCode(barCodeRespons.getData().getServiceCode());
        accessInfoParam.setCabin(barCodeRespons.getData().getCabin());
        accessInfoParam.setUsername(barCodeRespons.getData().getUsername());
        accessInfoParam.setCardService(barCodeRespons.getData().getCardService());
        accessInfoParam.setCardNo(barCodeRespons.getData().getCardNo());
        accessInfoParam.setMemLevel(barCodeRespons.getData().getMemLevel());
        accessInfoParam.setStarLevel(barCodeRespons.getData().getStarLevel());
        accessInfoParam.setRoomCode(robot.getRoomCode());
        accessInfoParam.setFlightNo(barCodeRespons.getData().getFlightNo());
        accessInfoParam.setOrig(barCodeRespons.getData().getOrig());
        accessInfoParam.setSeq(barCodeRespons.getData().getSeg());
        Result<AccessInfoResponse> accessInfoResponse = this.itILtsFeignClient.selectAccessInfo(headerMap, accessInfoParam);
        barCodeRespons.getData().setInType(accessInfoResponse.getData().getInType());
        return barCodeRespons.getData();
    }

    @Override
    public BarCodeRespons barCodeForTest(String personId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<CollectInResponse> passengerGetIn(CollectInParam param) {
        Result<CollectInResponse> collectInResponse = null;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String sign = this.getSign(param.getAccountId(), this.appId, this.apKey, timestamp);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("appId", this.appId);
        headerMap.put("timestamp", timestamp);
        headerMap.put("sign", sign);
        headerMap.put("accountId", param.getAccountId());
        collectInResponse = this.itILtsFeignClient.collectIn(headerMap, param);
        if (!collectInResponse.getCode().equals("1")) {
            return collectInResponse;
        }
        Date dNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Passenger info = new Passenger();
        info.setUserName(param.getUserName());
        info.setRoomCode(param.getRoomCode());
        info.setFlightNo(param.getFlightno());
        info.setOrig(param.getOrig());
        info.setDest(param.getDest());
        info.setCabin(param.getCabin());
        info.setSeat(param.getSeat());
        info.setSeq(param.getSeq());
        info.setCardService(param.getCardService());
        info.setCardNo(param.getCardNo());
        info.setMemLevel(param.getMemLevel());
        info.setStarLevel(param.getStarLevel());
        info.setInType(param.getInType());
        info.setGetInTime(dNow);
        info.setStatus("0");
        info.setReid(param.getReId());
        info.setFlightDate(DateUtils.getDate());
        info.setPid(param.getPId());
        this.getIn(info);
        param.getList().forEach(x -> {
            Passenger item = new Passenger();
            item.setUserName(x.getUserName());
            item.setRoomCode(x.getRoomCode());
            item.setFlightNo(x.getFlightno());
            item.setOrig(x.getOrig());
            item.setDest(x.getDest());
            item.setCabin(x.getCabin());
            item.setSeat(x.getSeat());
            item.setSeq(x.getSeq());
            item.setInType(x.getInType());
            item.setGetInTime(dNow);
            item.setStatus("1");
            item.setReid(x.getReId());
            item.setFlightDate(DateUtils.getDate());
            this.getIn(item);
        });
        return collectInResponse;
    }

    @Override
    public CollectInResponse2 barCode2(CollectInParam2 param) {
        List<GetInTmp> tmpList;
        log.info("\u8fd9\u91cc\u662f\u751f\u4ea7\u73af\u5883\u83b7\u53d6barcode\uff0crobotId\uff1a" + param.getRobotId());
        ConfigRobot robot = this.configRobotService.getConfigRobotByRobotId(param.getRobotId());
        CollectInResponse2 data = new CollectInResponse2();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String sign = this.getSign("", this.appId, this.apKey, timestamp);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("appId", this.appId);
        headerMap.put("timestamp", timestamp);
        headerMap.put("sign", sign);
        log.info("lts request : {}", (Object)JsonUtils.obj2String((Object)param));
        data = this.itILtsFeignClient.saveCollect(headerMap, param);
        log.info("lts response : {}", (Object)JsonUtils.obj2String((Object)data));
        GetInTmp tmpPara = new GetInTmp();
        tmpPara.setCode(param.getCollectData());
        tmpPara.setInType(param.getCollectType());
        if (param.getCollectType().equals("FACE")) {
            tmpPara.setCode("face");
        }
        if (ObjectUtils.isNotEmpty(tmpList = this.getInTmpService.selectGetInTmpList(tmpPara))) {
            GetInTmp tmp = tmpList.get(0);
            data.setTmp(tmp);
            BarCodeRespons passengerResponse = new BarCodeRespons();
            passengerResponse.setCarouselCd("-");
            passengerResponse.setGateCd("-");
            passengerResponse.setEstmTakeOffTime("-");
            passengerResponse.setSeg("-");
            if (ObjectUtils.isNotEmpty((Object)tmp.getCardNo())) {
                passengerResponse.setCardNo("-");
            }
            passengerResponse.setCabin("-");
            passengerResponse.setCardService("-");
            passengerResponse.setDest("-");
            passengerResponse.setOrig("-");
            passengerResponse.setUsername("-");
            passengerResponse.setUsername("-");
            if (ObjectUtils.isNotEmpty((Object)data.getData().getMemberName())) {
                passengerResponse.setUsername(data.getData().getMemberName().getLastName() + data.getData().getMemberName().getFirstName());
            }
            passengerResponse.setFlightNo("-");
            passengerResponse.setSeat("-");
            passengerResponse.setStarLevel("-");
            passengerResponse.setFollowerNum(0);
            passengerResponse.setCraftType("-");
            if (ObjectUtils.isNotEmpty((Object)data.getData().getFollowerNum())) {
                passengerResponse.setFollowerNum(data.getData().getFollowerNum());
            }
            data.setPassengerResponse(passengerResponse);
        }
        data.setParam(param);
        if (ObjectUtils.isNotEmpty((Object)data) && ObjectUtils.isNotEmpty((Object)data.getData()) && ObjectUtils.isNotEmpty((Object)data.getData().getCollectId())) {
            data.setCollectId(data.getData().getCollectId());
        }
        return data;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void passengerGetIn2(String robotId, CollectInResponse2 param) {
        ConfigRobot robot = this.configRobotService.getConfigRobotByRobotId(robotId);
        this.passengerLogService.insertGetInLog(robot, param);
        Date dNow = new Date();
        Passenger info = new Passenger();
        if (ObjectUtils.isNotEmpty((Object)((Object)param.getTmp()))) {
            GetInTmp tmp = param.getTmp();
            info.setUserName(tmp.getUserName());
            info.setRoomCode(robot.getRoomCode());
            info.setFlightNo(tmp.getFlightNo());
            info.setOrig(tmp.getOrig());
            info.setDest(tmp.getDest());
            info.setCabin(tmp.getCabin());
            info.setSeat(tmp.getSeat());
            info.setSeq(tmp.getSeg());
        }
        if (ObjectUtils.isNotEmpty((Object)param.getData().getMemberInfo())) {
            info.setCardService(param.getData().getMemberInfo().getMemberCardService());
            info.setCardNo(param.getData().getMemberInfo().getMemberId());
        }
        info.setInType(DictUtils.getDictValue((String)"collect_type", (String)param.getParam().getCollectType()));
        info.setGetInTime(dNow);
        info.setStatus("1");
        info.setReid(param.getParam().getReId());
        info.setFlightDate(DateUtils.getDate());
        info.setPid(param.getParam().getPId());
        info.setRobotId(robotId);
        if (ObjectUtils.isNotEmpty((Object)param.getData().getFollowerNum())) {
            info.setFollowerNum(param.getData().getFollowerNum());
        }
        if (param.getData().getIsMember().booleanValue()) {
            info.setIsMember("1");
        } else {
            info.setIsMember("0");
        }
        if (ObjectUtils.isNotEmpty((Object)param.getData().getCollectId())) {
            info.setColledtId(param.getData().getCollectId());
        }
        FlightInfo flightParam = new FlightInfo();
        flightParam.setFlightNo(info.getFlightNo());
        flightParam.setScheExecDate(DateUtils.getDate());
        List<FlightInfo> flightInfoList = this.flightInfoService.selectFlightInfoList(flightParam);
        FlightInfo flight = new FlightInfo();
        if (ObjectUtils.isNotEmpty(flightInfoList)) {
            flight = flightInfoList.get(0);
            info.setFlightId(flight.getFlightId());
        }
        this.getIn(info);
    }

    private void getIn(Passenger info) {
        FlightInfo flightParam = new FlightInfo();
        flightParam.setFlightNo(info.getFlightNo());
        flightParam.setScheExecDate(info.getFlightDate());
        List<FlightInfo> flightInfoList = this.flightInfoService.selectFlightInfoList(flightParam);
        if (!ObjectUtils.isEmpty(flightInfoList) && flightInfoList.size() > 0) {
            info.setFlightId(flightInfoList.get(0).getFlightId());
        }
        List<Passenger> infoTmp = this.passengerMapper.selectPassengerList_Re(info);
        boolean isRepeat = false;
        if (infoTmp.size() > 0) {
            for (Passenger ps : infoTmp) {
                if (!ObjectUtils.isEmpty((Object)ps.getGetOutTime())) continue;
                if (ps.getRoomCode().equals(info.getRoomCode())) {
                    isRepeat = true;
                    ps.setStatus("1");
                    this.passengerMapper.updatePassenger(ps);
                    continue;
                }
                ps.setGetOutTime(info.getGetInTime());
                ps.setStatus("0");
                this.passengerMapper.updatePassenger(ps);
            }
        }
        if (!isRepeat) {
            this.passengerMapper.insertPassenger(info);
        }
    }

    @Override
    public int setPassengerGetOut(String pId, String oriImgUrl, Long regionId) {
        Passenger ps = this.passengerMapper.selectPassengerByPid(pId);
        if (ObjectUtils.isNotEmpty((Object)((Object)ps))) {
            return this.passengerMapper.setPassengerGetOut(ps.getId(), oriImgUrl, regionId);
        }
        return 0;
    }

    @Override
    public int updatePassengerRegionAndStatus(String pId, Long regionId, String status, String origImageUrl, String registerImageUrl) {
        Passenger ps = this.passengerMapper.selectPassengerByPid(pId);
        if (ObjectUtils.isNotEmpty((Object)((Object)ps))) {
            return this.passengerMapper.updatePassengerRegionAndStatus(ps.getId(), regionId, status, origImageUrl, registerImageUrl);
        }
        return 0;
    }

    private String getSign(String accountId, String appId, String appKey, String timestamp) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank((CharSequence)accountId)) {
            sb.append("accountId=").append(accountId).append("&");
        }
        if (StringUtils.isNotBlank((CharSequence)appId)) {
            sb.append("appId=").append(appId).append("&");
        }
        if (StringUtils.isNotBlank((CharSequence)appKey)) {
            sb.append("appKey=").append(appKey).append("&");
        }
        sb.append("timestamp=").append(timestamp);
        return Md5Utils.hash((String)sb.toString()).toUpperCase();
    }

    @Override
    public RecognitionResult findPassage(String pId) {
        RecognitionResult result = new RecognitionResult();
        CaptureFace captureFace = this.regionMatchService.getLatestCaptureFace(pId);
        if (captureFace != null && captureFace.getFaceId() != null) {
            try {
                PassengerLocationLog existingLog = this.passengerLocationLogMapper.selectPassengerLocationLogByCtsId(String.valueOf(captureFace.getCts()));
                if (existingLog != null) {
                    return null;
                }
            }
            catch (Exception e) {
                log.error("[PID-{}-FIND] \u67e5\u8be2CtsID\u5b58\u5728\u6027\u65f6\u53d1\u751f\u5f02\u5e38", (Object)pId, (Object)e);
            }
        }
        ConfigRegion configRegion = this.regionMatchService.matchRegion(captureFace, pId);
        result.setRegion(configRegion);
        if (captureFace != null) {
            ConfigDevice configDevice = this.configDeviceMapper.selectConfigDeviceByDeepGlintDeviceId(captureFace.getLogicDeviceId());
            result.setConfigDevice(configDevice);
            result.setCts("" + captureFace.getCts());
            result.setOrigImageUrl(captureFace.getOrigImageUrl());
            if (captureFace.getTags() != null) {
                result.setRegisterImageUrl(((HitTag)captureFace.getTags().get(0)).getRegisterUrl());
            }
        }
        result.setRecognitionType("face");
        return result;
    }

    @Override
    public Passenger selectPassengerById(Long id) {
        return this.passengerMapper.selectPassengerById(id);
    }

    @Override
    public List<Passenger> selectPassengerList(Passenger tPassenger) {
        return this.passengerMapper.selectPassengerList(tPassenger);
    }

    @Override
    public int insertPassenger(Passenger passenger) {
        passenger.setCreateTime(DateUtils.getNowDate());
        return this.passengerMapper.insertPassenger(passenger);
    }

    @Override
    public int updatePassenger(Passenger passenger) {
        return this.passengerMapper.updatePassenger(passenger);
    }

    @Override
    public int deletePassengerByIds(Long[] ids) {
        return this.passengerMapper.deletePassengerByIds(ids);
    }

    @Override
    public List<Passenger> selectPassengerOutgoingList(Passenger passenger) {
        return this.passengerMapper.selectPassengerOutgoingList(passenger);
    }

    @Override
    public List<Passenger> selectPassengerInLoungeList(Passenger passenger) {
        return this.passengerMapper.selectPassengerInLoungeList(passenger);
    }

    @Override
    public PassengerStatistics getPassengerStatistics() {
        PassengerStatistics statistics = new PassengerStatistics();
        statistics.setCurrentPassengerCount(this.passengerLocationLogMapper.countCurrentPassengers());
        statistics.setCurrentPassengerDetails(this.passengerLocationLogMapper.selectCurrentPassengerDetails());
        statistics.setDepartedPassengerCount(this.passengerLocationLogMapper.countDepartedPassengers());
        statistics.setDepartedPassengerDetails(this.passengerLocationLogMapper.selectDepartedPassengerDetails());
        statistics.setVisitorCount(this.passengerLocationLogMapper.countVisitors());
        statistics.setVisitorDetails(this.passengerLocationLogMapper.selectVisitorDetails());
        return statistics;
    }

    @Override
    public List<FlightChangePassengerDTO> selectPassengerWithFlightChangeList(Passenger passenger) {
        return this.passengerMapper.selectPassengerWithFlightChangeList(passenger);
    }

    @Override
    public List<Passenger> selectPassenger(PassengerParam param) {
        List<Passenger> list = this.passengerMapper.selectPassengerList2(param);
        for (Passenger passenger : list) {
            passenger.setInTypeText(this.getInTypeText(passenger.getInType()));
        }
        return list;
    }

    private String getInTypeText(String inType) {
        if (inType == null) {
            return null;
        }
        switch (inType) {
            case "1": {
                return "\u8eab\u4efd\u8bc1\u9a8c\u8bc1";
            }
            case "2": {
                return "\u626b\u7801\u51c6\u5165";
            }
            case "3": {
                return "\u4eba\u8138\u8bc6\u522b";
            }
        }
        return inType;
    }
}
