package com.topcoder.shared.netCommon.customserializer;

public interface CustomSerializerProvider {
    boolean canHandle(Class var1);

    CustomSerializer getSerializer(Class var1);
}
