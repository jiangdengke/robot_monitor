/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

import com.robotmonitor.flight.domain.kafka.BoardingGate;
import java.util.ArrayList;

public class BoardingGateGroup {
    private ArrayList<BoardingGate> boardingGate;

    public ArrayList<BoardingGate> getBoardingGate() {
        return this.boardingGate;
    }

    public void setBoardingGate(ArrayList<BoardingGate> boardingGate) {
        this.boardingGate = boardingGate;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BoardingGateGroup)) {
            return false;
        }
        BoardingGateGroup other = (BoardingGateGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ArrayList<BoardingGate> this$boardingGate = this.getBoardingGate();
        ArrayList<BoardingGate> other$boardingGate = other.getBoardingGate();
        return !(this$boardingGate == null ? other$boardingGate != null : !((Object)this$boardingGate).equals(other$boardingGate));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BoardingGateGroup;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ArrayList<BoardingGate> $boardingGate = this.getBoardingGate();
        result = result * 59 + ($boardingGate == null ? 43 : ((Object)$boardingGate).hashCode());
        return result;
    }

    public String toString() {
        return "BoardingGateGroup(boardingGate=" + this.getBoardingGate() + ")";
    }
}
