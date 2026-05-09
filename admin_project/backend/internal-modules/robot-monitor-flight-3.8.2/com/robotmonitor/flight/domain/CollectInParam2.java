/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class CollectInParam2 {
    private String collectType;
    private String identityType;
    private String dataResource;
    private String collectData;
    private String ip;
    private String hostCollectId;
    private String robotId;
    private String reId;
    private String pId;

    public String getCollectType() {
        return this.collectType;
    }

    public String getIdentityType() {
        return this.identityType;
    }

    public String getDataResource() {
        return this.dataResource;
    }

    public String getCollectData() {
        return this.collectData;
    }

    public String getIp() {
        return this.ip;
    }

    public String getHostCollectId() {
        return this.hostCollectId;
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getReId() {
        return this.reId;
    }

    public String getPId() {
        return this.pId;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public void setDataResource(String dataResource) {
        this.dataResource = dataResource;
    }

    public void setCollectData(String collectData) {
        this.collectData = collectData;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setHostCollectId(String hostCollectId) {
        this.hostCollectId = hostCollectId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setReId(String reId) {
        this.reId = reId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CollectInParam2)) {
            return false;
        }
        CollectInParam2 other = (CollectInParam2)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$collectType = this.getCollectType();
        String other$collectType = other.getCollectType();
        if (this$collectType == null ? other$collectType != null : !this$collectType.equals(other$collectType)) {
            return false;
        }
        String this$identityType = this.getIdentityType();
        String other$identityType = other.getIdentityType();
        if (this$identityType == null ? other$identityType != null : !this$identityType.equals(other$identityType)) {
            return false;
        }
        String this$dataResource = this.getDataResource();
        String other$dataResource = other.getDataResource();
        if (this$dataResource == null ? other$dataResource != null : !this$dataResource.equals(other$dataResource)) {
            return false;
        }
        String this$collectData = this.getCollectData();
        String other$collectData = other.getCollectData();
        if (this$collectData == null ? other$collectData != null : !this$collectData.equals(other$collectData)) {
            return false;
        }
        String this$ip = this.getIp();
        String other$ip = other.getIp();
        if (this$ip == null ? other$ip != null : !this$ip.equals(other$ip)) {
            return false;
        }
        String this$hostCollectId = this.getHostCollectId();
        String other$hostCollectId = other.getHostCollectId();
        if (this$hostCollectId == null ? other$hostCollectId != null : !this$hostCollectId.equals(other$hostCollectId)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$reId = this.getReId();
        String other$reId = other.getReId();
        if (this$reId == null ? other$reId != null : !this$reId.equals(other$reId)) {
            return false;
        }
        String this$pId = this.getPId();
        String other$pId = other.getPId();
        return !(this$pId == null ? other$pId != null : !this$pId.equals(other$pId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CollectInParam2;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $collectType = this.getCollectType();
        result = result * 59 + ($collectType == null ? 43 : $collectType.hashCode());
        String $identityType = this.getIdentityType();
        result = result * 59 + ($identityType == null ? 43 : $identityType.hashCode());
        String $dataResource = this.getDataResource();
        result = result * 59 + ($dataResource == null ? 43 : $dataResource.hashCode());
        String $collectData = this.getCollectData();
        result = result * 59 + ($collectData == null ? 43 : $collectData.hashCode());
        String $ip = this.getIp();
        result = result * 59 + ($ip == null ? 43 : $ip.hashCode());
        String $hostCollectId = this.getHostCollectId();
        result = result * 59 + ($hostCollectId == null ? 43 : $hostCollectId.hashCode());
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $reId = this.getReId();
        result = result * 59 + ($reId == null ? 43 : $reId.hashCode());
        String $pId = this.getPId();
        result = result * 59 + ($pId == null ? 43 : $pId.hashCode());
        return result;
    }

    public String toString() {
        return "CollectInParam2(collectType=" + this.getCollectType() + ", identityType=" + this.getIdentityType() + ", dataResource=" + this.getDataResource() + ", collectData=" + this.getCollectData() + ", ip=" + this.getIp() + ", hostCollectId=" + this.getHostCollectId() + ", robotId=" + this.getRobotId() + ", reId=" + this.getReId() + ", pId=" + this.getPId() + ")";
    }
}
