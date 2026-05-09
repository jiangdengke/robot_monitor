/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

import com.robotmonitor.flight.domain.kafka.Alternate;

public class AlternateGroup {
    private Alternate alternate;

    public Alternate getAlternate() {
        return this.alternate;
    }

    public void setAlternate(Alternate alternate) {
        this.alternate = alternate;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AlternateGroup)) {
            return false;
        }
        AlternateGroup other = (AlternateGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Alternate this$alternate = this.getAlternate();
        Alternate other$alternate = other.getAlternate();
        return !(this$alternate == null ? other$alternate != null : !((Object)this$alternate).equals(other$alternate));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AlternateGroup;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Alternate $alternate = this.getAlternate();
        result = result * 59 + ($alternate == null ? 43 : ((Object)$alternate).hashCode());
        return result;
    }

    public String toString() {
        return "AlternateGroup(alternate=" + this.getAlternate() + ")";
    }
}
