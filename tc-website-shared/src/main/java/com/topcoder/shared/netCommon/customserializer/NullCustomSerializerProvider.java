package com.topcoder.shared.netCommon.customserializer;

public class NullCustomSerializerProvider implements CustomSerializerProvider {
    public NullCustomSerializerProvider() {
    }

    public boolean canHandle(Class clazz) {
        return false;
    }

    public CustomSerializer getSerializer(Class clazz) {
        return null;
    }
}