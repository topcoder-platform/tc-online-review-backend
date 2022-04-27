// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public class DecimalValue extends DataValue
{
    private double value;
    
    public DecimalValue() {
    }
    
    public DecimalValue(final double value) {
        this.value = value;
    }
    
    public void parse(final DataValueReader reader, final DataType type) throws IOException, DataValueParseException {
        reader.skipWhitespace();
        int i = reader.read();
        long sign = 1L;
        final StringBuffer sb = new StringBuffer();
        this.value = 0.0;
        if ((char)i == '+' || (char)i == '-') {
            if ((char)i == '-') {
                sign = -1L;
            }
            reader.skipWhitespace();
            i = reader.read();
        }
        for (int previousI = 0; i != -1 && (Character.isDigit((char)i) || i == 46 || i == 101 || i == 69 || ((previousI == 101 || previousI == 69) && (i == 45 || i == 43))); i = reader.read()) {
            previousI = i;
            sb.append((char)i);
        }
        reader.unread(i);
        try {
            this.value = Double.parseDouble(sb.toString());
        }
        catch (Exception e) {
            reader.exception("Invalid decimal format.");
        }
        this.value *= sign;
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeDouble(this.value);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.value = reader.readDouble();
    }
    
    public String encode() {
        return String.valueOf(this.value);
    }
    
    public Object getValue() {
        return new Double(this.value);
    }
}
