/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.flight.dto;

import com.robotmonitor.flight.dto.CmdItemDto;
import java.util.List;

public class CmdDto {
    private String type;
    private String name;
    private List<CmdItemDto> list;

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public List<CmdItemDto> getList() {
        return this.list;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(List<CmdItemDto> list) {
        this.list = list;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CmdDto)) {
            return false;
        }
        CmdDto other = (CmdDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        List<CmdItemDto> this$list = this.getList();
        List<CmdItemDto> other$list = other.getList();
        return !(this$list == null ? other$list != null : !((Object)this$list).equals(other$list));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CmdDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        List<CmdItemDto> $list = this.getList();
        result = result * 59 + ($list == null ? 43 : ((Object)$list).hashCode());
        return result;
    }

    public String toString() {
        return "CmdDto(type=" + this.getType() + ", name=" + this.getName() + ", list=" + this.getList() + ")";
    }
}
