// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class ResultConverterFutureDecorator<T, V> implements Future<V>
{
    private Future<T> future;
    
    protected abstract V convertResult(final T p0) throws InterruptedException, ExecutionException;
    
    public ResultConverterFutureDecorator(final Future<T> future) {
        this.future = future;
    }
    
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return this.future.cancel(mayInterruptIfRunning);
    }
    
    public V get() throws InterruptedException, ExecutionException {
        final T value = this.future.get();
        return this.convertResult(value);
    }
    
    public V get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        final T value = this.future.get(timeout, unit);
        return this.convertResult(value);
    }
    
    public boolean isCancelled() {
        return this.future.isCancelled();
    }
    
    public boolean isDone() {
        return this.future.isDone();
    }
}
