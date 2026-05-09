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

public class PassengerLocationLog {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u65c5\u5ba2REID")
    private String reid;
    @Excel(name="\u65c5\u5ba2PID")
    private String pid;
    @Excel(name="\u65c5\u5ba2ID")
    private String passengerId;
    @Excel(name="\u65c5\u5ba2\u59d3\u540d")
    private String userName;
    @Excel(name="\u4f11\u606f\u5ba4\u7f16\u7801")
    private String roomCode;
    @Excel(name="\u822a\u73ed\u53f7")
    private String flightNo;
    @Excel(name="\u822a\u73ed\u65e5\u671f", dateFormat="yyyy-MM-dd")
    private Date flightDate;
    @Excel(name="\u533a\u57dfID")
    private Integer regionId;
    @Excel(name="\u533a\u57df\u540d\u79f0")
    private String regionName;
    @Excel(name="\u5750\u6807\u4fe1\u606f")
    private String coordinate;
    @Excel(name="\u8bbe\u5907ID")
    private String deviceId;
    @Excel(name="\u8bbe\u5907\u540d\u79f0")
    private String deviceName;
    @Excel(name="\u683c\u7075\u6df1\u77b3\u8bbe\u5907ID")
    private String deepGlintDeviceId;
    @Excel(name="\u521b\u5efa\u65f6\u95f4", dateFormat="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Excel(name="\u8bc6\u522b\u7c7b\u578b")
    private String recognitionType;
    @Excel(name="oriImageUrl")
    private String oriImageUrl;
    @Excel(name="registerImageUrl")
    private String registerImageUrl;
    @Excel(name="cts\u552f\u4e00Id")
    private String cts;
    @Excel(name="is_out")
    private String isOut;

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

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassengerId() {
        return this.passengerId;
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

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getRegionId() {
        return this.regionId;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeepGlintDeviceId(String deepGlintDeviceId) {
        this.deepGlintDeviceId = deepGlintDeviceId;
    }

    public String getDeepGlintDeviceId() {
        return this.deepGlintDeviceId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getOriImageUrl() {
        return this.oriImageUrl;
    }

    public void setOriImageUrl(String oriImageUrl) {
        this.oriImageUrl = oriImageUrl;
    }

    public String getRegisterImageUrl() {
        return this.registerImageUrl;
    }

    public void setRegisterImageUrl(String registerImageUrl) {
        this.registerImageUrl = registerImageUrl;
    }

    public String getIsOut() {
        return this.isOut;
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.MULTI_LINE_STYLE).append("id", (Object)this.getId()).append("reid", (Object)this.getReid()).append("pid", (Object)this.getPid()).append("passengerId", (Object)this.getPassengerId()).append("userName", (Object)this.getUserName()).append("roomCode", (Object)this.getRoomCode()).append("flightNo", (Object)this.getFlightNo()).append("flightDate", (Object)this.getFlightDate()).append("regionId", (Object)this.getRegionId()).append("regionName", (Object)this.getRegionName()).append("coordinate", (Object)this.getCoordinate()).append("deviceId", (Object)this.getDeviceId()).append("deviceName", (Object)this.getDeviceName()).append("deepGlintDeviceId", (Object)this.getDeepGlintDeviceId()).append("createTime", (Object)this.getCreateTime()).append("origImageUrl", (Object)this.getOriImageUrl()).append("registerImageUrl", (Object)this.getRegisterImageUrl()).append("cts", (Object)this.getCts()).append("isOut", (Object)this.getIsOut()).toString();
    }
}
