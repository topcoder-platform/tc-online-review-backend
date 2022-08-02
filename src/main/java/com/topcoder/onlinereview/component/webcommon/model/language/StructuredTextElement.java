package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;
import java.io.ObjectStreamException;

public class StructuredTextElement extends BaseElement
{
    private String name;
    private String innerXMLText;

    public StructuredTextElement() {
        this.name = "";
        this.innerXMLText = "";
    }

    public StructuredTextElement(final String name, final String text) {
        this.name = "";
        this.innerXMLText = "";
        this.name = name;
        this.innerXMLText = text;
    }

    public String toXML() {
        final StringBuffer sb = new StringBuffer(20);
        sb.append("<").append(this.name).append(" escaped=\"1\">");
        int pos = this.innerXMLText.indexOf(38);
        if (pos > -1) {
            int lstPos;
            for (lstPos = 0; pos > -1; lstPos = ++pos, pos = this.innerXMLText.indexOf(38, lstPos)) {
                sb.append(this.innerXMLText.substring(lstPos, pos));
                sb.append("&amp;");
            }
            sb.append(this.innerXMLText.substring(lstPos));
        }
        else {
            sb.append(this.innerXMLText);
        }
        sb.append("</").append(this.name).append(">");
        return sb.toString();
    }

    public String toString() {
        return this.innerXMLText;
    }

    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeString(this.name);
        writer.writeString(this.innerXMLText);
    }

    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.name = reader.readString();
        this.innerXMLText = reader.readString();
    }

    public String getInnerXmlText() {
        return this.innerXMLText;
    }
}

