package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;
import java.io.ObjectStreamException;

public class TextElement extends BaseElement
{
    private String text;
    private boolean escapedText;

    public TextElement() {
        this.text = "";
        this.escapedText = false;
    }

    public TextElement(final String text) {
        this.text = "";
        this.escapedText = false;
        this.text = text;
    }

    public TextElement(final boolean escapedText, final String text) {
        this.text = "";
        this.escapedText = false;
        this.escapedText = escapedText;
        this.text = text;
    }

    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeString(this.text);
        writer.writeBoolean(this.escapedText);
    }

    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.text = reader.readString();
        this.escapedText = reader.readBoolean();
    }

    public String getEditableText() {
        return this.text;
    }

    public void setEditableText(final String text) {
        this.text = text;
    }

    public String toXML() {
        return BaseElement.encodeHTML(this.text);
    }

    public String toString() {
        if (this.escapedText) {
            return this.text;
        }
        return BaseElement.encodeHTML(this.text);
    }

    public boolean isEscapedText() {
        return this.escapedText;
    }

    public void setEscapedText(final boolean escapedText) {
        this.escapedText = escapedText;
    }
}
