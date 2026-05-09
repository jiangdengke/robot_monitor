/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.domain;

import com.robotmonitor.ai.domain.PlaceImage;
import java.util.List;

public class PlaceInfo {
    private Long id;
    private String name;
    private String location;
    private String coordinate;
    private List<PlaceImage> pictures;
    private String audioUrl;
    private String introduction;

    public PlaceInfo() {
    }

    public PlaceInfo(Long id, String name, String location, String coordinate, List<PlaceImage> pictures, String audioUrl, String introduction) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.coordinate = coordinate;
        this.pictures = pictures;
        this.audioUrl = audioUrl;
        this.introduction = introduction;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public List<PlaceImage> getPictures() {
        return this.pictures;
    }

    public String getAudioUrl() {
        return this.audioUrl;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setPictures(List<PlaceImage> pictures) {
        this.pictures = pictures;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PlaceInfo)) {
            return false;
        }
        PlaceInfo other = (PlaceInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$location = this.getLocation();
        String other$location = other.getLocation();
        if (this$location == null ? other$location != null : !this$location.equals(other$location)) {
            return false;
        }
        String this$coordinate = this.getCoordinate();
        String other$coordinate = other.getCoordinate();
        if (this$coordinate == null ? other$coordinate != null : !this$coordinate.equals(other$coordinate)) {
            return false;
        }
        List<PlaceImage> this$pictures = this.getPictures();
        List<PlaceImage> other$pictures = other.getPictures();
        if (this$pictures == null ? other$pictures != null : !((Object)this$pictures).equals(other$pictures)) {
            return false;
        }
        String this$audioUrl = this.getAudioUrl();
        String other$audioUrl = other.getAudioUrl();
        if (this$audioUrl == null ? other$audioUrl != null : !this$audioUrl.equals(other$audioUrl)) {
            return false;
        }
        String this$introduction = this.getIntroduction();
        String other$introduction = other.getIntroduction();
        return !(this$introduction == null ? other$introduction != null : !this$introduction.equals(other$introduction));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PlaceInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $location = this.getLocation();
        result = result * 59 + ($location == null ? 43 : $location.hashCode());
        String $coordinate = this.getCoordinate();
        result = result * 59 + ($coordinate == null ? 43 : $coordinate.hashCode());
        List<PlaceImage> $pictures = this.getPictures();
        result = result * 59 + ($pictures == null ? 43 : ((Object)$pictures).hashCode());
        String $audioUrl = this.getAudioUrl();
        result = result * 59 + ($audioUrl == null ? 43 : $audioUrl.hashCode());
        String $introduction = this.getIntroduction();
        result = result * 59 + ($introduction == null ? 43 : $introduction.hashCode());
        return result;
    }

    public String toString() {
        return "PlaceInfo(id=" + this.getId() + ", name=" + this.getName() + ", location=" + this.getLocation() + ", coordinate=" + this.getCoordinate() + ", pictures=" + this.getPictures() + ", audioUrl=" + this.getAudioUrl() + ", introduction=" + this.getIntroduction() + ")";
    }
}
