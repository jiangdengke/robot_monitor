/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.config;

import com.robotmonitor.common.annotation.Excel;
import com.robotmonitor.common.core.domain.BaseEntity;

public class ConfigImg
extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name="\u56fe\u7247\u7c7b\u522b")
    private String imgType;
    @Excel(name="\u56fe\u7247")
    private String img;
    @Excel(name="\u56fe\u7247\u540d\u79f0")
    private String imgName;
    @Excel(name="\u5bbd")
    private Long width;
    @Excel(name="\u9ad8")
    private Long height;
    @Excel(name="\u70b9\u4f4d\u72b6\u6001 1-\u542f\u7528 0-\u505c\u7528")
    private Long enable;
    @Excel(name="\u8d35\u5bbe\u5ba4CODE")
    private String roomCode;
    private String deptName;
    @Excel(name="\u662f\u5426\u5220\u9664")
    private String isDelete;
    private String imgTypeStr;

    public Long getId() {
        return this.id;
    }

    public String getImgType() {
        return this.imgType;
    }

    public String getImg() {
        return this.img;
    }

    public String getImgName() {
        return this.imgName;
    }

    public Long getWidth() {
        return this.width;
    }

    public Long getHeight() {
        return this.height;
    }

    public Long getEnable() {
        return this.enable;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public String getIsDelete() {
        return this.isDelete;
    }

    public String getImgTypeStr() {
        return this.imgTypeStr;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public void setEnable(Long enable) {
        this.enable = enable;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public void setImgTypeStr(String imgTypeStr) {
        this.imgTypeStr = imgTypeStr;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfigImg)) {
            return false;
        }
        ConfigImg other = (ConfigImg)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$width = this.getWidth();
        Long other$width = other.getWidth();
        if (this$width == null ? other$width != null : !((Object)this$width).equals(other$width)) {
            return false;
        }
        Long this$height = this.getHeight();
        Long other$height = other.getHeight();
        if (this$height == null ? other$height != null : !((Object)this$height).equals(other$height)) {
            return false;
        }
        Long this$enable = this.getEnable();
        Long other$enable = other.getEnable();
        if (this$enable == null ? other$enable != null : !((Object)this$enable).equals(other$enable)) {
            return false;
        }
        String this$imgType = this.getImgType();
        String other$imgType = other.getImgType();
        if (this$imgType == null ? other$imgType != null : !this$imgType.equals(other$imgType)) {
            return false;
        }
        String this$img = this.getImg();
        String other$img = other.getImg();
        if (this$img == null ? other$img != null : !this$img.equals(other$img)) {
            return false;
        }
        String this$imgName = this.getImgName();
        String other$imgName = other.getImgName();
        if (this$imgName == null ? other$imgName != null : !this$imgName.equals(other$imgName)) {
            return false;
        }
        String this$roomCode = this.getRoomCode();
        String other$roomCode = other.getRoomCode();
        if (this$roomCode == null ? other$roomCode != null : !this$roomCode.equals(other$roomCode)) {
            return false;
        }
        String this$deptName = this.getDeptName();
        String other$deptName = other.getDeptName();
        if (this$deptName == null ? other$deptName != null : !this$deptName.equals(other$deptName)) {
            return false;
        }
        String this$isDelete = this.getIsDelete();
        String other$isDelete = other.getIsDelete();
        if (this$isDelete == null ? other$isDelete != null : !this$isDelete.equals(other$isDelete)) {
            return false;
        }
        String this$imgTypeStr = this.getImgTypeStr();
        String other$imgTypeStr = other.getImgTypeStr();
        return !(this$imgTypeStr == null ? other$imgTypeStr != null : !this$imgTypeStr.equals(other$imgTypeStr));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConfigImg;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $width = this.getWidth();
        result = result * 59 + ($width == null ? 43 : ((Object)$width).hashCode());
        Long $height = this.getHeight();
        result = result * 59 + ($height == null ? 43 : ((Object)$height).hashCode());
        Long $enable = this.getEnable();
        result = result * 59 + ($enable == null ? 43 : ((Object)$enable).hashCode());
        String $imgType = this.getImgType();
        result = result * 59 + ($imgType == null ? 43 : $imgType.hashCode());
        String $img = this.getImg();
        result = result * 59 + ($img == null ? 43 : $img.hashCode());
        String $imgName = this.getImgName();
        result = result * 59 + ($imgName == null ? 43 : $imgName.hashCode());
        String $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        String $deptName = this.getDeptName();
        result = result * 59 + ($deptName == null ? 43 : $deptName.hashCode());
        String $isDelete = this.getIsDelete();
        result = result * 59 + ($isDelete == null ? 43 : $isDelete.hashCode());
        String $imgTypeStr = this.getImgTypeStr();
        result = result * 59 + ($imgTypeStr == null ? 43 : $imgTypeStr.hashCode());
        return result;
    }

    public String toString() {
        return "ConfigImg(id=" + this.getId() + ", imgType=" + this.getImgType() + ", img=" + this.getImg() + ", imgName=" + this.getImgName() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", enable=" + this.getEnable() + ", roomCode=" + this.getRoomCode() + ", deptName=" + this.getDeptName() + ", isDelete=" + this.getIsDelete() + ", imgTypeStr=" + this.getImgTypeStr() + ")";
    }
}
