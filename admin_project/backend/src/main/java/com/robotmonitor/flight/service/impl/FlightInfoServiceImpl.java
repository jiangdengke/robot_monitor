/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.DateUtils
 *  javax.xml.bind.JAXBContext
 *  javax.xml.bind.JAXBException
 *  javax.xml.bind.Unmarshaller
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apache.commons.lang3.builder.ReflectionToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.flight.service.impl;

import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.FlightKafkaLog;
import com.robotmonitor.flight.domain.FlightParam;
import com.robotmonitor.flight.domain.FlightWarning;
import com.robotmonitor.flight.domain.kafka.Msg;
import com.robotmonitor.flight.domain.kafka.Station;
import com.robotmonitor.flight.mapper.FlightInfoMapper;
import com.robotmonitor.flight.service.IFlightInfoService;
import com.robotmonitor.flight.service.IFlightKafkaLogService;
import com.robotmonitor.flight.service.IFlightWarningService;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightInfoServiceImpl
implements IFlightInfoService {
    @Autowired
    private FlightInfoMapper flightInfoMapper;
    @Autowired
    IFlightKafkaLogService logService;
    @Autowired
    IFlightWarningService warningService;

    @Override
    public FlightInfo selectFlightInfoByFlightId(String flightId) {
        return this.flightInfoMapper.selectFlightInfoByFlightId(flightId);
    }

    @Override
    public List<FlightInfo> selectFlightInfoList(FlightInfo flightInfo) {
        return this.flightInfoMapper.selectFlightInfoList(flightInfo);
    }

    @Override
    public int insertFlightInfo(FlightInfo flightInfo) {
        return this.flightInfoMapper.insertFlightInfo(flightInfo);
    }

    @Override
    public int updateFlightInfo(FlightInfo flightInfo) {
        flightInfo.setUpdateTime(DateUtils.getNowDate());
        return this.flightInfoMapper.updateFlightInfo(flightInfo);
    }

    @Override
    public int deleteFlightInfoByFlightIds(Long[] flightIds) {
        return this.flightInfoMapper.deleteFlightInfoByFlightIds(flightIds);
    }

    @Override
    public int deleteFlightInfoByFlightId(String flightId) {
        return this.flightInfoMapper.deleteFlightInfoByFlightId(flightId);
    }

    @Override
    public int addTmp(String message) throws JAXBException {
        String[] msgs = message.split("</msg>");
        JAXBContext jaxbContext = JAXBContext.newInstance((Class[])new Class[]{Msg.class});
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        for (String msgStr : msgs) {
            Msg msg;
            if (msgStr.trim().isEmpty() || ObjectUtils.isEmpty((Object)(msg = (Msg)unmarshaller.unmarshal((Reader)new StringReader(msgStr.trim() + "</msg>"))))) continue;
            System.out.println("Name: " + msg.getMeta().getSubType() + ", SendTime: " + msg.getMeta().getSendTime());
            System.out.println(message);
            String subType = msg.getMeta().getSubType();
            String airport = msg.getMeta().getAirportIataCd();
            String sendTime = msg.getMeta().getSendTime();
            String flightId = msg.getFlight().getFlightId();
            this.writeLog(subType, airport, message);
            FlightInfo infoNew = this.getFlightInfo(msg);
            if (subType.equals("insert") || subType.equals("update")) {
                FlightInfo info = this.selectFlightInfoByFlightId(flightId);
                if (!ObjectUtils.isEmpty((Object)((Object)info)) && Long.valueOf(sendTime) <= Long.valueOf(info.getSendTime())) continue;
                if (ObjectUtils.isEmpty((Object)((Object)info))) {
                    this.insertFlightInfo(this.getFlightInfo(msg));
                    continue;
                }
                this.compareFlightInfo(info, infoNew);
                continue;
            }
            if (!subType.equals("delete")) continue;
            FlightWarning warning = new FlightWarning();
            warning.setWarningType("4");
            warning.setStatus("0");
            warning.setChangeAfter(infoNew.getDomFlightAbstateReason() + infoNew.getDomFlightAbstateReasonDesc() + infoNew.getIntFlightAbstateReason() + infoNew.getIntFlightAbstateReasonDesc());
            warning.setCreateTime(new Date());
            this.warningService.insertFlightWarning(warning);
            infoNew.setUpdateTime(new Date());
            infoNew.setIsDelete("1");
            this.updateFlightInfo(infoNew);
        }
        return 1;
    }

    private FlightInfo getFlightInfo(Msg msg) {
        String airport = msg.getMeta().getAirport();
        String seq = "";
        FlightInfo info = new FlightInfo();
        info.setFlightId(msg.getFlight().getFlightId());
        info.setSendTime(msg.getMeta().getSendTime());
        info.setAirlineCd(msg.getFlight().getAirlineIataCd());
        info.setFlightNo(msg.getFlight().getFlightNo() + msg.getFlight().getFlightSuffix());
        info.setScheExecDate(msg.getFlight().getScheExecDate());
        info.setFlightAttr(msg.getFlight().getFlightAttr());
        info.setCraftType(msg.getFlight().getCraftType());
        info.setCraftNo(msg.getFlight().getCraftNo());
        info.setLatestOffStatus(msg.getFlight().getLatestOffStatus());
        info.setLatestOnStatus(msg.getFlight().getLatestOnStatus());
        info.setIntFlightState(msg.getFlight().getIntFlightState());
        info.setDomFlightState(msg.getFlight().getDomFlightState());
        info.setIntFlightAbstate(msg.getFlight().getDomFlightAbstate());
        info.setDomFlightState(msg.getFlight().getDomFlightState());
        info.setIntFlightAbstateReason(msg.getFlight().getIntFlightAbstateReason());
        info.setDomFlightAbstateReason(msg.getFlight().getDomFlightAbstateReason());
        info.setIntInnerFlightAbstateReason(msg.getFlight().getIntFlightAbstateReason());
        info.setDomInnerFlightAbstateReason(msg.getFlight().getDomInnerFlightAbstateReason());
        Object airline = "";
        for (Station st : msg.getFlight().getStationGroup().getStation()) {
            airline = st.getStationIataCd() + "-";
            if (!st.getStationIataCd().equals(airport)) continue;
            seq = st.getStationSequence();
            info.setStation(airport);
            info.setStationCn(st.getStationCn());
            info.setScheTakeOffTime(st.getScheTakeOffTime());
            info.setEstmTakeOffTime(st.getEstmTakeOffTime());
            info.setActlTakeOffTime(st.getActlTakeOffTime());
        }
        if (((String)airline).length() > 0) {
            airline = ((String)airline).substring(0, ((String)airline).length() - 1);
        }
        info.setAirline((String)airline);
        info.setUpdateTime(new Date());
        return info;
    }

    private void writeLog(String subType, String airport, String msg) {
        FlightKafkaLog log = new FlightKafkaLog();
        log.setMsg(msg);
        log.setSubType(subType);
        log.setAirportCode(airport);
        this.logService.insertFlightKafkaLog(log);
    }

    private int compareFlightInfo(FlightInfo info, FlightInfo infoNew) {
        String infoNewStr;
        String infoStr = ReflectionToStringBuilder.toString((Object)((Object)info), (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
        if (infoStr.equals(infoNewStr = ReflectionToStringBuilder.toString((Object)((Object)infoNew), (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE))) {
            return 0;
        }
        Date dnow = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        if (!info.getScheTakeOffTime().equals(infoNew.getScheTakeOffTime()) && info.getScheExecDate().equals(sd.format(dnow))) {
            FlightWarning warning = new FlightWarning();
            warning.setWarningType("2");
            warning.setStatus("0");
            warning.setChangeBefore(info.getScheTakeOffTime());
            warning.setChangeAfter(infoNew.getScheTakeOffTime());
            warning.setCreateTime(new Date());
            this.warningService.insertFlightWarning(warning);
        }
        infoNew.setUpdateTime(new Date());
        this.updateFlightInfo(infoNew);
        return 1;
    }

    @Override
    public List<FlightInfo> selectWillTakeOffFlights(FlightParam param) {
        return this.flightInfoMapper.selectWillTakeOffFlights(param);
    }
}
