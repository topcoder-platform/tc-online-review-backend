// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.util.concurrent;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class NullFuture<V> implements Future<V>
{
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return false;
    }
    
    public V get() {
        return null;
    }
    
    public V get(final long timeout, final TimeUnit unit) {
        return null;
    }
    
    public boolean isCancelled() {
        return false;
    }
    
    public boolean isDone() {
        return true;
    }
}
