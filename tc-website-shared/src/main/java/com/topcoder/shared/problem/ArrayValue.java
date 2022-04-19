// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.ArrayList;

public class ArrayValue extends DataValue
{
    private ArrayList values;
    
    public ArrayValue() {
    }
    
    public ArrayValue(final ArrayList values) {
        this.values = values;
    }
    
    public ArrayValue(final DataValueReader reader, final DataType type) throws IOException, DataValueParseException {
        this.parse(reader, type);
    }
    
    public void parse(final DataValueReader reader, final DataType type) throws IOException, DataValueParseException {
        try {
            reader.expect('{', true);
            final DataType subtype = type.reduceDimension();
            this.values = new ArrayList();
            if (reader.checkAhead('}', true)) {
                return;
            }
            do {
                this.values.add(DataValue.parseValue(reader, subtype));
            } while (reader.checkAhead(',', true));
            reader.expect('}', true);
        }
        catch (InvalidTypeException ex) {
            reader.exception("Invalid array type: " + type.getDescription());
        }
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeArrayList(this.values);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.values = reader.readArrayList();
    }
    
    public String encode() {
        final String[] vals = new String[this.values.size()];
        int len = 2;
        for (int i = 0; i < vals.length; ++i) {
            vals[i] = this.values.get(i).toString();
            len += vals[i].length() + 2;
        }
        final StringBuffer buf = new StringBuffer(len);
        buf.append('{');
        for (int j = 0; j < vals.length; ++j) {
            if (j > 0) {
                buf.append(", ");
            }
            buf.append(vals[j]);
        }
        buf.append(" }");
        return buf.toString();
    }
    
    public Object getValue() {
        return this.values.toArray();
    }
}
