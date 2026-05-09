/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

import com.robotmonitor.common.utils.StringUtils;

public class Admittance {
    private String robotId;
    private String admitType;
    private String code;
    private String base64Code;
    private String base64Code2;
    private String qrChannelType;
    private String language;

    public String toString() {
        return "Admittance{robotId='" + this.robotId + "', admitType='" + this.admitType + "', code='" + this.code + "', base64Code='" + StringUtils.abbreviate((String)this.base64Code, (int)50) + "', base64Code2='" + StringUtils.abbreviate((String)this.base64Code2, (int)50) + "', qrChannelType='" + this.qrChannelType + "', language='" + this.language + "'}";
    }

    public String getRobotId() {
        return this.robotId;
    }

    public String getAdmitType() {
        return this.admitType;
    }

    public String getCode() {
        return this.code;
    }

    public String getBase64Code() {
        return this.base64Code;
    }

    public String getBase64Code2() {
        return this.base64Code2;
    }

    public String getQrChannelType() {
        return this.qrChannelType;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public void setAdmitType(String admitType) {
        this.admitType = admitType;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBase64Code(String base64Code) {
        this.base64Code = base64Code;
    }

    public void setBase64Code2(String base64Code2) {
        this.base64Code2 = base64Code2;
    }

    public void setQrChannelType(String qrChannelType) {
        this.qrChannelType = qrChannelType;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Admittance)) {
            return false;
        }
        Admittance other = (Admittance)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$robotId = this.getRobotId();
        String other$robotId = other.getRobotId();
        if (this$robotId == null ? other$robotId != null : !this$robotId.equals(other$robotId)) {
            return false;
        }
        String this$admitType = this.getAdmitType();
        String other$admitType = other.getAdmitType();
        if (this$admitType == null ? other$admitType != null : !this$admitType.equals(other$admitType)) {
            return false;
        }
        String this$code = this.getCode();
        String other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) {
            return false;
        }
        String this$base64Code = this.getBase64Code();
        String other$base64Code = other.getBase64Code();
        if (this$base64Code == null ? other$base64Code != null : !this$base64Code.equals(other$base64Code)) {
            return false;
        }
        String this$base64Code2 = this.getBase64Code2();
        String other$base64Code2 = other.getBase64Code2();
        if (this$base64Code2 == null ? other$base64Code2 != null : !this$base64Code2.equals(other$base64Code2)) {
            return false;
        }
        String this$qrChannelType = this.getQrChannelType();
        String other$qrChannelType = other.getQrChannelType();
        if (this$qrChannelType == null ? other$qrChannelType != null : !this$qrChannelType.equals(other$qrChannelType)) {
            return false;
        }
        String this$language = this.getLanguage();
        String other$language = other.getLanguage();
        return !(this$language == null ? other$language != null : !this$language.equals(other$language));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Admittance;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $robotId = this.getRobotId();
        result = result * 59 + ($robotId == null ? 43 : $robotId.hashCode());
        String $admitType = this.getAdmitType();
        result = result * 59 + ($admitType == null ? 43 : $admitType.hashCode());
        String $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        String $base64Code = this.getBase64Code();
        result = result * 59 + ($base64Code == null ? 43 : $base64Code.hashCode());
        String $base64Code2 = this.getBase64Code2();
        result = result * 59 + ($base64Code2 == null ? 43 : $base64Code2.hashCode());
        String $qrChannelType = this.getQrChannelType();
        result = result * 59 + ($qrChannelType == null ? 43 : $qrChannelType.hashCode());
        String $language = this.getLanguage();
        result = result * 59 + ($language == null ? 43 : $language.hashCode());
        return result;
    }
}
