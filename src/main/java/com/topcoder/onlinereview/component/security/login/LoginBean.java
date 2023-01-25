/*
 * Copyright (C) 2002-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.security.login;

import com.topcoder.onlinereview.component.grpcclient.GrpcHelper;
import com.topcoder.onlinereview.component.grpcclient.security.SecurityServiceRpc;
import com.topcoder.onlinereview.component.security.GeneralSecurityException;
import com.topcoder.onlinereview.component.security.RolePrincipal;
import com.topcoder.onlinereview.component.security.SecurityDB;
import com.topcoder.onlinereview.component.security.TCSubject;
import com.topcoder.onlinereview.grpc.security.proto.UserRoleProto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>A stateless EJB to be used for authenticating users to application.</p>
 *
 * <p>Version 2.0 (LDAP Authentication Release Assembly v1.0) change notes:
 *   <ul>
 *     <li>Re-written the whole logic for user authentication to authenticate users against <code>LDAP</code> server
 *     instead of database.</li>
 *   </ul>
 * </p>
 *
 * <p>
 * Version 2.1 (Impersonation Login Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #login(String, String, String)} method to support impersonated logins.</li>
 *   </ol>
 * </p>
 * 
 * <p>
 * Version 2.2(BUGR-9941) Change notes:
 *    <ol>
 *       <li>Add {@link #isCloudSpokesUser(String)} method.
 *    </ol>
 * </p>
 *
 * @author Heather Van Aelst, isv, KeSyren
 * @version 2.2
 */
public class LoginBean {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered during user authentication.</p>
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);

    /**
     * <p>A <code>String</code> providing the default JNDI name for the data source to be used for establishing
     * connection to target database.</p>
     */
    private static final String DATA_SOURCE = "java:comp/env/jdbc/DefaultDS";

    /**
     * <p>Constructs new <code>LoginBean</code> instance. This implementation does nothing.</p>
     *
     * @since 2.0
     */
    public LoginBean() {
    }

    /**
     * <p>Logs the user to application using specified credentials for authentication.</p>
     *
     * @param username a <code>String</code> providing the username for user authentication.
     * @param password a <code>String</code> providing the plain text password for user authentication.
     * @return a <code>TCSubject</code> providing the identity for the user successfully authenticated to application.
     * @throws AuthenticationException if provided credentials are invalid.
     * @throws GeneralSecurityException if an unexpected error occurs while authenticating user to application.
     */
    public TCSubject login(String username, String password) throws GeneralSecurityException {
        return login(username, password, DATA_SOURCE);
    }

    /**
     * <p>Logs the user to application using the specified credentials. Checks username and password, returns a
     * TCSubject representation of the user that includes the user's roles.</p>
     *
     * @param username a <code>String</code> providing the username for user authentication.
     * @param password a <code>String</code> providing the plain text password for user authentication.
     * @param dataSource a <code>String</code> referencing the data source to be used for establishing connection to
     *        target database.
     * @return a <code>TCSubject</code> providing the identity for the user successfully authenticated to application.
     * @throws AuthenticationException if provided credentials are invalid.
     * @throws GeneralSecurityException if an unexpected error occurs while authenticating user to application.
     */
    public TCSubject login(String username, String password, String dataSource) throws GeneralSecurityException {

        if (logger.isDebugEnabled()) {
            logger.debug("LoginBean.login: " + username);
        }

        boolean impersonationUsed = false;
        String impersonatedUsername = null;
        if (username != null) {
            int slashPos = username.indexOf("/");
            if (slashPos >= 0) {
                impersonationUsed = true;
                impersonatedUsername = username.substring(slashPos + 1);
                username = username.substring(0, slashPos);
            }
        }

        checkLength(username, SecurityDB.maxUsernameLength);
        checkLength(password, SecurityDB.maxPasswordLength);
        if (impersonationUsed) {
            checkLength(impersonatedUsername, SecurityDB.maxUsernameLength);
        }

        // Authenticate user against LDAP server and map user to user ID
        long userId = loginToLDAPDirectory(username, password);
        Set<RolePrincipal> userRoles = getUserRoles(userId);
        if (impersonationUsed) {
            boolean canPerformImpersonatedLogins = false;

            try {
                Long impersonationRoleId = getImpersonationRoleId();

                // Check if current user is granted a permission to perform impersonated logins. If so then perform
                // impersonated login; otherwise raise a security exception
                for (RolePrincipal userRole : userRoles) {
                    if (userRole.getId() == impersonationRoleId) {
                        canPerformImpersonatedLogins = true;
                        break;
                    }
                }
            } catch (NamingException e) {
                throw new GeneralSecurityException("Failed to retrieve the value of java:comp/env/impersonationRoleId "
                        + "environment entry from JNDI context", e);
            }

            if (canPerformImpersonatedLogins) {
                long impersonatedUserId = loginToLDAPDirectory(impersonatedUsername);
                Set<RolePrincipal> impersonatedUserRoles = getUserRoles(impersonatedUserId);
                TCSubject impersonatedTCSubject = new TCSubject(impersonatedUserRoles, impersonatedUserId);
                impersonatedTCSubject.setImpersonatedByUserId(userId);
                impersonatedTCSubject.setImpersonatedByUsername(username);
                if (logger.isDebugEnabled()) {
                    logger.debug("Logged as user " + impersonatedUsername + "(ID: " + impersonatedUserId
                            + ") impersonated by user " + username + " (ID: " + userId + ")");
                }
                return impersonatedTCSubject;
            } else {
                logger.error("Denied to perform impersonated login on behalf of user " + impersonatedUsername
                        + " by user " + username + "(ID: " + userId + ")");
                throw new AuthenticationException("You can not perform requested operation");
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Logging in login_id: " + userId);
            }
            return new TCSubject(userRoles, userId);
        }
    }

    /**
     * <p>Returns the impersonation role ID.</p>
     *
     * @return Impersonation role ID.
     * @throws NamingException if unable to retrieve the impersonation role ID.
     */
    protected Long getImpersonationRoleId() throws NamingException {
        InitialContext context = new InitialContext();
        Long impersonationRoleId = (Long)context.lookup("java:comp/env/impersonationRoleId");
        return impersonationRoleId;
    }

    /**
     * <p>Use this to check the length of a parameter against a defined maximum. For example, compare a submitted
     * username to the length of the username column in the db.  Throws GeneralSecurityException if param is too long or
     * is empty.<p>
     *
     * @param param a <code>String</code> providing the value to be checked.
     * @param maxLength an <code>int</code> providing the maximum allowed length.
     * @since 2.1
     */
    protected void checkLength(String param, int maxLength) throws GeneralSecurityException {
        if (param.trim().length() == 0) {
            throw new GeneralSecurityException("Parameter <" + param + "> is empty.");
        }
        if (param.length() > maxLength) {
            throw new GeneralSecurityException("Parameter <" + param + "> is too long.  Should be <= " + maxLength);
        }
    }

    /**
     * <p>Gets the roles assigned to user mapped to specified user ID.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user to gather roles for.
     * @return a <code>Set</code> of roles assigned to specified user.
     * @throws GeneralSecurityException if an unexpected error occurs while getting user roles from persistent data
     *         store.
     * @since 2.1
     */
    public static Set<RolePrincipal> getUserRoles(long userId) throws GeneralSecurityException {
        // Collect user's roles from database
        SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
        Set<RolePrincipal> userRoles = new HashSet<RolePrincipal>();
        try {
            List<UserRoleProto> result =  securityServiceRpc.getUserRoles(userId);
            // Build list of RolePrincipals associated with user
            for (UserRoleProto row : result) {
                userRoles.add(new RolePrincipal(row.getDescription(), row.getRoleId()));
            }
        } catch (Exception e) {
            throw new GeneralSecurityException(e);
        }
        return userRoles;
    }

    /**
     * <p>Authenticates user against <code>LDAP</code> directory based on provided credentials. Verifies that there is
     * an <code>LDAP</code> entry with DN matching the specified username and that it has associated password equal to
     * specified one.</p>
     *
     * @param username a <code>String</code> providing the username for user authentication.
     * @param password a <code>String</code> providing the encrypted text password for user authentication.
     * @return a <code>long</code> providing the ID for the user authenticated based on provided credentials.
     * @throws AuthenticationException if provided credentials are invalid or user account is not active.
     * @throws GeneralSecurityException if an unexpected error occurs while authenticating user to application.
     * @since 2.0
     */
    private long loginToLDAPDirectory(String username, String password) throws GeneralSecurityException {
        throw new AuthenticationException("don't support ldap anymore");
    }

    /**
     * <p>Authenticates user against <code>LDAP</code> directory based on provided username only. Verifies that there is
     * an <code>LDAP</code> entry with DN matching the specified username.</p>
     *
     * @param username a <code>String</code> providing the username for user authentication.
     * @return a <code>long</code> providing the ID for the user authenticated based on provided username.
     * @throws AuthenticationException if provided username is invalid or user account is not active.
     * @throws GeneralSecurityException if an unexpected error occurs while authenticating user to application.
     * @since 2.1
     */
    private long loginToLDAPDirectory(String username) throws GeneralSecurityException {
        throw new AuthenticationException("don't support ldap anymore");
    }
    
    /**
     * This method checks if the given handle is user migrated from CloudSpokes.
     * @throws GeneralSecurityException If there is any general security error.
     */
    public boolean isCloudSpokesUser(String handle) throws GeneralSecurityException {
        try {
            SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
            return securityServiceRpc.isCloudSpokesUser(handle);
        } catch (Exception e) {
            throw new GeneralSecurityException(e);
        }
    }
}
