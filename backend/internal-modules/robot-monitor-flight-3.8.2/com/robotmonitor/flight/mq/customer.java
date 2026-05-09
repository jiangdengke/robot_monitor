/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.enums.FlightChangeType
 *  javax.xml.bind.JAXBContext
 *  javax.xml.bind.JAXBException
 *  javax.xml.bind.Unmarshaller
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apache.commons.lang3.builder.ReflectionToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 *  org.apache.kafka.clients.consumer.Consumer
 *  org.junit.Test
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.kafka.annotation.KafkaListener
 *  org.springframework.stereotype.Service
 */
package com.robotmonitor.flight.mq;

import com.robotmonitor.common.enums.FlightChangeType;
import com.robotmonitor.flight.domain.FlightGate;
import com.robotmonitor.flight.domain.FlightInfo;
import com.robotmonitor.flight.domain.FlightKafkaLog;
import com.robotmonitor.flight.domain.FlightWarning;
import com.robotmonitor.flight.domain.kafka.BoardingGate;
import com.robotmonitor.flight.domain.kafka.Msg;
import com.robotmonitor.flight.domain.kafka.Station;
import com.robotmonitor.flight.service.IFlightGateService;
import com.robotmonitor.flight.service.IFlightInfoService;
import com.robotmonitor.flight.service.IFlightKafkaLogService;
import com.robotmonitor.flight.service.IFlightWarningService;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.kafka.clients.consumer.Consumer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class customer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    @Autowired
    IFlightKafkaLogService logService;
    @Autowired
    IFlightInfoService flightService;
    @Autowired
    IFlightWarningService warningService;
    @Autowired
    IFlightGateService flightGateService;

    @KafkaListener(topics={"${spring.kafka.consumer.topic}"}, groupId="${spring.kafka.consumer.topic}")
    public void flightInsertListener(String message) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance((Class[])new Class[]{Msg.class});
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Msg msg = (Msg)unmarshaller.unmarshal((Reader)new StringReader(message));
        if (ObjectUtils.isEmpty((Object)msg)) {
            return;
        }
        System.out.println("Name: " + msg.getMeta().getSubType() + ", SendTime: " + msg.getMeta().getSendTime());
        String subType = msg.getMeta().getSubType();
        String airport = msg.getMeta().getAirport();
        String sendTime = msg.getMeta().getSendTime();
        String flightId = msg.getFlight().getFlightId();
        this.writeLog(subType, airport, message);
        FlightInfo infoNew = this.getFlightInfo(msg);
        if (subType.equals("insert") || subType.equals("update")) {
            FlightInfo info = this.flightService.selectFlightInfoByFlightId(flightId);
            if (!ObjectUtils.isEmpty((Object)((Object)info)) && Long.parseLong(sendTime) <= Long.parseLong(info.getSendTime())) {
                return;
            }
            if (ObjectUtils.isEmpty((Object)((Object)info))) {
                this.flightService.insertFlightInfo(this.getFlightInfo(msg));
                return;
            }
            this.compareFlightInfo(info, infoNew);
        } else if (subType.equals("delete")) {
            FlightWarning warning = new FlightWarning();
            warning.setWarningType(FlightChangeType.CANCEL.getCode());
            warning.setStatus("0");
            warning.setChangeAfter(infoNew.getDomFlightAbstateReason() + infoNew.getDomFlightAbstateReasonDesc() + infoNew.getIntFlightAbstateReason() + infoNew.getIntFlightAbstateReasonDesc());
            warning.setCreateTime(new Date());
            this.warningService.insertFlightWarning(warning);
            infoNew.setUpdateTime(new Date());
            infoNew.setIsDelete("1");
            this.flightService.updateFlightInfo(infoNew);
        }
    }

    @KafkaListener(topics={"${spring.kafka.consumer.topic-gate}"})
    public void flightGateListener(String message) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance((Class[])new Class[]{Msg.class});
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Msg msg = (Msg)unmarshaller.unmarshal((Reader)new StringReader(message));
        if (ObjectUtils.isEmpty((Object)msg)) {
            return;
        }
        System.out.println("Name: " + msg.getMeta().getSubType() + ", SendTime: " + msg.getMeta().getSendTime());
        String subType = msg.getMeta().getSubType();
        String airport = msg.getMeta().getAirport();
        String sendTime = msg.getMeta().getSendTime();
        String flightId = msg.getFlight().getFlightId();
        this.writeLog(subType, airport, message);
        FlightGate infoNew = this.getFlightGate(msg);
        if (ObjectUtils.isEmpty((Object)((Object)infoNew))) {
            return;
        }
        FlightGate info = this.flightGateService.selectFlightGateByFlightId(flightId);
        if (ObjectUtils.isEmpty((Object)((Object)info))) {
            this.flightGateService.insertFlightGate(infoNew);
            return;
        }
        if (Long.parseLong(sendTime) <= Long.parseLong(info.getSendTime())) {
            return;
        }
        this.compareFlightGate(info, infoNew);
    }

    private void compareFlightInfo(FlightInfo info, FlightInfo infoNew) {
        String infoNewStr;
        String infoStr = ReflectionToStringBuilder.toString((Object)((Object)info), (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
        if (infoStr.equals(infoNewStr = ReflectionToStringBuilder.toString((Object)((Object)infoNew), (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE))) {
            return;
        }
        Date dnow = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        if (ObjectUtils.isEmpty((Object)info.getScheTakeOffTime())) {
            info.setScheTakeOffTime("");
        }
        if (!info.getScheTakeOffTime().equals(infoNew.getScheTakeOffTime()) && info.getScheExecDate().equals(sd.format(dnow))) {
            FlightWarning warning = new FlightWarning();
            warning.setWarningType(FlightChangeType.TIME_CHANGE.getCode());
            warning.setStatus("0");
            warning.setChangeBefore(info.getScheTakeOffTime());
            warning.setChangeAfter(infoNew.getScheTakeOffTime());
            warning.setCreateTime(new Date());
            this.warningService.insertFlightWarning(warning);
        }
        infoNew.setUpdateTime(new Date());
        this.flightService.updateFlightInfo(infoNew);
    }

    private void compareFlightGate(FlightGate info, FlightGate infoNew) {
        String infoNewStr;
        String infoStr = ReflectionToStringBuilder.toString((Object)((Object)info), (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
        if (infoStr.equals(infoNewStr = ReflectionToStringBuilder.toString((Object)((Object)infoNew), (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE))) {
            return;
        }
        Date dnow = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        if (!info.getGateCd().equals(infoNew.getGateCd()) && info.getScheExecDate().equals(sd.format(dnow))) {
            FlightWarning warning = new FlightWarning();
            warning.setWarningType(FlightChangeType.GATE_CHANGE.getCode());
            warning.setStatus("0");
            warning.setChangeBefore(info.getGateCd());
            warning.setChangeAfter(infoNew.getGateCd());
            warning.setCreateTime(new Date());
            this.warningService.insertFlightWarning(warning);
        }
        infoNew.setUpdateTime(new Date());
        this.flightGateService.updateFlightGate(infoNew);
    }

    private FlightInfo getFlightInfo(Msg msg) {
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
        if (ObjectUtils.isNotEmpty((Object)msg.getFlight().getStationGroup()) && ObjectUtils.isNotEmpty(msg.getFlight().getStationGroup().getStation())) {
            for (Station sta : msg.getFlight().getStationGroup().getStation()) {
                if (!sta.getStationIataCd().equals(msg.getMeta().getAirport())) continue;
                info.setEstmTakeOffTime(sta.getEstmTakeOffTime());
            }
        }
        if (ObjectUtils.isEmpty((Object)info.getEstmTakeOffTime())) {
            info.setEstmTakeOffTime("");
        }
        info.setUpdateTime(new Date());
        return info;
    }

    private FlightGate getFlightGate(Msg msg) {
        FlightGate info = new FlightGate();
        info.setFlightId(msg.getFlight().getFlightId());
        info.setSendTime(msg.getMeta().getSendTime());
        info.setScheExecDate(msg.getFlight().getScheExecDate());
        Object gateStr = "";
        if (ObjectUtils.isEmpty((Object)msg.getFlight().getBoardingGateGroup()) || ObjectUtils.isEmpty(msg.getFlight().getBoardingGateGroup().getBoardingGate())) {
            return null;
        }
        for (BoardingGate gate : msg.getFlight().getBoardingGateGroup().getBoardingGate()) {
            gateStr = (String)gateStr + gate.getGateCd();
            info.setEstmEndTime(gate.getEstmEndTime());
            info.setEstmEndTime(gate.getEstmEndTime());
            info.setTerminalCd(gate.getTerminalCd());
            info.setGateAttr(gate.getGateAttr());
        }
        info.setGateCd((String)gateStr);
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

    @Test
    public void test() throws JAXBException {
        String message = "<msg><meta><sender>ARDT</sender><receiver/><sequence>1</sequence><sendTime>20260304141338805</sendTime><type>flight</type><subType>update</subType><source>PEK</source><messageId>ODS_FLIGHT_1025047605710958592</messageId><responseId/><airport>PEK</airport><airportIataCd>PEK</airportIataCd><airportIcaoCd>ZBAA</airportIcaoCd></meta><flight><flightId>CA-1413-20260304-D</flightId><associateflightId>CA-1614-20260304-A</associateflightId><associateReturnId/><fmsId/><ffid/><airlineIataCd>CA</airlineIataCd><airlineIcaoCd>CCA</airlineIcaoCd><subAirlineCd>0</subAirlineCd><flightNo>1413</flightNo><flightSuffix/><scheExecDate>20260304</scheExecDate><flightScheBatchDate>20260304</flightScheBatchDate><ioAttr>D</ioAttr><flightTask>W/Z</flightTask><serviceType>J</serviceType><flightAttr>DOM</flightAttr><count/><agency>1</agency><seatLayout>F0J12G0Y147</seatLayout><cargoFlt>0</cargoFlt><craftType>738</craftType><craftNo>B5851</craftNo><latestOffStatus>CHK</latestOffStatus><latestOnStatus>SCH</latestOnStatus><domFlightState/><intFlightState/><domFlightAbstate>DLY</domFlightAbstate><intFlightAbstate/><domFlightAbstateReason/><intFlightAbstateReason/><domInnerFlightAbstateReason/><intInnerFlightAbstateReason/><domBoardingStartTime/><intBoardingStartTime/><domPastStationBoardingTime/><intPastStationBoardingTime/><domLastCallTime/><intLastCallTime/><domBoardingCloseTime/><intBoardingCloseTime/><domEstmStartCheckInTime/><domEstmEndCheckInTime/><domStartCheckInTime/><domEndCheckInTime/><intEstmStartCheckInTime/><intEstmEndCheckInTime/><intStartCheckInTime/><intEndCheckInTime/><vip>0</vip><cancelTime/><alternateGroup><alternate><alternateNo>1</alternateNo><alternateIataCd>KWE</alternateIataCd><divEstmTakeOffTime/><divActlTakeOffTime/><divEstmLandInTime/><divActlLandInTime/></alternate></alternateGroup><sharingGroup></sharingGroup><stationGroup><station><stationSequence>1</stationSequence><stationIataCd>PEK</stationIataCd><stationCn>\u5317\u4eac\u9996\u90fd\u56fd\u9645\u673a\u573a</stationCn><alternateFlag>N</alternateFlag><scheTakeOffTime>20260304141000</scheTakeOffTime><estmTakeOffTime/><actlTakeOffTime/><scheLandInTime/><estmLandInTime/><actlLandInTime/></station><station><stationSequence>2</stationSequence><stationIataCd>KMG</stationIataCd><stationCn>\u6606\u660e\u957f\u6c34\u56fd\u9645\u673a\u573a</stationCn><alternateFlag>N</alternateFlag><scheTakeOffTime/><estmTakeOffTime/><actlTakeOffTime/><scheLandInTime>20260304180500</scheLandInTime><estmLandInTime/><actlLandInTime/></station></stationGroup><terminal><domTerminalCd>T3</domTerminalCd><intTerminalCd/></terminal><runway><runwayCd/></runway></flight></msg>";
        JAXBContext jaxbContext = JAXBContext.newInstance((Class[])new Class[]{Msg.class});
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Msg msg = (Msg)unmarshaller.unmarshal((Reader)new StringReader(message));
        if (ObjectUtils.isEmpty((Object)msg)) {
            return;
        }
        System.out.println("Name: " + msg.getMeta().getSubType() + ", SendTime: " + msg.getMeta().getSendTime());
        String subType = msg.getMeta().getSubType();
        String airport = msg.getMeta().getAirport();
        String sendTime = msg.getMeta().getSendTime();
        String flightId = msg.getFlight().getFlightId();
        FlightInfo infoNew = this.getFlightInfo(msg);
        long ss = Long.parseLong(sendTime);
        System.out.println(ss);
    }
}
