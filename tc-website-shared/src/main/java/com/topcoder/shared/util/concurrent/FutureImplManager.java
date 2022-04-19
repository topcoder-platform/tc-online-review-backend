// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.util.concurrent;

import com.topcoder.shared.util.logging.Logger;

import java.lang.ref.WeakReference;
import java.util.*;

public class FutureImplManager<ID, T>
{
    private static final long MAX_GAP = 2000L;
    private Logger log;
    private Object requestResponseLock;
    private Map<ID, TimedEntry<WeakReference<ManagedFutureImpl<T, ID>>>> pendingFutures;
    private Map<ID, TimedEntry<T>> pendingResults;
    private Map<ID, TimedEntry<Exception>> pendingExceptions;
    private ManagedFutureImpl.FutureHandler<ID> futureReleaseHandler;
    
    public FutureImplManager() {
        this.log = Logger.getLogger(this.getClass());
        this.requestResponseLock = new Object();
        this.pendingFutures = new HashMap<ID, TimedEntry<WeakReference<ManagedFutureImpl<T, ID>>>>();
        this.pendingResults = new LinkedHashMap<ID, TimedEntry<T>>();
        this.pendingExceptions = new LinkedHashMap<ID, TimedEntry<Exception>>();
        this.futureReleaseHandler = new ManagedFutureImpl.FutureHandler<ID>() {
            public void futureReady(final ID id) {
                synchronized (FutureImplManager.this.requestResponseLock) {
                    FutureImplManager.this.pendingFutures.remove(id);
                }
            }
            
            public boolean cancel(final ID id, final boolean mayInterrupt) {
                return false;
            }
        };
    }
    
    public int getPendingFuturesSize() {
        synchronized (this.requestResponseLock) {
            return this.pendingFutures.size();
        }
    }
    
    public Set<ID> getPendingFutureIds() {
        synchronized (this.requestResponseLock) {
            return new HashSet<ID>((Collection<? extends ID>)this.pendingFutures.keySet());
        }
    }
    
    public FutureImpl<T> getFuture(final ID id) {
        synchronized (this.requestResponseLock) {
            final TimedEntry<WeakReference<ManagedFutureImpl<T, ID>>> timedEntry = this.pendingFutures.get(id);
            if (timedEntry != null) {
                return timedEntry.getValue().get();
            }
            return null;
        }
    }
    
    public boolean setValue(final ID id, final T result) {
        if (this.log.isTraceEnabled()) {
            this.log.trace("Setting result for: " + id + " result=" + result);
        }
        synchronized (this.requestResponseLock) {
            final TimedEntry<WeakReference<ManagedFutureImpl<T, ID>>> futureResponse = this.pendingFutures.remove(id);
            if (futureResponse != null) {
                final ManagedFutureImpl<T, ID> ManagedFutureImpl = futureResponse.getValue().get();
                if (ManagedFutureImpl != null) {
                    ManagedFutureImpl.setValue((T)result);
                    return true;
                }
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Future does not exists adding as pending result: " + id + " result=" + result);
            }
            this.pendingResults.put(id, new TimedEntry<T>(result));
            return false;
        }
    }
    
    public boolean cancel(final ID id, final boolean mayInterrupt) {
        if (this.log.isTraceEnabled()) {
            this.log.trace("Cancelling future for: " + id + " mayInterrupt=" + mayInterrupt);
        }
        synchronized (this.requestResponseLock) {
            final TimedEntry<WeakReference<ManagedFutureImpl<T, ID>>> futureResponse = this.pendingFutures.remove(id);
            if (futureResponse != null) {
                final ManagedFutureImpl<T, ID> ManagedFutureImpl = futureResponse.getValue().get();
                if (ManagedFutureImpl != null) {
                    return ManagedFutureImpl.cancel(mayInterrupt);
                }
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Future does not exist :" + id);
            }
            return false;
        }
    }
    
    public boolean setException(final ID id, final Exception ex) {
        if (this.log.isTraceEnabled()) {
            this.log.trace("Setting exception for: " + id + " ex=" + ex);
        }
        synchronized (this.requestResponseLock) {
            final TimedEntry<WeakReference<ManagedFutureImpl<T, ID>>> futureResponse = this.pendingFutures.remove(id);
            if (futureResponse != null) {
                final ManagedFutureImpl<T, ID> ManagedFutureImpl = futureResponse.getValue().get();
                if (ManagedFutureImpl != null) {
                    ManagedFutureImpl.setException(ex);
                    return true;
                }
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Future does not exists adding as pending result: " + id + " ex=" + ex);
            }
            this.pendingExceptions.put(id, new TimedEntry<Exception>(ex));
            return false;
        }
    }
    
    public ManagedFutureImpl<T, ID> newFuture(final ID id) {
        if (this.log.isTraceEnabled()) {
            this.log.trace("Creating future for: " + id);
        }
        final ManagedFutureImpl<T, ID> future = new ManagedFutureImpl<T, ID>(id, this.futureReleaseHandler);
        synchronized (this.requestResponseLock) {
            final TimedEntry<T> response = this.pendingResults.remove(id);
            final TimedEntry<Exception> exception = this.pendingExceptions.remove(id);
            this.cleanUp();
            if (response == null && exception == null) {
                this.pendingFutures.put(id, new TimedEntry<WeakReference<ManagedFutureImpl<T, ID>>>(new WeakReference<ManagedFutureImpl<T, ID>>(future)));
            }
            else if (response != null) {
                future.setValue((T)response.getValue());
            }
            else {
                future.setException(exception.getValue());
            }
            return future;
        }
    }
    
    private void cleanUp() {
        final long minTS = System.currentTimeMillis() - 2000L;
        this.cleanUp(this.pendingResults, minTS);
        this.cleanUp(this.pendingExceptions, minTS);
    }
    
    private <S> void cleanUp(final Map<ID, TimedEntry<S>> sets, final long minTS) {
        final Iterator<Map.Entry<ID, TimedEntry<S>>> iterator = sets.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<ID, TimedEntry<S>> timedEntry = iterator.next();
            if (timedEntry.getValue().getEntryTS() > minTS) {
                return;
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Removing pending result with ID=: " + timedEntry.getKey());
            }
            iterator.remove();
        }
    }
    
    public void release() {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Releasing Future manager");
        }
        synchronized (this.requestResponseLock) {
            this.pendingFutures.clear();
            this.pendingResults.clear();
            this.pendingExceptions.clear();
        }
    }
}
