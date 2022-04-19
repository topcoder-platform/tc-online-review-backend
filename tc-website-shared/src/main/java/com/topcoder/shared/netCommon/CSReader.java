package com.topcoder.shared.netCommon;

import java.io.IOException;
import java.util.*;

public interface CSReader {
    void setMemoryUsageLimit(long var1);

    void resetMemoryUsage();

    byte readByte() throws IOException;

    short readShort() throws IOException;

    int readInt() throws IOException;

    long readLong() throws IOException;

    boolean readBoolean() throws IOException;

    double readDouble() throws IOException;

    String readString() throws IOException;

    String readUTF() throws IOException;

    byte[] readByteArray() throws IOException;

    char[] readCharArray() throws IOException;

    Object[] readObjectArray() throws IOException;

    Object[] readObjectArray(Class var1) throws IOException;

    Object[][] readObjectArrayArray(Class var1) throws IOException;

    Object[][] readObjectArrayArray() throws IOException;

    ArrayList readArrayList() throws IOException;

    List readList(List var1) throws IOException;

    Collection readCollection(Collection var1) throws IOException;

    HashMap readHashMap() throws IOException;

    Map readMap(Map var1) throws IOException;

    Object readObject() throws IOException;

    Class readClass() throws IOException;

    Object readEncrypt() throws IOException;
}
