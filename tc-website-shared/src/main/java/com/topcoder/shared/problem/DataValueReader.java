// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;

public class DataValueReader
{
    private PushbackReader reader;
    private int line;
    private int column;
    private int prevcolumn;
    private boolean eof;
    
    public DataValueReader(final String text) throws IOException {
        this(text, 1, 1);
    }
    
    public DataValueReader(final String text, final int line, final int column) throws IOException {
        this(new StringReader(text), line, column);
    }
    
    public DataValueReader(final Reader reader) throws IOException {
        this(reader, 1, 1);
    }
    
    public DataValueReader(final Reader reader, final int line, final int column) throws IOException {
        this.prevcolumn = -1;
        this.eof = false;
        this.reader = new PushbackReader(reader);
        this.line = line;
        this.column = column;
    }
    
    public void exception(final String message) throws DataValueParseException {
        throw new DataValueParseException(message, this.line, this.column);
    }
    
    public int read() throws IOException, DataValueParseException {
        return this.read(false);
    }
    
    void incrementColumn() {
        ++this.column;
    }
    
    void incrementLine() {
        ++this.line;
        this.prevcolumn = this.column;
        this.column = 1;
    }
    
    void decrementColumn() {
        if (this.column < 2) {
            this.column = this.prevcolumn;
            this.prevcolumn = -1;
        }
        else {
            --this.column;
        }
    }
    
    public int read(final boolean errorOnEOF) throws IOException, DataValueParseException {
        if (this.eof) {
            if (errorOnEOF) {
                this.exception("Unexpected EOF");
            }
            return -1;
        }
        final int i = this.reader.read();
        if (i == -1) {
            this.eof = true;
            if (errorOnEOF) {
                this.exception("Unexpected EOF");
            }
            this.incrementColumn();
            return -1;
        }
        if ((char)i == '\n') {
            this.incrementLine();
        }
        else {
            this.incrementColumn();
        }
        return i;
    }
    
    public void unread(final int c) throws IOException {
        this.decrementColumn();
        if (this.eof) {
            this.eof = false;
        }
        if (c != -1) {
            this.reader.unread(c);
        }
    }
    
    public void skipWhitespace() throws IOException, DataValueParseException {
        int i;
        for (i = this.read(); i != -1 && Character.isWhitespace((char)i); i = this.read()) {}
        this.unread(i);
    }
    
    void expect(final char c) throws IOException, DataValueParseException {
        this.expect(c, false);
    }
    
    void expect(final char c, final boolean whitespace) throws IOException, DataValueParseException {
        if (whitespace) {
            this.skipWhitespace();
        }
        final int i = this.read();
        if (i == -1) {
            this.unread(i);
            this.expectedException(c, "EOF");
        }
        if ((char)i != c) {
            this.unread(i);
            this.expectedException(c, i);
        }
    }
    
    void expectedException(final int x, final int y) throws DataValueParseException {
        if (x == -1) {
            this.expectedException("EOF", y);
        }
        if (y == -1) {
            this.expectedException(x, "EOF");
        }
        this.expectedException("``" + (char)x + "''", "``" + (char)y + "''");
    }
    
    void expectedException(final String x, final int y) throws DataValueParseException {
        if (y == -1) {
            this.expectedException(x, "EOF");
        }
        this.expectedException(x, "``" + (char)y + "''");
    }
    
    void expectedException(final int x, final String y) throws DataValueParseException {
        if (x == -1) {
            this.expectedException("EOF", y);
        }
        this.expectedException("``" + (char)x + "''", y);
    }
    
    void expectedException(final String x, final String y) throws DataValueParseException {
        this.exception("Expected " + x + ", got " + y);
    }
    
    boolean checkAhead(final char c) throws IOException, DataValueParseException {
        return this.checkAhead(c, false);
    }
    
    boolean checkAhead(final char c, final boolean whitespace) throws IOException, DataValueParseException {
        if (whitespace) {
            this.skipWhitespace();
        }
        final int i = this.read();
        if ((char)i == c) {
            return true;
        }
        this.unread(i);
        return false;
    }
}
