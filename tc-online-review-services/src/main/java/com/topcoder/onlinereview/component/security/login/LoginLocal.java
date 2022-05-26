package com.topcoder.onlinereview.component.security.login;

import com.topcoder.onlinereview.component.security.GeneralSecurityException;
import com.topcoder.onlinereview.component.security.TCSubject;

public interface LoginLocal extends javax.ejb.EJBLocalObject {

    public TCSubject login(String username, String password)
            throws GeneralSecurityException;
    public TCSubject login(String username, String password, String dataSource)
            throws GeneralSecurityException;
    
    /**
     * This method checks if the given handle is user migrated from CloudSpokes.
     * @throws GeneralSecurityException If there is any general security error.
     */
    public boolean isCloudSpokesUser(String handle) throws GeneralSecurityException;


}
