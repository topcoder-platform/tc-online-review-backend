package com.topcoder.shared.netCommon.customserializer;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;

public interface CustomSerializer {
    Object readObject(CSReader var1) throws IOException;

    void writeObject(CSWriter var1, Object var2) throws IOException;
}
