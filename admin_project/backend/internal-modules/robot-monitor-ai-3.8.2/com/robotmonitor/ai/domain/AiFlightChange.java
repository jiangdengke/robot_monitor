/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.flight.domain.FlightWarning
 */
package com.robotmonitor.ai.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.flight.domain.FlightWarning;
import java.util.Date;

public class AiFlightChange {
    @Excel(name="\u9884\u8b66\u7c7b\u522b \u89c1\u5b57\u5178\u8868flightwarningtype")
    private String warningType;
    @Excel(name="\u53d8\u66f4\u524d")
    private String changeBefore;
    @Excel(name="\u53d8\u66f4\u540e")
    private String changeAfter;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public AiFlightChange(FlightWarning flightWarning) {
        this.warningType = flightWarning.getWarningType();
        this.changeBefore = flightWarning.getChangeBefore();
        this.changeAfter = flightWarning.getChangeAfter();
        this.createTime = flightWarning.getCreateTime();
    }

    public String toString() {
        return "AiFlightChange{warningType(\u9884\u8b66\u7c7b\u522b:1,\u767b\u673a\u53e3\u53d8\u52a8;2,\u822a\u73ed\u5ef6\u8bef3,\u5373\u5c06\u767b\u673a4,\u822a\u73ed\u53d6\u6d88)='" + this.warningType + "', changeBefore(\u53d8\u66f4\u524d)='" + this.changeBefore + "', changeAfter(\u53d8\u66f4\u540e)='" + this.changeAfter + "', createTime(\u521b\u5efa\u65f6\u95f4)=" + this.createTime + "}";
    }

    public String getWarningType() {
        return this.warningType;
    }

    public String getChangeBefore() {
        return this.changeBefore;
    }

    public String getChangeAfter() {
        return this.changeAfter;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public void setChangeBefore(String changeBefore) {
        this.changeBefore = changeBefore;
    }

    public void setChangeAfter(String changeAfter) {
        this.changeAfter = changeAfter;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AiFlightChange)) {
            return false;
        }
        AiFlightChange other = (AiFlightChange)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$warningType = this.getWarningType();
        String other$warningType = other.getWarningType();
        if (this$warningType == null ? other$warningType != null : !this$warningType.equals(other$warningType)) {
            return false;
        }
        String this$changeBefore = this.getChangeBefore();
        String other$changeBefore = other.getChangeBefore();
        if (this$changeBefore == null ? other$changeBefore != null : !this$changeBefore.equals(other$changeBefore)) {
            return false;
        }
        String this$changeAfter = this.getChangeAfter();
        String other$changeAfter = other.getChangeAfter();
        if (this$changeAfter == null ? other$changeAfter != null : !this$changeAfter.equals(other$changeAfter)) {
            return false;
        }
        Date this$createTime = this.getCreateTime();
        Date other$createTime = other.getCreateTime();
        return !(this$createTime == null ? other$createTime != null : !((Object)this$createTime).equals(other$createTime));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AiFlightChange;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $warningType = this.getWarningType();
        result = result * 59 + ($warningType == null ? 43 : $warningType.hashCode());
        String $changeBefore = this.getChangeBefore();
        result = result * 59 + ($changeBefore == null ? 43 : $changeBefore.hashCode());
        String $changeAfter = this.getChangeAfter();
        result = result * 59 + ($changeAfter == null ? 43 : $changeAfter.hashCode());
        Date $createTime = this.getCreateTime();
        result = result * 59 + ($createTime == null ? 43 : ((Object)$createTime).hashCode());
        return result;
    }
}
