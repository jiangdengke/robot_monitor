/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Excel
 *  com.robotmonitor.common.core.domain.BaseEntity
 */
package com.robotmonitor.config.domain;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;

public class ConfigAreaDetail
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="")
    private Long areaId;
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

    public Long getId() {
        return this.id;
    }

    public Long getAreaId() {
        return this.areaId;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfigAreaDetail)) {
            return false;
        }
        ConfigAreaDetail other = (ConfigAreaDetail)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$areaId = this.getAreaId();
        Long other$areaId = other.getAreaId();
        if (this$areaId == null ? other$areaId != null : !((Object)this$areaId).equals(other$areaId)) {
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
        return !(this$arrAudio == null ? other$arrAudio != null : !this$arrAudio.equals(other$arrAudio));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConfigAreaDetail;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $areaId = this.getAreaId();
        result = result * 59 + ($areaId == null ? 43 : ((Object)$areaId).hashCode());
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
        return result;
    }

    public String toString() {
        return "ConfigAreaDetail(id=" + this.getId() + ", areaId=" + this.getAreaId() + ", languageType=" + this.getLanguageType() + ", areaName=" + this.getAreaName() + ", label=" + this.getLabel() + ", audio=" + this.getAudio() + ", arrText=" + this.getArrText() + ", arrAudio=" + this.getArrAudio() + ")";
    }
}
