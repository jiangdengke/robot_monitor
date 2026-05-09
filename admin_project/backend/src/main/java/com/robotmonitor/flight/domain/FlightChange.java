/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.robotmonitor.flight.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class FlightChange {
    private Long id;
    private String name;
    private String carrier;
    private String flightNo;
    private String deptName;
    private String arrName;
    @JsonFormat(pattern="HH:mm", timezone="GMT+8")
    private Date deptTime;
    @JsonFormat(pattern="HH:mm", timezone="GMT+8")
    private Date arrTime;
    private String changeStatus;
    private String changeStatusCn;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarrier() {
        return this.carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getArrName() {
        return this.arrName;
    }

    public void setArrName(String arrName) {
        this.arrName = arrName;
    }

    public Date getDeptTime() {
        return this.deptTime;
    }

    public void setDeptTime(Date deptTime) {
        this.deptTime = deptTime;
    }

    public Date getArrTime() {
        return this.arrTime;
    }

    public void setArrTime(Date arrTime) {
        this.arrTime = arrTime;
    }

    public String getChangeStatus() {
        return this.changeStatus;
    }

    public void setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
    }

    public String getChangeStatusCn() {
        return this.changeStatusCn;
    }

    public void setChangeStatusCn(String changeStatusCn) {
        this.changeStatusCn = changeStatusCn;
    }
}
