package com.topcoder.onlinereview.component.security.login;

import com.topcoder.onlinereview.component.security.GeneralSecurityException;
import com.topcoder.onlinereview.component.security.TCSubject;

import java.rmi.RemoteException;

public interface LoginInterface {

    TCSubject login(String username, String password)
            throws RemoteException, GeneralSecurityException;
    TCSubject login(String username, String password, String dataSource)
            throws RemoteException, GeneralSecurityException;

    /**
     * This method checks if the given handle is user migrated from CloudSpokes.
     * @throws RemoteException If there is any error.
     * @throws GeneralSecurityException If there is any general security error.
     */
    public boolean isCloudSpokesUser(String handle) throws RemoteException, GeneralSecurityException;

}
