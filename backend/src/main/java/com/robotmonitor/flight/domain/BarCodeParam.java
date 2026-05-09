/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class BarCodeParam {
    private String barCode;
    private String mode = "0";

    public String getBarCode() {
        return this.barCode;
    }

    public String getMode() {
        return this.mode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BarCodeParam)) {
            return false;
        }
        BarCodeParam other = (BarCodeParam)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$barCode = this.getBarCode();
        String other$barCode = other.getBarCode();
        if (this$barCode == null ? other$barCode != null : !this$barCode.equals(other$barCode)) {
            return false;
        }
        String this$mode = this.getMode();
        String other$mode = other.getMode();
        return !(this$mode == null ? other$mode != null : !this$mode.equals(other$mode));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BarCodeParam;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $barCode = this.getBarCode();
        result = result * 59 + ($barCode == null ? 43 : $barCode.hashCode());
        String $mode = this.getMode();
        result = result * 59 + ($mode == null ? 43 : $mode.hashCode());
        return result;
    }

    public String toString() {
        return "BarCodeParam(barCode=" + this.getBarCode() + ", mode=" + this.getMode() + ")";
    }
}
