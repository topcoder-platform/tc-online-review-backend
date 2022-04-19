// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.util.concurrent;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E>, Serializable
{
    private final ConcurrentHashMap<E, Boolean> map;
    
    public ConcurrentHashSet() {
        this.map = new ConcurrentHashMap<E, Boolean>();
    }
    
    public ConcurrentHashSet(final int initialCapacity) {
        this.map = new ConcurrentHashMap<E, Boolean>(initialCapacity);
    }
    
    public ConcurrentHashSet(final int initialCapacity, final float loadFactor, final int concurrencyLevel) {
        this.map = new ConcurrentHashMap<E, Boolean>(initialCapacity, loadFactor, concurrencyLevel);
    }
    
    public ConcurrentHashSet(final Collection<? extends E> c) {
        this.map = new ConcurrentHashMap<E, Boolean>(c.size() + 1);
        this.addAll(c);
    }
    
    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }
    
    @Override
    public int size() {
        return this.map.size();
    }
    
    @Override
    public boolean add(final E o) {
        return this.map.put(o, Boolean.TRUE) == Boolean.TRUE;
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.map.remove(o) == Boolean.TRUE;
    }
    
    @Override
    public void clear() {
        this.map.clear();
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    public Object[] toArray() {
        return this.map.keySet().toArray();
    }
    
    @Override
    public <T> T[] toArray(final T[] a) {
        return this.map.keySet().toArray(a);
    }
    
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
}
