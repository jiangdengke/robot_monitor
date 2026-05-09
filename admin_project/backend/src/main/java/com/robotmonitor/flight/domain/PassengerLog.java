/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 */
package com.robotmonitor.flight.domain;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;

public class PassengerLog
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="${comment}", readConverterExp="$column.readConverterExp()")
    private String pId;
    @Excel(name="\u51c6\u5165\u6570\u636e")
    private String collectData;
    @Excel(name="1\u4e3a\u8eab\u4efd\u8bc1\u9a8c\u8bc1,2\u626b\u7801\u51c6\u5165 3\u4eba\u8138\u8bc6\u522b,9,\u51c6\u51fa")
    private String getType;
    @Excel(name="\u673a\u5668\u4ebaID")
    private String robotId;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    @Excel(name="1:\u6210\u529f 0:\u5931\u8d25")
    private String isSuccess;
    @Excel(name="\u8fd4\u56de\u7ed3\u679c")
    private String backInfo;

    public Long getId() {
        return this.id;
    }

    public String getPId() {
        return this.pId;
    }

    public String getCollectData() {
        return this.collectData;
    }

    public String getGetType() {
        return this.getType;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getIsSuccess() {
        return this.isSuccess;
    }

    public String getBackInfo() {
        return this.backInfo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public void setCollectData(String collectData) {
        this.collectData = collectData;
    }

    public void setGetType(String getType) {
        this.getType = getType;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void setBackInfo(String backInfo) {
        this.backInfo = backInfo;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PassengerLog)) {
            return false;
        }
        PassengerLog other = (PassengerLog)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$pId = this.getPId();
        String other$pId = other.getPId();
        if (this$pId == null ? other$pId != null : !this$pId.equals(other$pId)) {
            return false;
        }
        String this$collectData = this.getCollectData();
        String other$collectData = other.getCollectData();
        if (this$collectData == null ? other$collectData != null : !this$collectData.equals(other$collectData)) {
            return false;
        }
        String this$getType = this.getGetType();
        String other$getType = other.getGetType();
        if (this$getType == null ? other$getType != null : !this$getType.equals(other$getType)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$isSuccess = this.getIsSuccess();
        String other$isSuccess = other.getIsSuccess();
        if (this$isSuccess == null ? other$isSuccess != null : !this$isSuccess.equals(other$isSuccess)) {
            return false;
        }
        String this$backInfo = this.getBackInfo();
        String other$backInfo = other.getBackInfo();
        return !(this$backInfo == null ? other$backInfo != null : !this$backInfo.equals(other$backInfo));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PassengerLog;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $pId = this.getPId();
        result = result * 59 + ($pId == null ? 43 : $pId.hashCode());
        String $collectData = this.getCollectData();
        result = result * 59 + ($collectData == null ? 43 : $collectData.hashCode());
        String $getType = this.getGetType();
        result = result * 59 + ($getType == null ? 43 : $getType.hashCode());
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $isSuccess = this.getIsSuccess();
        result = result * 59 + ($isSuccess == null ? 43 : $isSuccess.hashCode());
        String $backInfo = this.getBackInfo();
        result = result * 59 + ($backInfo == null ? 43 : $backInfo.hashCode());
        return result;
    }

    public String toString() {
        return "PassengerLog(id=" + this.getId() + ", pId=" + this.getPId() + ", collectData=" + this.getCollectData() + ", getType=" + this.getGetType() + ", robotId=" + this.getRobotId() + ", roomCode=" + this.getRoomCode() + ", isSuccess=" + this.getIsSuccess() + ", backInfo=" + this.getBackInfo() + ")";
    }
}
