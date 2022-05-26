package com.topcoder.onlinereview.component.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class BaseEJB implements SessionBean {
    private SessionContext sctx;
    private static Logger log = LoggerFactory.getLogger(BaseEJB.class);

    //required ejb methods
    public void ejbActivate() {
    }

    /**
     *
     */
    public void ejbPassivate() {
    }

    /**
     *
     */
    public void ejbCreate() {
        //InitContext = new InitialContext(); // from BaseEJB
    }

    /**
     *
     */
    public void ejbRemove() {
    }

    /**
     *
     *
     */
    public void setSessionContext(SessionContext ctx) {
        this.sctx = ctx;
    }

    /**
     * Use this to check the length of a parameter against a defined maximum.
     * For example, compare a submitted username to the length of the username
     * column in the db.  Throws GeneralSecurityException if param is too long.
     *
     * @param param
     * @param maxLength
     */
    protected void checkLength(String param, int maxLength)
            throws GeneralSecurityException {

        if (param.length() > maxLength) {
            throw new GeneralSecurityException("Parameter <" + param + "> is too long.  Should be <= " + maxLength);
        }
    }


}
