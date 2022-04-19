// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

public class DataValueParseException extends Exception
{
    private String message;
    private int line;
    private int column;
    
    public DataValueParseException(final String message) {
        this(message, 0, 0);
    }
    
    public DataValueParseException(final String message, final int line, final int column) {
        this.message = message;
        this.line = line;
        this.column = column;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public int getLine() {
        return this.line;
    }
    
    public int getColumn() {
        return this.column;
    }
    
    public String toString() {
        String result = "";
        if (this.line != 0) {
            result = result + "Line " + this.line + ": ";
        }
        if (this.column != 0) {
            result = result + "Column " + this.column + ": ";
        }
        return result + this.message;
    }
}
