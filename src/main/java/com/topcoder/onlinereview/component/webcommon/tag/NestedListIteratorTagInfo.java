package com.topcoder.onlinereview.component.webcommon.tag;

import jakarta.servlet.jsp.tagext.TagData;
import jakarta.servlet.jsp.tagext.TagExtraInfo;
import jakarta.servlet.jsp.tagext.VariableInfo;

public class NestedListIteratorTagInfo extends TagExtraInfo {

    public VariableInfo[] getVariableInfo(TagData data) {
        return new VariableInfo[]{
            new VariableInfo(data.getId(),
                    "java.util.List",
                    true,
                    VariableInfo.NESTED)
        };
    }

}
