package com.topcoder.onlinereview.component.webcommon.tag;

public class NextPageTag extends PageTag {

    /**
     * Please change that number if you affect the fields in a way that will affect the
     * serialization for this object, i.e. when data members are changed.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected boolean displayLink() {
        return true;
    }

    @Override
    protected int getPageDelta() {
        return 1;
    }

    @Override
    protected String getDefaultText() {
        return "next  &gt;&gt;";
    }

}
