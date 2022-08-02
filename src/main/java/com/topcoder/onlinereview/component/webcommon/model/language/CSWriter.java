package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CSWriter {
    void writeByte(byte var1) throws IOException;

    void writeShort(short var1) throws IOException;

    void writeInt(int var1) throws IOException;

    void writeLong(long var1) throws IOException;

    void writeBoolean(boolean var1) throws IOException;

    void writeDouble(double var1) throws IOException;

    void writeString(String var1) throws IOException;

    void writeUTF(String var1) throws IOException;

    void writeByteArray(byte[] var1) throws IOException;

    void writeCharArray(char[] var1) throws IOException;

    void writeObjectArray(Object[] var1) throws IOException;

    void writeObjectArrayArray(Object[][] var1) throws IOException;

    void writeArrayList(ArrayList var1) throws IOException;

    void writeList(List var1) throws IOException;

    void writeCollection(Collection var1) throws IOException;

    void writeHashMap(HashMap var1) throws IOException;

    void writeMap(Map var1) throws IOException;

    void writeObject(Object var1) throws IOException;

    void writeClass(Class var1) throws IOException;

    void writeEncrypt(Object var1) throws IOException;
}
