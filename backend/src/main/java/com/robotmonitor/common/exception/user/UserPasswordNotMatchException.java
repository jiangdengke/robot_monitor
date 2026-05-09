/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.exception.user;

import com.robotmonitor.common.exception.user.UserException;

public class UserPasswordNotMatchException
extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("user.password.not.match", (Object[])null);
    }
}
