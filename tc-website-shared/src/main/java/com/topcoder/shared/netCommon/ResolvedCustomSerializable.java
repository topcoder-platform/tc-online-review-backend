package com.topcoder.shared.netCommon;

public interface ResolvedCustomSerializable extends CustomSerializable {
    Object readResolve();
}
