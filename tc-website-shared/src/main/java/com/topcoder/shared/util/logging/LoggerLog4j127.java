package com.topcoder.shared.util.logging;

final class LoggerLog4j127 extends Logger {
    private final org.apache.log4j.Logger logger;

    LoggerLog4j127(org.apache.log4j.Logger logger) {
        this.logger = logger;
    }

    public void debug(Object message) {
        this.logger.debug(message);
    }

    public void debug(Object message, Throwable t) {
        this.logger.debug(message, t);
    }

    public void info(Object message) {
        this.logger.info(message);
    }

    public void info(Object message, Throwable t) {
        this.logger.info(message, t);
    }

    public void warn(Object message) {
        this.logger.warn(message);
    }

    public void warn(Object message, Throwable t) {
        this.logger.warn(message, t);
    }

    public void error(Object message) {
        this.logger.error(message);
    }

    public void error(Object message, Throwable t) {
        this.logger.error(message, t);
    }

    public void fatal(Object message) {
        this.logger.fatal(message);
    }

    public void fatal(Object message, Throwable t) {
        this.logger.fatal(message, t);
    }

    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    public void trace(Object message, Throwable t) {
        this.logger.trace(message, t);
    }

    public void trace(Object message) {
        this.logger.trace(message);
    }
}

