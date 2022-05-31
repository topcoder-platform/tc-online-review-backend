package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.IOException;
import java.io.ObjectStreamException;

public interface CustomSerializable {
  void customWriteObject(CSWriter var1) throws IOException;

  void customReadObject(CSReader var1) throws IOException, ObjectStreamException;
}
