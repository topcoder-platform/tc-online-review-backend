package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;

public class WebService extends BaseElement
{
    private String name;
    private String javaDocAddress;
    private int webServiceId;
    private int problemId;

    public WebService() {
        this.name = "";
        this.javaDocAddress = "";
        this.webServiceId = -1;
        this.problemId = -1;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setJavaDocAddress(final String javaDocAddress) {
        this.javaDocAddress = javaDocAddress;
    }

    public String getJavaDocAddress() {
        return this.javaDocAddress;
    }

    public void setWebServiceId(final int webServiceId) {
        this.webServiceId = webServiceId;
    }

    public int getWebServiceId() {
        return this.webServiceId;
    }

    public void setProblemId(final int problemId) {
        this.problemId = problemId;
    }

    public int getProblemId() {
        return this.problemId;
    }

    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeString(this.name);
        writer.writeString(this.javaDocAddress);
        writer.writeInt(this.webServiceId);
        writer.writeInt(this.problemId);
    }

    public void customReadObject(final CSReader reader) throws IOException {
        this.name = reader.readString();
        this.javaDocAddress = reader.readString();
        this.webServiceId = reader.readInt();
        this.problemId = reader.readInt();
    }

    public String toXML() {
        final StringBuffer xml = new StringBuffer();
        xml.append("<web-service>");
        xml.append("<name>");
        xml.append(this.name);
        xml.append("</name>");
        xml.append("</web-service>");
        return xml.toString();
    }
}
