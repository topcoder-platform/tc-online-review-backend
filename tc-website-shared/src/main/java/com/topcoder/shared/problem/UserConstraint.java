// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public class UserConstraint extends Constraint
{
    private Element elem;
    
    public UserConstraint() {
    }
    
    public UserConstraint(final String text) {
        super("");
        this.elem = new StructuredTextElement("user-constraint", text);
    }
    
    public UserConstraint(final Element elem) {
        super("");
        this.elem = elem;
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        super.customWriteObject(writer);
        writer.writeObject(this.elem);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        super.customReadObject(reader);
        this.elem = (Element)reader.readObject();
    }
    
    public String toXML() {
        return this.elem.toXML();
    }
    
    public Element getUserConstraint() {
        return this.elem;
    }
    
    public String getText() {
        return this.elem.toString();
    }
}
