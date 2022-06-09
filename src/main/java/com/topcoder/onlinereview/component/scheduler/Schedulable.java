package com.topcoder.onlinereview.component.scheduler;

public interface Schedulable {
    boolean isDone();

    void close();

    String getStatus();
}
