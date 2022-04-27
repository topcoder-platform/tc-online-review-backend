// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;
import com.topcoder.shared.netCommon.CustomSerializable;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class ComponentCategory implements Serializable, Cloneable, CustomSerializable
{
    private String name;
    private boolean checked;
    private int id;
    
    public ComponentCategory() {
    }
    
    public ComponentCategory(final String name, final boolean checked, final int id) {
        this.name = name;
        this.checked = checked;
        this.id = id;
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeString(this.name);
        writer.writeBoolean(this.checked);
        writer.writeInt(this.id);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.name = reader.readString();
        this.checked = reader.readBoolean();
        this.id = reader.readInt();
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean getChecked() {
        return this.checked;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setChecked(final boolean checked) {
        this.checked = checked;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
}
