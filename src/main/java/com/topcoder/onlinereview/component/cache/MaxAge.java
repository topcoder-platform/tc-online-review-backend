package com.topcoder.onlinereview.component.cache;

public enum MaxAge {
    FIVE_MINUTES(300000),
    QUARTER_HOUR(900000),
    HALF_HOUR(1800000),
    HOUR(3600000),
    THREE_HOUR(10800000),
    MAX(1000 * 60 * 60 * 24 * 3);

    private final int age;

    private MaxAge(int age) {
        this.age = age;
    }

    public int age() {
        return this.age;
    }
}
