package com.topcoder.shared.netCommon;

import java.io.IOException;
import java.io.ObjectStreamException;

public interface CustomSerializable {
    void customWriteObject(CSWriter var1) throws IOException;

    void customReadObject(CSReader var1) throws IOException, ObjectStreamException;
}
