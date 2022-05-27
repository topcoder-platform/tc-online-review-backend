// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;
import java.io.ObjectStreamException;

public abstract class BaseLanguage implements Language, ResolvedCustomSerializable
{
    private int id;
    private transient String name;

    public BaseLanguage() {
    }

    protected BaseLanguage(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(final Object o) {
        return o != null && o instanceof Language && ((Language)o).getId() == this.id;
    }

    public int hashCode() {
        return this.id;
    }

    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeInt(this.id);
    }

    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.id = reader.readInt();
    }

    public Object readResolve() {
        return getLanguage(this.id);
    }

    public static Language getLanguage(final int typeID) {
        switch (typeID) {
            case 1: {
                return JavaLanguage.JAVA_LANGUAGE;
            }
            case 3: {
                return CPPLanguage.CPP_LANGUAGE;
            }
            case 4: {
                return CSharpLanguage.CSHARP_LANGUAGE;
            }
            case 5: {
                return VBLanguage.VB_LANGUAGE;
            }
            case 6: {
                return PythonLanguage.PYTHON_LANGUAGE;
            }
            case 7: {
                return RLanguage.R_LANGUAGE;
            }
            default: {
                throw new IllegalArgumentException("Invalid language: " + typeID);
            }
        }
    }
}
