package com.topcoder.onlinereview.component.webcommon.model.language;

public interface ResolvedCustomSerializable extends CustomSerializable {
    Object readResolve();
}
