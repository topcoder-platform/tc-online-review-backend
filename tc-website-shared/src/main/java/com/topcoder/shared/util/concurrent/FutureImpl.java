// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.util.concurrent;

import java.util.concurrent.*;

public class FutureImpl<T> implements Future<T>
{
    private static final int RESULT_SET = 3;
    private static final int FAILED = 2;
    private static final int CANCELLED = 1;
    private volatile int status;
    private Object mutex;
    private T value;
    private Exception exception;
    
    public FutureImpl() {
        this.mutex = new Object();
    }
    
    public boolean cancel(final boolean mayInterruptIfRunning) {
        synchronized (this.mutex) {
            if (this.status != 0) {
                return false;
            }
            this.status = 1;
            this.mutex.notifyAll();
        }
        final boolean result = this.bareCancel();
        this.instanceReady();
        return result;
    }
    
    public boolean setValue(final T value) {
        synchronized (this.mutex) {
            if (this.status != 0) {
                return false;
            }
            this.value = value;
            this.status = 3;
            this.mutex.notifyAll();
        }
        this.instanceReady();
        return true;
    }
    
    public void setException(final Exception e) {
        synchronized (this.mutex) {
            if (this.status != 0) {
                return;
            }
            this.exception = e;
            this.status = 2;
            this.mutex.notifyAll();
        }
        this.instanceReady();
    }
    
    public T get() throws InterruptedException, ExecutionException {
        try {
            return this.get(0L, TimeUnit.MILLISECONDS);
        }
        catch (TimeoutException e) {
            return null;
        }
    }
    
    public T get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        this.waitForStatusChange(unit.toMillis(timeout));
        if (this.status == 3) {
            return this.value;
        }
        if (this.status == 1) {
            throw new CancellationException("Cancelled!");
        }
        if (this.status == 2) {
            throw new ExecutionException("The execution thrown an exception", this.exception);
        }
        throw new TimeoutException("Timeout reached!");
    }
    
    private void waitForStatusChange(final long timeout) throws InterruptedException {
        if (timeout == 0L) {
            synchronized (this.mutex) {
                while (this.status == 0) {
                    this.mutex.wait();
                }
            }
        }
        else {
            long finalTime = Long.MAX_VALUE;
            if (timeout < finalTime - System.currentTimeMillis()) {
                finalTime = System.currentTimeMillis() + timeout;
            }
            synchronized (this.mutex) {
                for (long waitTime = finalTime - System.currentTimeMillis(); this.status == 0 && waitTime > 0L; waitTime = finalTime - System.currentTimeMillis()) {
                    this.mutex.wait(waitTime);
                }
            }
        }
    }
    
    public boolean isCancelled() {
        return this.status == 1;
    }
    
    public boolean isDone() {
        return this.status != 0;
    }
    
    protected void instanceReady() {
    }
    
    protected boolean bareCancel() {
        return false;
    }
}
