// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.util.ArrayList;

public class ProblemComponent extends BaseElement
{
    static String SECTION_HEADER;
    static String CODE;
    public static int DEFAULT_MEM_LIMIT;
    public static int DEFAULT_EXECUTION_TIME_LIMIT;
    public static int DEFAULT_COMPILE_TIME_LIMIT;
    private boolean unsafe;
    private boolean valid;
    private ArrayList messages;
    private String name;
    private Element intro;
    private String className;
    private String exposedClassName;
    private String gccBuildCommand;
    private String cppApprovedPath;
    private String pythonCommand;
    private String pythonApprovedPath;
    private String[] methodNames;
    private DataType[] returnTypes;
    private DataType[][] paramTypes;
    private String[][] paramNames;
    private String[] exposedMethodNames;
    private DataType[] exposedReturnTypes;
    private DataType[][] exposedParamTypes;
    private String[][] exposedParamNames;
    private Element spec;
    private Element[] notes;
    private Constraint[] constraints;
    private TestCase[] testCases;
    private int componentTypeID;
    private int componentId;
    private int problemId;
    private String defaultSolution;
    private WebService[] webServices;
    private int memLimitMB;
    private int roundType;
    private ArrayList categories;
    private int codeLengthLimit;
    private int executionTimeLimit;
    private int compileTimeLimit;
    
    public ProblemComponent() {
        this.unsafe = true;
        this.valid = true;
        this.messages = new ArrayList();
        this.name = "";
        this.intro = new TextElement();
        this.className = "";
        this.exposedClassName = "";
        this.gccBuildCommand = "";
        this.cppApprovedPath = "";
        this.pythonCommand = "";
        this.pythonApprovedPath = "";
        this.methodNames = new String[0];
        this.returnTypes = new DataType[0];
        this.paramTypes = new DataType[0][0];
        this.paramNames = new String[0][0];
        this.exposedMethodNames = new String[0];
        this.exposedReturnTypes = new DataType[0];
        this.exposedParamTypes = new DataType[0][0];
        this.exposedParamNames = new String[0][0];
        this.spec = new TextElement();
        this.notes = new Element[0];
        this.constraints = new Constraint[0];
        this.testCases = new TestCase[0];
        this.componentTypeID = ProblemConstants.MAIN_COMPONENT;
        this.componentId = -1;
        this.problemId = -1;
        this.defaultSolution = "";
        this.webServices = new WebService[0];
        this.memLimitMB = ProblemComponent.DEFAULT_MEM_LIMIT;
        this.roundType = -1;
        this.categories = new ArrayList();
        this.codeLengthLimit = Integer.MAX_VALUE;
        this.executionTimeLimit = ProblemComponent.DEFAULT_EXECUTION_TIME_LIMIT;
        this.compileTimeLimit = ProblemComponent.DEFAULT_COMPILE_TIME_LIMIT;
    }
    
    public ProblemComponent(final boolean unsafe) {
        this.unsafe = true;
        this.valid = true;
        this.messages = new ArrayList();
        this.name = "";
        this.intro = new TextElement();
        this.className = "";
        this.exposedClassName = "";
        this.gccBuildCommand = "";
        this.cppApprovedPath = "";
        this.pythonCommand = "";
        this.pythonApprovedPath = "";
        this.methodNames = new String[0];
        this.returnTypes = new DataType[0];
        this.paramTypes = new DataType[0][0];
        this.paramNames = new String[0][0];
        this.exposedMethodNames = new String[0];
        this.exposedReturnTypes = new DataType[0];
        this.exposedParamTypes = new DataType[0][0];
        this.exposedParamNames = new String[0][0];
        this.spec = new TextElement();
        this.notes = new Element[0];
        this.constraints = new Constraint[0];
        this.testCases = new TestCase[0];
        this.componentTypeID = ProblemConstants.MAIN_COMPONENT;
        this.componentId = -1;
        this.problemId = -1;
        this.defaultSolution = "";
        this.webServices = new WebService[0];
        this.memLimitMB = ProblemComponent.DEFAULT_MEM_LIMIT;
        this.roundType = -1;
        this.categories = new ArrayList();
        this.codeLengthLimit = Integer.MAX_VALUE;
        this.executionTimeLimit = ProblemComponent.DEFAULT_EXECUTION_TIME_LIMIT;
        this.compileTimeLimit = ProblemComponent.DEFAULT_COMPILE_TIME_LIMIT;
        this.unsafe = unsafe;
    }
    
    public static String encodeXML(final String text) {
        final StringBuffer buf = new StringBuffer(text.length());
        final ArrayList bad = new ArrayList();
        for (int i = 0; i < ProblemConstants.BAD_XML_CHARS.length; ++i) {
            bad.add(new Character(ProblemConstants.BAD_XML_CHARS[i]));
        }
        for (int i = 0; i < text.length(); ++i) {
            if (bad.indexOf(new Character(text.charAt(i))) == -1) {
                buf.append(text.charAt(i));
            }
            else {
                buf.append("/ASCII" + (int)text.charAt(i) + "/");
            }
        }
        return buf.toString();
    }
    
    public static String decodeXML(String text) {
        final StringBuffer buf = new StringBuffer(text.length());
        while (text.length() > 0) {
            boolean appendChar = true;
            if (text.startsWith("/ASCII") && text.indexOf("/", 2) != -1) {
                try {
                    buf.append((char)Integer.parseInt(text.substring(6, text.indexOf("/", 2))));
                    appendChar = false;
                    text = text.substring(text.indexOf("/", 2) + 1);
                }
                catch (NumberFormatException ex) {}
            }
            if (appendChar) {
                buf.append(text.charAt(0));
                text = text.substring(1);
            }
        }
        return buf.toString();
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeBoolean(this.unsafe);
        writer.writeBoolean(this.valid);
        writer.writeArrayList(this.messages);
        writer.writeString(this.name);
        writer.writeObject(this.intro);
        writer.writeString(this.className);
        writer.writeString(this.exposedClassName);
        writer.writeObjectArray(this.methodNames);
        writer.writeObjectArray(this.returnTypes);
        writer.writeObjectArrayArray(this.paramTypes);
        writer.writeObjectArrayArray(this.paramNames);
        writer.writeObjectArray(this.exposedMethodNames);
        writer.writeObjectArray(this.exposedReturnTypes);
        writer.writeObjectArrayArray(this.exposedParamTypes);
        writer.writeObjectArrayArray(this.exposedParamNames);
        writer.writeObject(this.spec);
        writer.writeObjectArray(this.notes);
        writer.writeObjectArray(this.constraints);
        writer.writeObjectArray(this.testCases);
        writer.writeInt(this.componentTypeID);
        writer.writeInt(this.componentId);
        writer.writeInt(this.problemId);
        writer.writeString(this.defaultSolution);
        writer.writeObjectArray(this.webServices);
        writer.writeInt(this.memLimitMB);
        writer.writeInt(this.roundType);
        writer.writeArrayList(this.categories);
        writer.writeInt(this.codeLengthLimit);
        writer.writeInt(this.executionTimeLimit);
        writer.writeInt(this.compileTimeLimit);
        writer.writeString(this.gccBuildCommand);
        writer.writeString(this.cppApprovedPath);
        writer.writeString(this.pythonCommand);
        writer.writeString(this.pythonApprovedPath);
    }
    
    public void customReadObject(final CSReader reader) throws IOException {
        this.unsafe = reader.readBoolean();
        this.valid = reader.readBoolean();
        this.messages = reader.readArrayList();
        this.name = reader.readString();
        this.intro = (Element)reader.readObject();
        this.className = reader.readString();
        this.exposedClassName = reader.readString();
        this.methodNames = (String[])reader.readObjectArray(String.class);
        this.returnTypes = (DataType[])reader.readObjectArray(DataType.class);
        this.paramTypes = (DataType[][])reader.readObjectArrayArray(DataType.class);
        this.paramNames = (String[][])reader.readObjectArrayArray(String.class);
        this.exposedMethodNames = (String[])reader.readObjectArray(String.class);
        this.exposedReturnTypes = (DataType[])reader.readObjectArray(DataType.class);
        this.exposedParamTypes = (DataType[][])reader.readObjectArrayArray(DataType.class);
        this.exposedParamNames = (String[][])reader.readObjectArrayArray(String.class);
        this.spec = (Element)reader.readObject();
        this.notes = (Element[])reader.readObjectArray(Element.class);
        this.constraints = (Constraint[])reader.readObjectArray(Constraint.class);
        this.testCases = (TestCase[])reader.readObjectArray(TestCase.class);
        this.componentTypeID = reader.readInt();
        this.componentId = reader.readInt();
        this.problemId = reader.readInt();
        this.defaultSolution = reader.readString();
        this.webServices = (WebService[])reader.readObjectArray(WebService.class);
        this.memLimitMB = reader.readInt();
        this.roundType = reader.readInt();
        this.categories = reader.readArrayList();
        this.codeLengthLimit = reader.readInt();
        this.executionTimeLimit = reader.readInt();
        this.compileTimeLimit = reader.readInt();
        this.gccBuildCommand = reader.readString();
        this.cppApprovedPath = reader.readString();
        this.pythonCommand = reader.readString();
        this.pythonApprovedPath = reader.readString();
    }
    
    public boolean isUnsafe() {
        return this.unsafe;
    }
    
    public void setUnsafe(final boolean unsafe) {
        this.unsafe = unsafe;
    }
    
    public boolean isValid() {
        return this.valid;
    }
    
    public void setValid(final boolean valid) {
        this.valid = valid;
    }
    
    public ArrayList getMessages() {
        return this.messages;
    }
    
    public void setMessages(final ArrayList messages) {
        this.messages = messages;
    }
    
    public void clearMessages() {
        this.messages = new ArrayList();
    }
    
    public void addMessage(final ProblemMessage message) {
        if (message.getType() != ProblemMessage.WARNING) {
            this.valid = false;
        }
        this.messages.add(message);
    }
    
    public Element getIntro() {
        return this.intro;
    }
    
    public void setIntro(final Element intro) {
        this.intro = intro;
    }
    
    public Element getSpec() {
        return this.spec;
    }
    
    public void setSpec(final Element spec) {
        this.spec = spec;
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public String getExposedClassName() {
        return this.exposedClassName;
    }
    
    public void setClassName(final String className) {
        this.className = className;
    }
    
    public void setExposedClassName(final String className) {
        this.exposedClassName = className;
    }
    
    public String getMethodName() {
        return (this.methodNames.length > 0) ? this.methodNames[0] : "";
    }
    
    public String getMethodName(final int idx) {
        return (this.methodNames.length > idx) ? this.methodNames[idx] : "";
    }
    
    public String getExposedMethodName(final int idx) {
        return (this.exposedMethodNames.length > idx) ? this.exposedMethodNames[idx] : "";
    }
    
    public void setMethodName(final String methodName) {
        this.methodNames = new String[] { methodName };
    }
    
    public void setMethodNames(final String[] methodNames) {
        this.methodNames = methodNames;
    }
    
    public void setExposedMethodNames(final String[] methodNames) {
        this.exposedMethodNames = methodNames;
    }
    
    public DataType getReturnType() {
        return (this.returnTypes.length > 0) ? this.returnTypes[0] : new DataType();
    }
    
    public void setReturnType(final DataType returnType) {
        this.returnTypes = new DataType[] { returnType };
    }
    
    public void setReturnTypes(final DataType[] returnTypes) {
        this.returnTypes = returnTypes;
    }
    
    public void setExposedReturnType(final DataType returnType) {
        this.exposedReturnTypes = new DataType[] { returnType };
    }
    
    public void setExposedReturnTypes(final DataType[] returnTypes) {
        this.exposedReturnTypes = returnTypes;
    }
    
    public DataType[] getParamTypes() {
        return (this.paramTypes.length > 0) ? this.paramTypes[0] : new DataType[0];
    }
    
    public DataType[] getParamTypes(final int idx) {
        return (this.paramTypes.length > idx) ? this.paramTypes[idx] : new DataType[0];
    }
    
    public DataType[] getExposedParamTypes() {
        return (this.exposedParamTypes.length > 0) ? this.exposedParamTypes[0] : new DataType[0];
    }
    
    public DataType[] getExposedParamTypes(final int idx) {
        return (this.exposedParamTypes.length > idx) ? this.exposedParamTypes[idx] : new DataType[0];
    }
    
    public void setParamTypes(final DataType[] paramTypes) {
        this.paramTypes = new DataType[][] { paramTypes };
    }
    
    public void setParamTypes(final DataType[][] paramTypes) {
        this.paramTypes = paramTypes;
    }
    
    public void setExposedParamTypes(final DataType[] paramTypes) {
        this.exposedParamTypes = new DataType[][] { paramTypes };
    }
    
    public void setExposedParamTypes(final DataType[][] paramTypes) {
        this.exposedParamTypes = paramTypes;
    }
    
    public String[] getParamNames() {
        return (this.paramNames.length > 0) ? this.paramNames[0] : new String[0];
    }
    
    public String[] getParamNames(final int idx) {
        return (this.paramNames.length > idx) ? this.paramNames[idx] : new String[0];
    }
    
    public String[] getExposedParamNames() {
        return (this.exposedParamNames.length > 0) ? this.exposedParamNames[0] : new String[0];
    }
    
    public String[] getExposedParamNames(final int idx) {
        return (this.exposedParamNames.length > idx) ? this.exposedParamNames[idx] : new String[0];
    }
    
    public void setParamNames(final String[] paramNames) {
        this.paramNames = new String[][] { paramNames };
    }
    
    public void setParamNames(final String[][] paramNames) {
        this.paramNames = paramNames;
    }
    
    public void setExposedParamNames(final String[] paramNames) {
        this.exposedParamNames = new String[][] { paramNames };
    }
    
    public void setExposedParamNames(final String[][] paramNames) {
        this.exposedParamNames = paramNames;
    }
    
    public Element[] getNotes() {
        return this.notes;
    }
    
    public void setNotes(final Element[] notes) {
        this.notes = notes;
    }
    
    public Constraint[] getConstraints() {
        return this.constraints;
    }
    
    public void setConstraints(final Constraint[] constraints) {
        this.constraints = constraints;
    }
    
    public TestCase[] getTestCases() {
        return this.testCases;
    }
    
    public void setTestCases(final TestCase[] testCases) {
        this.testCases = testCases;
    }
    
    public void setWebServices(final WebService[] webServices) {
        this.webServices = webServices;
    }
    
    public WebService[] getWebServices() {
        return this.webServices;
    }
    
    public void setMemLimitMB(final int memLimitMB) {
        if (memLimitMB <= 0) {
            this.memLimitMB = ProblemComponent.DEFAULT_MEM_LIMIT;
        }
        else {
            this.memLimitMB = memLimitMB;
        }
    }
    
    public int getMemLimitMB() {
        return this.memLimitMB;
    }
    
    public int getRoundType() {
        return this.roundType;
    }
    
    public void setRoundType(final int roundType) {
        this.roundType = roundType;
    }
    
    public int getCodeLengthLimit() {
        return this.codeLengthLimit;
    }
    
    public void setCodeLengthLimit(final int codeLengthLimit) {
        this.codeLengthLimit = codeLengthLimit;
    }
    
    public int getExecutionTimeLimit() {
        return this.executionTimeLimit;
    }
    
    public void setExecutionTimeLimit(final int executionTimeLimit) {
        this.executionTimeLimit = executionTimeLimit;
    }
    
    public String getGccBuildCommand() {
        return this.gccBuildCommand;
    }
    
    public void setGccBuildCommand(final String gccBuildCommand) {
        this.gccBuildCommand = gccBuildCommand;
    }
    
    public String getCppApprovedPath() {
        return this.cppApprovedPath;
    }
    
    public void setCppApprovedPath(final String cppApprovedPath) {
        this.cppApprovedPath = cppApprovedPath;
    }
    
    public String getPythonCommand() {
        return this.pythonCommand;
    }
    
    public void setPythonCommand(final String pythonCommand) {
        this.pythonCommand = pythonCommand;
    }
    
    public String getPythonApprovedPath() {
        return this.pythonApprovedPath;
    }
    
    public void setPythonApprovedPath(final String pythonApprovedPath) {
        this.pythonApprovedPath = pythonApprovedPath;
    }
    
    public int getCompileTimeLimit() {
        return this.compileTimeLimit;
    }
    
    public void setCompileTimeLimit(final int compileTimeLimit) {
        this.compileTimeLimit = compileTimeLimit;
    }
    
    public static String handleTextElement(final String name, final Element elem) {
        if (elem instanceof TextElement) {
            return "<" + name + ">" + elem.toString() + "</" + name + ">";
        }
        return elem.toXML();
    }
    
    public String toXML() {
        final StringBuffer buf = new StringBuffer(4096);
        buf.append("<?xml version=\"1.0\"?>");
        buf.append("<problem");
        buf.append(" xmlns=\"http://topcoder.com\"");
        buf.append(" name=\"");
        buf.append(this.name);
        buf.append("\" code_length_limit=\"");
        buf.append(this.codeLengthLimit);
        buf.append("\" execution_time_limit=\"");
        buf.append(this.executionTimeLimit);
        buf.append("\" compile_time_limit=\"");
        buf.append(this.compileTimeLimit);
        buf.append("\" gcc_build_command=\"");
        buf.append(BaseElement.encodeHTML(this.gccBuildCommand));
        buf.append("\" cpp_approved_path=\"");
        buf.append(BaseElement.encodeHTML(this.cppApprovedPath));
        buf.append("\" python_command=\"");
        buf.append(BaseElement.encodeHTML(this.pythonCommand));
        buf.append("\" python_approved_path=\"");
        buf.append(BaseElement.encodeHTML(this.pythonApprovedPath));
        buf.append("\"><signature><class>");
        buf.append(this.className);
        buf.append("</class>");
        for (int i = 0; i < this.methodNames.length; ++i) {
            buf.append("<method><name>");
            buf.append(this.methodNames[i]);
            buf.append("</name><return>");
            buf.append(this.returnTypes[i].toXML());
            buf.append("</return><params>");
            for (int j = 0; j < this.paramTypes[i].length; ++j) {
                buf.append("<param>");
                buf.append(this.paramTypes[i][j].toXML());
                buf.append("<name>");
                buf.append(this.paramNames[i][j]);
                buf.append("</name></param>");
            }
            buf.append("</params></method>");
        }
        if (this.exposedClassName != null && !this.exposedClassName.equals("")) {
            buf.append("<exposed_class>");
            buf.append(this.exposedClassName);
            buf.append("</exposed_class>");
        }
        for (int i = 0; i < this.exposedMethodNames.length; ++i) {
            buf.append("<exposed_method><name>");
            buf.append(this.exposedMethodNames[i]);
            buf.append("</name><return>");
            buf.append(this.exposedReturnTypes[i].toXML());
            buf.append("</return><params>");
            for (int j = 0; j < this.exposedParamTypes[i].length; ++j) {
                buf.append("<param>");
                buf.append(this.exposedParamTypes[i][j].toXML());
                buf.append("<name>");
                buf.append(this.exposedParamNames[i][j]);
                buf.append("</name></param>");
            }
            buf.append("</params></exposed_method>");
        }
        buf.append("</signature>");
        if (this.intro != null) {
            buf.append(handleTextElement("intro", this.intro));
        }
        if (this.spec != null) {
            buf.append(handleTextElement("spec", this.spec));
        }
        buf.append("<notes>");
        for (int i = 0; i < this.notes.length; ++i) {
            buf.append(handleTextElement("note", this.notes[i]));
        }
        buf.append("</notes><constraints>");
        for (int i = 0; i < this.constraints.length; ++i) {
            buf.append(this.constraints[i].toXML());
        }
        buf.append("</constraints><test-cases>");
        for (int i = 0; i < this.testCases.length; ++i) {
            buf.append(this.testCases[i].toXML());
        }
        buf.append("</test-cases><memlimit>");
        buf.append(this.memLimitMB);
        buf.append("</memlimit><roundType>");
        buf.append(this.roundType);
        buf.append("</roundType></problem>");
        return buf.toString();
    }
    
    public String toString() {
        final StringBuffer str = new StringBuffer();
        str.append("com.topcoder.shared.problem.ProblemComponent[");
        str.append("unsafe=");
        str.append(this.unsafe);
        str.append(",codeLengthLimit=");
        str.append(this.codeLengthLimit);
        str.append(",executionTimeLimit=");
        str.append(this.executionTimeLimit);
        str.append(",compileTimeLimit=");
        str.append(this.compileTimeLimit);
        str.append(",gccBuildCommand=");
        str.append(this.gccBuildCommand);
        str.append(",cppApprovedPath=");
        str.append(this.cppApprovedPath);
        str.append(",pythonCommand=");
        str.append(this.pythonCommand);
        str.append(",pythonApprovedPath=");
        str.append(this.pythonApprovedPath);
        str.append(",valid=");
        str.append(this.valid);
        str.append(",messages=");
        str.append(this.messages);
        str.append(",name=");
        str.append(this.name);
        str.append(",intro=");
        str.append(this.intro);
        str.append(",className=");
        str.append(this.className);
        str.append(",methodNames=");
        str.append(this.methodNames);
        str.append(",returnTypes=");
        str.append(this.returnTypes);
        str.append(",paramTypes=");
        str.append(this.paramTypes);
        str.append(",paramNames=");
        str.append(this.paramNames);
        str.append(",exposedMethodNames=");
        str.append(this.exposedMethodNames);
        str.append(",exposedReturnTypes=");
        str.append(this.exposedReturnTypes);
        str.append(",exposedParamTypes=");
        str.append(this.exposedParamTypes);
        str.append(",exposedParamNames=");
        str.append(this.exposedParamNames);
        str.append(",memLimitMB=");
        str.append(this.memLimitMB);
        str.append(",roundType=");
        str.append(this.roundType);
        str.append(",spec=");
        str.append(this.spec);
        str.append(",notes=");
        str.append(this.notes);
        str.append(",constraints=");
        str.append(this.constraints);
        str.append(",testCases=");
        str.append(this.testCases);
        str.append("]");
        return str.toString();
    }
    
    public int getComponentTypeID() {
        return this.componentTypeID;
    }
    
    public void setComponentTypeID(final int componentTypeID) {
        this.componentTypeID = componentTypeID;
    }
    
    public final void setComponentId(final int componentId) {
        this.componentId = componentId;
    }
    
    public final void setDefaultSolution(final String solution) {
        this.defaultSolution = solution;
    }
    
    public final int getComponentId() {
        return this.componentId;
    }
    
    public final String getDefaultSolution() {
        return this.defaultSolution;
    }
    
    public int getProblemId() {
        return this.problemId;
    }
    
    public void setProblemId(final int problemId) {
        this.problemId = problemId;
    }
    
    public static String getCacheKey(final int componentID) {
        return "ProblemComponent." + componentID;
    }
    
    public final String getCacheKey() {
        return getCacheKey(this.componentId);
    }
    
    public String getReturnType(final int language) {
        return this.returnTypes[0].getDescriptor(language);
    }
    
    public String getResultType() {
        return this.returnTypes[0].getDescriptor(1);
    }
    
    public ArrayList getArgs() {
        final ArrayList ret = new ArrayList();
        for (int i = 0; i < this.paramTypes[0].length; ++i) {
            ret.add(this.paramTypes[0][i].getDescriptor(1));
        }
        return ret;
    }
    
    public DataType[] getAllReturnTypes() {
        return this.returnTypes;
    }
    
    public String[] getAllMethodNames() {
        return this.methodNames;
    }
    
    public DataType[][] getAllParamTypes() {
        return this.paramTypes;
    }
    
    public String[][] getAllParamNames() {
        return this.paramNames;
    }
    
    public DataType[] getAllExposedReturnTypes() {
        return this.exposedReturnTypes;
    }
    
    public String[] getAllExposedMethodNames() {
        return this.exposedMethodNames;
    }
    
    public DataType[][] getAllExposedParamTypes() {
        return this.exposedParamTypes;
    }
    
    public String[][] getAllExposedParamNames() {
        return this.exposedParamNames;
    }
    
    public void setCategories(final ArrayList categories) {
        this.categories = categories;
    }
    
    public ArrayList getCategories() {
        return this.categories;
    }
    
    static {
        ProblemComponent.SECTION_HEADER = "h3";
        ProblemComponent.CODE = "<code>";
        ProblemComponent.DEFAULT_MEM_LIMIT = 64;
        ProblemComponent.DEFAULT_EXECUTION_TIME_LIMIT = 840000;
        ProblemComponent.DEFAULT_COMPILE_TIME_LIMIT = 30000;
    }
}
