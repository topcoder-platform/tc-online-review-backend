/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request.failuretests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.*;

/**
 * mock class only for testing purpose.
 * @author FireIce
 * @version 2.0
 */
public class MockServletRequest implements ServletRequest {

    /**
     * mock method.
     */
    public Object getAttribute(String arg0) {
        return null;
    }

    /**
     * mock method.
     */
    public Enumeration getAttributeNames() {
        return null;
    }

    /**
     * mock method.
     */
    public String getCharacterEncoding() {
        return null;
    }

    /**
     * mock method.
     */
    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
    }

    /**
     * mock method.
     */
    public int getContentLength() {
        return 0;
    }

    /**
     * mock method.
     */
    public String getContentType() {
        return null;
    }

    /**
     * mock method.
     */
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    /**
     * mock method.
     */
    public String getParameter(String arg0) {
        return null;
    }

    /**
     * mock method.
     */
    public Enumeration getParameterNames() {
        return null;
    }

    /**
     * mock method.
     */
    public String[] getParameterValues(String arg0) {
        return null;
    }

    /**
     * mock method.
     */
    public Map getParameterMap() {
        return null;
    }

    /**
     * mock method.
     */
    public String getProtocol() {
        return null;
    }

    /**
     * mock method.
     */
    public String getScheme() {
        return null;
    }

    /**
     * mock method.
     */
    public String getServerName() {
        return null;
    }

    /**
     * mock method.
     */
    public int getServerPort() {
        return 0;
    }

    /**
     * mock method.
     */
    public BufferedReader getReader() throws IOException {
        return null;
    }

    /**
     * mock method.
     */
    public String getRemoteAddr() {
        return null;
    }

    /**
     * mock method.
     */
    public String getRemoteHost() {
        return null;
    }

    /**
     * mock method.
     */
    public void setAttribute(String arg0, Object arg1) {
    }

    /**
     * mock method.
     */
    public void removeAttribute(String arg0) {

    }

    /**
     * mock method.
     */
    public Locale getLocale() {
        return null;
    }

    /**
     * mock method.
     */
    public Enumeration getLocales() {
        return null;
    }

    /**
     * mock method.
     */
    public boolean isSecure() {
        return false;
    }

    /**
     * mock method.
     */
    public RequestDispatcher getRequestDispatcher(String arg0) {
        return null;
    }

    /**
     * mock method.
     */
    public String getRealPath(String arg0) {
        return null;
    }

    public int getRemotePort() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getLocalName() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getLocalAddr() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getLocalPort() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }

}
