package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public interface Element extends Serializable, Cloneable, CustomSerializable
{
    void customWriteObject(final CSWriter p0) throws IOException;

    void customReadObject(final CSReader p0) throws IOException, ObjectStreamException;

    String toXML();

    void setRenderer(final ElementRenderer p0);

    ElementRenderer getRenderer();
}
