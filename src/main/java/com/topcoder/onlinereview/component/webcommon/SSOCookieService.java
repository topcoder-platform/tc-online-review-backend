/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.webcommon;

import com.topcoder.onlinereview.component.grpcclient.GrpcHelper;
import com.topcoder.onlinereview.component.grpcclient.webcommon.WebCommonServiceRpc;
import com.topcoder.onlinereview.grpc.webcommon.proto.GetUserPasswordResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.MessageDigest;

import static com.topcoder.onlinereview.component.util.SpringUtils.getPropertyValue;

/**
 * <p>
 * This class implements the interface SSOCookieService.
 * </p>
 * <p>
 *     Version 1.1 (BUGR-10718) changes:
 *     <ul>
 *         <li>Moved createCookie and getCookie methods in <code>CookieHelper</code>.</li>
 *     </ul>
 * </p>
 * <p>
 * <b> Thread Safety:</b> This class is immutable thus thread-safe.
 * </p>
 * @author ecnu_haozi, MonicaMuranyi
 * @version 1.1
 * @since 1.0 (TCCC-5802    https://apps.topcoder.com/bugs/browse/TCCC-5802)
 */
public class SSOCookieService {
    /**
     * Constructor.
     */
    public SSOCookieService(){
    }

    /**
     * <p>
     * When the user try to log in, this method is called to set a SSO cookie in the user's browser.
     * </p>
     * @param response The HTTP response sent back to the user's browser.
     * @param userId the user id.
     * @param rememberMe a flag to indicate if the user select rememberMe checkbox while logging in.
     * @throws Exception if any error occurs.
     */
    public void setSSOCookie(HttpServletResponse response, long userId, boolean rememberMe) throws Exception {
        String userIdentity = userId + "|" + hashForUser(userId);
        if (rememberMe) {
            //This is a persistent cookie which will persist unless the user log out or change password.
            response.addCookie( CookieHelper.createCookie(getPropertyValue("SSO_COOKIE_KEY"), userIdentity, getPropertyValue("SSO_DOMAIN"), Integer.MAX_VALUE));
        } else {
            //This is a session cookie which will invalidate when the user exit the browser.
            response.addCookie( CookieHelper.createCookie(getPropertyValue("SSO_COOKIE_KEY"), userIdentity, getPropertyValue("SSO_DOMAIN"), null));
        }
    }
    /**
     * <p>
     * Remove the SSO cookie in the user's browser.
     * </p>
     * @param response The HTTP response sent back to the user's browser.
     */
    public void removeSSOCookie(HttpServletResponse response) {
         response.addCookie( CookieHelper.createCookie(getPropertyValue("SSO_COOKIE_KEY"), "", getPropertyValue("SSO_DOMAIN"), 0));
    }

    /**
     * <p>
     * Check if the SSO cookie exist in the user's browser. No matter what the SSO cookie's value is.
     * </p>
     * @param request the HTTP request sent from the user's browser.
     * @return true if the SSO cookie exist, false otherwise.
     */
    public boolean existSSOCookie(HttpServletRequest request) {
        // Get cookies from the request
        Cookie[] cookies = request.getCookies();

        // no cookies, return null
        if (cookies == null) {
            return false;
        }

        Cookie ssoCookie = CookieHelper.getCookie(cookies, getPropertyValue("SSO_COOKIE_KEY"));

        if (ssoCookie == null) {
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Retrieve user identity from the SSO cookie in the user's browser. 
     * </p>
     * @param request the HTTP request sent from the user's browser.
     * @return the user id. If the sso cookie doesn't exist or the sso cookie's value is invalid, return NULL.
     * @throws Exception if any error occurs.
     */
    public Long getUserIdFromSSOCookie(HttpServletRequest request) throws Exception{
        // Get cookies from the request
        Cookie[] cookies = request.getCookies();

        // no cookies, return null
        if (cookies == null) {
            return null;
        }

        Cookie ssoCookie = CookieHelper.getCookie(cookies, getPropertyValue("SSO_COOKIE_KEY"));

        if (ssoCookie == null) {
            return null;
        }

        // Get auth cookie value
        String value = ssoCookie.getValue();

        if (value == null) {
            return null;
        }

        // Parse the cookie value
        String[] parts = value.split("\\|");

        if (parts.length != 2) {
            return null;
        }

        // Parse user ID from the cookie value
        long userId;

        try {
            userId = Long.parseLong(parts[0]);
        } catch (NumberFormatException e) {

            // return null if parsing error occurred
            return null;
        }

        String hashedValue = parts[1];

        // Get password from the result set and hash it.
        String realHashedValue = hashForUser(userId);

        if (realHashedValue.equals(hashedValue)) {
            return userId;
        }

        return null;
    }

    /**
     * Compute a one-way hash of a userid and the corresponding crypted
     * password, plus a magic string thrown in for good measure.  Salting
     * this might be nice, but it doesn't seem to buy us anything as long
     * as the magic string remains a secret.
     * <p/>
     * The intent here is that
     * 1) login cookies cannot be guessed
     * 2) changing your password should invalidate any login cookies which may exist
     * 3) login cookies cannot be used to gain any information about the password
     * 4) if user status changes, it invalidates login cookies
     * <p/>
     * I would just tack on the crypted password itself, but they are
     * reversibly encrypted with a secret key using Blowfish, and I don't
     * know how well Blowfish holds up to a chosen-plaintext attack.
     * <p/>
     *
     * @param uid the user id
     * @return the hash
     * @throws Exception if there is a problem getting data from the data base or if the SHA-256 algorithm doesn't exist
     */
    private String hashForUser(long uid) throws Exception {
        //log.debug("hash for user: " + uid);
        WebCommonServiceRpc webCommonServiceRpc = GrpcHelper.getWebCommonServiceRpc();
        GetUserPasswordResponse result = webCommonServiceRpc.getUserPassword(uid);

        String password = result.hasPassword() ? result.getPassword() : null;
        String status = result.hasStatus() ? result.getStatus() : null;


        String plainString = getPropertyValue("SSO_HASH_SECRET") + uid + password + status;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] plain = (plainString).getBytes();
        byte[] raw = md.digest(plain);
        StringBuffer hex = new StringBuffer();
        for (byte aRaw : raw) hex.append(Integer.toHexString(aRaw & 0xff));
        String hashString = hex.toString();

        return hashString;
    }
}