package com.topcoder.onlinereview.component.cache;

import com.topcoder.onlinereview.component.exception.BaseException;

public class TCCacheException extends BaseException {
    public TCCacheException() {
    }

    public TCCacheException(String message) {
        super(message);
    }

    public TCCacheException(Throwable nestedException) {
        super(nestedException);
    }

    public TCCacheException(String message, Throwable nestedException) {
        super(message, nestedException);
    }
}
