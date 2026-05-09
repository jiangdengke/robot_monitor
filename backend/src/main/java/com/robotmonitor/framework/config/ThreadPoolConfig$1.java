/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.Threads
 */
package com.robotmonitor.framework.config;

import com.robotmonitor.common.utils.Threads;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

class ThreadPoolConfig.1
extends ScheduledThreadPoolExecutor {
    ThreadPoolConfig.1(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Threads.printException((Runnable)r, (Throwable)t);
    }
}
