package com.topcoder.onlinereview.component.webcommon.tag;

import jakarta.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.topcoder.onlinereview.component.webcommon.tag.BaseTag.ERRORS_KEY;

/**
 * @author djFD molc@mail.ru
 * @version 1.02
 */
public class ErrorIteratorTag extends IteratorTag {

    private String name = null;

    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        HashMap errors = (HashMap) pageContext.getRequest().getAttribute(ERRORS_KEY);
        setCollection(errors == null ? new ArrayList() : (List) errors.get(name));

        return super.doStartTag();
    }


    /**
     * Just in case the app server is caching tag (jboss!!!)
     * we have to clear out all the instance variables at the
     * end of execution.
     */
    public int doEndTag() throws JspException {
        release();
        return super.doEndTag();
    }

    public void release() {
        this.name = null;
        super.release();
    }


}
