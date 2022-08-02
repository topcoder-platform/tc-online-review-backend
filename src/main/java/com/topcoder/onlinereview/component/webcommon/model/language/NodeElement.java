package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.HashMap;

public class NodeElement extends BaseElement
{
    private String name;
    private HashMap<String, String> attributes;
    private ArrayList children;
    private String text;

    public NodeElement() {
    }

    public NodeElement(final String name, final HashMap attributes, final ArrayList children, final String text) {
        this.name = name;
        this.attributes = attributes;
        this.children = children;
        this.text = text;
    }

    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeString(this.name);
        writer.writeHashMap(this.attributes);
        writer.writeArrayList(this.children);
        writer.writeString(this.text);
    }

    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.name = reader.readString();
        this.attributes = reader.readHashMap();
        this.children = reader.readArrayList();
        this.text = reader.readString();
    }

    public String getText() {
        return this.text;
    }

    public HashMap getAttributes() {
        return this.attributes;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList getChildren() {
        return this.children;
    }

    public String toXML() {
        final StringBuffer buf = new StringBuffer(64 * this.children.size());
        buf.append('<');
        buf.append(this.name);
        for (final String key : this.attributes.keySet()) {
            buf.append(' ');
            buf.append(key);
            buf.append("=\"");
            buf.append(BaseElement.encodeHTML(this.attributes.get(key)));
            buf.append('\"');
        }
        buf.append('>');
        for (int j = 0; j < this.children.size(); ++j) {
            final Element e = (Element)this.children.get(j);
            buf.append(e.toXML());
        }
        buf.append("</");
        buf.append(this.name);
        buf.append('>');
        return buf.toString();
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer(64 * this.children.size());
        final boolean print = NodeElement.USER_ONLY_TAGS_LIST.contains(this.name);
        if (print) {
            buf.append('<');
            buf.append(this.name);
            for (final String key : this.attributes.keySet()) {
                buf.append(' ');
                buf.append(key);
                buf.append("=\"");
                buf.append(BaseElement.encodeHTML(this.attributes.get(key)));
                buf.append('\"');
            }
            buf.append('>');
        }
        for (int j = 0; j < this.children.size(); ++j) {
            final Element e = (Element)this.children.get(j);
            buf.append(e.toString());
        }
        if (print) {
            buf.append("</");
            buf.append(this.name);
            buf.append('>');
        }
        return buf.toString();
    }
}

