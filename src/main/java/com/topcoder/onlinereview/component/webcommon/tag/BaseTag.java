package com.topcoder.onlinereview.component.webcommon.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import java.util.HashMap;


/**
 * My comments/description/notes go here
 *
 * @author djFD molc@mail.ru
 * @version 1.02
 */
public abstract class BaseTag extends BodyTagSupport {

    public static final String ERRORS_KEY = "processor_errors";
    public static final String DEFAULTS_KEY = "processor_defaults";

    protected String name;

    /**
     * Sets the name.
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    protected Object getDefaultValue() {
        try {
            HashMap defaults = (HashMap) pageContext.getRequest().getAttribute(DEFAULTS_KEY);
            return defaults.get(name);
        } catch (Exception e) {
            return null;
        }
    }

    public int doEndTag() throws JspException {
        this.name = null;
        this.id = null;//declared in super class
        init();
        return super.doEndTag();
    }

    /**
     * Just in case the app server is caching tag (jboss!!!)
     * we have to clear out all the instance variables at the
     * end of execution
     */
    protected abstract void init();


}
