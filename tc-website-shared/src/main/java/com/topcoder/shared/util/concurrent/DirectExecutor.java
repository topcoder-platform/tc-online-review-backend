// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.util.concurrent;

import java.util.concurrent.Executor;

public class DirectExecutor implements Executor
{
    public void execute(final Runnable command) {
        command.run();
    }
}
