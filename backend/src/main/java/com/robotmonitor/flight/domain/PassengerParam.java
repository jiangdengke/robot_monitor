/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class PassengerParam {
    private String userName;
    private String roomCode;
    private String flightNo;
    private String flightDate;
    private String cardService;
    private String cardNo;
    private String memLevel;
    private String starLevel;
    private String inType;
    private String status;
    private String orderType;

    public String getUserName() {
        return this.userName;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public String getFlightDate() {
        return this.flightDate;
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

    public String getInType() {
        return this.inType;
    }

    public String getStatus() {
        return this.status;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
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

    public void setInType(String inType) {
        this.inType = inType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PassengerParam)) {
            return false;
        }
        PassengerParam other = (PassengerParam)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$userName = this.getUserName();
        String other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) {
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
        String this$flightDate = this.getFlightDate();
        String other$flightDate = other.getFlightDate();
        if (this$flightDate == null ? other$flightDate != null : !this$flightDate.equals(other$flightDate)) {
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
        String this$inType = this.getInType();
        String other$inType = other.getInType();
        if (this$inType == null ? other$inType != null : !this$inType.equals(other$inType)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        String this$orderType = this.getOrderType();
        String other$orderType = other.getOrderType();
        return !(this$orderType == null ? other$orderType != null : !this$orderType.equals(other$orderType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PassengerParam;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $flightNo = this.getFlightNo();
        result = result * 59 + ($flightNo == null ? 43 : $flightNo.hashCode());
        String $flightDate = this.getFlightDate();
        result = result * 59 + ($flightDate == null ? 43 : $flightDate.hashCode());
        String $cardService = this.getCardService();
        result = result * 59 + ($cardService == null ? 43 : $cardService.hashCode());
        String $cardNo = this.getCardNo();
        result = result * 59 + ($cardNo == null ? 43 : $cardNo.hashCode());
        String $memLevel = this.getMemLevel();
        result = result * 59 + ($memLevel == null ? 43 : $memLevel.hashCode());
        String $starLevel = this.getStarLevel();
        result = result * 59 + ($starLevel == null ? 43 : $starLevel.hashCode());
        String $inType = this.getInType();
        result = result * 59 + ($inType == null ? 43 : $inType.hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $orderType = this.getOrderType();
        result = result * 59 + ($orderType == null ? 43 : $orderType.hashCode());
        return result;
    }

    public String toString() {
        return "PassengerParam(userName=" + this.getUserName() + ", roomCode=" + this.getRoomCode() + ", flightNo=" + this.getFlightNo() + ", flightDate=" + this.getFlightDate() + ", cardService=" + this.getCardService() + ", cardNo=" + this.getCardNo() + ", memLevel=" + this.getMemLevel() + ", starLevel=" + this.getStarLevel() + ", inType=" + this.getInType() + ", status=" + this.getStatus() + ", orderType=" + this.getOrderType() + ")";
    }
}
