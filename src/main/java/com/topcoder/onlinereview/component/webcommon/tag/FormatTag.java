package com.topcoder.onlinereview.component.webcommon.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;


public class FormatTag extends TagSupport {
    private Object object = null;
    private String format = null;
    private String ifNull = "";
    private String timeZone = null;

    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    protected String getTimeZone() {
        return timeZone;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setIfNull(String ifNull) {
        this.ifNull = ifNull;
    }

    /**
     * Just in case the app server is caching tag (jboss!!!)
     * we have to clear out all the instance variables at the
     * end of execution
     */
    public int doEndTag() throws JspException {
        this.object = null;
        this.format = null;
        this.ifNull = "";
        this.timeZone = null;
        return super.doEndTag();
    }

}
