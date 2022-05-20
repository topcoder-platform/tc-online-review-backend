package com.topcoder.onlinereview.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils  implements ApplicationContextAware {
    /**
     * <p>A <code>ApplicationContext</code> providing the current application context.</p>
     */
    private static ApplicationContext applicationContext;

    /**
     * <p>Constructs new <code>SpringContextProvider</code> instance.</p>
     */
    public SpringUtils() {
    }

    /**
     * <p>Gets the current application context.</p>
     *
     * @return a <code>ApplicationContext</code> providing the current application context.
     */
    public static ApplicationContext getApplicationContext() {
        return SpringUtils.applicationContext;
    }

    /**
     * <p>Sets the current application context.</p>
     *
     * @param applicationContext a <code>ApplicationContext</code> providing the current application context.
     * @throws ApplicationContextException if an unexpected error occurs while accessing the JNDI context or reading
     *         configuration for JNDI utility.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public static String getPropertyValue(String propertyName) {
        return applicationContext.getEnvironment().getProperty(propertyName);
    }
}
