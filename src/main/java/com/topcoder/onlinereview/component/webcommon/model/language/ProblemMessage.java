package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class ProblemMessage implements Serializable, Cloneable, CustomSerializable
{
    public static short WARNING;
    public static short ERROR;
    public static short FATAL_ERROR;
    private int type;
    private int line;
    private int column;
    private String message;

    public ProblemMessage() {
    }

    public ProblemMessage(final int type, String message, final int line, final int column) {
        if (message == null) {
            message = "";
        }
        this.type = type;
        this.message = message;
        this.line = line;
        this.column = column;
    }

    public ProblemMessage(final int type, String message) {
        if (message == null) {
            message = "";
        }
        this.type = type;
        this.message = message;
        final int n = 0;
        this.column = n;
        this.line = n;
    }

    public int getType() {
        return this.type;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public String getMessage() {
        return this.message;
    }

    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeInt(this.type);
        writer.writeInt(this.line);
        writer.writeInt(this.column);
        writer.writeString(this.message);
    }

    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.type = reader.readInt();
        this.line = reader.readInt();
        this.column = reader.readInt();
        this.message = reader.readString();
    }

    public void log(final Object trace) {
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer(this.message.length() + 32);
        if (this.line != 0) {
            buf.append("Line ");
            buf.append(this.line);
            buf.append(": ");
        }
        if (this.column != 0) {
            buf.append("Column ");
            buf.append(this.column);
            buf.append(": ");
        }
        buf.append(this.message);
        return buf.toString();
    }

    static {
        ProblemMessage.WARNING = 0;
        ProblemMessage.ERROR = 1;
        ProblemMessage.FATAL_ERROR = 2;
    }
}

