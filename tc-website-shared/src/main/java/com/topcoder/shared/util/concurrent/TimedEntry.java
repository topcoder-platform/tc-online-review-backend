// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.util.concurrent;

public class TimedEntry<T>
{
    private long entryTS;
    private T value;
    
    public TimedEntry(final T value) {
        this.entryTS = System.currentTimeMillis();
        this.value = value;
    }
    
    public long getEntryTS() {
        return this.entryTS;
    }
    
    public T getValue() {
        return this.value;
    }
}
