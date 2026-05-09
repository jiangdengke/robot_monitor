/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

import com.robotmonitor.flight.domain.UserCollectInfo;
import java.util.List;

public class CollectInParam {
    private String accountId;
    private String roomCode;
    private int forceIn;
    private String operater;
    private String serviceCode;
    private String flightno;
    private String orig;
    private String dest;
    private String userName;
    private String cabin;
    private String Seat;
    private String seq;
    private String cardService;
    private String cardNo;
    private String memLevel;
    private String starLevel;
    private String inType;
    private List<UserCollectInfo> list;
    private String reId;
    private String pId;

    public String getAccountId() {
        return this.accountId;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public int getForceIn() {
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

    public List<UserCollectInfo> getList() {
        return this.list;
    }

    public String getReId() {
        return this.reId;
    }

    public String getPId() {
        return this.pId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setForceIn(int forceIn) {
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

    public void setList(List<UserCollectInfo> list) {
        this.list = list;
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
        if (!(o instanceof CollectInParam)) {
            return false;
        }
        CollectInParam other = (CollectInParam)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getForceIn() != other.getForceIn()) {
            return false;
        }
        String this$accountId = this.getAccountId();
        String other$accountId = other.getAccountId();
        if (this$accountId == null ? other$accountId != null : !this$accountId.equals(other$accountId)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
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
        List<UserCollectInfo> this$list = this.getList();
        List<UserCollectInfo> other$list = other.getList();
        if (this$list == null ? other$list != null : !((Object)this$list).equals(other$list)) {
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
        return other instanceof CollectInParam;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getForceIn();
        String $accountId = this.getAccountId();
        result = result * 59 + ($accountId == null ? 43 : $accountId.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
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
        List<UserCollectInfo> $list = this.getList();
        result = result * 59 + ($list == null ? 43 : ((Object)$list).hashCode());
        String $reId = this.getReId();
        result = result * 59 + ($reId == null ? 43 : $reId.hashCode());
        String $pId = this.getPId();
        result = result * 59 + ($pId == null ? 43 : $pId.hashCode());
        return result;
    }

    public String toString() {
        return "CollectInParam(accountId=" + this.getAccountId() + ", roomCode=" + this.getRoomCode() + ", forceIn=" + this.getForceIn() + ", operater=" + this.getOperater() + ", serviceCode=" + this.getServiceCode() + ", flightno=" + this.getFlightno() + ", orig=" + this.getOrig() + ", dest=" + this.getDest() + ", userName=" + this.getUserName() + ", cabin=" + this.getCabin() + ", Seat=" + this.getSeat() + ", seq=" + this.getSeq() + ", cardService=" + this.getCardService() + ", cardNo=" + this.getCardNo() + ", memLevel=" + this.getMemLevel() + ", starLevel=" + this.getStarLevel() + ", inType=" + this.getInType() + ", list=" + this.getList() + ", reId=" + this.getReId() + ", pId=" + this.getPId() + ")";
    }
}
