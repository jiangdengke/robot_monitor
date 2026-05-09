/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.config.ConfigImg
 *  org.springframework.data.annotation.Id
 */
package com.robotmonitor.config.dto;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.config.ConfigImg;
import java.util.List;
import org.springframework.data.annotation.Id;

public class ConfigAreaDto {
    @Id
    private Long id;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    @Excel(name="\u56fe\u7247ID")
    private String imgIds;
    @Excel(name="\u662f\u5426\u5c55\u793a")
    private String isShow;
    @Excel(name="\u662f\u5426\u652f\u6301\u5f15\u5bfc")
    private String isGuide;
    @Excel(name="\u6700\u5927\u5bb9\u91cf")
    private Long maxCapacity;
    @Excel(name="\u5750\u6807")
    private String coordinate;
    private Long curCapacity = Long.getLong("0");
    private double perCapacity = 0.0;
    private List<ConfigImg> imgList;
    @Excel(name="\u8bed\u97f3\u7c7b\u522b")
    private String languageType;
    @Excel(name="")
    private String areaName;
    @Excel(name="\u6807\u7b7e")
    private String label;
    @Excel(name="\u97f3\u9891")
    private String audio;
    @Excel(name="\u5230\u8fbe\u6587\u5b57")
    private String arrText;
    @Excel(name="\u5230\u8fbe\u8bed\u97f3")
    private String arrAudio;
    private String remark;
    private List<String> imgUrlList;

    public Long getId() {
        return this.id;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getImgIds() {
        return this.imgIds;
    }

    public String getIsShow() {
        return this.isShow;
    }

    public String getIsGuide() {
        return this.isGuide;
    }

    public Long getMaxCapacity() {
        return this.maxCapacity;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public Long getCurCapacity() {
        return this.curCapacity;
    }

    public double getPerCapacity() {
        return this.perCapacity;
    }

    public List<ConfigImg> getImgList() {
        return this.imgList;
    }

    public String getLanguageType() {
        return this.languageType;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public String getLabel() {
        return this.label;
    }

    public String getAudio() {
        return this.audio;
    }

    public String getArrText() {
        return this.arrText;
    }

    public String getArrAudio() {
        return this.arrAudio;
    }

    public String getRemark() {
        return this.remark;
    }

    public List<String> getImgUrlList() {
        return this.imgUrlList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public void setIsGuide(String isGuide) {
        this.isGuide = isGuide;
    }

    public void setMaxCapacity(Long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setCurCapacity(Long curCapacity) {
        this.curCapacity = curCapacity;
    }

    public void setPerCapacity(double perCapacity) {
        this.perCapacity = perCapacity;
    }

    public void setImgList(List<ConfigImg> imgList) {
        this.imgList = imgList;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public void setArrText(String arrText) {
        this.arrText = arrText;
    }

    public void setArrAudio(String arrAudio) {
        this.arrAudio = arrAudio;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfigAreaDto)) {
            return false;
        }
        ConfigAreaDto other = (ConfigAreaDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Double.compare(this.getPerCapacity(), other.getPerCapacity()) != 0) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$maxCapacity = this.getMaxCapacity();
        Long other$maxCapacity = other.getMaxCapacity();
        if (this$maxCapacity == null ? other$maxCapacity != null : !((Object)this$maxCapacity).equals(other$maxCapacity)) {
            return false;
        }
        Long this$curCapacity = this.getCurCapacity();
        Long other$curCapacity = other.getCurCapacity();
        if (this$curCapacity == null ? other$curCapacity != null : !((Object)this$curCapacity).equals(other$curCapacity)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$imgIds = this.getImgIds();
        String other$imgIds = other.getImgIds();
        if (this$imgIds == null ? other$imgIds != null : !this$imgIds.equals(other$imgIds)) {
            return false;
        }
        String this$isShow = this.getIsShow();
        String other$isShow = other.getIsShow();
        if (this$isShow == null ? other$isShow != null : !this$isShow.equals(other$isShow)) {
            return false;
        }
        String this$isGuide = this.getIsGuide();
        String other$isGuide = other.getIsGuide();
        if (this$isGuide == null ? other$isGuide != null : !this$isGuide.equals(other$isGuide)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
            return false;
        }
        List<ConfigImg> this$imgList = this.getImgList();
        List<ConfigImg> other$imgList = other.getImgList();
        if (this$imgList == null ? other$imgList != null : !((Object)this$imgList).equals(other$imgList)) {
            return false;
        }
        String this$languageType = this.getLanguageType();
        String other$languageType = other.getLanguageType();
        if (this$languageType == null ? other$languageType != null : !this$languageType.equals(other$languageType)) {
            return false;
        }
        String this$areaName = this.getAreaName();
        String other$areaName = other.getAreaName();
        if (this$areaName == null ? other$areaName != null : !this$areaName.equals(other$areaName)) {
            return false;
        }
        String this$label = this.getLabel();
        String other$label = other.getLabel();
        if (this$label == null ? other$label != null : !this$label.equals(other$label)) {
            return false;
        }
        String this$audio = this.getAudio();
        String other$audio = other.getAudio();
        if (this$audio == null ? other$audio != null : !this$audio.equals(other$audio)) {
            return false;
        }
        String this$arrText = this.getArrText();
        String other$arrText = other.getArrText();
        if (this$arrText == null ? other$arrText != null : !this$arrText.equals(other$arrText)) {
            return false;
        }
        String this$arrAudio = this.getArrAudio();
        String other$arrAudio = other.getArrAudio();
        if (this$arrAudio == null ? other$arrAudio != null : !this$arrAudio.equals(other$arrAudio)) {
            return false;
        }
        String this$remark = this.getRemark();
        String other$remark = other.getRemark();
        if (this$remark == null ? other$remark != null : !this$remark.equals(other$remark)) {
            return false;
        }
        List<String> this$imgUrlList = this.getImgUrlList();
        List<String> other$imgUrlList = other.getImgUrlList();
        return !(this$imgUrlList == null ? other$imgUrlList != null : !((Object)this$imgUrlList).equals(other$imgUrlList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConfigAreaDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $perCapacity = Double.doubleToLongBits(this.getPerCapacity());
        result = result * 59 + (int)($perCapacity >>> 32 ^ $perCapacity);
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $maxCapacity = this.getMaxCapacity();
        result = result * 59 + ($maxCapacity == null ? 43 : ((Object)$maxCapacity).hashCode());
        Long $curCapacity = this.getCurCapacity();
        result = result * 59 + ($curCapacity == null ? 43 : ((Object)$curCapacity).hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $imgIds = this.getImgIds();
        result = result * 59 + ($imgIds == null ? 43 : $imgIds.hashCode());
        String $isShow = this.getIsShow();
        result = result * 59 + ($isShow == null ? 43 : $isShow.hashCode());
        String $isGuide = this.getIsGuide();
        result = result * 59 + ($isGuide == null ? 43 : $isGuide.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        List<ConfigImg> $imgList = this.getImgList();
        result = result * 59 + ($imgList == null ? 43 : ((Object)$imgList).hashCode());
        String $languageType = this.getLanguageType();
        result = result * 59 + ($languageType == null ? 43 : $languageType.hashCode());
        String $areaName = this.getAreaName();
        result = result * 59 + ($areaName == null ? 43 : $areaName.hashCode());
        String $label = this.getLabel();
        result = result * 59 + ($label == null ? 43 : $label.hashCode());
        String $audio = this.getAudio();
        result = result * 59 + ($audio == null ? 43 : $audio.hashCode());
        String $arrText = this.getArrText();
        result = result * 59 + ($arrText == null ? 43 : $arrText.hashCode());
        String $arrAudio = this.getArrAudio();
        result = result * 59 + ($arrAudio == null ? 43 : $arrAudio.hashCode());
        String $remark = this.getRemark();
        result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
        List<String> $imgUrlList = this.getImgUrlList();
        result = result * 59 + ($imgUrlList == null ? 43 : ((Object)$imgUrlList).hashCode());
        return result;
    }

    public String toString() {
        return "ConfigAreaDto(id=" + this.getId() + ", roomCode=" + this.getRoomCode() + ", imgIds=" + this.getImgIds() + ", isShow=" + this.getIsShow() + ", isGuide=" + this.getIsGuide() + ", maxCapacity=" + this.getMaxCapacity() + ", coordinate=" + this.getCoordinate() + ", curCapacity=" + this.getCurCapacity() + ", perCapacity=" + this.getPerCapacity() + ", imgList=" + this.getImgList() + ", languageType=" + this.getLanguageType() + ", areaName=" + this.getAreaName() + ", label=" + this.getLabel() + ", audio=" + this.getAudio() + ", arrText=" + this.getArrText() + ", arrAudio=" + this.getArrAudio() + ", remark=" + this.getRemark() + ", imgUrlList=" + this.getImgUrlList() + ")";
    }
}
