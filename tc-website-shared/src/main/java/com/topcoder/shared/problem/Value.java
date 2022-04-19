// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public class Value extends BaseElement
{
    private String value;
    
    public Value(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeString(this.value);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.value = reader.readString();
    }
    
    public String toXML() {
        final StringBuffer sb = new StringBuffer();
        sb.append("<value value='");
        sb.append(BaseElement.encodeHTML(this.value.toString()));
        sb.append("'></value>");
        return sb.toString();
    }
}
