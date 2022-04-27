package com.topcoder.shared.util.logging;

interface LoggerFactory {
    Logger getLogger(Class var1);

    Logger getLogger(String var1);
}
