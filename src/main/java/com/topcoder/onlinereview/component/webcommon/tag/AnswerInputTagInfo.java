package com.topcoder.onlinereview.component.webcommon.tag;

import jakarta.servlet.jsp.tagext.TagData;
import jakarta.servlet.jsp.tagext.TagExtraInfo;
import jakarta.servlet.jsp.tagext.VariableInfo;


public class AnswerInputTagInfo
        extends TagExtraInfo {
    public VariableInfo[] getVariableInfo(TagData data) {
        VariableInfo[] variableInfo = new VariableInfo[]
        {
            new VariableInfo(data.getId(), String.class.getName(), true, VariableInfo.NESTED),
            new VariableInfo(AnswerInput.ANSWER_TEXT, String.class.getName(), true, VariableInfo.NESTED)
        };
        return variableInfo;
    }
}

