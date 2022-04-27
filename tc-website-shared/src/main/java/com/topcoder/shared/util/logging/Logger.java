package com.topcoder.shared.util.logging;

public abstract class Logger {
    private static final LoggerFactory LOGGER_FACTORY = new LoggerFactoryLog4j127();

    public Logger() {
    }

    public static Logger getLogger(Class clazz) {
        return LOGGER_FACTORY.getLogger(clazz);
    }

    public static Logger getLogger(String categoryName) {
        return LOGGER_FACTORY.getLogger(categoryName);
    }

    public abstract void debug(Object var1);

    public abstract void debug(Object var1, Throwable var2);

    public abstract void info(Object var1);

    public abstract void info(Object var1, Throwable var2);

    public abstract void warn(Object var1);

    public abstract void warn(Object var1, Throwable var2);

    public abstract void error(Object var1);

    public abstract void error(Object var1, Throwable var2);

    public abstract void fatal(Object var1);

    public abstract void fatal(Object var1, Throwable var2);

    public abstract boolean isDebugEnabled();

    public abstract boolean isInfoEnabled();

    public abstract boolean isTraceEnabled();

    public abstract void trace(Object var1, Throwable var2);

    public abstract void trace(Object var1);
}
