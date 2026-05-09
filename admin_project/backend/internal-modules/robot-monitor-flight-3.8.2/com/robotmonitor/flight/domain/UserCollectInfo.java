/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class UserCollectInfo {
    private String roomCode;
    private String forceIn;
    private String operater;
    private String serviceCode;
    private String flightno;
    private String orig;
    private String dest;
    private String userName;
    private String cabin;
    private String Seat;
    private String seq;
    private String inType;
    private String reId;

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getForceIn() {
        return this.forceIn;
    }

    public String getOperater() {
        return this.operater;
    }

    public String getServiceCode() {
        return this.serviceCode;
    }

    public String getFlightno() {
        return this.flightno;
    }

    public String getOrig() {
        return this.orig;
    }

    public String getDest() {
        return this.dest;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getCabin() {
        return this.cabin;
    }

    public String getSeat() {
        return this.Seat;
    }

    public String getSeq() {
        return this.seq;
    }

    public String getInType() {
        return this.inType;
    }

    public String getReId() {
        return this.reId;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setForceIn(String forceIn) {
        this.forceIn = forceIn;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setFlightno(String flightno) {
        this.flightno = flightno;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public void setSeat(String Seat) {
        this.Seat = Seat;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public void setReId(String reId) {
        this.reId = reId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UserCollectInfo)) {
            return false;
        }
        UserCollectInfo other = (UserCollectInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$forceIn = this.getForceIn();
        String other$forceIn = other.getForceIn();
        if (this$forceIn == null ? other$forceIn != null : !this$forceIn.equals(other$forceIn)) {
            return false;
        }
        String this$operater = this.getOperater();
        String other$operater = other.getOperater();
        if (this$operater == null ? other$operater != null : !this$operater.equals(other$operater)) {
            return false;
        }
        String this$serviceCode = this.getServiceCode();
        String other$serviceCode = other.getServiceCode();
        if (this$serviceCode == null ? other$serviceCode != null : !this$serviceCode.equals(other$serviceCode)) {
            return false;
        }
        String this$flightno = this.getFlightno();
        String other$flightno = other.getFlightno();
        if (this$flightno == null ? other$flightno != null : !this$flightno.equals(other$flightno)) {
            return false;
        }
        String this$orig = this.getOrig();
        String other$orig = other.getOrig();
        if (this$orig == null ? other$orig != null : !this$orig.equals(other$orig)) {
            return false;
        }
        String this$dest = this.getDest();
        String other$dest = other.getDest();
        if (this$dest == null ? other$dest != null : !this$dest.equals(other$dest)) {
            return false;
        }
        String this$userName = this.getUserName();
        String other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) {
            return false;
        }
        String this$cabin = this.getCabin();
        String other$cabin = other.getCabin();
        if (this$cabin == null ? other$cabin != null : !this$cabin.equals(other$cabin)) {
            return false;
        }
        String this$Seat = this.getSeat();
        String other$Seat = other.getSeat();
        if (this$Seat == null ? other$Seat != null : !this$Seat.equals(other$Seat)) {
            return false;
        }
        String this$seq = this.getSeq();
        String other$seq = other.getSeq();
        if (this$seq == null ? other$seq != null : !this$seq.equals(other$seq)) {
            return false;
        }
        String this$inType = this.getInType();
        String other$inType = other.getInType();
        if (this$inType == null ? other$inType != null : !this$inType.equals(other$inType)) {
            return false;
        }
        String this$reId = this.getReId();
        String other$reId = other.getReId();
        return !(this$reId == null ? other$reId != null : !this$reId.equals(other$reId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof UserCollectInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $forceIn = this.getForceIn();
        result = result * 59 + ($forceIn == null ? 43 : $forceIn.hashCode());
        String $operater = this.getOperater();
        result = result * 59 + ($operater == null ? 43 : $operater.hashCode());
        String $serviceCode = this.getServiceCode();
        result = result * 59 + ($serviceCode == null ? 43 : $serviceCode.hashCode());
        String $flightno = this.getFlightno();
        result = result * 59 + ($flightno == null ? 43 : $flightno.hashCode());
        String $orig = this.getOrig();
        result = result * 59 + ($orig == null ? 43 : $orig.hashCode());
        String $dest = this.getDest();
        result = result * 59 + ($dest == null ? 43 : $dest.hashCode());
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        String $cabin = this.getCabin();
        result = result * 59 + ($cabin == null ? 43 : $cabin.hashCode());
        String $Seat = this.getSeat();
        result = result * 59 + ($Seat == null ? 43 : $Seat.hashCode());
        String $seq = this.getSeq();
        result = result * 59 + ($seq == null ? 43 : $seq.hashCode());
        String $inType = this.getInType();
        result = result * 59 + ($inType == null ? 43 : $inType.hashCode());
        String $reId = this.getReId();
        result = result * 59 + ($reId == null ? 43 : $reId.hashCode());
        return result;
    }

    public String toString() {
        return "UserCollectInfo(roomCode=" + this.getRoomCode() + ", forceIn=" + this.getForceIn() + ", operater=" + this.getOperater() + ", serviceCode=" + this.getServiceCode() + ", flightno=" + this.getFlightno() + ", orig=" + this.getOrig() + ", dest=" + this.getDest() + ", userName=" + this.getUserName() + ", cabin=" + this.getCabin() + ", Seat=" + this.getSeat() + ", seq=" + this.getSeq() + ", inType=" + this.getInType() + ", reId=" + this.getReId() + ")";
    }
}
