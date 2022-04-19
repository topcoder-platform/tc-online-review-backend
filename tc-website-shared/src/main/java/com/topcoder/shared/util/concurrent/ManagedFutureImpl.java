// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.util.concurrent;

public class ManagedFutureImpl<T, W> extends FutureImpl<T>
{
    private static final FutureHandler NULL_HANDLER;
    private W id;
    private FutureHandler<W> handler;
    
    public ManagedFutureImpl() {
        this.handler = (FutureHandler<W>)ManagedFutureImpl.NULL_HANDLER;
    }
    
    public ManagedFutureImpl(final W id, final FutureHandler<W> handler) {
        this.id = id;
        this.handler = handler;
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.instanceReady();
    }
    
    @Override
    protected void instanceReady() {
        try {
            if (this.handler != null) {
                this.handler.futureReady(this.id);
            }
        }
        catch (Exception ex) {}
        this.handler = null;
    }
    
    static {
        NULL_HANDLER = new NullFutureHandlerImplementation();
    }
    
    private static final class NullFutureHandlerImplementation implements FutureHandler
    {
        public void futureReady(final Object id) {
        }
        
        public boolean cancel(final Object id, final boolean mayInterrupt) {
            return false;
        }
    }
    
    public interface FutureHandler<W>
    {
        void futureReady(final W p0);
        
        boolean cancel(final W p0, final boolean p1);
    }
}
