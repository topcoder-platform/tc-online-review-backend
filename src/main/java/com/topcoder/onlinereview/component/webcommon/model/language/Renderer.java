package com.topcoder.onlinereview.component.webcommon.model.language;

public interface Renderer {
    String toHTML(final Language p0) throws Exception;

    String toPlainText(final Language p0) throws Exception;
}
