// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.util.concurrent;

public class Waiter
{
    private final Object mutex;
    private long maxTime;
    private long elapse;
    
    public Waiter(final Object mutex) {
        this.mutex = mutex;
        this.init0();
    }
    
    private void init0() {
        this.maxTime = 0L;
        this.elapse = 0L;
    }
    
    public Waiter(final long elapse, final Object mutex) {
        this.mutex = mutex;
        if (elapse <= 0L) {
            this.init0();
        }
        else {
            this.elapse = elapse;
            this.resolveMaxTime();
        }
    }
    
    private void resolveMaxTime() {
        if (Long.MAX_VALUE - this.elapse < System.currentTimeMillis()) {
            this.maxTime = Long.MAX_VALUE;
        }
        else {
            this.maxTime = System.currentTimeMillis() + this.elapse;
        }
    }
    
    public boolean elapsed() {
        return this.elapse != 0L && System.currentTimeMillis() >= this.maxTime;
    }
    
    public void await() throws InterruptedException {
        if (this.elapse == 0L) {
            this.mutex.wait();
        }
        else {
            final long timeToWait = this.maxTime - System.currentTimeMillis();
            if (timeToWait > 0L) {
                this.mutex.wait(timeToWait);
            }
        }
    }
    
    public long getRemaining() {
        if (this.elapse == 0L) {
            return Long.MAX_VALUE;
        }
        final long time = this.maxTime - System.currentTimeMillis();
        return (time > 0L) ? time : 0L;
    }
    
    public void synchNotifyAll() {
        synchronized (this.mutex) {
            this.mutex.notifyAll();
        }
    }
    
    public void synchNotify() {
        synchronized (this.mutex) {
            this.mutex.notifyAll();
        }
    }
    
    public void reset() {
        synchronized (this.mutex) {
            if (this.elapse != 0L) {
                this.resolveMaxTime();
            }
            this.mutex.notifyAll();
        }
    }
}
