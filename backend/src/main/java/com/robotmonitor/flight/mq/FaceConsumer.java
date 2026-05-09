/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.config.domain.ConfigDevice
 *  com.robotmonitor.config.mapper.ConfigDeviceMapper
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 *  org.springframework.util.CollectionUtils
 */
package com.robotmonitor.flight.mq;

import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.config.domain.ConfigDevice;
import com.robotmonitor.config.mapper.ConfigDeviceMapper;
import com.robotmonitor.flight.domain.BarCodeRespons;
import com.robotmonitor.flight.domain.CollectInParam;
import com.robotmonitor.flight.domain.Passenger;
import com.robotmonitor.flight.domain.UserCollectInfo;
import com.robotmonitor.flight.dto.kafka.deepglint.FaceKafka;
import com.robotmonitor.flight.dto.kafka.deepglint.FaceKafkaBinData;
import com.robotmonitor.flight.dto.kafka.deepglint.FaceKafkaMsg;
import com.robotmonitor.flight.dto.kafka.deepglint.data.RecFace;
import com.robotmonitor.flight.dto.kafka.deepglint.data.face.metadata.Tag;
import com.robotmonitor.flight.service.IPassengerService;
import com.robotmonitor.flight.service.IUpdatePassengerService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FaceConsumer {
    private static final Logger log = LoggerFactory.getLogger(FaceConsumer.class);
    @Autowired
    private IUpdatePassengerService updatePassengerService;
    @Autowired
    private ConfigDeviceMapper configDeviceMapper;
    private static final String KAFKA_EVENT_HUMAN_BODY_DETECT = "HUMANBODYDETECT";
    @Autowired
    private IPassengerService passengerService;

    public void faceListener(String message) {
        Tag tag;
        log.info("\u6536\u5230 face \u6d88\u606f: {}", (Object)message);
        FaceKafkaMsg faceKafkaMsg = (FaceKafkaMsg)JsonUtils.string2Obj((String)message, FaceKafkaMsg.class);
        log.info(faceKafkaMsg.getBinData());
        String binData = new String(Base64.getDecoder().decode(faceKafkaMsg.getBinData()));
        log.info("binData : {}", (Object)binData);
        FaceKafkaBinData faceKafkaBinData = faceKafkaMsg.getBinDataObj();
        if (null == faceKafkaBinData) {
            log.info("face bin data\u4e3a\u7a7a\uff0c\u8df3\u8fc7\u6d88\u606f");
            return;
        }
        if (CollectionUtils.isEmpty(faceKafkaBinData.getRecFaces())) {
            log.info("face\u4e3a\u7a7a\uff0c\u8df3\u8fc7\u6d88\u606f");
            return;
        }
        if (null == faceKafkaBinData.getRecFaces().get(0) || null == faceKafkaBinData.getRecFaces().get(0).getMetadata() || null == faceKafkaBinData.getRecFaces().get(0).getMetadata().getAdditionalInfos() || Strings.isBlank((String)faceKafkaBinData.getRecFaces().get(0).getMetadata().getAdditionalInfos().getPersonID())) {
            log.info("PersonID\u4e3a\u7a7a, \u8df3\u8fc7\u5904\u7406");
            return;
        }
        Set<String> exitDeviceIds = this.getExitDeviceIds();
        RecFace recFace = faceKafkaBinData.getRecFaces().get(0);
        boolean hasTags = this.hasTags(recFace);
        Tag tag2 = tag = hasTags ? recFace.getMetadata().getAdditionalInfos().getTagsObj().get(0) : null;
        if ("temp-test".equals(recFace.getMetadata().getAdditionalInfos().getUniqueSensorId())) {
            log.info("\u6536\u5230\u6d4b\u8bd5\u6570\u636e\uff1a{}", (Object)binData);
            return;
        }
        if ("3".equals(recFace.getMetadata().getAdditionalInfos().getUniqueSensorId())) {
            log.info("\u6536\u5230\u51c6\u5165\u6444\u50cf\u5934\u6570\u636e");
            this.testAccess(recFace.getMetadata().getAdditionalInfos().getPersonID(), null != tag ? tag.getRegisterID() : null);
        }
        if ("4".equals(recFace.getMetadata().getAdditionalInfos().getUniqueSensorId())) {
            log.info("\u6536\u5230\u51c6\u51fa\u6444\u50cf\u5934\u6570\u636e");
        }
        this.updatePassengerService.updatePassengerLocation(recFace.getMetadata().getAdditionalInfos().getPersonID(), exitDeviceIds, recFace.getMetadata().getAdditionalInfos().getUniqueSensorId(), hasTags, recFace.getMetadata().getTimestamp(), recFace.getImg().getSnapBox().getX(), recFace.getImg().getSnapBox().getY(), recFace.getImg().getSnapBox().getWidth(), recFace.getImg().getSnapBox().getHeight(), recFace.getOriginImg().getWidth(), recFace.getOriginImg().getHeight(), this.getCts(), recFace.getOriginImg().getUri(), null != tag && null != tag.getFace() ? tag.getFace().getUrl() : null, null != tag ? tag.getRegisterID() : null);
    }

    private void testAccess(String personID, String rid) {
        BarCodeRespons barCodeRespons = this.passengerService.barCodeForTest(personID);
        if (null == barCodeRespons) {
            log.info("\u672a\u627e\u5230\u7528\u6237");
            return;
        }
        Passenger passenger = new Passenger();
        passenger.setUserName(barCodeRespons.getUsername());
        CollectInParam cin = new CollectInParam();
        cin.setReId(rid);
        cin.setPId(personID);
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
        cin.setRoomCode("PEK2DX1");
        cin.setList(new ArrayList<UserCollectInfo>());
        this.passengerService.passengerGetIn(cin);
    }

    private boolean hasTags(RecFace recFace) {
        return Strings.isNotBlank((String)recFace.getMetadata().getAdditionalInfos().getTags());
    }

    public void faceListenerOld(String message) {
        FaceKafka faceKafka = (FaceKafka)JsonUtils.string2Obj((String)message, FaceKafka.class);
        log.info("\u6536\u5230 face \u6d88\u606f: {}", (Object)message);
        if (KAFKA_EVENT_HUMAN_BODY_DETECT.equals(faceKafka.getType())) {
            log.info("\u8df3\u8fc7\u4eba\u4f53\u68c0\u6d4b\u6d88\u606f, type: {}", (Object)faceKafka.getType());
            return;
        }
        if (Strings.isBlank((String)faceKafka.getPersonID())) {
            log.info("PersonID\u4e3a\u7a7a, \u8df3\u8fc7\u5904\u7406");
            return;
        }
        Set<String> exitDeviceIds = this.getExitDeviceIds();
        this.updatePassengerService.updatePassengerLocation(faceKafka.getPersonID(), exitDeviceIds, faceKafka.getFace().getLogicDeviceID(), !CollectionUtils.isEmpty(faceKafka.getTags()), faceKafka.getFace().getTime(), faceKafka.getOrigImage().getCapturePosition().getX(), faceKafka.getOrigImage().getCapturePosition().getY(), faceKafka.getOrigImage().getCapturePosition().getWidth(), faceKafka.getOrigImage().getCapturePosition().getHeight(), faceKafka.getOrigImage().getWidth(), faceKafka.getOrigImage().getHeight(), this.getCts(), faceKafka.getOrigImage().getUrl(), !CollectionUtils.isEmpty(faceKafka.getTags()) ? faceKafka.getTags().get(0).getFace().getUrl() : null, !CollectionUtils.isEmpty(faceKafka.getTags()) ? faceKafka.getTags().get(0).getRegisterID() : null);
    }

    private long getCts() {
        Instant now = Instant.now();
        long epochSecond = now.getEpochSecond();
        long nanoOfSecond = now.getNano();
        return epochSecond * 1000000000L + nanoOfSecond;
    }

    private Set<String> getExitDeviceIds() {
        log.info("\u5f00\u59cb\u67e5\u8be2\u51fa\u53e3\u6444\u50cf\u5934\u8bbe\u5907\u4fe1\u606f");
        List exitDevices = this.configDeviceMapper.selectExitDevices();
        HashSet<String> exitDeviceIds = new HashSet<String>();
        for (ConfigDevice device : exitDevices) {
            if (device.getDeepGlintDeviceId() == null) continue;
            exitDeviceIds.add(device.getDeepGlintDeviceId());
        }
        log.info("\u83b7\u53d6\u5230 {} \u4e2a\u51fa\u53e3\u6444\u50cf\u5934\u8bbe\u5907", (Object)exitDeviceIds.size());
        return exitDeviceIds;
    }
}
