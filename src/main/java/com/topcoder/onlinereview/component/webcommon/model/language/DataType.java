package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.HashMap;

public class DataType extends BaseElement
{
    private HashMap typeMapping;
    private int id;
    private String description;
    private String baseName;
    private int dim;

    public DataType() {
        this.typeMapping = new HashMap();
        this.id = -1;
        this.description = "";
    }

    public DataType(final String description) {
        this(-1, description, new HashMap());
    }

    public DataType(final int id, final String description) {
        this(id, description, new HashMap());
    }

    public DataType(final int id, final String description, final HashMap typeMapping) {
        this.typeMapping = new HashMap();
        this.id = -1;
        this.description = "";
        this.id = id;
        this.description = description;
        this.typeMapping = typeMapping;
        this.parseDescription();
        SimpleDataTypeFactory.registerDataType(this);
    }

    public void setTypeMapping(final HashMap typeMapping) {
        this.typeMapping = typeMapping;
    }

    public HashMap getTypeMapping() {
        return this.typeMapping;
    }

    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeInt(this.id);
        writer.writeString(this.description);
        writer.writeHashMap(this.typeMapping);
    }

    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.id = reader.readInt();
        this.description = reader.readString();
        this.typeMapping = reader.readHashMap();
        this.parseDescription();
        SimpleDataTypeFactory.registerDataType(this);
    }

    private void parseDescription() {
        int x = this.description.indexOf(91);
        if (x == -1) {
            this.baseName = this.description;
            this.dim = 0;
            return;
        }
        this.baseName = this.description.substring(0, x);
        this.dim = 0;
        while (x != -1) {
            ++this.dim;
            x = this.description.indexOf(91, x + 1);
        }
    }

    public String getDescription() {
        return this.description;
    }

    public String getDescriptor(final Language language) {
        final String desc = (String)this.typeMapping.get(new Integer(language.getId()));
        return (desc == null) ? this.getDescription() : desc;
    }

    public String getDescriptor(final int id) {
        final String desc = (String)this.typeMapping.get(new Integer(id));
        return (desc == null) ? this.getDescription() : desc;
    }

    public String getBaseName() {
        return this.baseName;
    }

    public int getDimension() {
        return this.dim;
    }

    public DataType reduceDimension() throws InvalidTypeException {
        final StringBuffer buf = new StringBuffer(this.description);
        final int i = this.description.indexOf("[]");
        if (i != -1) {
            buf.delete(i, i + 2);
            return SimpleDataTypeFactory.getDataType(buf.toString());
        }
        throw new InvalidTypeException("Attempt to reduce dimension of type " + this.description);
    }

    public String toXML() {
        return "<type>" + BaseElement.encodeHTML(this.description) + "</type>";
    }

    public boolean equals(final Object o) {
        return o != null && o instanceof DataType && this.description.equals(((DataType)o).getDescription());
    }

    DataType cloneDataType() {
        try {
            return (DataType)this.clone();
        }
        catch (Exception ex) {
            return null;
        }
    }

    public int getID() {
        return this.id;
    }

    public String toString() {
        return this.toXML();
    }
}
