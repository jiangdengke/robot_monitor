/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain.kafka;

import com.robotmonitor.flight.domain.kafka.Carousel;
import java.util.ArrayList;

public class CarouselGroup {
    private ArrayList<Carousel> carousel;

    public ArrayList<Carousel> getCarousel() {
        return this.carousel;
    }

    public void setCarousel(ArrayList<Carousel> carousel) {
        this.carousel = carousel;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CarouselGroup)) {
            return false;
        }
        CarouselGroup other = (CarouselGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ArrayList<Carousel> this$carousel = this.getCarousel();
        ArrayList<Carousel> other$carousel = other.getCarousel();
        return !(this$carousel == null ? other$carousel != null : !((Object)this$carousel).equals(other$carousel));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CarouselGroup;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ArrayList<Carousel> $carousel = this.getCarousel();
        result = result * 59 + ($carousel == null ? 43 : ((Object)$carousel).hashCode());
        return result;
    }

    public String toString() {
        return "CarouselGroup(carousel=" + this.getCarousel() + ")";
    }
}
