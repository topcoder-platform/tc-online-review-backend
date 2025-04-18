package com.topcoder.onlinereview.component.webcommon.tag;

import jakarta.servlet.jsp.JspException;
import java.util.Collection;
import java.util.List;

/**
 * @author lars
 */
public class ListSelectTag extends SelectTag {
    static public class Option {
        private String v, t;
        private boolean s;

        public Option(String val, String text) {
            this(val, text, false);
        }

        public Option(String val, String text, boolean selected) {
            v = val;
            t = text;
            s = selected;
        }
    }

    private List list = null;

    public void setList(List l) {
        list = l;
    }

    /**
     * @see SelectTag#getOptionText(Object)
     */
    protected String getOptionText(Object o) {
        Option t = (Option) o;
        if (t.s) {
            setSelectedText(t.t);
        }
        return t.t;
    }

    /**
     */
    protected String getOptionValue(Object o) {
        Option t = (Option) o;
        if (t.s) {
            setSelectedValue(t.v);
        }
        return t.v;
    }

    /**
     */
    protected Collection getSelectOptions() throws JspException {
        return list;
    }
}
