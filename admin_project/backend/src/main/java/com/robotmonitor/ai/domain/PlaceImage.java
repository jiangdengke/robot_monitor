/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.ai.domain;

public class PlaceImage {
    private String imageName;
    private String imageData;

    public PlaceImage() {
    }

    public PlaceImage(String imageName, String imageData) {
        this.imageName = imageName;
        this.imageData = imageData;
    }

    public String getImageName() {
        return this.imageName;
    }

    public String getImageData() {
        return this.imageData;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PlaceImage)) {
            return false;
        }
        PlaceImage other = (PlaceImage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$imageName = this.getImageName();
        String other$imageName = other.getImageName();
        if (this$imageName == null ? other$imageName != null : !this$imageName.equals(other$imageName)) {
            return false;
        }
        String this$imageData = this.getImageData();
        String other$imageData = other.getImageData();
        return !(this$imageData == null ? other$imageData != null : !this$imageData.equals(other$imageData));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PlaceImage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $imageName = this.getImageName();
        result = result * 59 + ($imageName == null ? 43 : $imageName.hashCode());
        String $imageData = this.getImageData();
        result = result * 59 + ($imageData == null ? 43 : $imageData.hashCode());
        return result;
    }

    public String toString() {
        return "PlaceImage(imageName=" + this.getImageName() + ", imageData=" + this.getImageData() + ")";
    }
}
