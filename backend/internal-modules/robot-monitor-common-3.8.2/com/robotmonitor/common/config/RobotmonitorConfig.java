/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.stereotype.Component
 */
package com.robotmonitor.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="robotmonitor")
public class RobotmonitorConfig {
    private String name;
    private String version;
    private String copyrightYear;
    private boolean demoEnabled;
    private static String profile;
    private static boolean addressEnabled;
    private static String captchaType;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return this.copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled() {
        return this.demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled) {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        RobotmonitorConfig.profile = profile;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        RobotmonitorConfig.addressEnabled = addressEnabled;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        RobotmonitorConfig.captchaType = captchaType;
    }

    public static String getImportPath() {
        return RobotmonitorConfig.getProfile() + "/import";
    }

    public static String getAvatarPath() {
        return RobotmonitorConfig.getProfile() + "/avatar";
    }

    public static String getDownloadPath() {
        return RobotmonitorConfig.getProfile() + "/download/";
    }

    public static String getUploadPath() {
        return RobotmonitorConfig.getProfile() + "/upload";
    }
}
