/*
 * Copyright (C) 2011-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.security.groups.services.hibernate;

import com.topcoder.onlinereview.component.security.groups.services.SecurityGroupException;
import com.topcoder.onlinereview.component.util.LoggingWrapperUtility;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>
 * This is a base service of all the services,it contains some utility
 * </p>
 * <p>
 * Thread Safety:Implementations are expected to be effectively thread-safe
 * </p>
 * 
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) change notes:
 * </p>
 * 
 * @author backstretlili, TCSASSEMBLER
 * 
 * @version 1.1
 * 
 */
public abstract class BaseGroupService implements InitializingBean {

    /**
     * <p>
     * This method wraps the exception instance into a SecurityGroupException
     * and log down the exception.
     * </p>
     * 
     * @param e
     *            original exception.
     * @param logger
     *            Log instance to log exception.
     * @param signature
     *            the method signature.
     * @throws SecurityGroupException
     *             Wrapped {@link SecurityGroupException}.
     */
    protected void wrapAndLogSecurityException(Exception e, Logger logger, String signature) throws SecurityGroupException {
        SecurityGroupException securityGroupException = new SecurityGroupException(e.getMessage(), e);
        LoggingWrapperUtility.logException(logger, signature, securityGroupException);
        throw securityGroupException;
    }
    
    /**
     * Gets the like string used in hql.
     * 
     * @param str the string to process
     * @return the like string used in hql
     * @since 1.1
     */
    protected String getLikeString(String str) {
        return "%" + str.replaceAll("\\%", "") + "%";
    }
    
    /**
     * Checks if the given string is non-null non-empty.
     * 
     * @param str the string to check
     * @return true if the given string is non-null non-empty; false otherwise
     * @since 1.1
     */
    protected boolean isNonNullNonEmpty(String str) {
        return str != null && str.trim().length() > 0;
    }

    public void afterPropertiesSet() {
    }
}
