/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.domain;

public class MemberName {
    private String firstName;
    private String lastName;
    private String cNLastName;
    private String cNFirstName;

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getCNLastName() {
        return this.cNLastName;
    }

    public String getCNFirstName() {
        return this.cNFirstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCNLastName(String cNLastName) {
        this.cNLastName = cNLastName;
    }

    public void setCNFirstName(String cNFirstName) {
        this.cNFirstName = cNFirstName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MemberName)) {
            return false;
        }
        MemberName other = (MemberName)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$firstName = this.getFirstName();
        String other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) {
            return false;
        }
        String this$lastName = this.getLastName();
        String other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) {
            return false;
        }
        String this$cNLastName = this.getCNLastName();
        String other$cNLastName = other.getCNLastName();
        if (this$cNLastName == null ? other$cNLastName != null : !this$cNLastName.equals(other$cNLastName)) {
            return false;
        }
        String this$cNFirstName = this.getCNFirstName();
        String other$cNFirstName = other.getCNFirstName();
        return !(this$cNFirstName == null ? other$cNFirstName != null : !this$cNFirstName.equals(other$cNFirstName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof MemberName;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $firstName = this.getFirstName();
        result = result * 59 + ($firstName == null ? 43 : $firstName.hashCode());
        String $lastName = this.getLastName();
        result = result * 59 + ($lastName == null ? 43 : $lastName.hashCode());
        String $cNLastName = this.getCNLastName();
        result = result * 59 + ($cNLastName == null ? 43 : $cNLastName.hashCode());
        String $cNFirstName = this.getCNFirstName();
        result = result * 59 + ($cNFirstName == null ? 43 : $cNFirstName.hashCode());
        return result;
    }

    public String toString() {
        return "MemberName(firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", cNLastName=" + this.getCNLastName() + ", cNFirstName=" + this.getCNFirstName() + ")";
    }
}
