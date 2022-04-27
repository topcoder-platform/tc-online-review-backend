package com.topcoder.shared.util.logging;

final class LoggerFactoryLog4j127 implements LoggerFactory {
    LoggerFactoryLog4j127() {
    }

    public Logger getLogger(Class clazz) {
        return new LoggerLog4j127(org.apache.log4j.Logger.getLogger(clazz));
    }

    public Logger getLogger(String categoryName) {
        return new LoggerLog4j127(org.apache.log4j.Logger.getLogger(categoryName));
    }
}

