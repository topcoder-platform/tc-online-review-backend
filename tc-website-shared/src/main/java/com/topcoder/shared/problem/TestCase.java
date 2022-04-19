// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public class TestCase extends BaseElement
{
    public static final String UNKNOWN_OUTPUT = "UNKNOWN-OUTPUT10291821323";
    public static final String ERROR = "ERROR-GENERATING-OUTPUT10291821323";
    private String[] input;
    private String output;
    private Element annotation;
    private boolean example;
    private boolean systemTest;
    private Integer id;
    
    public TestCase() {
    }
    
    public TestCase(final Integer id, final String[] input, final String output, final boolean example) {
        this(id, input, output, null, example);
    }
    
    public TestCase(final Integer id, final String[] input, final String output, final Element annotation, final boolean example) {
        this(id, input, output, annotation, example, false);
    }
    
    public TestCase(final Integer id, final String[] input, final String output, final Element annotation, final boolean example, final boolean systemTest) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.annotation = annotation;
        this.example = example;
        this.systemTest = systemTest;
        this.output = ProblemComponent.decodeXML(this.output);
        for (int i = 0; i < this.input.length; ++i) {
            this.input[i] = ProblemComponent.decodeXML(this.input[i]);
        }
    }
    
    public TestCase(final Integer id, final String[] input, final Element annotation, final boolean example) {
        this(id, input, "UNKNOWN-OUTPUT10291821323", annotation, example);
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeObject(this.id);
        writer.writeObjectArray(this.input);
        writer.writeString(this.output);
        writer.writeObject(this.annotation);
        writer.writeBoolean(this.example);
        writer.writeBoolean(this.systemTest);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.id = (Integer)reader.readObject();
        final Object[] o_input = reader.readObjectArray();
        this.output = reader.readString();
        this.annotation = (Element)reader.readObject();
        this.example = reader.readBoolean();
        this.systemTest = reader.readBoolean();
        this.input = new String[o_input.length];
        for (int i = 0; i < o_input.length; ++i) {
            this.input[i] = (String)o_input[i];
        }
    }
    
    public boolean isExample() {
        return this.example;
    }
    
    public void setExample(final boolean example) {
        this.example = example;
    }
    
    public String[] getInput() {
        return this.input;
    }
    
    public void setOutput(final String output) {
        this.output = output;
    }
    
    public String getOutput() {
        return this.output;
    }
    
    public Element getAnnotation() {
        return this.annotation;
    }
    
    public boolean isSystemTest() {
        return this.systemTest;
    }
    
    public void setSystemTest(final boolean systemTest) {
        this.systemTest = systemTest;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String toXML() {
        final StringBuffer buf = new StringBuffer(256);
        buf.append("<test-case");
        if (this.id != null) {
            buf.append(" id=\"");
            buf.append(this.id);
            buf.append('\"');
        }
        if (this.example) {
            buf.append(" example=\"1\"");
        }
        if (this.systemTest) {
            buf.append(" systemTest=\"1\"");
        }
        buf.append('>');
        for (int i = 0; i < this.input.length; ++i) {
            buf.append("<input>");
            buf.append(ProblemComponent.encodeXML(this.input[i]));
            buf.append("</input>");
        }
        buf.append("<output>");
        buf.append(ProblemComponent.encodeXML(this.output));
        buf.append("</output>");
        if (this.annotation != null) {
            buf.append(ProblemComponent.handleTextElement("annotation", this.annotation));
        }
        buf.append("</test-case>");
        return buf.toString();
    }
    
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof TestCase)) {
            return false;
        }
        final TestCase t = (TestCase)obj;
        return this.toXML().equals(t.toXML());
    }
}
