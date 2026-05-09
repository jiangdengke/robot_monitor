/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

import com.robotmonitor.flight.domain.BarCodeRespons;
import com.robotmonitor.flight.domain.CollectInParam2;
import com.robotmonitor.flight.domain.GetInTmp;
import com.robotmonitor.flight.domain.ResData;

public class CollectInResponse2 {
    private String code;
    private String message;
    private ResData data;
    private BarCodeRespons passengerResponse;
    private String collectId;
    CollectInParam2 param;
    GetInTmp tmp;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public ResData getData() {
        return this.data;
    }

    public BarCodeRespons getPassengerResponse() {
        return this.passengerResponse;
    }

    public String getCollectId() {
        return this.collectId;
    }

    public CollectInParam2 getParam() {
        return this.param;
    }

    public GetInTmp getTmp() {
        return this.tmp;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(ResData data) {
        this.data = data;
    }

    public void setPassengerResponse(BarCodeRespons passengerResponse) {
        this.passengerResponse = passengerResponse;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public void setParam(CollectInParam2 param) {
        this.param = param;
    }

    public void setTmp(GetInTmp tmp) {
        this.tmp = tmp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CollectInResponse2)) {
            return false;
        }
        CollectInResponse2 other = (CollectInResponse2)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$code = this.getCode();
        String other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) {
            return false;
        }
        ResData this$data = this.getData();
        ResData other$data = other.getData();
        if (this$data == null ? other$data != null : !((Object)this$data).equals(other$data)) {
            return false;
        }
        BarCodeRespons this$passengerResponse = this.getPassengerResponse();
        BarCodeRespons other$passengerResponse = other.getPassengerResponse();
        if (this$passengerResponse == null ? other$passengerResponse != null : !((Object)this$passengerResponse).equals(other$passengerResponse)) {
            return false;
        }
        String this$collectId = this.getCollectId();
        String other$collectId = other.getCollectId();
        if (this$collectId == null ? other$collectId != null : !this$collectId.equals(other$collectId)) {
            return false;
        }
        CollectInParam2 this$param = this.getParam();
        CollectInParam2 other$param = other.getParam();
        if (this$param == null ? other$param != null : !((Object)this$param).equals(other$param)) {
            return false;
        }
        GetInTmp this$tmp = this.getTmp();
        GetInTmp other$tmp = other.getTmp();
        return !(this$tmp == null ? other$tmp != null : !((Object)((Object)this$tmp)).equals((Object)other$tmp));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CollectInResponse2;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        ResData $data = this.getData();
        result = result * 59 + ($data == null ? 43 : ((Object)$data).hashCode());
        BarCodeRespons $passengerResponse = this.getPassengerResponse();
        result = result * 59 + ($passengerResponse == null ? 43 : ((Object)$passengerResponse).hashCode());
        String $collectId = this.getCollectId();
        result = result * 59 + ($collectId == null ? 43 : $collectId.hashCode());
        CollectInParam2 $param = this.getParam();
        result = result * 59 + ($param == null ? 43 : ((Object)$param).hashCode());
        GetInTmp $tmp = this.getTmp();
        result = result * 59 + ($tmp == null ? 43 : ((Object)((Object)$tmp)).hashCode());
        return result;
    }

    public String toString() {
        return "CollectInResponse2(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ", passengerResponse=" + this.getPassengerResponse() + ", collectId=" + this.getCollectId() + ", param=" + this.getParam() + ", tmp=" + this.getTmp() + ")";
    }
}
