// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public class CharacterValue extends DataValue
{
    private char value;
    
    public CharacterValue() {
    }
    
    public CharacterValue(final char value) {
        this.value = value;
    }
    
    public void parse(final DataValueReader reader, final DataType type) throws IOException, DataValueParseException {
        reader.expect('\'', true);
        final int i = reader.read(true);
        switch ((char)i) {
            case '\'': {
                reader.exception("Missing character");
            }
            case '\\': {
                this.value = (char)reader.read(true);
                break;
            }
            default: {
                this.value = (char)i;
                break;
            }
        }
        reader.expect('\'', true);
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeInt(this.value);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.value = (char)reader.readInt();
    }
    
    public String encode() {
        if (this.value == '\\' || this.value == '\'') {
            return "'\\" + this.value + "'";
        }
        return "'" + this.value + "'";
    }
    
    public char getChar() {
        return this.value;
    }
    
    public Object getValue() {
        return new Character(this.value);
    }
}
