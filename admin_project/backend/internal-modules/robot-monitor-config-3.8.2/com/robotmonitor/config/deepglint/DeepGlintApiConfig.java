/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.config.deepglint;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="deepglint")
public class DeepGlintApiConfig {
    private String baseUrl;
    private String accessKey;
    private String secretKey;
    private String authKey;
    private String repoId;
    private Integer imageWidth;
    private Integer imageHeight;
    private String recognitionType;

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public Integer getImageWidth() {
        return this.imageWidth;
    }

    public Integer getImageHeight() {
        return this.imageHeight;
    }

    public String getRecognitionType() {
        return this.recognitionType;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setRecognitionType(String recognitionType) {
        this.recognitionType = recognitionType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DeepGlintApiConfig)) {
            return false;
        }
        DeepGlintApiConfig other = (DeepGlintApiConfig)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$imageWidth = this.getImageWidth();
        Integer other$imageWidth = other.getImageWidth();
        if (this$imageWidth == null ? other$imageWidth != null : !((Object)this$imageWidth).equals(other$imageWidth)) {
            return false;
        }
        Integer this$imageHeight = this.getImageHeight();
        Integer other$imageHeight = other.getImageHeight();
        if (this$imageHeight == null ? other$imageHeight != null : !((Object)this$imageHeight).equals(other$imageHeight)) {
            return false;
        }
        String this$baseUrl = this.getBaseUrl();
        String other$baseUrl = other.getBaseUrl();
        if (this$baseUrl == null ? other$baseUrl != null : !this$baseUrl.equals(other$baseUrl)) {
            return false;
        }
        String this$accessKey = this.getAccessKey();
        String other$accessKey = other.getAccessKey();
        if (this$accessKey == null ? other$accessKey != null : !this$accessKey.equals(other$accessKey)) {
            return false;
        }
        String this$secretKey = this.getSecretKey();
        String other$secretKey = other.getSecretKey();
        if (this$secretKey == null ? other$secretKey != null : !this$secretKey.equals(other$secretKey)) {
            return false;
        }
        String this$authKey = this.getAuthKey();
        String other$authKey = other.getAuthKey();
        if (this$authKey == null ? other$authKey != null : !this$authKey.equals(other$authKey)) {
            return false;
        }
        String this$repoId = this.getRepoId();
        String other$repoId = other.getRepoId();
        if (this$repoId == null ? other$repoId != null : !this$repoId.equals(other$repoId)) {
            return false;
        }
        String this$recognitionType = this.getRecognitionType();
        String other$recognitionType = other.getRecognitionType();
        return !(this$recognitionType == null ? other$recognitionType != null : !this$recognitionType.equals(other$recognitionType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof DeepGlintApiConfig;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $imageWidth = this.getImageWidth();
        result = result * 59 + ($imageWidth == null ? 43 : ((Object)$imageWidth).hashCode());
        Integer $imageHeight = this.getImageHeight();
        result = result * 59 + ($imageHeight == null ? 43 : ((Object)$imageHeight).hashCode());
        String $baseUrl = this.getBaseUrl();
        result = result * 59 + ($baseUrl == null ? 43 : $baseUrl.hashCode());
        String $accessKey = this.getAccessKey();
        result = result * 59 + ($accessKey == null ? 43 : $accessKey.hashCode());
        String $secretKey = this.getSecretKey();
        result = result * 59 + ($secretKey == null ? 43 : $secretKey.hashCode());
        String $authKey = this.getAuthKey();
        result = result * 59 + ($authKey == null ? 43 : $authKey.hashCode());
        String $repoId = this.getRepoId();
        result = result * 59 + ($repoId == null ? 43 : $repoId.hashCode());
        String $recognitionType = this.getRecognitionType();
        result = result * 59 + ($recognitionType == null ? 43 : $recognitionType.hashCode());
        return result;
    }

    public String toString() {
        return "DeepGlintApiConfig(baseUrl=" + this.getBaseUrl() + ", accessKey=" + this.getAccessKey() + ", secretKey=" + this.getSecretKey() + ", authKey=" + this.getAuthKey() + ", repoId=" + this.getRepoId() + ", imageWidth=" + this.getImageWidth() + ", imageHeight=" + this.getImageHeight() + ", recognitionType=" + this.getRecognitionType() + ")";
    }
}
