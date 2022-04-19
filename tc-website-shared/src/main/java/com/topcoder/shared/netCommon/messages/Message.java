package com.topcoder.shared.netCommon.messages;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;
import com.topcoder.shared.netCommon.CustomSerializable;

import java.io.IOException;
import java.io.Serializable;

public abstract class Message implements Serializable, Cloneable, CustomSerializable {
    public Message() {
    }

    public void customWriteObject(CSWriter writer) throws IOException {
    }

    public void customReadObject(CSReader reader) throws IOException {
    }

    public String toString() {
        return "(Message)[]";
    }
}
