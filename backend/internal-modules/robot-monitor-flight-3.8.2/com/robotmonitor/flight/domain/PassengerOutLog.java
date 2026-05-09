/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.robotmonitor.flight.domain;

import com.robotmonitor.common.annotation.Excel;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PassengerOutLog {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u65c5\u5ba2REID")
    private String reid;
    @Excel(name="\u65c5\u5ba2PID")
    private String pid;
    @Excel(name="\u65c5\u5ba2ID")
    private String passengerId;
    @Excel(name="\u51c6\u51fa\u65f6\u95f4", width=30.0, dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date outTime;
    @Excel(name="\u65c5\u5ba2\u59d3\u540d")
    private String userName;
    @Excel(name="\u4f11\u606f\u5ba4\u7f16\u7801")
    private String roomCode;
    @Excel(name="\u822a\u73ed\u53f7")
    private String flightNo;
    @Excel(name="\u822a\u73ed\u65e5\u671f", width=30.0, dateFormat="yyyy-MM-dd")
    private Date flightDate;
    @Excel(name="\u8bc6\u522b\u7c7b\u578b")
    private String recognitionType;
    @Excel(name="origImageUrl")
    private String origImageUrl;
    @Excel(name="registerImageUrl")
    private String registerImageUrl;
    @Excel(name="cts\u552f\u4e00Id")
    private String cts;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getReid() {
        return this.reid;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPassengerId() {
        return this.passengerId;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Date getOutTime() {
        return this.outTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public Date getFlightDate() {
        return this.flightDate;
    }

    public String getRecognitionType() {
        return this.recognitionType;
    }

    public void setRecognitionType(String recognitionType) {
        this.recognitionType = recognitionType;
    }

    public String getCts() {
        return this.cts;
    }

    public void setCts(String cts) {
        this.cts = cts;
    }

    public String getRegisterImageUrl() {
        return this.registerImageUrl;
    }

    public void setRegisterImageUrl(String registerImageUrl) {
        this.registerImageUrl = registerImageUrl;
    }

    public String getOrigImageUrl() {
        return this.origImageUrl;
    }

    public void setOrigImageUrl(String origImageUrl) {
        this.origImageUrl = origImageUrl;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("id", (Object)this.getId()).append("reid", (Object)this.getReid()).append("pid", (Object)this.getPid()).append("passengerId", (Object)this.getPassengerId()).append("outTime", (Object)this.getOutTime()).append("userName", (Object)this.getUserName()).append("roomCode", (Object)this.getRoomCode()).append("flightNo", (Object)this.getFlightNo()).append("flightDate", (Object)this.getFlightDate()).append("origImageUrl", (Object)this.getOrigImageUrl()).append("registerImageUrl", (Object)this.getRegisterImageUrl()).append("cts", (Object)this.getCts()).toString();
    }
}
