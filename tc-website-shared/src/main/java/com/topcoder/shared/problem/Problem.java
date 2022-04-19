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

public class Problem extends BaseElement implements Element, Serializable, CustomSerializable
{
    public static final int TYPE_SINGLE = 1;
    public static final int TYPE_TEAM = 2;
    public static final int TYPE_LONG = 3;
    private ProblemComponent[] problemComponents;
    private int problemId;
    private String name;
    private int problemTypeID;
    private String problemText;
    private WebService[] webServices;
    
    public final String getCacheKey() {
        return "Problem." + this.problemId;
    }
    
    public Problem() {
        this.problemComponents = new ProblemComponent[0];
        this.problemId = -1;
        this.name = "";
        this.problemTypeID = -1;
        this.problemText = "";
        this.webServices = new WebService[0];
    }
    
    public static String getCacheKey(final int problemid) {
        return "Problem." + problemid;
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeInt(this.problemComponents.length);
        for (int i = 0; i < this.problemComponents.length; ++i) {
            writer.writeObject(this.problemComponents[i]);
        }
        writer.writeInt(this.problemId);
        writer.writeString(this.name);
        writer.writeInt(this.problemTypeID);
        writer.writeString(this.problemText);
        writer.writeObjectArray(this.webServices);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        final int count = reader.readInt();
        this.problemComponents = new ProblemComponent[count];
        for (int i = 0; i < count; ++i) {
            this.problemComponents[i] = (ProblemComponent)reader.readObject();
        }
        this.problemId = reader.readInt();
        this.name = reader.readString();
        this.problemTypeID = reader.readInt();
        this.problemText = reader.readString();
        Object[] o_webServices = reader.readObjectArray();
        if (o_webServices == null) {
            o_webServices = new Object[0];
        }
        this.webServices = new WebService[o_webServices.length];
        for (int j = 0; j < o_webServices.length; ++j) {
            this.webServices[j] = (WebService)o_webServices[j];
        }
    }
    
    public void setProblemId(final int problemId) {
        this.problemId = problemId;
    }
    
    public int getProblemId() {
        return this.problemId;
    }
    
    public Object clone() {
        final Problem p = new Problem();
        p.setProblemId(this.problemId);
        p.setProblemText(this.problemText);
        p.setProblemComponents(this.problemComponents);
        return p;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getProblemText() {
        return this.problemText;
    }
    
    public void setProblemText(final String problemText) {
        this.problemText = problemText;
    }
    
    public ProblemComponent[] getProblemComponents() {
        return this.problemComponents;
    }
    
    public void setProblemComponents(final ProblemComponent[] problemComponents) {
        this.problemComponents = problemComponents;
    }
    
    public ProblemComponent getPrimaryComponent() {
        for (int i = 0; i < this.problemComponents.length; ++i) {
            if (this.problemComponents[i].getComponentTypeID() == ProblemConstants.MAIN_COMPONENT) {
                return this.problemComponents[i];
            }
        }
        return null;
    }
    
    public int getProblemTypeID() {
        return this.problemTypeID;
    }
    
    public void setProblemTypeID(final int problemTypeID) {
        this.problemTypeID = problemTypeID;
    }
    
    public ProblemComponent getComponent(final int i) {
        if (i >= this.problemComponents.length) {
            return null;
        }
        return this.problemComponents[i];
    }
    
    public String toString() {
        final StringBuffer ret = new StringBuffer(1000);
        ret.append("(com.topcoder.shared.problem.Problem) [");
        ret.append("problemComponents = ");
        if (this.problemComponents == null) {
            ret.append("null");
        }
        else {
            ret.append("{");
            for (int i = 0; i < this.problemComponents.length; ++i) {
                ret.append(this.problemComponents[i].toString() + ",");
            }
            ret.append("}");
        }
        ret.append(", ");
        ret.append("problemId = ");
        ret.append(this.problemId);
        ret.append(", ");
        ret.append("name = ");
        if (this.name == null) {
            ret.append("null");
        }
        else {
            ret.append(this.name.toString());
        }
        ret.append(", ");
        ret.append("problemTypeID = ");
        ret.append(this.problemTypeID);
        ret.append(", ");
        ret.append("problemText = ");
        if (this.problemText == null) {
            ret.append("null");
        }
        else {
            ret.append(this.problemText.toString());
        }
        ret.append(", ");
        ret.append("]");
        return ret.toString();
    }
    
    public boolean isValid() {
        boolean valid = true;
        for (int i = 0; i < this.problemComponents.length; ++i) {
            valid = (valid && this.problemComponents[i].isValid());
        }
        return valid;
    }
    
    public void setWebServices(final WebService[] webServices) {
        this.webServices = webServices;
    }
    
    public WebService[] getWebServices() {
        return this.webServices;
    }
    
    public String toXML() {
        final StringBuffer xml = new StringBuffer();
        return xml.toString();
    }
}
