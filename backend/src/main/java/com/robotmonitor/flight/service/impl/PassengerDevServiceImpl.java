/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.config.ConfigRegion
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.DictUtils
 *  com.robotmonitor.common.utils.StringUtils
 *  com.robotmonitor.common.utils.sign.Md5Utils
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
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.robotmonitor.flight.service.impl;

import com.robotmonitor.common.core.domain.config.ConfigRegion;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.DictUtils;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.sign.Md5Utils;
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.domain.RecognitionResult;
import com.robotmonitor.config.domain.deepglint.face.CaptureFace;
import com.robotmonitor.config.domain.deepglint.face.HitTag;
import com.robotmonitor.config.mapper.ConfigDeviceMapper;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.config.service.IDeepGlintService;
import com.robotmonitor.config.service.IRegionMatchService;
import com.robotmonitor.flight.domain.AuthResponse;
import com.robotmonitor.flight.domain.BarCodeRespons;
import com.robotmonitor.flight.domain.CollectInParam;
import com.robotmonitor.flight.domain.CollectInParam2;
import com.robotmonitor.flight.domain.CollectInResponse;
import com.robotmonitor.flight.domain.CollectInResponse2;
import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.GetInTmp;
import com.robotmonitor.flight.domain.OpenapiActionRespons;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.PassengerLocationLog;
import com.robotmonitor.flight.domain.PassengerParam;
import com.robotmonitor.flight.domain.PassengerStatistics;
import com.robotmonitor.flight.domain.ResData;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile(value={"prod"})
public class PassengerDevServiceImpl
implements IPassengerService {
    private static final Logger log = LoggerFactory.getLogger(PassengerDevServiceImpl.class);
    @Autowired
    private PassengerMapper passengerMapper;
    @Resource
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private RedisCache redisCache;
    private ILtsFeignClient itILtsFeignClient;
    @Value(value="${robotmonitor.ltsAppId}")
    private String appId;
    @Value(value="${robotmonitor.ltsAppKey}")
    private String apKey;
    @Value(value="${robotmonitor.roomCode}")
    private String roomCode;
    @Autowired
    private IDeepGlintService deepGlintService;
    @Autowired
    private IRegionMatchService regionMatchService;
    @Autowired
    private PassengerLocationLogMapper passengerLocationLogMapper;
    @Autowired
    private ConfigDeviceMapper configDeviceMapper;
    @Autowired
    private IGetInTmpService getInTmpService;
    @Autowired
    private IFlightInfoService flightInfoService;
    @Autowired
    private IConfigRobotService configRobotService;
    @Autowired
    private IPassengerLogService passengerLogService;

    @Override
    public AuthResponse getAuth(String employeeNo) {
        AuthResponse info = new AuthResponse();
        info.setAccountId("007bafa3d2844c269e24064bc163b578");
        info.setToken("BC7B36FE4D2924E49800D9B3DC4A325C");
        ArrayList<OpenapiActionRespons> actions = new ArrayList<OpenapiActionRespons>();
        OpenapiActionRespons action = new OpenapiActionRespons();
        action.setActionId(1234L);
        action.setActionName("\u4e58\u673a\u4fe1\u606f\u91c7\u96c6");
        action.setActionUrl("###");
        action.setActionType(1);
        action.setParentId(1L);
        action.setPriority(1);
        action.setNeedUserAuth(0);
        actions.add(action);
        info.setActions(actions);
        return info;
    }

    @Override
    public BarCodeRespons barCode(String robotId, String barCode, String inType) {
        log.info("\u8fd9\u91cc\u662f\u6d4b\u8bd5\u73af\u5883\u83b7\u53d6barcode\uff0crobotId\uff1a" + robotId);
        GetInTmp tmp = new GetInTmp();
        tmp.setCode(barCode);
        tmp.setInType(inType);
        List<GetInTmp> tmpList = this.getInTmpService.selectGetInTmpList(tmp);
        if (inType.equals("3")) {
            GetInTmp tmp3 = new GetInTmp();
            tmp3.setCode("7702284e-7c37-4fc6-bf85-79a1cff5e4fb");
            tmp3.setFlightNo("1234");
            tmp3.setCardService("CA");
            tmp3.setOrig("PEK");
            tmp3.setDest("SHA");
            tmp3.setCabin("Y");
            tmp3.setSeat("52B");
            tmp3.setStarLevel("2");
            tmp3.setInType("3");
            tmp3.setSeg("1");
            tmp3.setUserName("\u4ef2\u5fd7\u90a6");
            tmpList.add(tmp3);
        }
        if (ObjectUtils.isEmpty(tmpList)) {
            return null;
        }
        tmp = tmpList.get(0);
        BarCodeRespons info = new BarCodeRespons();
        info.setServiceCode(tmp.getCode());
        info.setFlightNo(tmp.getFlightNo());
        info.setOrig(DictUtils.getDictLabel((String)"iata_code", (String)tmp.getOrig()));
        info.setDest(DictUtils.getDictLabel((String)"iata_code", (String)tmp.getDest()));
        info.setUsername(tmp.getUserName());
        info.setCabin(tmp.getCabin());
        info.setSeat(tmp.getSeat());
        info.setSeg(tmp.getSeg());
        info.setCardService(tmp.getCardService());
        info.setStarLevel(tmp.getStarLevel());
        info.setInType(tmp.getInType());
        FlightInfo flightParam = new FlightInfo();
        flightParam.setFlightNo(info.getFlightNo());
        flightParam.setScheExecDate(DateUtils.getDate());
        List<FlightInfo> flightInfoList = this.flightInfoService.selectFlightInfoList(flightParam);
        if (ObjectUtils.isNotEmpty(flightInfoList)) {
            FlightInfo flight = flightInfoList.get(0);
            info.setGateCd(flight.getGateCd());
            if (StringUtils.isNotEmpty((String)flight.getEstmTakeOffTime()) && flight.getEstmTakeOffTime().length() == 14) {
                info.setEstmTakeOffTime(flight.getEstmTakeOffTime().substring(8, 12));
            }
            info.setCarouselCd(flight.getCarouselCd());
            info.setCraftType(flight.getCraftType());
        }
        return info;
    }

    @Override
    @PreAuthorize(value="permitAll")
    public BarCodeRespons barCodeForTest(String personId) {
        GetInTmp tmp = new GetInTmp();
        tmp.setCode(personId);
        tmp.setInType("3");
        List<GetInTmp> tmpList = this.getInTmpService.selectGetInTmpList(tmp);
        if (ObjectUtils.isEmpty(tmpList)) {
            return null;
        }
        tmp = tmpList.get(0);
        BarCodeRespons info = new BarCodeRespons();
        info.setServiceCode(tmp.getCode());
        info.setFlightNo(tmp.getFlightNo());
        info.setOrig(tmp.getOrig());
        info.setDest(tmp.getDest());
        info.setUsername(tmp.getUserName());
        info.setCabin(tmp.getCabin());
        info.setSeat(tmp.getSeat());
        info.setSeg(tmp.getSeg());
        info.setCardService(tmp.getCardService());
        info.setStarLevel(tmp.getStarLevel());
        info.setInType(tmp.getInType());
        return info;
    }

    @Override
    @Transactional
    @PreAuthorize(value="permitAll")
    public Result<CollectInResponse> passengerGetIn(CollectInParam param) {
        Result<CollectInResponse> collectInResponse = null;
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
        info.setStatus("1");
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
        collectInResponse = new Result<CollectInResponse>();
        collectInResponse.setCode("1");
        collectInResponse.setMessage("\u5141\u8bb8\u51c6\u5165\u6210\u529f\uff01");
        return collectInResponse;
    }

    @Override
    public CollectInResponse2 barCode2(CollectInParam2 param) {
        log.info("\u8fd9\u91cc\u662f\u6d4b\u8bd5\u73af\u5883\u83b7\u53d6barcode\uff0crobotId\uff1a" + param.getRobotId());
        ConfigRobot robot = this.configRobotService.getConfigRobotByRobotId(param.getRobotId());
        CollectInResponse2 data = new CollectInResponse2();
        GetInTmp tmpPara = new GetInTmp();
        tmpPara.setCode(param.getCollectData());
        tmpPara.setInType(param.getCollectType());
        List<GetInTmp> tmpList = this.getInTmpService.selectGetInTmpList(tmpPara);
        if (ObjectUtils.isNotEmpty(tmpList)) {
            GetInTmp tmp = tmpList.get(0);
            if (ObjectUtils.isEmpty((Object)((Object)tmp))) {
                return null;
            }
            data.setTmp(tmp);
            BarCodeRespons passengerResponse = new BarCodeRespons();
            passengerResponse.setCarouselCd("-");
            passengerResponse.setGateCd("-");
            passengerResponse.setSeg("-");
            passengerResponse.setServiceCode("-");
            if (ObjectUtils.isNotEmpty((Object)tmp.getCardNo())) {
                passengerResponse.setServiceCode(tmp.getCardNo());
            }
            passengerResponse.setCabin("-");
            if (ObjectUtils.isNotEmpty((Object)tmp.getCardService())) {
                passengerResponse.setCardService(tmp.getCardService());
            }
            passengerResponse.setDest("-");
            passengerResponse.setOrig("-");
            passengerResponse.setUsername("-");
            passengerResponse.setUsername(tmp.getUserName());
            passengerResponse.setFlightNo("-");
            passengerResponse.setSeat("-");
            passengerResponse.setGateCd("-");
            passengerResponse.setEstmTakeOffTime("-");
            passengerResponse.setCarouselCd("-");
            passengerResponse.setCraftType("-");
            passengerResponse.setStarLevel("-");
            FlightInfo flightParam = new FlightInfo();
            flightParam.setFlightNo(passengerResponse.getFlightNo());
            flightParam.setScheExecDate(DateUtils.getDate());
            List<FlightInfo> flightInfoList = this.flightInfoService.selectFlightInfoList(flightParam);
            FlightInfo flight = new FlightInfo();
            if (ObjectUtils.isNotEmpty(flightInfoList) && ObjectUtils.isNotEmpty((Object)((Object)(flight = flightInfoList.get(0))))) {
                passengerResponse.setGateCd(flight.getGateCd());
                passengerResponse.setCarouselCd(flight.getCarouselCd());
                passengerResponse.setCraftType(flight.getCraftType());
            }
            if (StringUtils.isNotEmpty((String)flight.getEstmTakeOffTime()) && flight.getEstmTakeOffTime().length() == 14) {
                passengerResponse.setEstmTakeOffTime(flight.getEstmTakeOffTime().substring(8, 12));
            }
            passengerResponse.setFollowerNum(tmp.getFollowerNum());
            ResData resData = new ResData();
            resData.setCollectId("1111111");
            data.setData(resData);
            data.setCode("1");
            data.setMessage("\u5c0a\u656c\u7684\u7a0b\u5f69\u65c5\u5ba2\uff0c\u6b22\u8fce\u60a8\u4f7f\u7528\u56fd\u822a\u56fd\u822a\u8d35\u5bbe\u4f11\u606f\u5ba4\u4f11\u606f\u5ba4\u3002\u76ee\u524d\uff0c\u60a8\u6240\u642d\u4e58\u7684CA1796\u822a\u73ed\u9884\u8ba1\u8d77\u98de\u65f6\u95f4\u4e3a\u5f85\u5b9a\uff0c\u767b\u673a\u53e3\u4e3a\u5f85\u5b9a\u3002\u767b\u673a\u53e3\u5c06\u4e8e\u8d77\u98de\u524d15\u5206\u949f\u5173\u95ed\uff0c\u6574\u70b9\u822a\u73ed\u5c06\u4e0d\u518d\u8fdb\u884c\u8231\u5185\u5e7f\u64ad\uff0c\u8bf7\u60a8\u5408\u7406\u89c4\u5212\u4f11\u606f\u65f6\u95f4\uff0c\u6309\u65f6\u524d\u5f80\u767b\u673a\u53e3\u4e58\u673a\u3002");
            data.setPassengerResponse(passengerResponse);
            data.setCollectId(String.valueOf(tmp.getId()));
            data.setPassengerResponse(passengerResponse);
        }
        data.setParam(param);
        return data;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void passengerGetIn2(String robotId, CollectInResponse2 param) {
        ConfigRobot robot = this.configRobotService.getConfigRobotByRobotId(robotId);
        this.passengerLogService.insertGetInLog(robot, param);
        Result collectInResponse = new Result();
        Date dNow = new Date();
        Passenger info = new Passenger();
        FlightInfo flight = new FlightInfo();
        GetInTmp tmp = param.getTmp();
        info.setUserName(tmp.getUserName());
        info.setRoomCode(robot.getRoomCode());
        info.setFlightNo(tmp.getFlightNo());
        info.setOrig(tmp.getOrig());
        info.setDest(tmp.getDest());
        info.setCabin(tmp.getCabin());
        info.setSeat(tmp.getSeat());
        info.setSeq(tmp.getSeg());
        info.setCardService(tmp.getCardService());
        info.setCardNo(tmp.getCardNo());
        info.setInType(tmp.getInType());
        info.setGetInTime(dNow);
        info.setStatus("1");
        info.setFlightDate(DateUtils.getDate());
        info.setRobotId(robotId);
        info.setFollowerNum(tmp.getFollowerNum());
        info.setIsMember(tmp.getIsMember());
        info.setColledtId(tmp.getColledId());
        FlightInfo flightParam = new FlightInfo();
        flightParam.setFlightNo(info.getFlightNo());
        flightParam.setScheExecDate(DateUtils.getDate());
        List<FlightInfo> flightInfoList = this.flightInfoService.selectFlightInfoList(flightParam);
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
        info.setStatus("1");
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

    private boolean saveBench(List<Passenger> list) {
        try {
            list.stream().forEach(x -> this.passengerMapper.insertPassenger((Passenger)((Object)x)));
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    private String getSign(String accountId, String appId, String appKey, String timestamp) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotNull((Object)accountId)) {
            sb.append("accountId=" + accountId + "&");
        }
        if (StringUtils.isNotNull((Object)appId)) {
            sb.append("appId=" + appId + "&");
        }
        if (StringUtils.isNotNull((Object)appKey)) {
            sb.append("appKey=" + appKey + "&");
        }
        sb.append("timestamp=" + timestamp + "&");
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

    public CaptureFace getLatestCaptureFace(List<CaptureFace> captureFaces) {
        return captureFaces.stream().max(Comparator.comparing(CaptureFace::getCaptureTime)).orElse(captureFaces.get(0));
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
    public int insertPassenger(Passenger tPassenger) {
        tPassenger.setCreateTime(DateUtils.getNowDate());
        return this.passengerMapper.insertPassenger(tPassenger);
    }

    @Override
    public int updatePassenger(Passenger tPassenger) {
        return this.passengerMapper.updatePassenger(tPassenger);
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
