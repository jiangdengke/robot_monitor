/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlElement
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 */
package com.robotmonitor.flight.domain.kafka;

import jakarta.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(value=XmlAccessType.FIELD)
public class Meta {
    @XmlElement(name="sender")
    private String sender;
    @XmlElement(name="receiver")
    private String receiver;
    @XmlElement(name="sequence")
    private String sequence;
    @XmlElement(name="sendTime")
    private String sendTime;
    @XmlElement(name="type")
    private String type;
    @XmlElement(name="subType")
    private String subType;
    @XmlElement(name="source")
    private String source;
    @XmlElement(name="messageId")
    private String messageId;
    @XmlElement(name="responseId")
    private String responseId;
    @XmlElement(name="airport")
    private String airport;
    @XmlElement(name="airportIataCd")
    private String airportIataCd;
    @XmlElement(name="airportIcaoCd")
    private String airportIcaoCd;

    public String getSender() {
        return this.sender;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public String getSequence() {
        return this.sequence;
    }

    public String getSendTime() {
        return this.sendTime;
    }

    public String getType() {
        return this.type;
    }

    public String getSubType() {
        return this.subType;
    }

    public String getSource() {
        return this.source;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public String getResponseId() {
        return this.responseId;
    }

    public String getAirport() {
        return this.airport;
    }

    public String getAirportIataCd() {
        return this.airportIataCd;
    }

    public String getAirportIcaoCd() {
        return this.airportIcaoCd;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public void setAirportIataCd(String airportIataCd) {
        this.airportIataCd = airportIataCd;
    }

    public void setAirportIcaoCd(String airportIcaoCd) {
        this.airportIcaoCd = airportIcaoCd;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Meta)) {
            return false;
        }
        Meta other = (Meta)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$sender = this.getSender();
        String other$sender = other.getSender();
        if (this$sender == null ? other$sender != null : !this$sender.equals(other$sender)) {
            return false;
        }
        String this$receiver = this.getReceiver();
        String other$receiver = other.getReceiver();
        if (this$receiver == null ? other$receiver != null : !this$receiver.equals(other$receiver)) {
            return false;
        }
        String this$sequence = this.getSequence();
        String other$sequence = other.getSequence();
        if (this$sequence == null ? other$sequence != null : !this$sequence.equals(other$sequence)) {
            return false;
        }
        String this$sendTime = this.getSendTime();
        String other$sendTime = other.getSendTime();
        if (this$sendTime == null ? other$sendTime != null : !this$sendTime.equals(other$sendTime)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$subType = this.getSubType();
        String other$subType = other.getSubType();
        if (this$subType == null ? other$subType != null : !this$subType.equals(other$subType)) {
            return false;
        }
        String this$source = this.getSource();
        String other$source = other.getSource();
        if (this$source == null ? other$source != null : !this$source.equals(other$source)) {
            return false;
        }
        String this$messageId = this.getMessageId();
        String other$messageId = other.getMessageId();
        if (this$messageId == null ? other$messageId != null : !this$messageId.equals(other$messageId)) {
            return false;
        }
        String this$responseId = this.getResponseId();
        String other$responseId = other.getResponseId();
        if (this$responseId == null ? other$responseId != null : !this$responseId.equals(other$responseId)) {
            return false;
        }
        String this$airport = this.getAirport();
        String other$airport = other.getAirport();
        if (this$airport == null ? other$airport != null : !this$airport.equals(other$airport)) {
            return false;
        }
        String this$airportIataCd = this.getAirportIataCd();
        String other$airportIataCd = other.getAirportIataCd();
        if (this$airportIataCd == null ? other$airportIataCd != null : !this$airportIataCd.equals(other$airportIataCd)) {
            return false;
        }
        String this$airportIcaoCd = this.getAirportIcaoCd();
        String other$airportIcaoCd = other.getAirportIcaoCd();
        return !(this$airportIcaoCd == null ? other$airportIcaoCd != null : !this$airportIcaoCd.equals(other$airportIcaoCd));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Meta;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $sender = this.getSender();
        result = result * 59 + ($sender == null ? 43 : $sender.hashCode());
        String $receiver = this.getReceiver();
        result = result * 59 + ($receiver == null ? 43 : $receiver.hashCode());
        String $sequence = this.getSequence();
        result = result * 59 + ($sequence == null ? 43 : $sequence.hashCode());
        String $sendTime = this.getSendTime();
        result = result * 59 + ($sendTime == null ? 43 : $sendTime.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $subType = this.getSubType();
        result = result * 59 + ($subType == null ? 43 : $subType.hashCode());
        String $source = this.getSource();
        result = result * 59 + ($source == null ? 43 : $source.hashCode());
        String $messageId = this.getMessageId();
        result = result * 59 + ($messageId == null ? 43 : $messageId.hashCode());
        String $responseId = this.getResponseId();
        result = result * 59 + ($responseId == null ? 43 : $responseId.hashCode());
        String $airport = this.getAirport();
        result = result * 59 + ($airport == null ? 43 : $airport.hashCode());
        String $airportIataCd = this.getAirportIataCd();
        result = result * 59 + ($airportIataCd == null ? 43 : $airportIataCd.hashCode());
        String $airportIcaoCd = this.getAirportIcaoCd();
        result = result * 59 + ($airportIcaoCd == null ? 43 : $airportIcaoCd.hashCode());
        return result;
    }

    public String toString() {
        return "Meta(sender=" + this.getSender() + ", receiver=" + this.getReceiver() + ", sequence=" + this.getSequence() + ", sendTime=" + this.getSendTime() + ", type=" + this.getType() + ", subType=" + this.getSubType() + ", source=" + this.getSource() + ", messageId=" + this.getMessageId() + ", responseId=" + this.getResponseId() + ", airport=" + this.getAirport() + ", airportIataCd=" + this.getAirportIataCd() + ", airportIcaoCd=" + this.getAirportIcaoCd() + ")";
    }
}
