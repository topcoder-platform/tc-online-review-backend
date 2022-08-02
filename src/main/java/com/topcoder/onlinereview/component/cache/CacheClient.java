package com.topcoder.onlinereview.component.cache;

public interface CacheClient {
    void set(String var1, Object var2) throws TCCacheException;

    void set(CacheAddress var1, Object var2) throws TCCacheException;

    void set(CacheAddress var1, Object var2, MaxAge var3) throws TCCacheException;

    Object get(String var1) throws TCCacheException;

    Object get(CacheAddress var1) throws TCCacheException;

    Object remove(String var1) throws TCCacheException;

    Object remove(CacheAddress var1) throws TCCacheException;

    void clearCache() throws TCCacheException;
}