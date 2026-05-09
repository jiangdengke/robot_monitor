/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.exception.file;

import com.robotmonitor.common.exception.file.InvalidExtensionException;

public static class InvalidExtensionException.InvalidVideoExtensionException
extends InvalidExtensionException {
    private static final long serialVersionUID = 1L;

    public InvalidExtensionException.InvalidVideoExtensionException(String[] allowedExtension, String extension, String filename) {
        super(allowedExtension, extension, filename);
    }
}
