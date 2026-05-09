/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain;

import java.io.Serializable;

public class R<T>
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 0;
    public static final int FAIL = 500;
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> ok() {
        return R.restResult(null, 0, "\u64cd\u4f5c\u6210\u529f");
    }

    public static <T> R<T> ok(T data) {
        return R.restResult(data, 0, "\u64cd\u4f5c\u6210\u529f");
    }

    public static <T> R<T> ok(T data, String msg) {
        return R.restResult(data, 0, msg);
    }

    public static <T> R<T> fail() {
        return R.restResult(null, 500, "\u64cd\u4f5c\u5931\u8d25");
    }

    public static <T> R<T> fail(String msg) {
        return R.restResult(null, 500, msg);
    }

    public static <T> R<T> fail(T data) {
        return R.restResult(data, 500, "\u64cd\u4f5c\u5931\u8d25");
    }

    public static <T> R<T> fail(T data, String msg) {
        return R.restResult(data, 500, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return R.restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<T>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
