package com.topcoder.onlinereview.component.webcommon.tag;

import jakarta.servlet.jsp.tagext.TagData;
import jakarta.servlet.jsp.tagext.TagExtraInfo;
import jakarta.servlet.jsp.tagext.VariableInfo;

/**
 * User: dok
 * Date: Dec 20, 2004
 * Time: 10:57:25 AM
 */
public class UseBeanTagInfo extends TagExtraInfo {

    public VariableInfo[] getVariableInfo(TagData data) {

        String clazz = (String) data.getAttribute("type");

        return new VariableInfo[]{
            new VariableInfo(data.getAttributeString("id"),
                    clazz,
                    true,
                    VariableInfo.AT_END)
        };

    }

}
