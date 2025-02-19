package com.topcoder.onlinereview.component.webcommon.tag;

import com.topcoder.onlinereview.component.webcommon.StringUtils;

import jakarta.servlet.jsp.JspException;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.component.util.CommonUtils.getString;

public class ResultSetItemTag extends FormatTag {

    private Map<String, Object> row;
    private List<Map<String, Object>> set;
    private String name;
    private int rowIndex = 0;   //default to the first row
    private boolean escapeHTML = true;  // default to true

    protected String getTimeZone() {
        Map<String, Object> row = this.row == null ? set.get(rowIndex) : this.row;
        return !row.containsKey(super.getTimeZone())?
                super.getTimeZone() : getString(row, super.getTimeZone());
    }

    public void setRow(Map<String, Object> row) {
        this.row = row;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSet(List<Map<String, Object>> set) {
        this.set = set;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setEscapeHtml(boolean escapeHTML) {
        this.escapeHTML = escapeHTML;
    }

    public int doStartTag() throws JspException {
        if (row == null) {
            if (escapeHTML) {
                if (set.get(rowIndex).containsKey(name) && set.get(rowIndex).get(name) instanceof String) {
                    setObject(StringUtils.htmlEncode(getString(set.get(rowIndex), name)));
                } else {
                    setObject(set.get(rowIndex).get(name));
                }
            } else {
                setObject(set.get(rowIndex).get(name));
            }
        } else {
            if (escapeHTML) {
                if (row.containsKey(name) && row.get(name) instanceof String) {
                    setObject(StringUtils.htmlEncode(getString(row, name)));
                } else {
                    setObject(row.get(name));
                }
            } else {
                setObject(row.get(name));
            }
        }
        return super.doStartTag();
    }

    /**
     * Just in case the app server is caching tag (jboss!!!)
     * we have to clear out all the instance variables at the
     * end of execution
     */
    public int doEndTag() throws JspException {
        this.row = null;
        this.set = null;
        this.name = null;
        this.rowIndex = 0;
        this.escapeHTML=true;
        return super.doEndTag();

    }

}

