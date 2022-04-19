// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public class IntegralValue extends DataValue
{
    private long value;
    
    public IntegralValue() {
    }
    
    public IntegralValue(final long value) {
        this.value = value;
    }
    
    public void parse(final DataValueReader reader, final DataType type) throws IOException, DataValueParseException {
        reader.skipWhitespace();
        int i = reader.read();
        boolean valid = false;
        long sign = 1L;
        this.value = 0L;
        if ((char)i == '+' || (char)i == '-') {
            if ((char)i == '-') {
                sign = -1L;
            }
            reader.skipWhitespace();
            i = reader.read();
        }
        while (i != -1 && Character.isDigit((char)i)) {
            this.value = this.value * 10L + (i - 48);
            i = reader.read();
            valid = true;
        }
        reader.unread(i);
        if (!valid) {
            reader.expectedException("decimal digit", i);
        }
        this.value *= sign;
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeLong(this.value);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.value = reader.readLong();
    }
    
    public String encode() {
        return String.valueOf(this.value);
    }
    
    public Object getValue() {
        return new Long(this.value);
    }
}
