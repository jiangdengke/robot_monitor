/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

import com.robotmonitor.flight.domain.kafka.Sharing;
import java.util.ArrayList;

public class SharingGroup {
    private ArrayList<Sharing> sharing;

    public ArrayList<Sharing> getSharing() {
        return this.sharing;
    }

    public void setSharing(ArrayList<Sharing> sharing) {
        this.sharing = sharing;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SharingGroup)) {
            return false;
        }
        SharingGroup other = (SharingGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ArrayList<Sharing> this$sharing = this.getSharing();
        ArrayList<Sharing> other$sharing = other.getSharing();
        return !(this$sharing == null ? other$sharing != null : !((Object)this$sharing).equals(other$sharing));
    }

    protected boolean canEqual(Object other) {
        return other instanceof SharingGroup;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ArrayList<Sharing> $sharing = this.getSharing();
        result = result * 59 + ($sharing == null ? 43 : ((Object)$sharing).hashCode());
        return result;
    }

    public String toString() {
        return "SharingGroup(sharing=" + this.getSharing() + ")";
    }
}
