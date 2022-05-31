package com.topcoder.onlinereview.component.webcommon.model.language;

import java.util.Arrays;
import java.util.List;

abstract class BaseElement implements Element
{
    protected static final String[] USER_ONLY_TAGS;
    protected static final List USER_ONLY_TAGS_LIST;
    private ElementRenderer renderer;

    public BaseElement() {
    }

    public void setRenderer(final ElementRenderer renderer) {
        this.renderer = renderer;
    }

    public ElementRenderer getRenderer() {
        return this.renderer;
    }

    static String encodeHTML(final String text) {
        return HTMLCharacterHandler.encodeSimple(text);
    }

    public static String decodeHTML(final String text) {
        return HTMLCharacterHandler.decode(text);
    }

    static {
        USER_ONLY_TAGS = new String[] { "ul", "ol", "li", "tt", "i", "b", "h1", "h2", "h3", "h4", "h5", "a", "img", "br", "sub", "sup", "p", "pre", "hr", "list", "type" };
        USER_ONLY_TAGS_LIST = Arrays.asList(BaseElement.USER_ONLY_TAGS);
    }
}
