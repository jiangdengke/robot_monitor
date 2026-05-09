/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.robotmonitor.config.domain.deepglint.compare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.robotmonitor.config.domain.deepglint.compare.RegisterData;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ListRegisterData {
    @JsonProperty(value="Registers")
    private List<RegisterData> registers;
    @JsonProperty(value="Count")
    private Integer count;

    public ListRegisterData() {
    }

    public ListRegisterData(List<RegisterData> registers, Integer count) {
        this.registers = registers;
        this.count = count;
    }

    public List<RegisterData> getRegisters() {
        return this.registers;
    }

    public void setRegisters(List<RegisterData> registers) {
        this.registers = registers;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
