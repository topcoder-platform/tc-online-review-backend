package com.topcoder.onlinereview.component.webcommon.tag;

import jakarta.servlet.jsp.tagext.TagData;
import jakarta.servlet.jsp.tagext.TagExtraInfo;
import jakarta.servlet.jsp.tagext.VariableInfo;

public class ListIteratorTagInfo extends TagExtraInfo {

    public VariableInfo[] getVariableInfo(TagData data) {

        String type = (String) data.getAttribute("type");
        if (type == null || type.trim().equals(""))
            type = Object.class.getName();

        return new VariableInfo[]{
            new VariableInfo(data.getId(),
                    type,
                    true,
                    VariableInfo.NESTED)
        };
    }

}

