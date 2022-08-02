package com.topcoder.onlinereview.component.webcommon.tag;

import javax.servlet.jsp.JspException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.component.util.CommonUtils.getString;

/**
 * Select tag for the ResultSetContainer
 *
 * @author djFD molc@mail.ru
 * @version 1.02
 */
public class RSCSelectTag extends SelectTag {
    private String fieldValue = null;
    private String fieldText = null;
    private List<Map<String, Object>> list = null;

    /**
     * Sets the fieldText.
     *
     * @param txt The fieldText to set
     */
    public void setFieldText(String txt) {
        fieldText = txt;
    }

    /**
     * Sets the fieldValue.
     *
     * @param val The fieldValue to set
     */
    public void setFieldValue(String val) {
        fieldValue = val;
    }

    /**
     * Sets the list.
     *
     * @param rsc The list to set
     */
    public void setList(List<Map<String, Object>> rsc) {
        list = rsc;
    }

    /**
     * @see SelectTag#getOptionText(Object)
     */
    protected String getOptionText(Object o) {
        return getString((Map<String, Object>)o, fieldText);
    }

    /**
     */
    protected String getOptionValue(Object o) {
        return getString((Map<String, Object>)o, fieldValue);
    }

    /**
     */
    protected Collection getSelectOptions() throws JspException {
        return list;
    }

    protected String getSelected() throws JspException {
        setSelectedValue(getDefaultValue() == null ? "" : getDefaultValue().toString());
        return super.getSelected();
    }

    protected void init() {
        super.init();
        this.fieldValue = null;
        this.fieldText = null;
        this.list = null;
    }

}
