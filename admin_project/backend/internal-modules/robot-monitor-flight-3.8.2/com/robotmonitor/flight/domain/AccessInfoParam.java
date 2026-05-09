/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class AccessInfoParam {
    private String serviceCode;
    private String cabin;
    private String username;
    private String cardService = "";
    private String cardNo;
    private String memLevel;
    private String starLevel;
    private String roomCode;
    private String flightNo;
    private String fltDateStr;
    private String orig;
    private String seq;

    public String getServiceCode() {
        return this.serviceCode;
    }

    public String getCabin() {
        return this.cabin;
    }

    public String getUsername() {
        return this.username;
    }

    public String getCardService() {
        return this.cardService;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public String getMemLevel() {
        return this.memLevel;
    }

    public String getStarLevel() {
        return this.starLevel;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public String getFltDateStr() {
        return this.fltDateStr;
    }

    public String getOrig() {
        return this.orig;
    }

    public String getSeq() {
        return this.seq;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCardService(String cardService) {
        this.cardService = cardService;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setMemLevel(String memLevel) {
        this.memLevel = memLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setFltDateStr(String fltDateStr) {
        this.fltDateStr = fltDateStr;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AccessInfoParam)) {
            return false;
        }
        AccessInfoParam other = (AccessInfoParam)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$serviceCode = this.getServiceCode();
        String other$serviceCode = other.getServiceCode();
        if (this$serviceCode == null ? other$serviceCode != null : !this$serviceCode.equals(other$serviceCode)) {
            return false;
        }
        String this$cabin = this.getCabin();
        String other$cabin = other.getCabin();
        if (this$cabin == null ? other$cabin != null : !this$cabin.equals(other$cabin)) {
            return false;
        }
        String this$username = this.getUsername();
        String other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) {
            return false;
        }
        String this$cardService = this.getCardService();
        String other$cardService = other.getCardService();
        if (this$cardService == null ? other$cardService != null : !this$cardService.equals(other$cardService)) {
            return false;
        }
        String this$cardNo = this.getCardNo();
        String other$cardNo = other.getCardNo();
        if (this$cardNo == null ? other$cardNo != null : !this$cardNo.equals(other$cardNo)) {
            return false;
        }
        String this$memLevel = this.getMemLevel();
        String other$memLevel = other.getMemLevel();
        if (this$memLevel == null ? other$memLevel != null : !this$memLevel.equals(other$memLevel)) {
            return false;
        }
        String this$starLevel = this.getStarLevel();
        String other$starLevel = other.getStarLevel();
        if (this$starLevel == null ? other$starLevel != null : !this$starLevel.equals(other$starLevel)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$flightNo = this.getFlightNo();
        String other$flightNo = other.getFlightNo();
        if (this$flightNo == null ? other$flightNo != null : !this$flightNo.equals(other$flightNo)) {
            return false;
        }
        String this$fltDateStr = this.getFltDateStr();
        String other$fltDateStr = other.getFltDateStr();
        if (this$fltDateStr == null ? other$fltDateStr != null : !this$fltDateStr.equals(other$fltDateStr)) {
            return false;
        }
        String this$orig = this.getOrig();
        String other$orig = other.getOrig();
        if (this$orig == null ? other$orig != null : !this$orig.equals(other$orig)) {
            return false;
        }
        String this$seq = this.getSeq();
        String other$seq = other.getSeq();
        return !(this$seq == null ? other$seq != null : !this$seq.equals(other$seq));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AccessInfoParam;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $serviceCode = this.getServiceCode();
        result = result * 59 + ($serviceCode == null ? 43 : $serviceCode.hashCode());
        String $cabin = this.getCabin();
        result = result * 59 + ($cabin == null ? 43 : $cabin.hashCode());
        String $username = this.getUsername();
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        String $cardService = this.getCardService();
        result = result * 59 + ($cardService == null ? 43 : $cardService.hashCode());
        String $cardNo = this.getCardNo();
        result = result * 59 + ($cardNo == null ? 43 : $cardNo.hashCode());
        String $memLevel = this.getMemLevel();
        result = result * 59 + ($memLevel == null ? 43 : $memLevel.hashCode());
        String $starLevel = this.getStarLevel();
        result = result * 59 + ($starLevel == null ? 43 : $starLevel.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        String $fltDateStr = this.getFltDateStr();
        result = result * 59 + ($fltDateStr == null ? 43 : $fltDateStr.hashCode());
        String $orig = this.getOrig();
        result = result * 59 + ($orig == null ? 43 : $orig.hashCode());
        String $seq = this.getSeq();
        result = result * 59 + ($seq == null ? 43 : $seq.hashCode());
        return result;
    }

    public String toString() {
        return "AccessInfoParam(serviceCode=" + this.getServiceCode() + ", cabin=" + this.getCabin() + ", username=" + this.getUsername() + ", cardService=" + this.getCardService() + ", cardNo=" + this.getCardNo() + ", memLevel=" + this.getMemLevel() + ", starLevel=" + this.getStarLevel() + ", roomCode=" + this.getRoomCode() + ", flightNo=" + this.getFlightNo() + ", fltDateStr=" + this.getFltDateStr() + ", orig=" + this.getOrig() + ", seq=" + this.getSeq() + ")";
    }
}
