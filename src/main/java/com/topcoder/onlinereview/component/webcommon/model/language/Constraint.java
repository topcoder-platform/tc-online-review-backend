package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;
import java.io.ObjectStreamException;

public abstract class Constraint extends BaseElement
{
    protected String param;

    public Constraint() {
    }

    public Constraint(final String param) {
        this.param = param;
    }

    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeString(this.param);
    }

    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.param = reader.readString();
    }
}
