// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public class StringValue extends DataValue
{
    private String value;
    
    public StringValue() {
    }
    
    public StringValue(final String value) {
        this.value = value;
    }
    
    public void parse(final DataValueReader reader, final DataType type) throws IOException, DataValueParseException {
        reader.expect('\"', true);
        final StringBuffer buf = new StringBuffer(64);
        while (!reader.checkAhead('\"')) {
            final int i = reader.read(true);
            char c;
            if ((char)i == '\\') {
                c = (char)reader.read(true);
            }
            else {
                c = (char)i;
            }
            buf.append(c);
        }
        this.value = buf.toString();
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeString(this.value);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.value = reader.readString();
    }
    
    public String encode() {
        final StringBuffer buf = new StringBuffer(this.value.length() + 32);
        buf.append('\"');
        for (int i = 0; i < this.value.length(); ++i) {
            final char c = this.value.charAt(i);
            if (c == '\\' || c == '\"') {
                buf.append('\\');
            }
            buf.append(c);
        }
        buf.append('\"');
        return buf.toString();
    }
    
    public Object getValue() {
        return this.value;
    }
}
