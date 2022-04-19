package com.topcoder.shared.netCommon.customserializer;

import java.util.HashMap;
import java.util.Map;

public class SimpleCustomSerializerProvider implements CustomSerializerProvider {
    private Map clazzMap = new HashMap(50);

    public SimpleCustomSerializerProvider() {
    }

    public boolean canHandle(Class clazz) {
        return this.clazzMap.containsKey(clazz);
    }

    public CustomSerializer getSerializer(Class clazz) {
        return (CustomSerializer) this.clazzMap.get(clazz);
    }

    public void registerSerializer(Class clazz, CustomSerializer serializer) {
        this.clazzMap.put(clazz, serializer);
    }

    public void unregisterSerializer(Class clazz, CustomSerializer serializer) {
        this.clazzMap.remove(clazz);
    }
}
