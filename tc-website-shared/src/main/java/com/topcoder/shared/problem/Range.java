// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public class Range extends BaseElement
{
    private String min;
    private String max;
    
    public Range(final String min, final String max) {
        this.min = min;
        this.max = max;
    }
    
    public Range() {
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeString(this.min);
        writer.writeString(this.max);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.min = reader.readString();
        this.max = reader.readString();
    }
    
    public String getMin() {
        return this.min;
    }
    
    public String getMax() {
        return this.max;
    }
    
    public String toXML() {
        final StringBuffer sb = new StringBuffer();
        sb.append("<range min='");
        sb.append(BaseElement.encodeHTML(this.min.toString()));
        sb.append("' max='");
        sb.append(BaseElement.encodeHTML(this.max.toString()));
        sb.append("'></range>");
        return sb.toString();
    }
}
